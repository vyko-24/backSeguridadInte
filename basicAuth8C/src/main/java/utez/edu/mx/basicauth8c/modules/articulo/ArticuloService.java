package utez.edu.mx.basicauth8c.modules.articulo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import utez.edu.mx.basicauth8c.kernel.CustomResponse;
import utez.edu.mx.basicauth8c.modules.almacen.Almacen;
import utez.edu.mx.basicauth8c.modules.almacen.AlmacenRepository;
import utez.edu.mx.basicauth8c.modules.bitacora.BitacoraService;
import utez.edu.mx.basicauth8c.modules.user.User;
import utez.edu.mx.basicauth8c.modules.user.UserRepository;
import utez.edu.mx.basicauth8c.modules.user.UserService;

import java.util.Optional;

@Service
public class ArticuloService {
    @Autowired
    private ArticuloRepository repository;

    @Autowired
    private CustomResponse customResponse;

    @Autowired
    private AlmacenRepository almacenRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BitacoraService bitacoraService;

    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<?> getAll(){
        bitacoraService.registrarBitacora("GET", "articulo", null, null);
        return customResponse.getJSONResponse(repository.findAll());
    }

    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<?> getByAlmacen(Long id){
        Optional<Almacen> almacen = almacenRepository.findById(id);
        if(almacen.isEmpty())
            return customResponse.getBadRequest("Almacen no encontrado");
        Almacen almacenAux = almacen.get();
        bitacoraService.registrarBitacora("GET", "articulo", null, null);
        return customResponse.getJSONResponse(repository.findByAlmacenes(almacenAux));
    }

    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<?> getById(Long id){
        if(!repository.existsById(id))
            return customResponse.getBadRequest("Articulo no encontrado");
        bitacoraService.registrarBitacora("GET", "articulo", null, null);
        return customResponse.getJSONResponse(repository.findById(id));
    }

    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<?> save(Articulo articulo){
        if (repository.findByNombre(articulo.getNombre()).isPresent())
            return customResponse.getBadRequest("Articulo ya registrado");
        bitacoraService.registrarBitacora("POST", "articulo", null, articulo);
        Optional<Almacen> almacen = almacenRepository.findById(articulo.getAlmacenes().get(0).getId());
        if(almacen.isEmpty())
            return customResponse.getBadRequest("Almacen no encontrado");
        Almacen almacenAux = almacen.get();
        if(!almacenAux.getCategoria().getNombre().equalsIgnoreCase(articulo.getCategoria().getNombre()))
            return customResponse.getBadRequest("La categoria no coincide con la del almacen");
        repository.save(articulo);
        almacenAux.getArticulos().add(articulo);
        almacenRepository.saveAndFlush(almacenAux);
        return customResponse.getJSONResponse(articulo);
    }

    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<?> update(Articulo articulo, Long id){
        Optional<Articulo> articuloFound = repository.findById(id);
        if(articuloFound.isEmpty())
            return customResponse.getBadRequest("Articulo no encontrado");
        Articulo articulo1 = articuloFound.get();
        Articulo articulo2 = articuloFound.get();
        articulo1.setNombre(articulo.getNombre());
        articulo1.setDescripcion(articulo.getDescripcion());
        articulo1.setCategoria(articulo.getCategoria());
        Optional<Almacen> foundalmacenprevio = almacenRepository.findById(articulo1.getAlmacenes().get(0).getId());
        Almacen almacenprevio = foundalmacenprevio.get();
        if(almacenprevio.getArticulos().contains(articulo1)){
            almacenprevio.getArticulos().remove(articulo1);
        }
        //elimnar el articulo del almacen previo
        if(!almacenprevio.getCategoria().getNombre().equalsIgnoreCase(articulo.getCategoria().getNombre()))
            return customResponse.getBadRequest("La categoria no coincide con la del almacen");
        almacenRepository.saveAndFlush(almacenprevio);

        articulo1.setAlmacenes(articulo.getAlmacenes());
        Optional<Almacen> almacen = almacenRepository.findById(articulo.getAlmacenes().get(0).getId());
        if(almacen.isEmpty())
            return customResponse.getBadRequest("Almacen no encontrado");
        Almacen almacenAux = almacen.get();
        repository.save(articulo1);
        almacenAux.getArticulos().add(articulo1);
        almacenRepository.saveAndFlush(almacenAux);
        bitacoraService.registrarBitacora("POST", "articulo", articulo2, articulo);

        return customResponse.getJSONResponse(articulo1);
    }

    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<?> delete(Long id){
        Optional<Articulo> articulo = repository.findById(id);
        if(articulo.isEmpty())
            return customResponse.getBadRequest("Articulo no encontrado");
        Articulo articulo1 = articulo.get();
        for (Almacen almacen : articulo1.getAlmacenes()) {
            almacen.getArticulos().remove(articulo1);
            almacenRepository.saveAndFlush(almacen);
        }
        articulo1.setAlmacenes(null);
        bitacoraService.registrarBitacora("DELETE", "articulo", articulo1, null);
        repository.delete(articulo1);
        return customResponse.getJSONResponse("Articulo eliminado");
    }

    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<?> findByEncargado(Long id){
        Optional< User> user = userRepository.findById(id);
        if(user.isEmpty())
            return customResponse.getBadRequest("Usuario no encontrado");
        User userAux = user.get();
        Optional<Almacen> almacen = almacenRepository.findByEncargado(userAux);
        if(almacen.isEmpty())
            return customResponse.getBadRequest("Almacen no encontrado");
        Almacen almacenAux = almacen.get();
        if(almacenAux.getArticulos().isEmpty())
            return customResponse.getBadRequest("No hay articulos en el almacen");
        bitacoraService.registrarBitacora("GET", "articulo", null, null);
        return customResponse.getJSONResponse(repository.findByAlmacenes(almacenAux));
    }
}

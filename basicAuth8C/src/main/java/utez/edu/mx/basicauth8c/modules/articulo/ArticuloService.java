package utez.edu.mx.basicauth8c.modules.articulo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import utez.edu.mx.basicauth8c.kernel.CustomResponse;
import utez.edu.mx.basicauth8c.modules.almacen.Almacen;
import utez.edu.mx.basicauth8c.modules.almacen.AlmacenRepository;
import utez.edu.mx.basicauth8c.modules.bitacora.BitacoraService;

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
        return customResponse.getJSONResponse(repository.save(articulo));
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
        articulo1.setAlmacenes(articulo.getAlmacenes());
        repository.save(articulo1);
        bitacoraService.registrarBitacora("POST", "articulo", articulo2, articulo);

        return customResponse.getJSONResponse(articulo1);
    }

    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<?> delete(Long id){
        Optional<Articulo> articulo = repository.findById(id);
        if(articulo.isEmpty())
            return customResponse.getBadRequest("Articulo no encontrado");
        Articulo articulo1 = articulo.get();
        bitacoraService.registrarBitacora("DELETE", "articulo", articulo1, null);
        repository.deleteById(id);
        return customResponse.getJSONResponse("Articulo eliminado");
    }
}

package utez.edu.mx.basicauth8c.modules.articulo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import utez.edu.mx.basicauth8c.kernel.CustomResponse;
import utez.edu.mx.basicauth8c.modules.almacen.Almacen;
import utez.edu.mx.basicauth8c.modules.almacen.AlmacenRepository;

import java.util.Optional;

@Service
public class ArticuloService {
    @Autowired
    private ArticuloRepository repository;

    @Autowired
    private CustomResponse customResponse;

    @Autowired
    private AlmacenRepository almacenRepository;

    @Transactional(readOnly = true)
    public ResponseEntity<?> getAll(){
        return customResponse.getJSONResponse(repository.findAll());
    }

    @Transactional(readOnly = true)
    public ResponseEntity<?> getByAlmacen(Long id){
        Optional<Almacen> almacen = almacenRepository.findById(id);
        if(almacen.isEmpty())
            return customResponse.getBadRequest("Almacen no encontrado");
        Almacen almacenAux = almacen.get();
        return customResponse.getJSONResponse(repository.findByAlmacenes(almacenAux));
    }

    @Transactional(readOnly = true)
    public ResponseEntity<?> getById(Long id){
        if(!repository.existsById(id))
            return customResponse.getBadRequest("Articulo no encontrado");
        return customResponse.getJSONResponse(repository.findById(id));
    }

    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<?> save(Articulo articulo){
        if (repository.findByNombre(articulo.getNombre()).isPresent())
            return customResponse.getBadRequest("Articulo ya registrado");
        return customResponse.getJSONResponse(repository.save(articulo));
    }

    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<?> update(Articulo articulo, Long id){
        Optional<Articulo> articuloFound = repository.findById(id);
        if(articuloFound.isEmpty())
            return customResponse.getBadRequest("Articulo no encontrado");
        Articulo articulo1 = articuloFound.get();
        articulo1.setNombre(articulo.getNombre());
        articulo1.setDescripcion(articulo.getDescripcion());
        articulo1.setCategoria(articulo.getCategoria());
        articulo1.setAlmacenes(articulo.getAlmacenes());

        return customResponse.getJSONResponse(repository.save(articulo1));
    }

    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<?> delete(Long id){
        if(!repository.existsById(id))
            return customResponse.getBadRequest("Articulo no encontrado");
        repository.deleteById(id);
        return customResponse.getJSONResponse("Articulo eliminado");
    }
}

package utez.edu.mx.basicauth8c.modules.categoria;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import utez.edu.mx.basicauth8c.kernel.CustomResponse;
import utez.edu.mx.basicauth8c.modules.bitacora.BitacoraService;

import java.util.Optional;

@Service
public class CategoriaService {
    @Autowired
    private CategoriaRepository repository;

    @Autowired
    private CustomResponse customResponse;

    @Autowired
    private BitacoraService bitacoraService;

    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<?> getAll(){
        bitacoraService.registrarBitacora("GET", "categoria", null, null);
        return customResponse.getJSONResponse(repository.findAll());
    }

    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<?> getById(Long id){
        if(!repository.existsById(id))
            return customResponse.getBadRequest("Categoria no encontrada");
        bitacoraService.registrarBitacora("GET", "categoria", null, null);
        return customResponse.getJSONResponse(repository.findById(id));
    }

    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<?> save(Categoria categoria){
        Optional<Categoria> foundCategoria = repository.findByNombre(categoria.getNombre());
        if (foundCategoria.isPresent())
            return customResponse.getBadRequest("Categoria ya registrada");
        bitacoraService.registrarBitacora("POST", "categoria", null, categoria);
        return customResponse.getJSONResponse(repository.save(categoria));
    }

    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<?> update(Categoria categoria, Long id){
        Optional<Categoria> categoriaFound = repository.findById(id);
        if(categoriaFound.isEmpty())
            return customResponse.getBadRequest("Categoria no encontrada");
        Categoria categoria1 = categoriaFound.get();
        Categoria categoria2 = categoriaFound.get();
        categoria1.setNombre(categoria.getNombre());
        repository.saveAndFlush(categoria1);
        bitacoraService.registrarBitacora("PUT", "categoria", categoria2, categoria1);
        return customResponse.getJSONResponse(categoria2);
    }

    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<?> delete(Long id){
        Optional<Categoria> categoriaFound = repository.findById(id);
        if(categoriaFound.isEmpty())
            return customResponse.getBadRequest("Categoria no encontrada");
        Categoria categoria = categoriaFound.get();
        bitacoraService.registrarBitacora("DELETE", "categoria", categoria, null);
        repository.delete(categoriaFound.get());
        return customResponse.getJSONResponse("Categoria eliminada");
    }
}

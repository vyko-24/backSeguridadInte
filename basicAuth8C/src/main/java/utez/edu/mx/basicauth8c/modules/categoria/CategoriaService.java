package utez.edu.mx.basicauth8c.modules.categoria;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import utez.edu.mx.basicauth8c.kernel.CustomResponse;

import java.util.Optional;

@Service
public class CategoriaService {
    @Autowired
    private CategoriaRepository repository;

    @Autowired
    private CustomResponse customResponse;

    @Transactional(readOnly = true)
    public ResponseEntity<?> getAll(){
        return customResponse.getJSONResponse(repository.findAll());
    }

    @Transactional(readOnly = true)
    public ResponseEntity<?> getById(Long id){
        if(!repository.existsById(id))
            return customResponse.getBadRequest("Categoria no encontrada");
        return customResponse.getJSONResponse(repository.findById(id));
    }

    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<?> save(Categoria categoria){
        if (repository.findById(categoria.getId()).isPresent())
            return customResponse.getBadRequest("Categoria ya registrada");
        return customResponse.getJSONResponse(repository.save(categoria));
    }

    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<?> update(Categoria categoria, Long id){
        Optional<Categoria> categoriaFound = repository.findById(id);
        if(categoriaFound.isEmpty())
            return customResponse.getBadRequest("Categoria no encontrada");
        Categoria categoria1 = categoriaFound.get();
        categoria1.setNombre(categoria.getNombre());
        return customResponse.getJSONResponse(repository.save(categoria1));
    }

    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<?> delete(Long id){
        Optional<Categoria> categoriaFound = repository.findById(id);
        if(categoriaFound.isEmpty())
            return customResponse.getBadRequest("Categoria no encontrada");
        repository.delete(categoriaFound.get());
        return customResponse.getJSONResponse("Categoria eliminada");
    }
}

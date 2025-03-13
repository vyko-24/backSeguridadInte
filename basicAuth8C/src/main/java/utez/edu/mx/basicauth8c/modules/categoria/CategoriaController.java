package utez.edu.mx.basicauth8c.modules.categoria;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/categoria")
@CrossOrigin("*")
public class CategoriaController {
    @Autowired
    private CategoriaService categoriaService;

    @GetMapping("/get/")
    public ResponseEntity<?> getAll(){
        return categoriaService.getAll();
    }

    @GetMapping("/get/{id}/")
    public ResponseEntity<?> getById(@PathVariable("id") Long id){
        return categoriaService.getById(id);
    }

    @PostMapping("/save/")
    public ResponseEntity<?> save(@RequestBody CategoriaDto categoria){
        return categoriaService.save(categoria.toEntity());
    }

    @PutMapping("/update/{id}/")
    public ResponseEntity<?> update(@RequestBody CategoriaDto categoria, @PathVariable("id") Long id){
        return categoriaService.update(categoria.toEntity(), id);
    }

    @DeleteMapping("/delete/{id}/")
    public ResponseEntity<?> delete(@PathVariable("id") Long id){
        return categoriaService.delete(id);
    }
}

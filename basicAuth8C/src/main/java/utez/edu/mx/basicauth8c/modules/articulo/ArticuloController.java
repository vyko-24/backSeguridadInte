package utez.edu.mx.basicauth8c.modules.articulo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/articulo")
@CrossOrigin("*")
public class ArticuloController {
    @Autowired
    private ArticuloService articuloService;

    @GetMapping("/get/")
    public ResponseEntity<?> getAll(){
        return articuloService.getAll();
    }

    @GetMapping("/get/{id}/")
    public ResponseEntity<?> getById(@PathVariable("id")  Long id){
        return articuloService.getById(id);
    }

    @GetMapping("/get/almacen/{id}/")
    public ResponseEntity<?> getByAlmacen(@PathVariable("id") Long id){
        return articuloService.getByAlmacen(id);
    }

    @PostMapping("/save/")
    public ResponseEntity<?> save(@RequestBody ArticuloDto articulo){
        return articuloService.save(articulo.toEntity());
    }

    @PutMapping("/update/{id}/")
    public ResponseEntity<?> update(@RequestBody ArticuloDto articulo, @PathVariable("id") Long id){
        return articuloService.update(articulo.toEntity(), id);
    }

    @DeleteMapping("/delete/{id}/")
    public ResponseEntity<?> delete(@PathVariable("id") Long id){
        return articuloService.delete(id);
    }
}

package utez.edu.mx.basicauth8c.modules.almacen;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import utez.edu.mx.basicauth8c.modules.articulo.ArticuloDto;

@RestController
@RequestMapping("/api/almacen")
public class AlmacenController {
    @Autowired
    private AlmacenService almacenService;

    @GetMapping("/get/")
    public ResponseEntity<?> getAll(){
        return almacenService.getAll();
    }

    @GetMapping("/get/{id}/")
    public ResponseEntity<?> getById(@PathVariable("id")  Long id){
        return almacenService.getById(id);
    }

    @PostMapping("/save/")
    public ResponseEntity<?> save(@RequestBody AlmacenDto almacen){
        return almacenService.save(almacen.toEntity());
    }

    @PutMapping("/update/{id}/")
    public ResponseEntity<?> update(@RequestBody AlmacenDto almacen, @PathVariable("id") Long id){
        return almacenService.update(almacen.toEntity(), id);
    }

    @DeleteMapping("/delete/{id}/")
    public ResponseEntity<?> delete(@PathVariable("id") Long id){
        return almacenService.delete(id);
    }

    @PostMapping("/createArticulo/{idAlmacen}/")
    public ResponseEntity<?> createArticulo(@RequestBody ArticuloDto articulo, @PathVariable("idAlmacen") Long idAlmacen){
        return almacenService.createArticulo(articulo.toEntity(), idAlmacen);
    }
}

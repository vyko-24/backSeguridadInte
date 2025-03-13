package utez.edu.mx.basicauth8c.modules.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
@CrossOrigin
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/get/")
    public ResponseEntity<?> getAll(){
        return userService.getAll();
    }

    @GetMapping("/get/{id}/")
    public ResponseEntity<?> getById(@PathVariable("id") Long id){
        return userService.getById(id);
    }

    @PostMapping("/save/")
    public ResponseEntity<?> save(@RequestBody UserDto user){
        return userService.save(user.toEntity());
    }

    @PutMapping("/update/{id}/")
    public ResponseEntity<?> update(@RequestBody UserDto user, @PathVariable("id") Long id){
        return userService.update(user.toEntity(), id);
    }

    @DeleteMapping("/delete/{id}/")
    public ResponseEntity<?> delete(@PathVariable("id") Long id){
        return userService.delete(id);
    }
}

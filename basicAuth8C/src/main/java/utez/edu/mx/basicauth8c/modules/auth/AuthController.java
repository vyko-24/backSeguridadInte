package utez.edu.mx.basicauth8c.modules.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import utez.edu.mx.basicauth8c.modules.user.UserService;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin("*")
public class AuthController {
    @Autowired
    private AuthService authService;


    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDto dto){
       return authService.login(dto);
    }

    @PutMapping("/regresarContrasena/{id}")
    public ResponseEntity<?> recuperarContrasena(@PathVariable("id") Long id){
        return authService.regresarContrasena(id);
    }

    @PutMapping("/updatePassword/{id}")
    public ResponseEntity<?> updatePassword(@PathVariable("id") Long id, @RequestBody LoginDto coso){
        return authService.updatePassword(id, coso);
    }
}

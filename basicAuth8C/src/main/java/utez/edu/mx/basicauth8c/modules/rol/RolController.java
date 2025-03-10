package utez.edu.mx.basicauth8c.modules.rol;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/role/")
public class RolController {
    @Autowired
    private RoleService roleService;

    @GetMapping("/")
    public ResponseEntity<?> getAll(){
        return roleService.getAll();
    }
}

package utez.edu.mx.basicauth8c.modules;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

// BA06: Crear el controller de testeo para el objeto de seguridad
@RestController
@RequestMapping("/api/test")
@CrossOrigin(origins = "*")
public class TestController {

    @GetMapping("")
    public ResponseEntity<?> test() {
        Map<String, String> body = new HashMap<>();
        body.put("message", "Operación exitosa");

        return new ResponseEntity<>(body, HttpStatus.OK);
    }

    @GetMapping("/secured")
    public ResponseEntity<?> secured() {
        Map<String, String> body = new HashMap<>();
        body.put("message", "Operación exitosa");

        return new ResponseEntity<>(body, HttpStatus.OK);
    }
}

// Siguiente -> Crear la configuracion de seguridad pára las rutas en MainSecurity



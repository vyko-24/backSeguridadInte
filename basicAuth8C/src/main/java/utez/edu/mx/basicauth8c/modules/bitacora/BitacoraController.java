package utez.edu.mx.basicauth8c.modules.bitacora;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api/bitacora")
@CrossOrigin(origins = {"*"})
public class BitacoraController {

    private final BitacoraService service;

    public BitacoraController(BitacoraService service) {
        this.service = service;
    }


    @GetMapping("/")
    public ResponseEntity<?> obtenerBitacora() {
        return service.getBitacora();
    }
}

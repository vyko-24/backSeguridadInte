package utez.edu.mx.basicauth8c.modules.bitacora;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import utez.edu.mx.basicauth8c.kernel.CustomResponse;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BitacoraService {

    @Autowired
    private BitacoraRepository repository;

    @Autowired
    private CustomResponse customResponse;

    public void registrarBitacora(String accion, String tabla, Object antes, Object despues) {
        String usuario = SecurityContextHolder.getContext().getAuthentication().getName();
        LocalDateTime fecha = LocalDateTime.now();

        Bitacora bitacora = new Bitacora(
                usuario,
                accion,
                tabla,
                convertirAJson(antes),
                convertirAJson(despues),
                fecha
        );

        repository.save(bitacora);
    }

    private String convertirAJson(Object objeto) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.writeValueAsString(objeto);
        } catch (JsonProcessingException e) {
            return "Error al serializar";
        }
    }
    @Transactional(readOnly = true)
    public ResponseEntity<?> getBitacora(){
        List<Bitacora> bitacoras = repository.findAllByOrderByFechaDesc();
        ObjectMapper objectMapper = new ObjectMapper();

        List<Bitacora> bitacorasTransformadas = bitacoras.stream().map(bitacora -> {
            try {
                if (bitacora.getDatosNuevos() != null) {
                    JsonNode jsonNode = objectMapper.readTree(bitacora.getDatosNuevos());
                    bitacora.setDatosNuevos(objectMapper.writeValueAsString(jsonNode)); // Convertimos de nuevo a String sin escapes
                }
                if (bitacora.getDatosAnteriores() != null) {
                    JsonNode jsonNode = objectMapper.readTree(bitacora.getDatosAnteriores());
                    bitacora.setDatosAnteriores(objectMapper.writeValueAsString(jsonNode)); // Convertimos de nuevo a String sin escapes
                }
            } catch (Exception e) {
                // Si hay error, dejamos los valores como están
            }
            return bitacora;
        }).collect(Collectors.toList());

        return  customResponse.getJSONResponse(bitacorasTransformadas);
    }
}

package utez.edu.mx.basicauth8c.kernel;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class CustomResponse {
    private Map<String, Object> body;

    public ResponseEntity<String> getOkResponse(Object data) {
        body = new HashMap<>();
        body.put("message", "Operación exitosa");
        body.put("status", "Ok");
        if (data != null) {
            body.put("data", data);
        }

        return new ResponseEntity<>(body.toString(), HttpStatus.OK);
    }

    public ResponseEntity<Map<String, Object>> getJSONResponse(Object data) {
        Map<String, Object> body = new HashMap<>();
        body.put("message", "Operación exitosa");
        body.put("status", "Ok");
        if (data != null) {
            body.put("data", data);
        }

        return new ResponseEntity<>(body, HttpStatus.OK);
    }

    public ResponseEntity<Map<String, Object>> getLoginJSONResponse(Object data, Boolean firstTime) {
        Map<String, Object> body = new HashMap<>();
        body.put("message", "Operación exitosa");
        body.put("status", "Ok");
        body.put("firstLogin", firstTime);
        if (data != null) {
            body.put("data", data);
        }

        return new ResponseEntity<>(body, HttpStatus.OK);
    }

    public ResponseEntity<String> getCreatedResponse(String message) {
        body = new HashMap<>();
        body.put("message", message);
        body.put("status", "Created");

        return new ResponseEntity<>(body.toString(), HttpStatus.CREATED);
    }

    public ResponseEntity<String> get400Response(int code) {
        body = new HashMap<>();
        body.put("message", code == 400 ? "No se pudo realizar la operación" : "No se encontró recurso solicitado");
        body.put("status", code==400 ? "Bad Request":"Not Found");

        return new ResponseEntity<>(body.toString(), code == 400 ? HttpStatus.BAD_REQUEST : HttpStatus.NOT_FOUND);
    }

    public ResponseEntity<String> getBadRequest(String message) {
        body = new HashMap<>();
        body.put("message", message);
        body.put("status", "Bad Request");

        return new ResponseEntity<>(body.toString(), HttpStatus.BAD_REQUEST);
    }
}

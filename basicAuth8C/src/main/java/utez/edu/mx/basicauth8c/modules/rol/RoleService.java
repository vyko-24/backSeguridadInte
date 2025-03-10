package utez.edu.mx.basicauth8c.modules.rol;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import utez.edu.mx.basicauth8c.kernel.CustomResponse;

@Service
public class RoleService {
    @Autowired
    private RolRepository rolRepository;

    @Autowired
    private CustomResponse customResponse;



    @Transactional(readOnly = true)
    public ResponseEntity<?> getAll() {
        return customResponse.getOkResponse(rolRepository.findAll());
    }
}

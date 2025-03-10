package utez.edu.mx.basicauth8c.modules.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import utez.edu.mx.basicauth8c.kernel.CustomResponse;
import utez.edu.mx.basicauth8c.modules.user.UseRepository;
import utez.edu.mx.basicauth8c.modules.user.User;

@Service
public class AuthService {
    @Autowired
    private UseRepository useRepository;

    @Autowired
    private CustomResponse customResponse;

    @Transactional(readOnly = true)
    public ResponseEntity<?> login(LoginDto dto) {
        User foundUser = useRepository.findByUsernameAndPassword(dto.getUsername(), dto.getPassword());
        if (foundUser == null)
            return customResponse.get400Response(404);
        return customResponse.getOkResponse("bearertoken."+foundUser.getUsername()+".voidtoken");
    }
}

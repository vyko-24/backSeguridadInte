package utez.edu.mx.basicauth8c.modules.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import utez.edu.mx.basicauth8c.kernel.CustomResponse;
import utez.edu.mx.basicauth8c.modules.user.User;
import utez.edu.mx.basicauth8c.modules.user.UserRepository;

import java.util.Optional;

@Service
public class AuthService {
    @Autowired
    private UserRepository useRepository;

    @Autowired
    private CustomResponse customResponse;

    @Transactional(readOnly = true)
    public ResponseEntity<?> login(LoginDto dto) {
        User foundUser = useRepository.findByUsernameAndPassword(dto.getUsername(), dto.getPassword());
        if (foundUser == null)
            return customResponse.get400Response(404);
        return customResponse.getOkResponse("bearertoken."+foundUser.getUsername()+".voidtoken");
    }

    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<?> regresarContrasena(Long id){
        Optional<User> foundUser = useRepository.findById(id);
        if (!foundUser.isPresent())
            return customResponse.get400Response(404);
        User user = foundUser.get();
        user.setPassword(user.getUsername());
        return customResponse.getJSONResponse(useRepository.save(user));
    }

    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<?> updatePassword(Long id, String password){
        Optional<User> foundUser = useRepository.findById(id);
        if (!foundUser.isPresent())
            return customResponse.get400Response(404);
        User user = foundUser.get();
        user.setPassword(password);
        return customResponse.getJSONResponse(useRepository.save(user));
    }
}

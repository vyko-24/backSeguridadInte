package utez.edu.mx.basicauth8c.modules.user;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import utez.edu.mx.basicauth8c.kernel.CustomResponse;

import java.sql.SQLException;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository repository;

    @Autowired
    private CustomResponse response;

    @Transactional(readOnly = true)
    public ResponseEntity<?> getAll(){
        return response.getJSONResponse(repository.findAll());
    }

    @Transactional(readOnly = true)
    public ResponseEntity<?> getById(Long id){
        if(!repository.existsById(id))
            return response.getBadRequest("Usuario no encontrado");
        return response.getJSONResponse(repository.findById(id));
    }

    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<?> save(User user){
        if (repository.findByEmail(user.getEmail()).isPresent())
            return response.getBadRequest("Correo ya registrado");
        User user1 = repository.findByUsername(user.getUsername());
        if(user1 != null)
            return response.getBadRequest("Usuario ya registrado");
        return response.getJSONResponse(repository.save(user));
    }

    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<?> update(User user, Long id){
        Optional<User> foundUser = repository.findById(id);
        if(foundUser.isEmpty())
            return response.getBadRequest("Usuario no encontrado");
        User user1 = foundUser.get();
        user1.setEmail(user.getEmail());
        user1.setNombre(user.getNombre());
        user1.setApellidos(user.getApellidos());
        user1.setUsername(user.getUsername());

        return response.getJSONResponse(repository.saveAndFlush(user1));
    }

    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<?> delete(Long id){
        Optional<User> foundUser = repository.findById(id);
        if(foundUser.isEmpty())
            return response.getBadRequest("Usuario no encontrado");
        repository.deleteById(id);
        return response.getJSONResponse("Usuario eliminado");
    }
}

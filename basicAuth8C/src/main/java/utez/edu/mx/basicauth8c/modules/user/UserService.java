package utez.edu.mx.basicauth8c.modules.user;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import utez.edu.mx.basicauth8c.kernel.CustomResponse;
import utez.edu.mx.basicauth8c.modules.bitacora.BitacoraService;

import java.sql.SQLException;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository repository;

    @Autowired
    private CustomResponse response;

    @Autowired
    private BitacoraService bitacoraService;

    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<?> getAll(){
        bitacoraService.registrarBitacora("GET", "usuario", null, null);
        return response.getJSONResponse(repository.findAll());
    }

    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<?> getById(Long id){
        if(!repository.existsById(id))
            return response.getBadRequest("Usuario no encontrado");
        bitacoraService.registrarBitacora("GET", "usuario", null, null);
        return response.getJSONResponse(repository.findById(id));
    }

    @Transactional(rollbackFor = Exception.class)
    public User findUserByUsername(String username) {
        User foundUser = repository.findByUsername(username);
        if (foundUser == null)
            return null;
        return foundUser;
    }

    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<?> update(User user, Long id){
        Optional<User> foundUser = repository.findById(id);
        if(foundUser.isEmpty())
            return response.getBadRequest("Usuario no encontrado");
        User user1 = foundUser.get();
        User user2 = foundUser.get();
        user1.setEmail(user.getEmail());
        user1.setNombre(user.getNombre());
        user1.setApellidos(user.getApellidos());
        user1.setUsername(user.getUsername());
        repository.save(user1);
        bitacoraService.registrarBitacora("PUT", "usuario", user2, user1);

        return response.getJSONResponse(user1);
    }

    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<?> changeStatus(Long id){
        Optional<User> foundUser = repository.findById(id);
        if(foundUser.isEmpty())
            return response.getBadRequest("Usuario no encontrado");
        User user = foundUser.get();
        User user2 = foundUser.get();
        user.setStatus(!user.getStatus());
        repository.saveAndFlush(user);
        bitacoraService.registrarBitacora("PUT", "usuario", user2, user);
        return response.getJSONResponse(user);
    }

    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<?> delete(Long id){
        Optional<User> foundUser = repository.findById(id);
        if(foundUser.isEmpty())
            return response.getBadRequest("Usuario no encontrado");
        if(foundUser.get().getAlmacen() != null)
            return response.getBadRequest("El usuario tiene un almacen asignado. Por favor elimine el almacen o cambie de encargado");
        User user = foundUser.get();
        bitacoraService.registrarBitacora("DELETE", "usuario", user, null);
        repository.deleteById(id);
        return response.getJSONResponse("Usuario eliminado");
    }
}

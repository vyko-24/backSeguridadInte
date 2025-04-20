package utez.edu.mx.basicauth8c.modules.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import utez.edu.mx.basicauth8c.kernel.CustomResponse;
import utez.edu.mx.basicauth8c.modules.bitacora.BitacoraService;
import utez.edu.mx.basicauth8c.modules.user.User;
import utez.edu.mx.basicauth8c.modules.user.UserRepository;
import utez.edu.mx.basicauth8c.security.token.JwtProvider;

import java.util.Optional;

@Service
public class AuthService {
    @Autowired
    private UserRepository useRepository;

    @Autowired
    private CustomResponse customResponse;

    @Autowired
    private BitacoraService bitacoraService;


    private final AuthenticationManager manager;
    private final JwtProvider provider;
    private final PasswordEncoder encoder;

    public AuthService(AuthenticationManager manager, JwtProvider provider, PasswordEncoder encoder) {
        this.manager = manager;
        this.provider = provider;
        this.encoder = encoder;
    }


    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<?> login(LoginDto dto) {
        System.out.println(dto);
        try{
            User foundUser = useRepository.findByUsername(dto.getUsername());
            if (foundUser == null)
                return customResponse.get400Response(404);
            if(!foundUser.getStatus())
                return customResponse.getBadRequest("Usuario inactivo");
            Authentication auth = manager.authenticate(
                    new UsernamePasswordAuthenticationToken(dto.getUsername(), dto.getPassword())
            );
            System.out.println(dto);
            SecurityContextHolder.getContext().setAuthentication(auth);
            String token = provider.generateToken(auth);
            SignedDto signedDto = new SignedDto(token, "Bearer", foundUser, foundUser.getRol());
            bitacoraService.registrarBitacora("LOGIN", "user", null, foundUser);
            if(dto.getUsername().equals(dto.getPassword()) ){
                return customResponse.getLoginJSONResponse(signedDto, true);
            }
            return customResponse.getLoginJSONResponse(signedDto, false);
        }catch (Exception e){
            e.printStackTrace();
            return customResponse.get400Response(400);
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<?> regresarContrasena(Long id){
        Optional<User> foundUser = useRepository.findById(id);
        if (!foundUser.isPresent())
            return customResponse.get400Response(404);
        User user = foundUser.get();
        user.setPassword(encoder.encode(user.getUsername()));
        bitacoraService.registrarBitacora("PUT", "user", null, user);
        return customResponse.getJSONResponse(useRepository.save(user));
    }

    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<?> updatePassword(Long id, LoginDto coso){
        Optional<User> foundUser = useRepository.findById(id);
        if (!foundUser.isPresent())
            return customResponse.get400Response(404);
        User user = foundUser.get();
        user.setPassword(encoder.encode(coso.getPassword()));
        return customResponse.getJSONResponse(useRepository.save(user));
    }


    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<?> save(User user){
        if (useRepository.findByEmail(user.getEmail()).isPresent())
            return customResponse.getBadRequest("Correo ya registrado");
        User user1 = useRepository.findByUsername(user.getUsername());
        if(user1 != null)
            return customResponse.getBadRequest("Usuario ya registrado");
        user.setPassword(encoder.encode(user.getUsername()));
        user.setStatus(true);
        bitacoraService.registrarBitacora("POST", "user", null, user);
        return customResponse.getJSONResponse(useRepository.save(user));
    }
}

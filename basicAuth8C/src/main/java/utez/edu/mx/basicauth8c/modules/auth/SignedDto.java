package utez.edu.mx.basicauth8c.modules.auth;

import lombok.Value;
import utez.edu.mx.basicauth8c.modules.rol.Rol;
import utez.edu.mx.basicauth8c.modules.user.User;

@Value
public class SignedDto {
    String token;
    String tokenType;
    User user;
    Rol role;
}

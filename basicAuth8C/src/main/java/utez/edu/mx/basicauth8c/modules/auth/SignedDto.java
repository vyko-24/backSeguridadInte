package utez.edu.mx.basicauth8c.modules.auth;

import lombok.*;
import lombok.Value;
import utez.edu.mx.basicauth8c.modules.rol.Rol;
import utez.edu.mx.basicauth8c.modules.user.User;

@Getter
@Builder
@NoArgsConstructor
public class SignedDto {
    String token;
    String tokenType;
    User user;
    Rol role;

    public SignedDto(String token, String tokenType, User user, Rol role) {
        this.token = token;
        this.tokenType = tokenType;
        this.user = user;
        this.role = role;
    }

}

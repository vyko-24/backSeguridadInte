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

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getTokenType() {
        return tokenType;
    }

    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Rol getRole() {
        return role;
    }

    public void setRole(Rol role) {
        this.role = role;
    }

    public SignedDto(String token, String tokenType, User user, Rol role) {
        this.token = token;
        this.tokenType = tokenType;
        this.user = user;
        this.role = role;
    }

}

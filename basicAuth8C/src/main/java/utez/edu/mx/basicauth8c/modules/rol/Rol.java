package utez.edu.mx.basicauth8c.modules.rol;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import utez.edu.mx.basicauth8c.modules.user.User;

import java.util.List;

@Entity
@Table(name = "roles")
public class Rol {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String nombre;

    @JsonIgnoreProperties(value = {"usuario"})
    @OneToMany(mappedBy = "rol",cascade = CascadeType.PERSIST)
    private List<User> usuario;
}

package utez.edu.mx.basicauth8c.modules.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import utez.edu.mx.basicauth8c.modules.almacen.Almacen;
import utez.edu.mx.basicauth8c.modules.rol.Rol;

import java.util.Set;

@Entity
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "nombre", nullable = false)
    private String nombre;

    @Column(name = "apellidos", nullable = false)
    private String apellidos;

    //private Almacen almacen;

    // Multiseleccion: shift + alt
    @Column(name = "username", nullable = false)
    private String username;

    @Column(name = "password", nullable = false)
    @JsonIgnore
    private String password;

    @ManyToOne
    @JoinColumn(name = "id_role")
    @JsonIgnoreProperties(value = {"usuario"})
    private Rol rol ;

    @OneToOne(mappedBy = "encargado", cascade = CascadeType.ALL)
    @JsonIgnoreProperties(value = {"encargado","categorias","almacen","articulos"})
    private Almacen almacen;

    @Column(name = "status", nullable = false, columnDefinition = "TINYINT(1) DEFAULT 1")
    private Boolean status;

    public User(Long id, String email, String nombre, String apellidos, String username, String password, Rol rol, Almacen almacen, Boolean status) {
        this.id = id;
        this.email = email;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.username = username;
        this.password = password;
        this.rol = rol;
        this.almacen = almacen;
        this.status = status;
    }

    public User(String email, String nombre, String apellidos, String username, String password, Rol rol, Boolean status) {
        this.email = email;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.username = username;
        this.password = password;
        this.rol = rol;
        this.status = status;
    }

    public User(Long id, String email, String nombre, String apellidos, String username, String password, Rol rol, Boolean status) {
        this.id = id;
        this.email = email;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.username = username;
        this.password = password;
        this.rol = rol;
        this.status = status;
    }

    public User(String email, String nombre, String apellidos, String username, String password, Rol rol, Almacen almacen, Boolean status) {
        this.email = email;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.username = username;
        this.password = password;
        this.rol = rol;
        this.almacen = almacen;
        this.status = status;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Almacen getAlmacen() {
        return almacen;
    }

    public void setAlmacen(Almacen almacen) {
        this.almacen = almacen;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public User() {
    }

    public User(String email, String nombre, String apellidos, String username, String password, Rol rol, Almacen almacen) {
        this.email = email;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.username = username;
        this.password = password;
        this.rol = rol;
        this.almacen = almacen;
    }

    public User(Long id, String email, String nombre, String apellidos, String username, String password, Rol rol, Almacen almacen) {
        this.id = id;
        this.email = email;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.username = username;
        this.password = password;
        this.rol = rol;
        this.almacen = almacen;
    }

    public User(String email, String nombre, String apellidos, String username, String password, Rol rol) {
        this.email = email;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.username = username;
        this.password = password;
        this.rol = rol;
    }

    public User(Long id, String email, String nombre, String apellidos, String username, String password, Rol rol) {
        this.id = id;
        this.email = email;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.username = username;
        this.password = password;
        this.rol = rol;
    }

    public User(Long id, String username, String password, Rol rol) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.rol = rol;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Rol getRol() {
        return rol;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }
}

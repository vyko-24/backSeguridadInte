package utez.edu.mx.basicauth8c.modules.auth;


public class RolDto {
    private Long id;
    private String nombre;

    public RolDto(Long id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    public RolDto() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}

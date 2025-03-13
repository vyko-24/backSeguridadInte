package utez.edu.mx.basicauth8c.modules.categoria;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import utez.edu.mx.basicauth8c.modules.almacen.Almacen;
import utez.edu.mx.basicauth8c.modules.articulo.Articulo;

import java.util.Set;

@Entity
@Table(name = "categoria")
public class Categoria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombre")
    private String nombre;

    @OneToMany(mappedBy = "categoria", cascade = CascadeType.ALL)
    @JsonIgnoreProperties(value = {"categoria","almacenes"})
    private Set<Articulo> articulo;

    @OneToMany(mappedBy = "categoria", cascade = CascadeType.ALL)
    @JsonIgnoreProperties(value = {"categoria","encargado","articulos"})
    private Set<Almacen> almacen;

    public Categoria() {
    }

    public Categoria(String nombre, Long id) {
        this.nombre = nombre;
        this.id = id;
    }

    public Categoria(String nombre) {
        this.nombre = nombre;
    }

    public Categoria(Long id, String nombre, Set<Articulo> articulo, Set<Almacen> almacen) {
        this.id = id;
        this.nombre = nombre;
        this.articulo = articulo;
        this.almacen = almacen;
    }

    public Categoria(String nombre, Set<Articulo> articulo, Set<Almacen> almacen) {
        this.nombre = nombre;
        this.articulo = articulo;
        this.almacen = almacen;
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

    public Set<Articulo> getArticulo() {
        return articulo;
    }

    public void setArticulo(Set<Articulo> articulo) {
        this.articulo = articulo;
    }

    public Set<Almacen> getAlmacen() {
        return almacen;
    }

    public void setAlmacen(Set<Almacen> almacen) {
        this.almacen = almacen;
    }
}

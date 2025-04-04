package utez.edu.mx.basicauth8c.modules.almacen;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import utez.edu.mx.basicauth8c.modules.articulo.Articulo;
import utez.edu.mx.basicauth8c.modules.categoria.Categoria;
import utez.edu.mx.basicauth8c.modules.user.User;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name="almacen")
public class Almacen {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "categoria_id", nullable = false)
    @JsonIgnoreProperties(value = {"articulo","almacen"})
    private Categoria categoria;

    @OneToOne
    @JoinColumn(name = "encargado_id", nullable = false)
    @JsonIgnoreProperties(value = {"almacenes","password","rol"})
    private User encargado;

    @Column(name = "identificador", nullable = false)
    private String identificador;

    @ManyToMany
    @JoinTable(
            name = "alamacen_articulos",
            joinColumns = @JoinColumn(name = "almacen_id"),
            inverseJoinColumns = @JoinColumn(name = "articulos_id")
    )
    @JsonIgnoreProperties(value = {"almacenes","categoria"})
    private Set<Articulo> articulos = new HashSet<>();

    public Almacen() {
    }

    public Almacen(Categoria categoria, User encargado, String identificador) {
        this.categoria = categoria;
        this.encargado = encargado;
        this.identificador = identificador;
    }

    public Almacen(Categoria categoria, User encargado, String identificador, Set<Articulo> articulos) {
        this.categoria = categoria;
        this.encargado = encargado;
        this.identificador = identificador;
        this.articulos = articulos;
    }

    public Almacen(Long id, Categoria categoria, User encargado, String identificador, Set<Articulo> articulos) {
        this.id = id;
        this.categoria = categoria;
        this.encargado = encargado;
        this.identificador = identificador;
        this.articulos = articulos;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public User getEncargado() {
        return encargado;
    }

    public void setEncargado(User encargado) {
        this.encargado = encargado;
    }

    public String getIdentificador() {
        return identificador;
    }

    public void setIdentificador(String identificador) {
        this.identificador = identificador;
    }

    public Set<Articulo> getArticulos() {
        return articulos;
    }

    public void setArticulos(Set<Articulo> articulos) {
        this.articulos = articulos;
    }
}

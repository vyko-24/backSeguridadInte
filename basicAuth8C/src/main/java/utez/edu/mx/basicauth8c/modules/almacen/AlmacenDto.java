package utez.edu.mx.basicauth8c.modules.almacen;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import utez.edu.mx.basicauth8c.modules.articulo.Articulo;
import utez.edu.mx.basicauth8c.modules.categoria.Categoria;
import utez.edu.mx.basicauth8c.modules.user.User;

import java.util.List;
import java.util.Set;

public class AlmacenDto {
    private Long id;
    private Categoria categoria;
    private User encargado;
    private String identificador;
    private Set<Articulo> articulos;

    public Almacen toEntity(){
        return new Almacen(categoria, encargado, identificador, articulos);
    }
    public AlmacenDto() {
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

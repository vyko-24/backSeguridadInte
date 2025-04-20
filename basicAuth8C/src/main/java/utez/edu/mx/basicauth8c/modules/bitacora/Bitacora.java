package utez.edu.mx.basicauth8c.modules.bitacora;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name="bitacora")
public class Bitacora {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String usuario;

    @Column
    private String accion;

    @Column
    private String tablaAfectada;

    @Lob // Guarda JSON en la base de datos sin convertirlo a String puro
    @Column(columnDefinition = "TEXT")
    private String datosAnteriores;

    @Lob
    @Column(columnDefinition = "TEXT")
    private String datosNuevos;

    @Column(columnDefinition = "TIMESTAMP DEFAULT NOW()")
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime fecha;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getAccion() {
        return accion;
    }

    public void setAccion(String accion) {
        this.accion = accion;
    }

    public String getTablaAfectada() {
        return tablaAfectada;
    }

    public void setTablaAfectada(String tablaAfectada) {
        this.tablaAfectada = tablaAfectada;
    }

    public String getDatosAnteriores() {
        return datosAnteriores;
    }

    public void setDatosAnteriores(String datosAnteriores) {
        this.datosAnteriores = datosAnteriores;
    }

    public String getDatosNuevos() {
        return datosNuevos;
    }

    public void setDatosNuevos(String datosNuevos) {
        this.datosNuevos = datosNuevos;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }

    public Bitacora() {
    }

    public Bitacora(Long id, String usuario, String accion, String tablaAfectada, String datosAnteriores, String datosNuevos, LocalDateTime fecha) {
        this.id = id;
        this.usuario = usuario;
        this.accion = accion;
        this.tablaAfectada = tablaAfectada;
        this.datosAnteriores = datosAnteriores;
        this.datosNuevos = datosNuevos;
        this.fecha = fecha;
    }

    public Bitacora(String usuario, String accion, String tablaAfectada, String datosAnteriores, String datosNuevos, LocalDateTime fecha) {
        this.usuario = usuario;
        this.accion = accion;
        this.tablaAfectada = tablaAfectada;
        this.datosAnteriores = datosAnteriores;
        this.datosNuevos = datosNuevos;
        this.fecha = fecha;
    }
}

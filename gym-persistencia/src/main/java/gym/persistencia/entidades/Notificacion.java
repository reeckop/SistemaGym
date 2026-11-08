package gym.persistencia.entidades;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Entidad que modela una notificación generada para un cliente del gimnasio.
 */
public class Notificacion implements Serializable {
    private static final long serialVersionUID = 1L;

    private int idNotificacion;
    private String tipo;
    private String mensaje;
    private LocalDateTime fechaHora;
    private String estado;
    private Cliente cliente;

    public Notificacion() {
    }

    public Notificacion(int idNotificacion, String tipo, String mensaje, LocalDateTime fechaHora, String estado) {
        this.idNotificacion = idNotificacion;
        this.tipo = tipo;
        this.mensaje = mensaje;
        this.fechaHora = fechaHora;
        this.estado = estado;
    }

    public Notificacion(int idNotificacion, String tipo, String mensaje, LocalDateTime fechaHora, String estado, Cliente cliente) {
        this.idNotificacion = idNotificacion;
        this.tipo = tipo;
        this.mensaje = mensaje;
        this.fechaHora = fechaHora;
        this.estado = estado;
        this.cliente = cliente;
    }

    // Métodos del diagrama
    public void generar() {
        this.fechaHora = LocalDateTime.now();
        this.estado = "GENERADA";
    }

    public boolean enviar() {
        this.estado = "ENVIADA";
        return true;
    }

    // Getters y Setters
    public int getIdNotificacion() {
        return idNotificacion;
    }

    public void setIdNotificacion(int idNotificacion) {
        this.idNotificacion = idNotificacion;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public LocalDateTime getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(LocalDateTime fechaHora) {
        this.fechaHora = fechaHora;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Notificacion that = (Notificacion) o;
        return idNotificacion == that.idNotificacion;
    }

    @Override
    public int hashCode() {
        return Objects.hash(idNotificacion);
    }

    @Override
    public String toString() {
        return "Notificacion{" +
                "idNotificacion=" + idNotificacion +
                ", tipo='" + tipo + '\'' +
                ", mensaje='" + mensaje + '\'' +
                ", fechaHora=" + fechaHora +
                ", estado='" + estado + '\'' +
                ", cliente=" + (cliente != null ? cliente.getNombre() : "N/A") +
                '}';
    }
}

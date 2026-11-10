package gym.persistencia.entidades;

import gym.persistencia.enums.EstadoNotificacion;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;
import org.bson.BsonType;
import org.bson.codecs.pojo.annotations.BsonId;
import org.bson.codecs.pojo.annotations.BsonRepresentation;

/**
 * Entidad que modela una notificación generada para un cliente del gimnasio.
 */
public class Notificacion {

    @BsonId
    @BsonRepresentation(BsonType.OBJECT_ID)
    private String idNotificacion;
    
    private String tipo;
    private String mensaje;
    private LocalDateTime fechaHora;
    private EstadoNotificacion estado;
    private Cliente cliente;

    public Notificacion() {
    }

    public Notificacion(String idNotificacion, String tipo, String mensaje, LocalDateTime fechaHora, EstadoNotificacion estado) {
        this.idNotificacion = idNotificacion;
        this.tipo = tipo;
        this.mensaje = mensaje;
        this.fechaHora = fechaHora;
        this.estado = estado;
    }

    public Notificacion(String idNotificacion, String tipo, String mensaje, LocalDateTime fechaHora, EstadoNotificacion estado, Cliente cliente) {
        this.idNotificacion = idNotificacion;
        this.tipo = tipo;
        this.mensaje = mensaje;
        this.fechaHora = fechaHora;
        this.estado = estado;
        this.cliente = cliente;
    }

    public String getIdNotificacion() {
        return idNotificacion;
    }

    public void setIdNotificacion(String idNotificacion) {
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

    public EstadoNotificacion getEstado() {
        return estado;
    }

    public void setEstado(EstadoNotificacion estado) {
        this.estado = estado;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    @Override
    public String toString() {
        return "Notificacion{" + "idNotificacion=" + idNotificacion + ", tipo=" + tipo + ", mensaje=" + mensaje + ", fechaHora=" + fechaHora + ", estado=" + estado + ", cliente=" + cliente + '}';
    }

}

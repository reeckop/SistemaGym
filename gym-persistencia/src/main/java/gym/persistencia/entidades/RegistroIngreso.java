package gym.persistencia.entidades;

import gym.persistencia.enums.EstadoRegistroIngreso;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;
import org.bson.BsonType;
import org.bson.codecs.pojo.annotations.BsonId;
import org.bson.codecs.pojo.annotations.BsonRepresentation;

/**
 * Entidad que registra el control de acceso/asistencia de los socios al gimnasio.
 */
public class RegistroIngreso {

    @BsonId
    @BsonRepresentation(BsonType.OBJECT_ID)
    private String idAcceso;
    
    private LocalDateTime fechaHoraEntrada;
    private LocalDateTime fechaHoraSalida;
    private EstadoRegistroIngreso estado;
    private Cliente cliente;

    public RegistroIngreso() {
    }

    public RegistroIngreso(String idAcceso, LocalDateTime fechaHoraEntrada, LocalDateTime fechaHoraSalida, EstadoRegistroIngreso estado) {
        this.idAcceso = idAcceso;
        this.fechaHoraEntrada = fechaHoraEntrada;
        this.fechaHoraSalida = fechaHoraSalida;
        this.estado = estado;
    }

    public RegistroIngreso(String idAcceso, LocalDateTime fechaHoraEntrada, LocalDateTime fechaHoraSalida, EstadoRegistroIngreso estado, Cliente cliente) {
        this.idAcceso = idAcceso;
        this.fechaHoraEntrada = fechaHoraEntrada;
        this.fechaHoraSalida = fechaHoraSalida;
        this.estado = estado;
        this.cliente = cliente;
    }

    public String getIdAcceso() {
        return idAcceso;
    }

    public void setIdAcceso(String idAcceso) {
        this.idAcceso = idAcceso;
    }

    public LocalDateTime getFechaHoraEntrada() {
        return fechaHoraEntrada;
    }

    public void setFechaHoraEntrada(LocalDateTime fechaHoraEntrada) {
        this.fechaHoraEntrada = fechaHoraEntrada;
    }

    public LocalDateTime getFechaHoraSalida() {
        return fechaHoraSalida;
    }

    public void setFechaHoraSalida(LocalDateTime fechaHoraSalida) {
        this.fechaHoraSalida = fechaHoraSalida;
    }

    public EstadoRegistroIngreso getEstado() {
        return estado;
    }

    public void setEstado(EstadoRegistroIngreso estado) {
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
        return "RegistroIngreso{" + "idAcceso=" + idAcceso + ", fechaHoraEntrada=" + fechaHoraEntrada + ", fechaHoraSalida=" + fechaHoraSalida + ", estado=" + estado + ", cliente=" + cliente + '}';
    }

    
}

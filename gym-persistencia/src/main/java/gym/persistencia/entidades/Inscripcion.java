package gym.persistencia.entidades;

import gym.persistencia.enums.EstadoRegistroIngreso;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import org.bson.BsonType;
import org.bson.codecs.pojo.annotations.BsonId;
import org.bson.codecs.pojo.annotations.BsonRepresentation;

/**
 * Entidad que modela la inscripción de un cliente en un horario de curso.
 */
public class Inscripcion {
    
    @BsonId
    @BsonRepresentation(BsonType.OBJECT_ID)
    private String idInscripcion;
    private LocalDate fecha;
    private EstadoRegistroIngreso estado;
    private Cliente cliente;
    private Horario horario;

    public Inscripcion() {
    }

    public Inscripcion(String idInscripcion, LocalDate fecha, EstadoRegistroIngreso estado) {
        this.idInscripcion = idInscripcion;
        this.fecha = fecha;
        this.estado = estado;
    }

    public Inscripcion(String idInscripcion, LocalDate fecha, EstadoRegistroIngreso estado, Cliente cliente, Horario horario) {
        this.idInscripcion = idInscripcion;
        this.fecha = fecha;
        this.estado = estado;
        this.cliente = cliente;
        this.horario = horario;
    }

    public String getIdInscripcion() {
        return idInscripcion;
    }

    public void setIdInscripcion(String idInscripcion) {
        this.idInscripcion = idInscripcion;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
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

    public Horario getHorario() {
        return horario;
    }

    public void setHorario(Horario horario) {
        this.horario = horario;
    }

    @Override
    public String toString() {
        return "Inscripcion{" + "idInscripcion=" + idInscripcion + ", fecha=" + fecha + ", estado=" + estado + ", cliente=" + cliente + ", horario=" + horario + '}';
    }
   
}

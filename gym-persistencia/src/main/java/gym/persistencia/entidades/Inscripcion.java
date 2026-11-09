package gym.persistencia.entidades;

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
public class Inscripcion implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @BsonId
    @BsonRepresentation(BsonType.OBJECT_ID)
    private String idInscripcion;
    private LocalDate fecha;
    private String estado;
    private Cliente cliente;
    private Horario horario;

    public Inscripcion() {
    }

    public Inscripcion(String idInscripcion, LocalDate fecha, String estado) {
        this.idInscripcion = idInscripcion;
        this.fecha = fecha;
        this.estado = estado;
    }

    public Inscripcion(String idInscripcion, LocalDate fecha, String estado, Cliente cliente, Horario horario) {
        this.idInscripcion = idInscripcion;
        this.fecha = fecha;
        this.estado = estado;
        this.cliente = cliente;
        this.horario = horario;
    }

    // Métodos del diagrama
    public void registrarInscripcion() {
        this.estado = "ACTIVA";
    }

    public void cancelarInscripcion() {
        this.estado = "CANCELADA";
    }

    public List<Inscripcion> consultarInscripciones() {
        return Collections.singletonList(this);
    }

    // Getters y Setters
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

    public Horario getHorario() {
        return horario;
    }

    public void setHorario(Horario horario) {
        this.horario = horario;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Inscripcion that = (Inscripcion) o;
        return idInscripcion == that.idInscripcion;
    }

    @Override
    public int hashCode() {
        return Objects.hash(idInscripcion);
    }

    @Override
    public String toString() {
        return "Inscripcion{" +
                "idInscripcion=" + idInscripcion +
                ", fecha=" + fecha +
                ", estado='" + estado + '\'' +
                ", cliente=" + (cliente != null ? cliente.getNombre() : "N/A") +
                ", horario=" + (horario != null ? horario.getIdHorario() : "N/A") +
                '}';
    }
}

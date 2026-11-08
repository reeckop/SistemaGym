package gym.persistencia.entidades;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * Entidad que modela un horario específico para la impartición de cursos.
 */
public class Horario implements Serializable {
    private static final long serialVersionUID = 1L;

    private int idHorario;
    private LocalDate fecha;
    private LocalTime horaInicio;
    private LocalTime horaFin;

    public Horario() {
    }

    public Horario(int idHorario, LocalDate fecha, LocalTime horaInicio, LocalTime horaFin) {
        this.idHorario = idHorario;
        this.fecha = fecha;
        this.horaInicio = horaInicio;
        this.horaFin = horaFin;
    }

    // Métodos del diagrama
    public boolean verificarDisponibilidad() {
        return horaInicio != null && horaFin != null && horaInicio.isBefore(horaFin);
    }

    public List<Horario> consultarHorarios() {
        return Collections.singletonList(this);
    }

    // Getters y Setters
    public int getIdHorario() {
        return idHorario;
    }

    public void setIdHorario(int idHorario) {
        this.idHorario = idHorario;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public LocalTime getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(LocalTime horaInicio) {
        this.horaInicio = horaInicio;
    }

    public LocalTime getHoraFin() {
        return horaFin;
    }

    public void setHoraFin(LocalTime horaFin) {
        this.horaFin = horaFin;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Horario horario = (Horario) o;
        return idHorario == horario.idHorario;
    }

    @Override
    public int hashCode() {
        return Objects.hash(idHorario);
    }

    @Override
    public String toString() {
        return "Horario{" +
                "idHorario=" + idHorario +
                ", fecha=" + fecha +
                ", horaInicio=" + horaInicio +
                ", horaFin=" + horaFin +
                '}';
    }
}

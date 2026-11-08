package gym.persistencia.entidades;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * Entidad que representa una clase o curso ofertado en el gimnasio.
 */
public class Curso implements Serializable {
    private static final long serialVersionUID = 1L;

    private int idCurso;
    private String nombre;
    private double precio;
    private int cupo;
    private String imagen;
    private List<Horario> horarios = new ArrayList<>();

    public Curso() {
    }

    public Curso(int idCurso, String nombre, double precio, int cupo, String imagen) {
        this.idCurso = idCurso;
        this.nombre = nombre;
        this.precio = precio;
        this.cupo = cupo;
        this.imagen = imagen;
        this.horarios = new ArrayList<>();
    }

    public Curso(int idCurso, String nombre, double precio, int cupo, String imagen, List<Horario> horarios) {
        this.idCurso = idCurso;
        this.nombre = nombre;
        this.precio = precio;
        this.cupo = cupo;
        this.imagen = imagen;
        this.horarios = horarios != null ? new ArrayList<>(horarios) : new ArrayList<>();
    }

    // Métodos del diagrama
    public void agregar() {
        // Ciclo de vida
    }

    public void editar() {
        // Ciclo de vida
    }

    public void eliminar() {
        // Ciclo de vida
    }

    public List<Curso> consultarCursos() {
        return Collections.singletonList(this);
    }

    public List<Horario> consultarHorarios() {
        return this.horarios;
    }

    public boolean verificarCupo() {
        return this.cupo > 0;
    }

    public void agregarHorario(Horario horario) {
        if (horario != null) {
            this.horarios.add(horario);
        }
    }

    // Getters y Setters
    public int getIdCurso() {
        return idCurso;
    }

    public void setIdCurso(int idCurso) {
        this.idCurso = idCurso;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public int getCupo() {
        return cupo;
    }

    public void setCupo(int cupo) {
        this.cupo = cupo;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public List<Horario> getHorarios() {
        return horarios;
    }

    public void setHorarios(List<Horario> horarios) {
        this.horarios = horarios != null ? new ArrayList<>(horarios) : new ArrayList<>();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Curso curso = (Curso) o;
        return idCurso == curso.idCurso;
    }

    @Override
    public int hashCode() {
        return Objects.hash(idCurso);
    }

    @Override
    public String toString() {
        return "Curso{" +
                "idCurso=" + idCurso +
                ", nombre='" + nombre + '\'' +
                ", precio=" + precio +
                ", cupo=" + cupo +
                ", horariosCount=" + (horarios != null ? horarios.size() : 0) +
                '}';
    }
}

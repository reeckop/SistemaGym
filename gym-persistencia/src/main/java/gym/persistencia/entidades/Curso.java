package gym.persistencia.entidades;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import org.bson.BsonType;
import org.bson.codecs.pojo.annotations.BsonId;
import org.bson.codecs.pojo.annotations.BsonRepresentation;

public class Curso implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @BsonId
    @BsonRepresentation(BsonType.OBJECT_ID)
    private String idCurso;
    private String nombre;
    private String disciplina;
    private double precio;
    private int cupo;
    private String imagen;
    private List<Horario> horarios = new ArrayList<>();

    public Curso() {
    }

    public Curso(String idCurso, String nombre, double precio, int cupo, String imagen) {
        this.idCurso = idCurso;
        this.nombre = nombre;
        this.disciplina = "";
        this.precio = precio;
        this.cupo = cupo;
        this.imagen = imagen;
        this.horarios = new ArrayList<>();
    }

    public Curso(String idCurso, String nombre, String disciplina, double precio, int cupo, String imagen) {
        this.idCurso = idCurso;
        this.nombre = nombre;
        this.disciplina = disciplina;
        this.precio = precio;
        this.cupo = cupo;
        this.imagen = imagen;
        this.horarios = new ArrayList<>();
    }

    public Curso(String idCurso, String nombre, String disciplina, double precio, int cupo, String imagen, List<Horario> horarios) {
        this.idCurso = idCurso;
        this.nombre = nombre;
        this.disciplina = disciplina;
        this.precio = precio;
        this.cupo = cupo;
        this.imagen = imagen;
        this.horarios = horarios != null ? new ArrayList<>(horarios) : new ArrayList<>();
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

    public String getIdCurso() {
        return idCurso;
    }

    public void setIdCurso(String idCurso) {
        this.idCurso = idCurso;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDisciplina() {
        return disciplina;
    }

    public void setDisciplina(String disciplina) {
        this.disciplina = disciplina;
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
                ", disciplina='" + disciplina + '\'' +
                ", precio=" + precio +
                ", cupo=" + cupo +
                ", horariosCount=" + (horarios != null ? horarios.size() : 0) +
                '}';
    }
}

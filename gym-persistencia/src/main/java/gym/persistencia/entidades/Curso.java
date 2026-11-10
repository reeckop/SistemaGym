package gym.persistencia.entidades;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import org.bson.BsonType;
import org.bson.codecs.pojo.annotations.BsonId;
import org.bson.codecs.pojo.annotations.BsonRepresentation;

public class Curso {
    
    @BsonId
    @BsonRepresentation(BsonType.OBJECT_ID)
    private String idCurso;
    
    private String nombre;
    private double precio;
    private int cupo;
    private String imagen;
    private List<Horario> horarios = new ArrayList<>();

    public Curso() {
    }

    public Curso(String idCurso, String nombre, double precio, int cupo, String imagen) {
        this.idCurso = idCurso;
        this.nombre = nombre;
        this.precio = precio;
        this.cupo = cupo;
        this.imagen = imagen;
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
        this.horarios = horarios;
    }

    @Override
    public String toString() {
        return "Curso{" + "idCurso=" + idCurso + ", nombre=" + nombre + ", precio=" + precio + ", cupo=" + cupo + ", imagen=" + imagen + ", horarios=" + horarios + '}';
    }

}

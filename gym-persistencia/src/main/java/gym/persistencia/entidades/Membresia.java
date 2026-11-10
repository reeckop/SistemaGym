package gym.persistencia.entidades;

import gym.persistencia.enums.EstadoMembresia;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import org.bson.BsonType;
import org.bson.codecs.pojo.annotations.BsonId;
import org.bson.codecs.pojo.annotations.BsonRepresentation;

public class Membresia {
    
    @BsonId
    @BsonRepresentation(BsonType.OBJECT_ID)
    private String idMembresia;
    
    private String nombre;
    private double precio;
    private String imagen;
    private EstadoMembresia estado;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private List<Beneficio> beneficios;

    public Membresia() {
    }

    public Membresia(String idMembresia, String nombre, double precio, String imagen, EstadoMembresia estado, LocalDate fechaInicio, LocalDate fechaFin, List<Beneficio> beneficios) {
        this.idMembresia = idMembresia;
        this.nombre = nombre;
        this.precio = precio;
        this.imagen = imagen;
        this.estado = estado;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.beneficios = beneficios;
    }

    public Membresia(String idMembresia, String nombre, double precio, String imagen, EstadoMembresia estado, LocalDate fechaInicio, LocalDate fechaFin) {
        this.idMembresia = idMembresia;
        this.nombre = nombre;
        this.precio = precio;
        this.imagen = imagen;
        this.estado = estado;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
    }

    public String getIdMembresia() {
        return idMembresia;
    }

    public void setIdMembresia(String idMembresia) {
        this.idMembresia = idMembresia;
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

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public EstadoMembresia getEstado() {
        return estado;
    }

    public void setEstado(EstadoMembresia estado) {
        this.estado = estado;
    }

    public LocalDate getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(LocalDate fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public LocalDate getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(LocalDate fechaFin) {
        this.fechaFin = fechaFin;
    }

    public List<Beneficio> getBeneficios() {
        return beneficios;
    }

    public void setBeneficios(List<Beneficio> beneficios) {
        this.beneficios = beneficios;
    }

    @Override
    public String toString() {
        return "Membresia{" + "idMembresia=" + idMembresia + ", nombre=" + nombre + ", precio=" + precio + ", imagen=" + imagen + ", estado=" + estado + ", fechaInicio=" + fechaInicio + ", fechaFin=" + fechaFin + ", beneficios=" + beneficios + '}';
    }
   
}

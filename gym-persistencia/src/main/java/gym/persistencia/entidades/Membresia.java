package gym.persistencia.entidades;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import org.bson.BsonType;
import org.bson.codecs.pojo.annotations.BsonId;
import org.bson.codecs.pojo.annotations.BsonRepresentation;

public class Membresia implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @BsonId
    @BsonRepresentation(BsonType.OBJECT_ID)
    private int idMembresia;
    private String nombre;
    private double precio;
    private String imagen;
    private String estado;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private List<Beneficio> beneficios = new ArrayList<>();

    public Membresia() {
        this.estado = "ACTIVA";
    }

    public Membresia(int idMembresia, String nombre, double precio) {
        this.idMembresia = idMembresia;
        this.nombre = nombre;
        this.precio = precio;
        this.estado = "ACTIVA";
        this.beneficios = new ArrayList<>();
    }

    public Membresia(int idMembresia, String nombre, double precio, String imagen, String estado, LocalDate fechaInicio, LocalDate fechaFin) {
        this.idMembresia = idMembresia;
        this.nombre = nombre;
        this.precio = precio;
        this.imagen = imagen;
        this.estado = estado;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.beneficios = new ArrayList<>();
    }

    public Membresia(int idMembresia, String nombre, double precio, String imagen, String estado, LocalDate fechaInicio, LocalDate fechaFin, List<Beneficio> beneficios) {
        this.idMembresia = idMembresia;
        this.nombre = nombre;
        this.precio = precio;
        this.imagen = imagen;
        this.estado = estado;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.beneficios = beneficios != null ? new ArrayList<>(beneficios) : new ArrayList<>();
    }

    public void agregar() {
    }

    public void editar() {
    }

    public void eliminar() {
    }

    public List<Membresia> consultarMembresias() {
        return Collections.singletonList(this);
    }

    public void agregarBeneficio(Beneficio beneficio) {
        if (beneficio != null) {
            this.beneficios.add(beneficio);
        }
    }

    public void removerBeneficio(Beneficio beneficio) {
        this.beneficios.remove(beneficio);
    }

    public int getIdMembresia() {
        return idMembresia;
    }

    public void setIdMembresia(int idMembresia) {
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

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
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
        this.beneficios = beneficios != null ? new ArrayList<>(beneficios) : new ArrayList<>();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Membresia membresia = (Membresia) o;
        return idMembresia == membresia.idMembresia;
    }

    @Override
    public int hashCode() {
        return Objects.hash(idMembresia);
    }

    @Override
    public String toString() {
        return "Membresia{" +
                "idMembresia=" + idMembresia +
                ", nombre='" + nombre + '\'' +
                ", precio=" + precio +
                ", estado='" + estado + '\'' +
                ", beneficiosCount=" + (beneficios != null ? beneficios.size() : 0) +
                '}';
    }
}

package gym.persistencia.entidades;

import java.io.Serializable;
import java.util.Objects;
import org.bson.BsonType;
import org.bson.codecs.pojo.annotations.BsonId;
import org.bson.codecs.pojo.annotations.BsonRepresentation;

/**
 * Entidad que representa un beneficio otorgado por una membresía.
 */
public class Beneficio implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @BsonId
    @BsonRepresentation(BsonType.OBJECT_ID)
    private String idBeneficio;
    
    private String nombre;
    private double valor;
    private String unidad;

    public Beneficio() {
    }

    public Beneficio(String idBeneficio, String nombre, double valor, String unidad) {
        this.idBeneficio = idBeneficio;
        this.nombre = nombre;
        this.valor = valor;
        this.unidad = unidad;
    }

    /**
     * Valida que el beneficio tenga datos consistentes.
     * @return true si el beneficio es válido.
     */
    public boolean validarBeneficio() {
        return nombre != null && !nombre.trim().isEmpty() && valor >= 0;
    }

    /**
     * Calcula o retorna el valor del beneficio aplicable.
     * @return el valor del beneficio.
     */
    public double aplicarBeneficio() {
        return this.valor;
    }

    // Getters y Setters
    public String getIdBeneficio() {
        return idBeneficio;
    }

    public void setIdBeneficio(String idBeneficio) {
        this.idBeneficio = idBeneficio;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public String getUnidad() {
        return unidad;
    }

    public void setUnidad(String unidad) {
        this.unidad = unidad;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Beneficio beneficio = (Beneficio) o;
        return idBeneficio == beneficio.idBeneficio;
    }

    @Override
    public int hashCode() {
        return Objects.hash(idBeneficio);
    }

    @Override
    public String toString() {
        return "Beneficio{" +
                "idBeneficio=" + idBeneficio +
                ", nombre='" + nombre + '\'' +
                ", valor=" + valor +
                ", unidad='" + unidad + '\'' +
                '}';
    }
}

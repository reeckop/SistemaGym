package gym.persistencia.entidades;

import java.io.Serializable;
import java.util.Objects;
import org.bson.BsonType;
import org.bson.codecs.pojo.annotations.BsonId;
import org.bson.codecs.pojo.annotations.BsonRepresentation;

/**
 * Entidad que representa la forma de pago utilizada (Efectivo, Tarjeta, Transferencia, etc.).
 */
public class MetodoPago implements Serializable {
    private static final long serialVersionUID = 1L;

    @BsonId
    @BsonRepresentation(BsonType.OBJECT_ID)
    private String idMetodoPago;
    private String nombre;

    public MetodoPago() {
    }

    public MetodoPago(String idMetodoPago, String nombre) {
        this.idMetodoPago = idMetodoPago;
        this.nombre = nombre;
    }

    // Getters y Setters
    public String getIdMetodoPago() {
        return idMetodoPago;
    }

    public void setIdMetodoPago(String idMetodoPago) {
        this.idMetodoPago = idMetodoPago;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MetodoPago that = (MetodoPago) o;
        return idMetodoPago == that.idMetodoPago;
    }

    @Override
    public int hashCode() {
        return Objects.hash(idMetodoPago);
    }

    @Override
    public String toString() {
        return "MetodoPago{" +
                "idMetodoPago=" + idMetodoPago +
                ", nombre='" + nombre + '\'' +
                '}';
    }
}

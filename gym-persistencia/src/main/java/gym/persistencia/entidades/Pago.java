package gym.persistencia.entidades;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;
import org.bson.BsonType;
import org.bson.codecs.pojo.annotations.BsonId;
import org.bson.codecs.pojo.annotations.BsonRepresentation;

/**
 * Entidad que modela un pago efectuado por una transacción o venta.
 */
public class Pago implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @BsonId
    @BsonRepresentation(BsonType.OBJECT_ID)
    private int idPago;
    private double monto;
    private LocalDateTime fechaHora;
    private String estado;
    private MetodoPago metodoPago;

    public Pago() {
    }

    public Pago(int idPago, double monto, LocalDateTime fechaHora, String estado) {
        this.idPago = idPago;
        this.monto = monto;
        this.fechaHora = fechaHora;
        this.estado = estado;
    }

    public Pago(int idPago, double monto, LocalDateTime fechaHora, String estado, MetodoPago metodoPago) {
        this.idPago = idPago;
        this.monto = monto;
        this.fechaHora = fechaHora;
        this.estado = estado;
        this.metodoPago = metodoPago;
    }

    // Métodos del diagrama
    public void registrarPago() {
        if (this.fechaHora == null) {
            this.fechaHora = LocalDateTime.now();
        }
        this.estado = "REGISTRADO";
    }

    public void confirmarPago() {
        this.estado = "CONFIRMADO";
    }

    // Getters y Setters
    public int getIdPago() {
        return idPago;
    }

    public void setIdPago(int idPago) {
        this.idPago = idPago;
    }

    public double getMonto() {
        return monto;
    }

    public void setMonto(double monto) {
        this.monto = monto;
    }

    public LocalDateTime getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(LocalDateTime fechaHora) {
        this.fechaHora = fechaHora;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public MetodoPago getMetodoPago() {
        return metodoPago;
    }

    public void setMetodoPago(MetodoPago metodoPago) {
        this.metodoPago = metodoPago;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pago pago = (Pago) o;
        return idPago == pago.idPago;
    }

    @Override
    public int hashCode() {
        return Objects.hash(idPago);
    }

    @Override
    public String toString() {
        return "Pago{" +
                "idPago=" + idPago +
                ", monto=" + monto +
                ", fechaHora=" + fechaHora +
                ", estado='" + estado + '\'' +
                ", metodoPago=" + (metodoPago != null ? metodoPago.getNombre() : "N/A") +
                '}';
    }
}

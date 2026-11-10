package gym.persistencia.entidades;

import gym.persistencia.enums.EstadoPago;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;
import org.bson.BsonType;
import org.bson.codecs.pojo.annotations.BsonId;
import org.bson.codecs.pojo.annotations.BsonRepresentation;

/**
 * Entidad que modela un pago efectuado por una transacción o venta.
 */
public class Pago {
    
    @BsonId
    @BsonRepresentation(BsonType.OBJECT_ID)
    private String idPago;
    
    private double monto;
    private LocalDateTime fechaHora;
    private EstadoPago estado;
    private MetodoPago metodoPago;

    public Pago() {
    }

    public Pago(String idPago, double monto, LocalDateTime fechaHora, EstadoPago estado) {
        this.idPago = idPago;
        this.monto = monto;
        this.fechaHora = fechaHora;
        this.estado = estado;
    }

    public Pago(String idPago, double monto, LocalDateTime fechaHora, EstadoPago estado, MetodoPago metodoPago) {
        this.idPago = idPago;
        this.monto = monto;
        this.fechaHora = fechaHora;
        this.estado = estado;
        this.metodoPago = metodoPago;
    }

    public String getIdPago() {
        return idPago;
    }

    public void setIdPago(String idPago) {
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

    public EstadoPago getEstado() {
        return estado;
    }

    public void setEstado(EstadoPago estado) {
        this.estado = estado;
    }

    public MetodoPago getMetodoPago() {
        return metodoPago;
    }

    public void setMetodoPago(MetodoPago metodoPago) {
        this.metodoPago = metodoPago;
    }

    @Override
    public String toString() {
        return "Pago{" + "idPago=" + idPago + ", monto=" + monto + ", fechaHora=" + fechaHora + ", estado=" + estado + ", metodoPago=" + metodoPago + '}';
    }

}

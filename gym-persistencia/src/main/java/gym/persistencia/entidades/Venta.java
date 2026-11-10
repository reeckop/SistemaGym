package gym.persistencia.entidades;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import org.bson.BsonType;
import org.bson.codecs.pojo.annotations.BsonId;
import org.bson.codecs.pojo.annotations.BsonRepresentation;

/**
 * Entidad que modela una venta realizada en el gimnasio.
 */
public class Venta {
    
    @BsonId
    @BsonRepresentation(BsonType.OBJECT_ID)
    private String idVenta;
    
    private LocalDateTime fechaHora;
    private double subtotal;
    private double descuento;
    private double total;
    private Cliente cliente;
    private List<DetalleVenta> detalles = new ArrayList<>();
    private Pago pago;

    public Venta() {
    }

    public Venta(String idVenta, LocalDateTime fechaHora, double subtotal, double descuento, double total) {
        this.idVenta = idVenta;
        this.fechaHora = fechaHora;
        this.subtotal = subtotal;
        this.descuento = descuento;
        this.total = total;
    }

    public Venta(String idVenta, Cliente cliente) {
        this.idVenta = idVenta;
        this.cliente = cliente;
        this.fechaHora = LocalDateTime.now();
        this.detalles = new ArrayList<>();
    }

    public Venta(String idVenta, LocalDateTime fechaHora, double subtotal, double descuento, double total, Cliente cliente, Pago pago) {
        this.idVenta = idVenta;
        this.fechaHora = fechaHora;
        this.subtotal = subtotal;
        this.descuento = descuento;
        this.total = total;
        this.cliente = cliente;
        this.pago = pago;
    }

    public String getIdVenta() {
        return idVenta;
    }

    public void setIdVenta(String idVenta) {
        this.idVenta = idVenta;
    }

    public LocalDateTime getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(LocalDateTime fechaHora) {
        this.fechaHora = fechaHora;
    }

    public double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(double subtotal) {
        this.subtotal = subtotal;
    }

    public double getDescuento() {
        return descuento;
    }

    public void setDescuento(double descuento) {
        this.descuento = descuento;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public List<DetalleVenta> getDetalles() {
        return detalles;
    }

    public void setDetalles(List<DetalleVenta> detalles) {
        this.detalles = detalles;
    }

    public Pago getPago() {
        return pago;
    }

    public void setPago(Pago pago) {
        this.pago = pago;
    }

    @Override
    public String toString() {
        return "Venta{" + "idVenta=" + idVenta + ", fechaHora=" + fechaHora + ", subtotal=" + subtotal + ", descuento=" + descuento + ", total=" + total + ", cliente=" + cliente + ", detalles=" + detalles + ", pago=" + pago + '}';
    }
     
}

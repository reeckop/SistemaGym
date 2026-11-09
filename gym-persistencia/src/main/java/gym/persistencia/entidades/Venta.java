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
public class Venta implements Serializable {
    private static final long serialVersionUID = 1L;
    
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
        this.fechaHora = LocalDateTime.now();
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

    // Métodos del diagrama
    public void agregarProducto(Producto producto, int cantidad) {
        if (producto != null && cantidad > 0) {
            DetalleVenta detalle = new DetalleVenta(producto, cantidad);
            this.detalles.add(detalle);
            calcularTotal();
        }
    }

    public double calcularTotal() {
        this.subtotal = 0.0;
        if (detalles != null) {
            for (DetalleVenta d : detalles) {
                this.subtotal += d.calcularSubtotal();
            }
        }
        aplicarDescuento();
        this.total = Math.max(0.0, this.subtotal - this.descuento);
        return this.total;
    }

    public double aplicarDescuento() {
        if (cliente != null && cliente.getMembresia() != null && cliente.getMembresia().getBeneficios() != null) {
            double desc = 0.0;
            for (Beneficio b : cliente.getMembresia().getBeneficios()) {
                if ("DESCUENTO".equalsIgnoreCase(b.getUnidad()) || "%".equalsIgnoreCase(b.getUnidad())) {
                    desc += (this.subtotal * (b.getValor() / 100.0));
                } else if ("MONTO".equalsIgnoreCase(b.getUnidad()) || "$".equalsIgnoreCase(b.getUnidad())) {
                    desc += b.getValor();
                }
            }
            this.descuento = desc;
        }
        return this.descuento;
    }

    public void confirmarVenta() {
        calcularTotal();
        if (this.pago != null) {
            this.pago.confirmarPago();
        }
    }

    public List<Venta> consultarVentas() {
        return Collections.singletonList(this);
    }

    // Getters y Setters
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
        this.detalles = detalles != null ? new ArrayList<>(detalles) : new ArrayList<>();
        calcularTotal();
    }

    public Pago getPago() {
        return pago;
    }

    public void setPago(Pago pago) {
        this.pago = pago;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Venta venta = (Venta) o;
        return idVenta == venta.idVenta;
    }

    @Override
    public int hashCode() {
        return Objects.hash(idVenta);
    }

    @Override
    public String toString() {
        return "Venta{" +
                "idVenta=" + idVenta +
                ", fechaHora=" + fechaHora +
                ", subtotal=" + subtotal +
                ", descuento=" + descuento +
                ", total=" + total +
                ", cliente=" + (cliente != null ? cliente.getNombre() : "N/A") +
                ", detallesCount=" + (detalles != null ? detalles.size() : 0) +
                ", pago=" + (pago != null ? pago.getEstado() : "Sin pago") +
                '}';
    }
}

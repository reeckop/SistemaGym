package gym.persistencia.entidades;

import java.io.Serializable;
import java.util.Objects;

/**
 * Entidad que modela una línea de detalle en una venta de productos.
 */
public class DetalleVenta implements Serializable {
    private static final long serialVersionUID = 1L;

    private Producto producto;
    private int cantidad;
    private double precioUnitario;
    private double subtotal;

    public DetalleVenta() {
    }

    public DetalleVenta(int cantidad, double precioUnitario) {
        this.cantidad = cantidad;
        this.precioUnitario = precioUnitario;
        this.subtotal = calcularSubtotal();
    }

    public DetalleVenta(Producto producto, int cantidad, double precioUnitario) {
        this.producto = producto;
        this.cantidad = cantidad;
        this.precioUnitario = precioUnitario;
        this.subtotal = calcularSubtotal();
    }

    public DetalleVenta(Producto producto, int cantidad) {
        this.producto = producto;
        this.cantidad = cantidad;
        this.precioUnitario = producto != null ? producto.getPrecio() : 0.0;
        this.subtotal = calcularSubtotal();
    }

    // Método del diagrama
    public double calcularSubtotal() {
        this.subtotal = this.cantidad * this.precioUnitario;
        return this.subtotal;
    }

    // Getters y Setters
    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
        if (producto != null && this.precioUnitario == 0) {
            this.precioUnitario = producto.getPrecio();
            calcularSubtotal();
        }
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
        calcularSubtotal();
    }

    public double getPrecioUnitario() {
        return precioUnitario;
    }

    public void setPrecioUnitario(double precioUnitario) {
        this.precioUnitario = precioUnitario;
        calcularSubtotal();
    }

    public double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(double subtotal) {
        this.subtotal = subtotal;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DetalleVenta that = (DetalleVenta) o;
        return cantidad == that.cantidad &&
                Double.compare(that.precioUnitario, precioUnitario) == 0 &&
                Objects.equals(producto, that.producto);
    }

    @Override
    public int hashCode() {
        return Objects.hash(producto, cantidad, precioUnitario);
    }

    @Override
    public String toString() {
        return "DetalleVenta{" +
                "producto=" + (producto != null ? producto.getNombre() : "N/A") +
                ", cantidad=" + cantidad +
                ", precioUnitario=" + precioUnitario +
                ", subtotal=" + subtotal +
                '}';
    }
}

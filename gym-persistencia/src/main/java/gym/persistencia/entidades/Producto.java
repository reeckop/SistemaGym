package gym.persistencia.entidades;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class Producto implements Serializable {
    private static final long serialVersionUID = 1L;

    private int idProducto;
    private String nombre;
    private String categoria;
    private double precio;
    private int stock;
    private String imagen;

    public Producto() {
    }

    public Producto(int idProducto, String nombre, String categoria, double precio, String imagen) {
        this.idProducto = idProducto;
        this.nombre = nombre;
        this.categoria = categoria;
        this.precio = precio;
        this.stock = 0;
        this.imagen = imagen;
    }

    public Producto(int idProducto, String nombre, String categoria, double precio, int stock, String imagen) {
        this.idProducto = idProducto;
        this.nombre = nombre;
        this.categoria = categoria;
        this.precio = precio;
        this.stock = stock;
        this.imagen = imagen;
    }

    public boolean consultarDisponibilidad() {
        return this.stock > 0;
    }

    public boolean consultarDisponibilidad(int cantidad) {
        return this.stock >= cantidad;
    }

    public void actualizarPrecio(double precio) {
        this.precio = precio;
    }

    public void agregar() {
    }

    public void editar() {
    }

    public void eliminar() {
    }

    public List<Producto> consultarProductos() {
        return Collections.singletonList(this);
    }

    public int getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(int idProducto) {
        this.idProducto = idProducto;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Producto producto = (Producto) o;
        return idProducto == producto.idProducto;
    }

    @Override
    public int hashCode() {
        return Objects.hash(idProducto);
    }

    @Override
    public String toString() {
        return "Producto{" +
                "idProducto=" + idProducto +
                ", nombre='" + nombre + '\'' +
                ", categoria='" + categoria + '\'' +
                ", precio=" + precio +
                ", stock=" + stock +
                ", imagen='" + imagen + '\'' +
                '}';
    }
}

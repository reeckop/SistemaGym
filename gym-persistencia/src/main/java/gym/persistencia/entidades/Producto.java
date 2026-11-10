package gym.persistencia.entidades;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import org.bson.BsonType;
import org.bson.codecs.pojo.annotations.BsonId;
import org.bson.codecs.pojo.annotations.BsonRepresentation;

public class Producto {

    @BsonId
    @BsonRepresentation(BsonType.OBJECT_ID)
    private String idProducto;
    
    private String nombre;
    private String categoria;
    private double precio;
    private int stock;
    private String imagen;

    public Producto() {
    }

    public Producto(String idProducto, String nombre, String categoria, double precio, int stock, String imagen) {
        this.idProducto = idProducto;
        this.nombre = nombre;
        this.categoria = categoria;
        this.precio = precio;
        this.stock = stock;
        this.imagen = imagen;
    }

    public Producto(String idProducto, String nombre, String categoria, double precio, int stock) {
        this.idProducto = idProducto;
        this.nombre = nombre;
        this.categoria = categoria;
        this.precio = precio;
        this.stock = stock;
    }

    public String getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(String idProducto) {
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
    public String toString() {
        return "Producto{" + "idProducto=" + idProducto + ", nombre=" + nombre + ", categoria=" + categoria + ", precio=" + precio + ", stock=" + stock + ", imagen=" + imagen + '}';
    }

}

package gym.persistencia.entidades;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import org.bson.BsonType;
import org.bson.codecs.pojo.annotations.BsonId;
import org.bson.codecs.pojo.annotations.BsonRepresentation;

/**
 * Entidad que representa un cliente/socio registrado en el gimnasio.
 */
public class Cliente {
    
    @BsonId
    @BsonRepresentation(BsonType.OBJECT_ID)
    private String idCliente;
    
    private String nombre;
    private String apellido;
    private String telefono;
    private LocalDate fechaIngreso;
    private Membresia membresia;

    public Cliente() {
    }

    public Cliente(String idCliente, String nombre, String apellido, String telefono, LocalDate fechaIngreso) {
        this.idCliente = idCliente;
        this.nombre = nombre;
        this.apellido = apellido;
        this.telefono = telefono;
        this.fechaIngreso = fechaIngreso;
    }

    public Cliente(String idCliente, String nombre, String apellido, String telefono, LocalDate fechaIngreso, Membresia membresia) {
        this.idCliente = idCliente;
        this.nombre = nombre;
        this.apellido = apellido;
        this.telefono = telefono;
        this.fechaIngreso = fechaIngreso;
        this.membresia = membresia;
    }

    public String getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(String idCliente) {
        this.idCliente = idCliente;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public LocalDate getFechaIngreso() {
        return fechaIngreso;
    }

    public void setFechaIngreso(LocalDate fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
    }

    public Membresia getMembresia() {
        return membresia;
    }

    public void setMembresia(Membresia membresia) {
        this.membresia = membresia;
    }

    @Override
    public String toString() {
        return "Cliente{" + "idCliente=" + idCliente + ", nombre=" + nombre + ", apellido=" + apellido + ", telefono=" + telefono + ", fechaIngreso=" + fechaIngreso + ", membresia=" + membresia + '}';
    }

    
}

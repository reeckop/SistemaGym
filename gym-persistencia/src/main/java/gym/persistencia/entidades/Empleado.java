/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gym.persistencia.entidades;

import org.bson.BsonType;
import org.bson.codecs.pojo.annotations.BsonId;
import org.bson.codecs.pojo.annotations.BsonRepresentation;

/**
 *
 * @author PC GAMER MASTER RACE
 */
public class Empleado {
    @BsonId
    @BsonRepresentation(BsonType.STRING)
    private String idEmpleado;
    
    private String nombre;
    private String apellido;
    private String telefono;
    private String nip;
    private String contrasena;
    private String fechaIngreso;
    private String puesto;

    public Empleado() {
    }

    public Empleado(String idEmpleado, String nombre, String apellido, String telefono, String nip, String contrasena, String fechaIngreso, String puesto) {
        this.idEmpleado = idEmpleado;
        this.nombre = nombre;
        this.apellido = apellido;
        this.telefono = telefono;
        this.nip = nip;
        this.contrasena = contrasena;
        this.fechaIngreso = fechaIngreso;
        this.puesto = puesto;
    }

    public String getIdEmpleado() {
        return idEmpleado;
    }

    public void setIdEmpleado(String idEmpleado) {
        this.idEmpleado = idEmpleado;
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

    public String getNip() {
        return nip;
    }

    public void setNip(String nip) {
        this.nip = nip;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public String getFechaIngreso() {
        return fechaIngreso;
    }

    public void setFechaIngreso(String fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
    }

    public String getPuesto() {
        return puesto;
    }

    public void setPuesto(String puesto) {
        this.puesto = puesto;
    }

    @Override
    public String toString() {
        return "Empleado{" + "idEmpleado=" + idEmpleado + ", nombre=" + nombre + ", apellido=" + apellido + ", telefono=" + telefono + ", nip=" + nip + ", contrasena=" + contrasena + ", fechaIngreso=" + fechaIngreso + ", puesto=" + puesto + '}';
    }
    
}

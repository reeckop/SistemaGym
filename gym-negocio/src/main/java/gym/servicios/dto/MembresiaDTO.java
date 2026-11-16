/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gym.servicios.dto;

import gym.servicios.enums.EstadoMembresia;
import java.time.LocalDate;
import java.util.List;

/**
 *
 * @author PC GAMER MASTER RACE
 */
public class MembresiaDTO {
    private String idMembresia;
    private String nombre;
    private double precio;
    private String imagen;
    private EstadoMembresia estado;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private List<BeneficioDTO> beneficios;

    public MembresiaDTO() {
    }

    public MembresiaDTO(String idMembresia, String nombre, double precio, String imagen, EstadoMembresia estado, LocalDate fechaInicio, LocalDate fechaFin, List<BeneficioDTO> beneficios) {
        this.idMembresia = idMembresia;
        this.nombre = nombre;
        this.precio = precio;
        this.imagen = imagen;
        this.estado = estado;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.beneficios = beneficios;
    }

    public String getIdMembresia() {
        return idMembresia;
    }

    public void setIdMembresia(String idMembresia) {
        this.idMembresia = idMembresia;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public EstadoMembresia getEstado() {
        return estado;
    }

    public void setEstado(EstadoMembresia estado) {
        this.estado = estado;
    }

    public LocalDate getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(LocalDate fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public LocalDate getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(LocalDate fechaFin) {
        this.fechaFin = fechaFin;
    }

    public List<BeneficioDTO> getBeneficios() {
        return beneficios;
    }

    public void setBeneficios(List<BeneficioDTO> beneficios) {
        this.beneficios = beneficios;
    }
}
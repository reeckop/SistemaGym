/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gym.servicios.dto;

/**
 *
 * @author PC GAMER MASTER RACE
 */
public class BeneficioDTO {
    private String idBeneficio;
    private String nombre;
    private double valor;
    private String unidad;

    public BeneficioDTO() {
    }

    public BeneficioDTO(String idBeneficio, String nombre, double valor, String unidad) {
        this.idBeneficio = idBeneficio;
        this.nombre = nombre;
        this.valor = valor;
        this.unidad = unidad;
    }

    public String getIdBeneficio() {
        return idBeneficio;
    }

    public void setIdBeneficio(String idBeneficio) {
        this.idBeneficio = idBeneficio;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public String getUnidad() {
        return unidad;
    }

    public void setUnidad(String unidad) {
        this.unidad = unidad;
    }
}

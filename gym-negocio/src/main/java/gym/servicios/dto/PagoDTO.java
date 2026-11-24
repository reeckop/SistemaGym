/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gym.servicios.dto;

import gym.servicios.enums.EstadoPago;
import java.time.LocalDateTime;

/**
 * Transporta los datos de pago entre las capas del sistema.
 *
 * @author PC GAMER MASTER RACE
 */
public class PagoDTO {
    private String idPago;
    private double monto;
    private LocalDateTime fechaHora;
    private EstadoPago estado;
    private MetodoPagoDTO metodoPago;

    public PagoDTO() {
    }

    public PagoDTO(String idPago, double monto, LocalDateTime fechaHora, EstadoPago estado, MetodoPagoDTO metodoPago) {
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

    public MetodoPagoDTO getMetodoPago() {
        return metodoPago;
    }

    public void setMetodoPago(MetodoPagoDTO metodoPago) {
        this.metodoPago = metodoPago;
    }
}
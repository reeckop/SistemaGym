/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gym.servicios.dto;

import gym.servicios.enums.EstadoRegistroIngreso;
import java.time.LocalDateTime;

/**
 * Transporta los datos de un registro de ingreso.
 *
 * @author PC GAMER MASTER RACE
 */
public class RegistroIngresoDTO {
    private String idAcceso;
    private LocalDateTime fechaHoraEntrada;
    private LocalDateTime fechaHoraSalida;
    private EstadoRegistroIngreso estado;
    private String idCliente;

    public RegistroIngresoDTO() {
    }

    public RegistroIngresoDTO(String idAcceso, LocalDateTime fechaHoraEntrada, LocalDateTime fechaHoraSalida, EstadoRegistroIngreso estado, String idCliente) {
        this.idAcceso = idAcceso;
        this.fechaHoraEntrada = fechaHoraEntrada;
        this.fechaHoraSalida = fechaHoraSalida;
        this.estado = estado;
        this.idCliente = idCliente;
    }

    public String getIdAcceso() {
        return idAcceso;
    }

    public void setIdAcceso(String idAcceso) {
        this.idAcceso = idAcceso;
    }

    public LocalDateTime getFechaHoraEntrada() {
        return fechaHoraEntrada;
    }

    public void setFechaHoraEntrada(LocalDateTime fechaHoraEntrada) {
        this.fechaHoraEntrada = fechaHoraEntrada;
    }

    public LocalDateTime getFechaHoraSalida() {
        return fechaHoraSalida;
    }

    public void setFechaHoraSalida(LocalDateTime fechaHoraSalida) {
        this.fechaHoraSalida = fechaHoraSalida;
    }

    public EstadoRegistroIngreso getEstado() {
        return estado;
    }

    public void setEstado(EstadoRegistroIngreso estado) {
        this.estado = estado;
    }

    public String getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(String idCliente) {
        this.idCliente = idCliente;
    }
}
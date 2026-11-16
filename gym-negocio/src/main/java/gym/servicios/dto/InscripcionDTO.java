/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gym.servicios.dto;

import gym.servicios.enums.EstadoInscripcion;
import java.time.LocalDate;

/**
 *
 * @author PC GAMER MASTER RACE
 */
public class InscripcionDTO {
    private String idInscripcion;
    private LocalDate fecha;
    private EstadoInscripcion estado;
    private String idCliente;
    private HorarioDTO horario;

    public InscripcionDTO() {
    }

    public InscripcionDTO(String idInscripcion, LocalDate fecha, EstadoInscripcion estado, String idCliente, HorarioDTO horario) {
        this.idInscripcion = idInscripcion;
        this.fecha = fecha;
        this.estado = estado;
        this.idCliente = idCliente;
        this.horario = horario;
    }

    public String getIdInscripcion() {
        return idInscripcion;
    }

    public void setIdInscripcion(String idInscripcion) {
        this.idInscripcion = idInscripcion;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public EstadoInscripcion getEstado() {
        return estado;
    }

    public void setEstado(EstadoInscripcion estado) {
        this.estado = estado;
    }

    public String getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(String idCliente) {
        this.idCliente = idCliente;
    }

    public HorarioDTO getHorario() {
        return horario;
    }

    public void setHorario(HorarioDTO horario) {
        this.horario = horario;
    }
}
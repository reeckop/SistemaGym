/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gym.servicios.dto;

import gym.servicios.enums.EstadoNotificacion;
import java.time.LocalDateTime;

/**
 *
 * @author PC GAMER MASTER RACE
 */
public class NotificacionDTO {
    private String idNotificacion;
    private String tipo;
    private String mensaje;
    private LocalDateTime fechaHora;
    private EstadoNotificacion estado;
    private String idCliente;

    public NotificacionDTO() {
    }

    public NotificacionDTO(String idNotificacion, String tipo, String mensaje, LocalDateTime fechaHora, EstadoNotificacion estado, String idCliente) {
        this.idNotificacion = idNotificacion;
        this.tipo = tipo;
        this.mensaje = mensaje;
        this.fechaHora = fechaHora;
        this.estado = estado;
        this.idCliente = idCliente;
    }

    public String getIdNotificacion() {
        return idNotificacion;
    }

    public void setIdNotificacion(String idNotificacion) {
        this.idNotificacion = idNotificacion;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public LocalDateTime getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(LocalDateTime fechaHora) {
        this.fechaHora = fechaHora;
    }

    public EstadoNotificacion getEstado() {
        return estado;
    }

    public void setEstado(EstadoNotificacion estado) {
        this.estado = estado;
    }

    public String getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(String idCliente) {
        this.idCliente = idCliente;
    }
    
}

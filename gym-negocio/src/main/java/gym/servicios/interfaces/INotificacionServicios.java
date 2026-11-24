/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package gym.servicios.interfaces;

import gym.excepciones.NegocioException;
import gym.servicios.dto.NotificacionDTO;
import java.util.List;

/**
 * Define las operaciones de negocio relacionadas con notificación.
 *
 * @author PC GAMER MASTER RACE
 */
public interface INotificacionServicios {
    void registrarNotificacion(NotificacionDTO notificacion) throws NegocioException;
    void actualizarNotificacion(NotificacionDTO notificacion) throws NegocioException;
    List<NotificacionDTO> consultarNotificacionesPorCliente(String idCliente) throws NegocioException;
    List<NotificacionDTO> consultarTodasLasNotificaciones() throws NegocioException;
}

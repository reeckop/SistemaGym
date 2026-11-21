/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gym.servicios.servicios;

import gym.excepciones.NegocioException;
import gym.persistencia.entidades.Cliente;
import gym.persistencia.entidades.Notificacion;
import gym.persistencia.excepciones.PersistenciaException;
import gym.persistencia.uow.NotificacionesUnitOfWork;
import gym.servicios.dto.NotificacionDTO;
import gym.servicios.interfaces.INotificacionServicios;
import gym.servicios.mappers.Mappers;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author PC GAMER MASTER RACE
 */
public class NotificacionServicios implements INotificacionServicios {

    private final NotificacionesUnitOfWork unidad;

    public NotificacionServicios(NotificacionesUnitOfWork unidad) {
        this.unidad = unidad;
    }

    @Override
    public void registrarNotificacion(NotificacionDTO notificacion) throws NegocioException {
        validarNotificacion(notificacion);

        try {
            Cliente cliente = unidad.clientes().obtenerPorId(notificacion.getIdCliente());

            if (cliente == null) {
                throw new NegocioException("El cliente no existe.");
            }

            unidad.notificaciones().guardar(Mappers.convertirAEntidad(notificacion, cliente));
        } catch (PersistenciaException e) {
            throw new NegocioException("No fue posible registrar la notificación.", e);
        }
    }

    @Override
    public void actualizarNotificacion(NotificacionDTO notificacion) throws NegocioException {
        validarNotificacion(notificacion);
        validarId(notificacion.getIdNotificacion());

        try {
            Cliente cliente = unidad.clientes().obtenerPorId(notificacion.getIdCliente());

            if (cliente == null) {
                throw new NegocioException("El cliente no existe.");
            }

            unidad.notificaciones().actualizarNotificacion(Mappers.convertirAEntidad(notificacion, cliente));
        } catch (PersistenciaException e) {
            throw new NegocioException("No fue posible actualizar la notificación.", e);
        }
    }

    @Override
    public List<NotificacionDTO> consultarNotificacionesPorCliente(String idCliente) throws NegocioException {

        validarId(idCliente);

        try {
            List<NotificacionDTO> resultado = new ArrayList<>();
            List<Notificacion> notificaciones =
                unidad.notificaciones().obtenerPorCliente(idCliente);

            if (notificaciones != null) {
                for (Notificacion notificacion : notificaciones) {
                    if (notificacion != null) {
                        resultado.add(Mappers.convertirADTO(notificacion));
                    }
                }
            }

            return resultado;
        } catch (PersistenciaException e) {
            throw new NegocioException("No fue posible consultar las notificaciones del cliente.", e);
        }
    }

    @Override
    public List<NotificacionDTO> consultarTodasLasNotificaciones() throws NegocioException {
        try {
            List<NotificacionDTO> resultado = new ArrayList<>();
            List<Notificacion> notificaciones = unidad.notificaciones().obtenerTodas();

            if (notificaciones != null) {
                for (Notificacion notificacion : notificaciones) {
                    if (notificacion != null) {
                        resultado.add(Mappers.convertirADTO(notificacion));
                    }
                }
            }

            return resultado;
        } catch (PersistenciaException e) {
            throw new NegocioException("No fue posible consultar las notificaciones.", e);
        }
    }

    private void validarNotificacion(NotificacionDTO notificacion) throws NegocioException {
        if (notificacion == null) {
            throw new NegocioException("Los datos de la notificación son obligatorios.");
        }

        if (notificacion.getTipo() == null || notificacion.getTipo().isBlank()) {
            throw new NegocioException("El tipo de notificación es obligatorio.");
        }

        if (notificacion.getMensaje() == null || notificacion.getMensaje().isBlank()) {
            throw new NegocioException("El mensaje es obligatorio.");
        }

        if (notificacion.getFechaHora() == null) {
            throw new NegocioException("La fecha de notificación es obligatoria.");
        }

        if (notificacion.getEstado() == null) {
            throw new NegocioException("El estado de la notificación es obligatorio.");
        }

        if (notificacion.getIdCliente() == null || notificacion.getIdCliente().isBlank()) {
            throw new NegocioException("El ID del cliente es obligatorio.");
        }
    }

    private void validarId(String id) throws NegocioException {
        if (id == null || id.isBlank()) {
            throw new NegocioException("El ID de notificación es obligatorio.");
        }
    }
}

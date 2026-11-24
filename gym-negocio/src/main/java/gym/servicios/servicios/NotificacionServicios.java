/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gym.servicios.servicios;

import gym.excepciones.NegocioException;
import gym.persistencia.entidades.Cliente;
import gym.persistencia.entidades.Notificacion;
import gym.persistencia.excepciones.PersistenciaException;
import gym.persistencia.unitofwork.IUnitOfWork;
import gym.persistencia.unitofwork.IUnitOfWorkFactory;
import gym.persistencia.unitofwork.UnitOfWorkFactory;
import gym.servicios.dto.NotificacionDTO;
import gym.servicios.interfaces.INotificacionServicios;
import gym.servicios.mappers.Mappers;
import java.util.ArrayList;
import java.util.List;

/**
 * Gestiona las reglas de negocio de notificación.
 *
 * @author PC GAMER MASTER RACE
 */
public class NotificacionServicios implements INotificacionServicios {
    private final IUnitOfWorkFactory unidadDeTrabajoFactory;

    public NotificacionServicios() {
        this(UnitOfWorkFactory.obtenerInstancia());
    }

    public NotificacionServicios(IUnitOfWorkFactory unidadDeTrabajoFactory) {
        this.unidadDeTrabajoFactory = java.util.Objects.requireNonNull(unidadDeTrabajoFactory);
    }

    @Override
    public void registrarNotificacion(NotificacionDTO notificacion) throws NegocioException {
        validarNotificacion(notificacion);

        IUnitOfWork unitOfWork = unidadDeTrabajoFactory.crear();

        try {
            Cliente cliente = unitOfWork.clienteDAO().obtenerPorId(notificacion.getIdCliente());

            if (cliente == null) {
                throw new NegocioException("El cliente no existe.");
            }

            unitOfWork.notificacionDAO().guardar(Mappers.convertirAEntidad(notificacion, cliente));
            unitOfWork.commit();

        } catch (PersistenciaException e) {
            unitOfWork.rollback();
            throw new NegocioException("No fue posible registrar la notificacion.", e);
        }
    }

    @Override
    public void actualizarNotificacion(NotificacionDTO notificacion) throws NegocioException {
        validarNotificacion(notificacion);
        validarId(notificacion.getIdNotificacion());

        IUnitOfWork unitOfWork = unidadDeTrabajoFactory.crear();

        try {
            Cliente cliente = unitOfWork.clienteDAO().obtenerPorId(notificacion.getIdCliente());

            if (cliente == null) {
                throw new NegocioException("El cliente no existe.");
            }

            unitOfWork.notificacionDAO().actualizarNotificacion(Mappers.convertirAEntidad(notificacion, cliente));
            unitOfWork.commit();

        } catch (PersistenciaException e) {
            unitOfWork.rollback();
            throw new NegocioException("No fue posible actualizar la notificacion.", e);
        }
    }

    @Override
    public List<NotificacionDTO> consultarNotificacionesPorCliente(String idCliente) throws NegocioException {

        validarId(idCliente);

        IUnitOfWork unitOfWork = unidadDeTrabajoFactory.crear();

        try {
            List<NotificacionDTO> resultado = new ArrayList<>();
            List<Notificacion> notificaciones = unitOfWork.notificacionDAO().obtenerPorCliente(idCliente);

            if (notificaciones != null) {
                for (Notificacion notificacion : notificaciones) {
                    if (notificacion != null) {
                        resultado.add(Mappers.convertirADTO(notificacion));
                    }
                }
            }

            unitOfWork.commit();
            return resultado;

        } catch (PersistenciaException e) {
            unitOfWork.rollback();
            throw new NegocioException("No fue posible consultar las notificaciones del cliente.", e);
        }
    }

    @Override
    public List<NotificacionDTO> consultarTodasLasNotificaciones() throws NegocioException {
        IUnitOfWork unitOfWork = unidadDeTrabajoFactory.crear();

        try {
            List<NotificacionDTO> resultado = new ArrayList<>();
            List<Notificacion> notificaciones = unitOfWork.notificacionDAO().obtenerTodas();

            if (notificaciones != null) {
                for (Notificacion notificacion : notificaciones) {
                    if (notificacion != null) {
                        resultado.add(Mappers.convertirADTO(notificacion));
                    }
                }
            }

            unitOfWork.commit();
            return resultado;

        } catch (PersistenciaException e) {
            unitOfWork.rollback();
            throw new NegocioException("No fue posible consultar las notificaciones.", e);
        }
    }

    private void validarNotificacion(NotificacionDTO notificacion) throws NegocioException {
        if (notificacion == null) {
            throw new NegocioException("Los datos de la notificacion son obligatorios.");
        }

        if (notificacion.getTipo() == null || notificacion.getTipo().isBlank()) {
            throw new NegocioException("El tipo de notificacion es obligatorio.");
        }

        if (notificacion.getMensaje() == null || notificacion.getMensaje().isBlank()) {
            throw new NegocioException("El mensaje es obligatorio.");
        }

        if (notificacion.getFechaHora() == null) {
            throw new NegocioException("La fecha de notificacion es obligatoria.");
        }

        if (notificacion.getEstado() == null) {
            throw new NegocioException("El estado de la notificacion es obligatorio.");
        }

        if (notificacion.getIdCliente() == null || notificacion.getIdCliente().isBlank()) {
            throw new NegocioException("El ID del cliente es obligatorio.");
        }
    }

    private void validarId(String id) throws NegocioException {
        if (id == null || id.isBlank()) {
            throw new NegocioException("El ID de notificacion es obligatorio.");
        }
    }
}

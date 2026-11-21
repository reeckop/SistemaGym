/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gym.servicios.servicios;

import gym.excepciones.NegocioException;
import gym.persistencia.entidades.Cliente;
import gym.persistencia.entidades.Inscripcion;
import gym.persistencia.excepciones.PersistenciaException;
import gym.persistencia.unitofwork.IUnitOfWork;
import gym.persistencia.unitofwork.UnitOfWork;
import gym.servicios.dto.InscripcionDTO;
import gym.servicios.interfaces.IInscripcionServicios;
import gym.servicios.mappers.Mappers;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author PC GAMER MASTER RACE
 */
public class InscripcionServicios implements IInscripcionServicios {

    @Override
    public void registrarInscripcion(InscripcionDTO inscripcion) throws NegocioException {
        validarInscripcion(inscripcion);

        IUnitOfWork unitOfWork = new UnitOfWork();

        try {
            Cliente cliente = unitOfWork.clienteDAO().obtenerPorId(inscripcion.getIdCliente());

            if (cliente == null) {
                throw new NegocioException("El cliente no existe.");
            }

            unitOfWork.inscripcionDAO().guardar(Mappers.convertirAEntidad(inscripcion, cliente));
            unitOfWork.commit();

        } catch (PersistenciaException e) {
            unitOfWork.rollback();
            throw new NegocioException("No fue posible registrar la inscripcion.", e);
        }
    }

    @Override
    public void actualizarInscripcion(InscripcionDTO inscripcion) throws NegocioException {
        validarInscripcion(inscripcion);
        validarId(inscripcion.getIdInscripcion());

        IUnitOfWork unitOfWork = new UnitOfWork();

        try {
            Cliente cliente = unitOfWork.clienteDAO().obtenerPorId(inscripcion.getIdCliente());

            if (cliente == null) {
                throw new NegocioException("El cliente no existe.");
            }

            unitOfWork.inscripcionDAO().actualizar(Mappers.convertirAEntidad(inscripcion, cliente));
            unitOfWork.commit();

        } catch (PersistenciaException e) {
            unitOfWork.rollback();
            throw new NegocioException("No fue posible actualizar la inscripcion.", e);
        }
    }

    @Override
    public void eliminarInscripcion(String id) throws NegocioException {
        validarId(id);

        IUnitOfWork unitOfWork = new UnitOfWork();

        try {
            unitOfWork.inscripcionDAO().eliminar(id);
            unitOfWork.commit();

        } catch (PersistenciaException e) {
            unitOfWork.rollback();
            throw new NegocioException("No fue posible eliminar la inscripcion.", e);
        }
    }

    @Override
    public List<InscripcionDTO> consultarTodasLasInscripciones() throws NegocioException {
        IUnitOfWork unitOfWork = new UnitOfWork();

        try {
            List<InscripcionDTO> resultado = new ArrayList<>();
            List<Inscripcion> inscripciones = unitOfWork.inscripcionDAO().obtenerTodos();

            if (inscripciones != null) {
                for (Inscripcion inscripcion : inscripciones) {
                    if (inscripcion != null) {
                        resultado.add(Mappers.convertirADTO(inscripcion));
                    }
                }
            }

            unitOfWork.commit();
            return resultado;

        } catch (PersistenciaException e) {
            unitOfWork.rollback();
            throw new NegocioException("No fue posible consultar las inscripciones.", e);
        }
    }

    private void validarInscripcion(InscripcionDTO inscripcion) throws NegocioException {
        if (inscripcion == null) {
            throw new NegocioException("Los datos de la inscripcion son obligatorios.");
        }

        if (inscripcion.getFecha() == null) {
            throw new NegocioException("La fecha de inscripcion es obligatoria.");
        }

        if (inscripcion.getEstado() == null) {
            throw new NegocioException("El estado de inscripcion es obligatorio.");
        }

        if (inscripcion.getIdCliente() == null || inscripcion.getIdCliente().isBlank()) {
            throw new NegocioException("El ID del cliente es obligatorio.");
        }
    }

    private void validarId(String id) throws NegocioException {
        if (id == null || id.isBlank()) {
            throw new NegocioException("El ID de inscripcion es obligatorio.");
        }
    }
}

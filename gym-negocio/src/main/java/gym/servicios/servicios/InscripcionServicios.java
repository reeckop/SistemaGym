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
import gym.persistencia.unitofwork.IUnitOfWorkFactory;
import gym.persistencia.unitofwork.UnitOfWorkFactory;
import gym.servicios.dto.InscripcionDTO;
import gym.servicios.interfaces.IInscripcionServicios;
import gym.servicios.mappers.Mappers;
import java.util.ArrayList;
import java.util.List;

/**
 * Gestiona las reglas de negocio de inscripción.
 *
 * @author PC GAMER MASTER RACE
 */
public class InscripcionServicios implements IInscripcionServicios {
    private final IUnitOfWorkFactory unidadDeTrabajoFactory;

    public InscripcionServicios() {
        this(UnitOfWorkFactory.obtenerInstancia());
    }

    public InscripcionServicios(IUnitOfWorkFactory unidadDeTrabajoFactory) {
        this.unidadDeTrabajoFactory = java.util.Objects.requireNonNull(unidadDeTrabajoFactory);
    }

    @Override
    public void registrarInscripcion(InscripcionDTO inscripcion) throws NegocioException {
        validarInscripcion(inscripcion);

        IUnitOfWork unitOfWork = unidadDeTrabajoFactory.crear();

        try {
            Cliente cliente = unitOfWork.clienteDAO().obtenerPorId(inscripcion.getIdCliente());

            if (cliente == null) {
                throw new NegocioException("El cliente no existe.");
            }

            validarDisponibilidad(unitOfWork, inscripcion);

            unitOfWork.inscripcionDAO().guardar(Mappers.convertirAEntidad(inscripcion, cliente));
            unitOfWork.commit();

        } catch (NegocioException e) {
            unitOfWork.rollback();
            throw e;
        } catch (PersistenciaException e) {
            unitOfWork.rollback();
            throw new NegocioException("No fue posible registrar la inscripcion.", e);
        }
    }

    private void validarDisponibilidad(IUnitOfWork unitOfWork, InscripcionDTO nueva) throws PersistenciaException, NegocioException {
        if (nueva.getHorario() == null || nueva.getHorario().getIdHorario() == null || nueva.getHorario().getFecha() == null
                || nueva.getHorario().getHoraInicio() == null || nueva.getHorario().getHoraFin() == null
                || !nueva.getHorario().getHoraInicio().isBefore(nueva.getHorario().getHoraFin())
                || nueva.getHorario().getFecha().isBefore(java.time.LocalDate.now())) {
            throw new NegocioException("Selecciona un horario válido con hora de inicio anterior a la hora de fin.");
        }
        List<Inscripcion> existentes = unitOfWork.inscripcionDAO().obtenerTodos();
        int inscritosHorario = 0;
        if (existentes != null) {
            for (Inscripcion actual : existentes) {
                if (actual == null || actual.getEstado() != gym.persistencia.enums.EstadoInscripcion.ACTIVA) continue;
                if (actual.getCliente() != null && nueva.getIdCliente().equals(actual.getCliente().getIdCliente())) {
                    throw new NegocioException("El cliente ya tiene una inscripción activa. Debe cancelarla antes de elegir otro curso.");
                }
                if (actual.getHorario() != null && nueva.getHorario().getIdHorario().equals(actual.getHorario().getIdHorario())) inscritosHorario++;
                if (actual.getHorario() != null && actual.getCliente() != null
                        && nueva.getIdCliente().equals(actual.getCliente().getIdCliente())
                        && actual.getHorario().getFecha() != null && actual.getHorario().getFecha().equals(nueva.getHorario().getFecha())
                        && actual.getHorario().getHoraInicio() != null && actual.getHorario().getHoraFin() != null
                        && nueva.getHorario().getHoraInicio().isBefore(actual.getHorario().getHoraFin())
                        && actual.getHorario().getHoraInicio().isBefore(nueva.getHorario().getHoraFin())) {
                    throw new NegocioException("El horario se cruza con otra actividad del cliente.");
                }
            }
        }
        List<gym.persistencia.entidades.Curso> cursos = unitOfWork.cursoDAO().obtenerTodos();
        boolean horarioExiste = false;
        if (cursos != null) for (gym.persistencia.entidades.Curso curso : cursos) {
            if (curso == null || curso.getHorarios() == null) continue;
            boolean horarioPertenece = curso.getHorarios().stream().anyMatch(h -> h != null && nueva.getHorario().getIdHorario().equals(h.getIdHorario()));
            if (horarioPertenece) {
                horarioExiste = true;
                if (inscritosHorario >= curso.getCupo()) throw new NegocioException("El curso ya no tiene cupo en ese horario.");
            }
        }
        if (!horarioExiste) throw new NegocioException("El horario seleccionado ya no está disponible.");
    }

    @Override
    public void actualizarInscripcion(InscripcionDTO inscripcion) throws NegocioException {
        validarInscripcion(inscripcion);
        validarId(inscripcion.getIdInscripcion());

        IUnitOfWork unitOfWork = unidadDeTrabajoFactory.crear();

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

        IUnitOfWork unitOfWork = unidadDeTrabajoFactory.crear();

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
        IUnitOfWork unitOfWork = unidadDeTrabajoFactory.crear();

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

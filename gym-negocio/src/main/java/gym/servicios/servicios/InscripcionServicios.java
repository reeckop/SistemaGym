/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gym.servicios.servicios;

import gym.excepciones.NegocioException;
import gym.persistencia.entidades.Cliente;
import gym.persistencia.entidades.Inscripcion;
import gym.persistencia.excepciones.PersistenciaException;
import gym.persistencia.facade.IPersistenciaFachada;
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

    private final IPersistenciaFachada fachada;

    public InscripcionServicios(IPersistenciaFachada fachada) {
        this.fachada = fachada;
    }

    @Override
    public void registrarInscripcion(InscripcionDTO inscripcion) throws NegocioException {
        validarInscripcion(inscripcion);

        try {
            Cliente cliente = fachada.obtenerClientePorId(inscripcion.getIdCliente());

            if (cliente == null) {
                throw new NegocioException("El cliente no existe.");
            }

            fachada.guardarInscripcion(Mappers.convertirAEntidad(inscripcion, cliente));
        } catch (PersistenciaException e) {
            throw new NegocioException("No fue posible registrar la inscripción.", e);
        }
    }

    @Override
    public void actualizarInscripcion(InscripcionDTO inscripcion) throws NegocioException {
        validarInscripcion(inscripcion);
        validarId(inscripcion.getIdInscripcion());

        try {
            Cliente cliente = fachada.obtenerClientePorId(inscripcion.getIdCliente());

            if (cliente == null) {
                throw new NegocioException("El cliente no existe.");
            }

            fachada.actualizarInscripcion(Mappers.convertirAEntidad(inscripcion, cliente));
        } catch (PersistenciaException e) {
            throw new NegocioException("No fue posible actualizar la inscripción.", e);
        }
    }

    @Override
    public void eliminarInscripcion(String id) throws NegocioException {
        validarId(id);

        try {
            fachada.eliminarInscripcion(id);
        } catch (PersistenciaException e) {
            throw new NegocioException("No fue posible eliminar la inscripción.", e);
        }
    }

    @Override
    public List<InscripcionDTO> consultarTodasLasInscripciones() throws NegocioException {
        try {
            List<InscripcionDTO> resultado = new ArrayList<>();
            List<Inscripcion> inscripciones = fachada.obtenerTodasLasInscripciones();

            if (inscripciones != null) {
                for (Inscripcion inscripcion : inscripciones) {
                    if (inscripcion != null) {
                        resultado.add(Mappers.convertirADTO(inscripcion));
                    }
                }
            }

            return resultado;
        } catch (PersistenciaException e) {
            throw new NegocioException("No fue posible consultar las inscripciones.", e);
        }
    }

    private void validarInscripcion(InscripcionDTO inscripcion) throws NegocioException {
        if (inscripcion == null) {
            throw new NegocioException("Los datos de la inscripción son obligatorios.");
        }

        if (inscripcion.getFecha() == null) {
            throw new NegocioException("La fecha de inscripción es obligatoria.");
        }

        if (inscripcion.getEstado() == null) {
            throw new NegocioException("El estado de inscripción es obligatorio.");
        }

        if (inscripcion.getIdCliente() == null || inscripcion.getIdCliente().isBlank()) {
            throw new NegocioException("El ID del cliente es obligatorio.");
        }
    }

    private void validarId(String id) throws NegocioException {
        if (id == null || id.isBlank()) {
            throw new NegocioException("El ID de inscripción es obligatorio.");
        }
    }
}

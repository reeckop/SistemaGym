/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gym.servicios.servicios;

import gym.excepciones.NegocioException;
import gym.persistencia.entidades.Membresia;
import gym.persistencia.facade.IPersistenciaFachada;
import gym.servicios.dto.MembresiaDTO;
import gym.persistencia.excepciones.PersistenciaException;
import gym.servicios.mappers.Mappers;
import gym.servicios.interfaces.IMembresiaServicios;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author PC GAMER MASTER RACE
 */

public class MembresiaServicios implements IMembresiaServicios {

    private final IPersistenciaFachada fachada;

    public MembresiaServicios(IPersistenciaFachada fachada) {
        this.fachada = fachada;
    }

    @Override
    public void registrarMembresia(MembresiaDTO membresia) throws NegocioException {
        validarMembresia(membresia);

        try {
            fachada.guardarMembresia(Mappers.convertirAEntidad(membresia));
        } catch (PersistenciaException e) {
            throw new NegocioException("No fue posible registrar la membresía.", e);
        }
    }

    @Override
    public void actualizarMembresia(MembresiaDTO membresia) throws NegocioException {
        validarMembresia(membresia);
        validarId(membresia.getIdMembresia());

        try {
            fachada.actualizarMembresia(Mappers.convertirAEntidad(membresia));
        } catch (PersistenciaException e) {
            throw new NegocioException("No fue posible actualizar la membresía.", e);
        }
    }

    @Override
    public void eliminarMembresia(String id) throws NegocioException {
        validarId(id);

        try {
            fachada.eliminarMembresia(id);
        } catch (PersistenciaException e) {
            throw new NegocioException("No fue posible eliminar la membresía.", e);
        }
    }

    @Override
    public List<MembresiaDTO> consultarTodasLasMembresias() throws NegocioException {
        try {
            List<MembresiaDTO> resultado = new ArrayList<>();
            List<Membresia> membresias = fachada.obtenerTodasLasMembresias();

            if (membresias != null) {
                for (Membresia membresia : membresias) {
                    if (membresia != null) {
                        resultado.add(Mappers.convertirADTO(membresia));
                    }
                }
            }

            return resultado;
        } catch (PersistenciaException e) {
            throw new NegocioException("No fue posible consultar las membresías.", e);
        }
    }

    private void validarMembresia(MembresiaDTO membresia) throws NegocioException {
        if (membresia == null) {
            throw new NegocioException("Los datos de la membresía son obligatorios.");
        }

        if (membresia.getNombre() == null || membresia.getNombre().isBlank()) {
            throw new NegocioException("El nombre de la membresía es obligatorio.");
        }

        if (membresia.getPrecio() < 0) {
            throw new NegocioException("El precio no puede ser negativo.");
        }

        if (membresia.getEstado() == null) {
            throw new NegocioException("El estado de la membresía es obligatorio.");
        }

        if (membresia.getFechaInicio() == null || membresia.getFechaFin() == null) {
            throw new NegocioException("Las fechas de la membresía son obligatorias.");
        }

        if (membresia.getFechaFin().isBefore(membresia.getFechaInicio())) {
            throw new NegocioException("La fecha fin no puede ser anterior a la fecha inicio.");
        }
    }

    private void validarId(String id) throws NegocioException {
        if (id == null || id.isBlank()) {
            throw new NegocioException("El ID de membresía es obligatorio.");
        }
    }
}

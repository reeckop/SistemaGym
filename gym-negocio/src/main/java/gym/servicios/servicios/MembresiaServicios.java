/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gym.servicios.servicios;

import gym.excepciones.NegocioException;
import gym.persistencia.entidades.Membresia;
import gym.servicios.dto.MembresiaDTO;
import gym.persistencia.excepciones.PersistenciaException;
import gym.persistencia.unitofwork.IUnitOfWork;
import gym.persistencia.unitofwork.UnitOfWork;
import gym.servicios.mappers.Mappers;
import gym.servicios.interfaces.IMembresiaServicios;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author PC GAMER MASTER RACE
 */

public class MembresiaServicios implements IMembresiaServicios {

    @Override
    public void registrarMembresia(MembresiaDTO membresia) throws NegocioException {
        validarMembresia(membresia);

        IUnitOfWork unitOfWork = new UnitOfWork();

        try {
            Membresia entidad = Mappers.convertirAEntidad(membresia);

            unitOfWork.membresiaDAO().guardar(entidad);

            unitOfWork.commit();

        } catch (PersistenciaException e) {
            unitOfWork.rollback();
            throw new NegocioException("No fue posible registrar la membresia.", e);
        }
    }

    @Override
    public void actualizarMembresia(MembresiaDTO membresia) throws NegocioException {
        validarMembresia(membresia);
        validarId(membresia.getIdMembresia());

        IUnitOfWork unitOfWork = new UnitOfWork();

        try {
            Membresia entidad = Mappers.convertirAEntidad(membresia);

            unitOfWork.membresiaDAO().actualizar(entidad);

            unitOfWork.commit();

        } catch (PersistenciaException e) {
            unitOfWork.rollback();
            throw new NegocioException("No fue posible actualizar la membresia.", e);
        }
    }

    @Override
    public void eliminarMembresia(String id) throws NegocioException {
        validarId(id);

        IUnitOfWork unitOfWork = new UnitOfWork();

        try {
            unitOfWork.membresiaDAO().eliminar(id);

            unitOfWork.commit();

        } catch (PersistenciaException e) {
            unitOfWork.rollback();
            throw new NegocioException("No fue posible eliminar la membresia.", e);
        }
    }

    @Override
    public List<MembresiaDTO> consultarTodasLasMembresias() throws NegocioException {

        IUnitOfWork unitOfWork = new UnitOfWork();

        try {
            List<MembresiaDTO> resultado = new ArrayList<>();

            List<Membresia> membresias = unitOfWork.membresiaDAO().obtenerTodos();

            if (membresias != null) {
                for (Membresia membresia : membresias) {
                    if (membresia != null) {
                        resultado.add(Mappers.convertirADTO(membresia));
                    }
                }
            }

            unitOfWork.commit();

            return resultado;

        } catch (PersistenciaException e) {
            unitOfWork.rollback();
            throw new NegocioException("No fue posible consultar las membresias.", e);
        }
    }

    private void validarMembresia(MembresiaDTO membresia) throws NegocioException {
        if (membresia == null) {
            throw new NegocioException("Los datos de la membresia son obligatorios.");
        }

        if (membresia.getNombre() == null || membresia.getNombre().isBlank()) {
            throw new NegocioException("El nombre de la membresia es obligatorio.");
        }

        if (membresia.getPrecio() < 0) {
            throw new NegocioException("El precio no puede ser negativo.");
        }

        if (membresia.getEstado() == null) {
            throw new NegocioException("El estado de la membresia es obligatorio.");
        }

        if (membresia.getFechaInicio() == null || membresia.getFechaFin() == null) {
            throw new NegocioException("Las fechas de la membresia son obligatorias.");
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

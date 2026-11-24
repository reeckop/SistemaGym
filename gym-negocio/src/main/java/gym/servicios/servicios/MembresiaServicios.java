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
import gym.persistencia.unitofwork.IUnitOfWorkFactory;
import gym.persistencia.unitofwork.UnitOfWorkFactory;
import gym.servicios.enums.EstadoMembresia;
import gym.servicios.mappers.Mappers;
import gym.servicios.interfaces.IMembresiaServicios;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author PC GAMER MASTER RACE
 */

public class MembresiaServicios implements IMembresiaServicios {
    private final IUnitOfWorkFactory unidadDeTrabajoFactory;

    public MembresiaServicios() {
        this(UnitOfWorkFactory.obtenerInstancia());
    }

    public MembresiaServicios(IUnitOfWorkFactory unidadDeTrabajoFactory) {
        this.unidadDeTrabajoFactory = java.util.Objects.requireNonNull(unidadDeTrabajoFactory);
    }

    @Override
    public void registrarMembresia(MembresiaDTO membresia) throws NegocioException {
        validarMembresia(membresia);

        IUnitOfWork unitOfWork = unidadDeTrabajoFactory.crear();

        try {
            membresia.setEstado(EstadoMembresia.ACTIVA);
            membresia.setFechaInicio(null);
            membresia.setFechaFin(null);

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

        IUnitOfWork unitOfWork = unidadDeTrabajoFactory.crear();

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

        IUnitOfWork unitOfWork = unidadDeTrabajoFactory.crear();

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

        IUnitOfWork unitOfWork = unidadDeTrabajoFactory.crear();

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

    private void validarMembresia(MembresiaDTO membresia)throws NegocioException {
        if (membresia == null) {
            throw new NegocioException("Los datos de la membresía son obligatorios.");
        }

        if (membresia.getNombre() == null
                || membresia.getNombre().isBlank()) {
            throw new NegocioException("El nombre es obligatorio.");
        }

        if (membresia.getPrecio() <= 0) {
            throw new NegocioException("El precio debe ser mayor que cero.");
        }
    }

    private void validarId(String id) throws NegocioException {
        if (id == null || id.isBlank()) {
            throw new NegocioException("El ID de membresía es obligatorio.");
        }
    }
}

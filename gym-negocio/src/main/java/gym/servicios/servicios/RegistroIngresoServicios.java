/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gym.servicios.servicios;

import gym.excepciones.NegocioException;
import gym.persistencia.entidades.Cliente;
import gym.persistencia.entidades.RegistroIngreso;
import gym.persistencia.excepciones.PersistenciaException;
import gym.persistencia.unitofwork.IUnitOfWork;
import gym.persistencia.unitofwork.IUnitOfWorkFactory;
import gym.persistencia.unitofwork.UnitOfWorkFactory;
import gym.servicios.dto.RegistroIngresoDTO;
import gym.servicios.interfaces.IRegistroIngresoServicios;
import gym.servicios.mappers.Mappers;
import java.util.ArrayList;
import java.util.List;

/**
 * Gestiona las reglas de negocio de registro ingreso.
 *
 * @author PC GAMER MASTER RACE
 */
public class RegistroIngresoServicios implements IRegistroIngresoServicios {
    private final IUnitOfWorkFactory unidadDeTrabajoFactory;

    public RegistroIngresoServicios() {
        this(UnitOfWorkFactory.obtenerInstancia());
    }

    public RegistroIngresoServicios(IUnitOfWorkFactory unidadDeTrabajoFactory) {
        this.unidadDeTrabajoFactory = java.util.Objects.requireNonNull(unidadDeTrabajoFactory);
    }

    @Override
    public void registrarIngreso(RegistroIngresoDTO registro) throws NegocioException {
        validarRegistro(registro);

        IUnitOfWork unitOfWork = unidadDeTrabajoFactory.crear();

        try {
            Cliente cliente = unitOfWork.clienteDAO().obtenerPorId(registro.getIdCliente());

            if (cliente == null) {
                throw new NegocioException("El cliente no existe.");
            }

            unitOfWork.accesoDAO().guardar(Mappers.convertirAEntidad(registro, cliente));
            unitOfWork.commit();

        } catch (PersistenciaException e) {
            unitOfWork.rollback();
            throw new NegocioException("No fue posible registrar el ingreso.", e);
        }
    }

    @Override
    public List<RegistroIngresoDTO> consultarIngresosPorCliente(String idCliente) throws NegocioException {
        validarId(idCliente);

        IUnitOfWork unitOfWork = unidadDeTrabajoFactory.crear();

        try {
            List<RegistroIngresoDTO> resultado = new ArrayList<>();
            List<RegistroIngreso> registros =
                    unitOfWork.accesoDAO().obtenerPorCliente(idCliente);

            if (registros != null) {
                for (RegistroIngreso registro : registros) {
                    if (registro != null) {
                        resultado.add(Mappers.convertirADTO(registro));
                    }
                }
            }

            unitOfWork.commit();
            return resultado;

        } catch (PersistenciaException e) {
            unitOfWork.rollback();
            throw new NegocioException("No fue posible consultar los ingresos del cliente.", e);
        }
    }

    @Override
    public List<RegistroIngresoDTO> consultarTodosLosIngresos() throws NegocioException {
        IUnitOfWork unitOfWork = unidadDeTrabajoFactory.crear();

        try {
            List<RegistroIngresoDTO> resultado = new ArrayList<>();
            List<RegistroIngreso> registros = unitOfWork.accesoDAO().obtenerTodos();

            if (registros != null) {
                for (RegistroIngreso registro : registros) {
                    if (registro != null) {
                        resultado.add(Mappers.convertirADTO(registro));
                    }
                }
            }

            unitOfWork.commit();
            return resultado;

        } catch (PersistenciaException e) {
            unitOfWork.rollback();
            throw new NegocioException("No fue posible consultar los ingresos.", e);
        }
    }

    private void validarRegistro(RegistroIngresoDTO registro) throws NegocioException {
        if (registro == null) {
            throw new NegocioException("Los datos del registro son obligatorios.");
        }

        if (registro.getFechaHoraEntrada() == null) {
            throw new NegocioException("La fecha y hora de entrada son obligatorias.");
        }

        if (registro.getEstado() == null) {
            throw new NegocioException("El estado del registro es obligatorio.");
        }

        if (registro.getIdCliente() == null || registro.getIdCliente().isBlank()) {
            throw new NegocioException("El ID del cliente es obligatorio.");
        }
    }

    private void validarId(String id) throws NegocioException {
        if (id == null || id.isBlank()) {
            throw new NegocioException("El ID de cliente es obligatorio.");
        }
    }
}

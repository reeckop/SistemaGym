/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gym.servicios.servicios;

import gym.excepciones.NegocioException;
import gym.persistencia.entidades.Empleado;
import gym.persistencia.excepciones.PersistenciaException;
import gym.persistencia.unitofwork.IUnitOfWork;
import gym.persistencia.unitofwork.IUnitOfWorkFactory;
import gym.persistencia.unitofwork.UnitOfWorkFactory;
import gym.servicios.dto.EmpleadoDTO;
import gym.servicios.interfaces.IEmpleadoServicios;
import gym.servicios.mappers.Mappers;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 *
 * @author PC GAMER MASTER RACE
 */
public class EmpleadoServicios implements IEmpleadoServicios {
    private final IUnitOfWorkFactory unidadDeTrabajoFactory;

    public EmpleadoServicios() {
        this(UnitOfWorkFactory.obtenerInstancia());
    }

    public EmpleadoServicios(IUnitOfWorkFactory unidadDeTrabajoFactory) {
        this.unidadDeTrabajoFactory = java.util.Objects.requireNonNull(unidadDeTrabajoFactory);
    }

    @Override
    public void registrarEmpleado(EmpleadoDTO empleado) throws NegocioException {
        validarEmpleado(empleado);

        IUnitOfWork unitOfWork = unidadDeTrabajoFactory.crear();

        try {
            if (unitOfWork.empleadoDAO().obtenerPorNip(empleado.getNip()) != null) {
                throw new NegocioException("El NIP ya está registrado.");
            }

            empleado.setIdEmpleado(UUID.randomUUID().toString());
            empleado.setFechaIngreso(LocalDate.now().toString());

            if (empleado.getPuesto() == null || empleado.getPuesto().isBlank()) {
                empleado.setPuesto("Recepcionista");
            }

            unitOfWork.empleadoDAO().guardar(Mappers.convertirAEntidad(empleado));
            unitOfWork.commit();

        } catch (PersistenciaException e) {
            e.printStackTrace();
            unitOfWork.rollback();
            throw new NegocioException("No fue posible registrar el empleado: " + e.getMessage(), e);
        } catch (NegocioException e) {
            unitOfWork.rollback();
            throw e;
        }
    }

    @Override
    public EmpleadoDTO iniciarSesion(String nip, String contrasena)
            throws NegocioException {

        if (nip == null || nip.isBlank()) {
            throw new NegocioException("El NIP es obligatorio.");
        }

        if (contrasena == null || contrasena.isBlank()) {
            throw new NegocioException("La contraseña es obligatoria.");
        }

        IUnitOfWork unitOfWork = unidadDeTrabajoFactory.crear();

        try {
            Empleado empleado = unitOfWork.empleadoDAO().obtenerPorNip(nip);

            if (empleado == null || !empleado.getContrasena().equals(contrasena)) {
                throw new NegocioException("NIP o contraseña incorrectos.");
            }

            EmpleadoDTO resultado = Mappers.convertirADTO(empleado);
            unitOfWork.commit();

            return resultado;

        } catch (PersistenciaException e) {
            unitOfWork.rollback();
            throw new NegocioException("No fue posible iniciar sesión.", e);
        } catch (NegocioException e) {
            unitOfWork.rollback();
            throw e;
        }
    }

    @Override
    public void actualizarEmpleado(EmpleadoDTO empleado) throws NegocioException {
        validarEmpleado(empleado);

        if (empleado.getIdEmpleado() == null || empleado.getIdEmpleado().isBlank()) {
            throw new NegocioException("El ID del empleado es obligatorio.");
        }

        IUnitOfWork unitOfWork = unidadDeTrabajoFactory.crear();

        try {
            Empleado existente = unitOfWork.empleadoDAO()
                    .obtenerPorId(empleado.getIdEmpleado());

            if (existente == null) {
                throw new NegocioException("El empleado no existe.");
            }

            unitOfWork.empleadoDAO().actualizar(Mappers.convertirAEntidad(empleado));
            unitOfWork.commit();

        } catch (PersistenciaException e) {
            unitOfWork.rollback();
            throw new NegocioException("No fue posible actualizar el empleado.", e);
        } catch (NegocioException e) {
            unitOfWork.rollback();
            throw e;
        }
    }

    @Override
    public void eliminarEmpleado(String id) throws NegocioException {
        if (id == null || id.isBlank()) {
            throw new NegocioException("El ID del empleado es obligatorio.");
        }

        IUnitOfWork unitOfWork = unidadDeTrabajoFactory.crear();

        try {
            if (unitOfWork.empleadoDAO().obtenerPorId(id) == null) {
                throw new NegocioException("El empleado no existe.");
            }

            unitOfWork.empleadoDAO().eliminar(id);
            unitOfWork.commit();

        } catch (PersistenciaException e) {
            unitOfWork.rollback();
            throw new NegocioException("No fue posible eliminar el empleado.", e);
        } catch (NegocioException e) {
            unitOfWork.rollback();
            throw e;
        }
    }

    @Override
    public List<EmpleadoDTO> consultarTodosLosEmpleados() throws NegocioException {
        IUnitOfWork unitOfWork = unidadDeTrabajoFactory.crear();

        try {
            List<EmpleadoDTO> resultado = new ArrayList<>();
            List<Empleado> empleados = unitOfWork.empleadoDAO().obtenerTodos();

            if (empleados != null) {
                for (Empleado empleado : empleados) {
                    if (empleado != null) {
                        resultado.add(Mappers.convertirADTO(empleado));
                    }
                }
            }

            unitOfWork.commit();
            return resultado;

        } catch (PersistenciaException e) {
            unitOfWork.rollback();
            throw new NegocioException("No fue posible consultar los empleados.", e);
        }
    }

    private void validarEmpleado(EmpleadoDTO empleado) throws NegocioException {
        if (empleado == null) {
            throw new NegocioException("Los datos del empleado son obligatorios.");
        }

        if (empleado.getNombre() == null || empleado.getNombre().isBlank()) {
            throw new NegocioException("El nombre es obligatorio.");
        }

        if (empleado.getApellido() == null || empleado.getApellido().isBlank()) {
            throw new NegocioException("El apellido es obligatorio.");
        }

        if (empleado.getTelefono() == null || empleado.getTelefono().isBlank()) {
            throw new NegocioException("El teléfono es obligatorio.");
        }

        if (empleado.getNip() == null || empleado.getNip().isBlank()) {
            throw new NegocioException("El NIP es obligatorio.");
        }

        if (empleado.getContrasena() == null || empleado.getContrasena().isBlank()) {
            throw new NegocioException("La contraseña es obligatoria.");
        }
    }
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gym.servicios.servicios;

import gym.excepciones.NegocioException;
import gym.persistencia.entidades.Cliente;
import gym.persistencia.entidades.RegistroIngreso;
import gym.persistencia.excepciones.PersistenciaException;
import gym.persistencia.facade.IPersistenciaFachada;
import gym.servicios.dto.RegistroIngresoDTO;
import gym.servicios.interfaces.IRegistroIngresoServicios;
import gym.servicios.mappers.Mappers;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author PC GAMER MASTER RACE
 */
public class RegistroIngresoServicios implements IRegistroIngresoServicios {

    private final IPersistenciaFachada fachada;

    public RegistroIngresoServicios(IPersistenciaFachada fachada) {
        this.fachada = fachada;
    }

    @Override
    public void registrarIngreso(RegistroIngresoDTO registro) throws NegocioException {
        validarRegistro(registro);

        try {
            Cliente cliente = fachada.obtenerClientePorId(registro.getIdCliente());

            if (cliente == null) {
                throw new NegocioException("El cliente no existe.");
            }

            fachada.guardarRegistroIngreso(Mappers.convertirAEntidad(registro, cliente));
        } catch (PersistenciaException e) {
            throw new NegocioException("No fue posible registrar el ingreso.", e);
        }
    }

    @Override
    public List<RegistroIngresoDTO> consultarIngresosPorCliente(String idCliente)
            throws NegocioException {

        validarId(idCliente);

        try {
            List<RegistroIngresoDTO> resultado = new ArrayList<>();
            List<RegistroIngreso> registros =
                fachada.obtenerRegistrosIngresoPorCliente(idCliente);

            if (registros != null) {
                for (RegistroIngreso registro : registros) {
                    if (registro != null) {
                        resultado.add(Mappers.convertirADTO(registro));
                    }
                }
            }

            return resultado;
        } catch (PersistenciaException e) {
            throw new NegocioException("No fue posible consultar los ingresos del cliente.", e);
        }
    }

    @Override
    public List<RegistroIngresoDTO> consultarTodosLosIngresos() throws NegocioException {
        try {
            List<RegistroIngresoDTO> resultado = new ArrayList<>();
            List<RegistroIngreso> registros = fachada.obtenerTodosLosRegistrosIngreso();

            if (registros != null) {
                for (RegistroIngreso registro : registros) {
                    if (registro != null) {
                        resultado.add(Mappers.convertirADTO(registro));
                    }
                }
            }

            return resultado;
        } catch (PersistenciaException e) {
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

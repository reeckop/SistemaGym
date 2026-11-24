/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package gym.servicios.interfaces;

import gym.excepciones.NegocioException;
import gym.servicios.dto.EmpleadoDTO;
import java.util.List;

/**
 * Define las operaciones de negocio relacionadas con empleado.
 *
 * @author PC GAMER MASTER RACE
 */
public interface IEmpleadoServicios {
    void registrarEmpleado(EmpleadoDTO empleado) throws NegocioException;

    EmpleadoDTO iniciarSesion(String nip, String contrasena) throws NegocioException;

    void actualizarEmpleado(EmpleadoDTO empleado) throws NegocioException;

    void eliminarEmpleado(String id) throws NegocioException;

    List<EmpleadoDTO> consultarTodosLosEmpleados() throws NegocioException;
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package gym.servicios.interfaces;

import gym.excepciones.NegocioException;
import gym.servicios.dto.RegistroIngresoDTO;
import java.util.List;

/**
 * Define las operaciones de negocio relacionadas con registro ingreso.
 *
 * @author PC GAMER MASTER RACE
 */
public interface IRegistroIngresoServicios {
    void registrarIngreso(RegistroIngresoDTO registro) throws NegocioException;
    List<RegistroIngresoDTO> consultarIngresosPorCliente(String idCliente) throws NegocioException;
    List<RegistroIngresoDTO> consultarTodosLosIngresos() throws NegocioException;
}

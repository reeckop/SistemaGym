/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package gym.servicios.interfaces;

import gym.excepciones.NegocioException;
import gym.servicios.dto.ClienteDTO;
import java.util.List;

/**
 * Define las operaciones de negocio relacionadas con cliente.
 *
 * @author PC GAMER MASTER RACE
 */
public interface IClienteServicios {
    void registrarCliente(ClienteDTO cliente) throws NegocioException;
    void actualizarCliente(ClienteDTO cliente) throws NegocioException;
    ClienteDTO consultarCliente(String id) throws NegocioException;
    List<ClienteDTO> consultarTodosLosClientes() throws NegocioException;

    
    
    
}

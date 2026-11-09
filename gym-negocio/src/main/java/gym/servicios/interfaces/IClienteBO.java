/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package gym.servicios.interfaces;

import gym.excepciones.NegocioException;
import gym.servicios.dto.ClienteDTO;
import java.util.List;

/**
 *
 * @author PC GAMER MASTER RACE
 */
public interface IClienteBO {
    
    void registrarCliente(ClienteDTO cliente) throws NegocioException;

    void actualizarCliente(ClienteDTO cliente) throws NegocioException;

    ClienteDTO obtenerClientePorId(String id) throws NegocioException;

    List<ClienteDTO> obtenerTodosLosClientes() throws NegocioException;
    
}

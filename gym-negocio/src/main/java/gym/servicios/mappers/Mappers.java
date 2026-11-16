/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gym.servicios.mappers;

import gym.persistencia.entidades.Cliente;
import gym.servicios.dto.ClienteDTO;

/**
 *
 * @author PC GAMER MASTER RACE
 */
public class Mappers {
    public static Cliente convertirAEntidad(ClienteDTO clienteDTO) {
        Cliente cliente = new Cliente();

        cliente.setIdCliente(clienteDTO.getIdCliente());
        cliente.setNombre(clienteDTO.getNombre());
        cliente.setApellido(clienteDTO.getApellido());
        cliente.setTelefono(clienteDTO.getTelefono());
        cliente.setFechaIngreso(clienteDTO.getFechaIngreso());

        return cliente;
    }

    public static ClienteDTO convertirADTO(Cliente cliente) {
        ClienteDTO clienteDTO = new ClienteDTO();

        clienteDTO.setIdCliente(cliente.getIdCliente());
        clienteDTO.setNombre(cliente.getNombre());
        clienteDTO.setApellido(cliente.getApellido());
        clienteDTO.setTelefono(cliente.getTelefono());
        clienteDTO.setFechaIngreso(cliente.getFechaIngreso());

        if (cliente.getMembresia() != null) {
            clienteDTO.setIdMembresia(cliente.getMembresia().getIdMembresia());
        }

        return clienteDTO;
    }
     
    
}

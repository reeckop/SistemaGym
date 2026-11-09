/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gym.servicios.mappers;

import gym.persistencia.entidades.Cliente;
import gym.persistencia.entidades.Membresia;
import gym.servicios.dto.ClienteDTO;

/**
 *
 * @author PC GAMER MASTER RACE
 */
public class Mappers {
     public static ClienteDTO convertirADTO(Cliente cliente) {
        if (cliente == null) {
            return null;
        }

        String idMembresia = null;

        if (cliente.getMembresia() != null) {
            idMembresia = cliente.getMembresia().getIdMembresia();
        }

        return new ClienteDTO(cliente.getIdCliente(),cliente.getNombre(),cliente.getApellido(),cliente.getTelefono(),cliente.getFechaIngreso(),idMembresia);
    }

    public static Cliente convertirAEntidad(ClienteDTO dto) {
        if (dto == null) {
            return null;
        }

        Membresia membresia = null;

        if (dto.getIdMembresia() != null && !dto.getIdMembresia().isBlank()) {
            membresia = new Membresia();
            membresia.setIdMembresia(dto.getIdMembresia());
        }

        return new Cliente(dto.getIdCliente(), dto.getNombre(), dto.getApellido(), dto.getTelefono(), dto.getFechaIngreso(), membresia);
    }
    
}

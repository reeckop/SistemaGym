/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gym.servicios.servicios;

import gym.excepciones.NegocioException;
import gym.persistencia.entidades.Cliente;
import gym.persistencia.entidades.Membresia;
import gym.persistencia.excepciones.PersistenciaException;
import gym.persistencia.facade.IPersistenciaFachada;
import gym.servicios.dto.ClienteDTO;
import gym.servicios.mappers.Mappers;
import java.util.ArrayList;
import java.util.List;
import gym.servicios.interfaces.IClienteServicios;
import java.util.regex.Pattern;

/**
 *
 * @author PC GAMER MASTER RACE
 */
public class ClienteServicios implements IClienteServicios {
     private final IPersistenciaFachada fachada;

    public ClienteServicios(IPersistenciaFachada fachada) {
        this.fachada = fachada;
    }

    @Override
    public void registrarCliente(ClienteDTO cliente) throws NegocioException {
        try {
            validarCliente(cliente);
            Cliente existente = fachada.obtenerClientePorId(cliente.getIdCliente());
            if (existente != null) {
                throw new NegocioException("El cliente ya está registrado.");
            }
            Membresia membresia = obtenerMembresia(cliente.getIdMembresia());
            Cliente entidad = Mappers.convertirAEntidad(cliente, membresia);
            fachada.guardarCliente(entidad);
        } catch (PersistenciaException e) {
            throw new NegocioException("Error al registrar el cliente.", e);
        }
    }

    @Override
    public void actualizarCliente(ClienteDTO cliente) throws NegocioException {
        try {
            validarCliente(cliente);
            Cliente existente = fachada.obtenerClientePorId(cliente.getIdCliente());
            if (existente == null) {
                throw new NegocioException("El cliente no existe.");
            }
            Membresia membresia = obtenerMembresia(cliente.getIdMembresia());
            Cliente entidad = Mappers.convertirAEntidad(cliente, membresia);
            fachada.actualizarCliente(entidad);
        } catch (PersistenciaException e) {
            throw new NegocioException("Error al actualizar el cliente.", e);
        }
    }

    @Override
    public ClienteDTO consultarCliente(String id) throws NegocioException {
        try {
            if (id == null || id.isBlank()) {
                throw new NegocioException("El ID del cliente es obligatorio.");
            }
            Cliente cliente = fachada.obtenerClientePorId(id);
            if (cliente == null) {
                throw new NegocioException("No se encontró el cliente.");
            }
            return Mappers.convertirADTO(cliente);
        } catch (PersistenciaException e) {
            throw new NegocioException("Error al consultar el cliente.", e);
        }
    }

    @Override
    public List<ClienteDTO> consultarTodosLosClientes() throws NegocioException {
        try {
            List<Cliente> clientes = fachada.obtenerTodosLosClientes();
            List<ClienteDTO> clientesDTO = new ArrayList<>();
            if (clientes != null) {
                for (Cliente cliente : clientes) {
                    if (cliente != null) {
                        clientesDTO.add(Mappers.convertirADTO(cliente));
                    }
                }
            }
            return clientesDTO;
        } catch (PersistenciaException e) {
            throw new NegocioException("Error al consultar los clientes.", e);
        }
    }

    private void validarCliente(ClienteDTO cliente) throws NegocioException {
        if (cliente == null) {
            throw new NegocioException("Los datos del cliente son obligatorios.");
        }

        if (cliente.getIdCliente() == null || cliente.getIdCliente().isBlank()) {
            throw new NegocioException("El ID del cliente es obligatorio.");
        }

        if (cliente.getNombre() == null || cliente.getNombre().isBlank()) {
            throw new NegocioException("El nombre del cliente es obligatorio.");
        }

        if (cliente.getApellido() == null || cliente.getApellido().isBlank()) {
            throw new NegocioException("El apellido del cliente es obligatorio.");
        }

        if (cliente.getTelefono() == null || !Pattern.matches("[0-9]{10}", cliente.getTelefono())) {
            throw new NegocioException("El teléfono debe contener 10 dígitos.");
        }
    }

    private Membresia obtenerMembresia(String idMembresia) throws PersistenciaException, NegocioException {
        if (idMembresia == null || idMembresia.isBlank()) {
            return null;
        }

        List<Membresia> membresias = fachada.obtenerTodasLasMembresias();

        if (membresias != null) {
            for (Membresia membresia : membresias) {
                if (membresia != null && idMembresia.equals(membresia.getIdMembresia())) {
                    return membresia;
                }
            }
        }

        throw new NegocioException("No se encontró la membresía indicada.");
    }
}

    
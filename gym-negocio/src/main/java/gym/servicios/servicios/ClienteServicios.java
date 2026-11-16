/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gym.servicios.servicios;

import gym.excepciones.NegocioException;
import gym.persistencia.entidades.Cliente;
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
    public void registrarCliente(ClienteDTO clienteDTO)throws NegocioException {
        validarCliente(clienteDTO);
        try {
            Cliente cliente = Mappers.convertirAEntidad(clienteDTO);
            fachada.guardarCliente(cliente);
        } catch (PersistenciaException e) {
            throw new NegocioException("No fue posible registrar el cliente.", e);
        }
    }

    @Override
    public void actualizarCliente(ClienteDTO clienteDTO)throws NegocioException {
        validarCliente(clienteDTO);
        validarId(clienteDTO.getIdCliente());
        try {
            Cliente clienteExistente = fachada.obtenerClientePorId(clienteDTO.getIdCliente());
            if (clienteExistente == null) {
                throw new NegocioException("El cliente no existe.");
            }
            Cliente cliente = Mappers.convertirAEntidad(clienteDTO);
            fachada.actualizarCliente(cliente);
        } catch (PersistenciaException e) {
            throw new NegocioException("No fue posible actualizar el cliente.", e);
        }
    }

    @Override
    public ClienteDTO consultarCliente(String id)throws NegocioException {
        validarId(id);
        try {
            Cliente cliente = fachada.obtenerClientePorId(id);
            if (cliente == null) {
                throw new NegocioException("El cliente no existe.");
            }
            return Mappers.convertirADTO(cliente);
        } catch (PersistenciaException e) {
            throw new NegocioException("No fue posible consultar el cliente.", e);
        }
    }

    @Override
    public List<ClienteDTO> consultarTodosLosClientes()throws NegocioException {
        try {
            List<Cliente> clientes = fachada.obtenerTodosLosClientes();
            List<ClienteDTO> clientesDTO = new ArrayList<>();
            for (Cliente cliente : clientes) {
                if (cliente != null) {
                    clientesDTO.add(Mappers.convertirADTO(cliente));
                }
            }
            return clientesDTO;
        } catch (PersistenciaException e) {
            throw new NegocioException("No fue posible consultar los clientes.", e);
        }
    }

    private void validarCliente(ClienteDTO clienteDTO) throws NegocioException {

        if (clienteDTO == null) {
            throw new NegocioException("Los datos del cliente son obligatorios.");
        }

        if (clienteDTO.getNombre() == null || clienteDTO.getNombre().isBlank()) {
            throw new NegocioException("El nombre es obligatorio.");
        }

        if (clienteDTO.getApellido() == null || clienteDTO.getApellido().isBlank()) {
            throw new NegocioException("El apellido es obligatorio.");
        }

        if (clienteDTO.getTelefono() == null || !TELEFONO_PATTERN.matcher(clienteDTO.getTelefono()).matches()) {
            throw new NegocioException("El teléfono debe contener exactamente 10 dígitos.");
        }

        if (clienteDTO.getFechaIngreso() == null) {
            throw new NegocioException("La fecha de ingreso es obligatoria.");
        }
    }

    private void validarId(String id) throws NegocioException {
        if (id == null || id.isBlank()) {
            throw new NegocioException("El ID del cliente es obligatorio.");
        }
    }
    
    private Pattern TELEFONO_PATTERN = Pattern.compile("^[0-9]{10}$");
    
}
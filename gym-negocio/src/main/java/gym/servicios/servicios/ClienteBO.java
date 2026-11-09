/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gym.servicios.servicios;

import gym.excepciones.NegocioException;
import gym.persistencia.entidades.Cliente;
import gym.persistencia.excepciones.PersistenciaException;
import gym.persistencia.facade.IPersistenciaFachada;
import gym.persistencia.facade.PersistenciaFachada;
import gym.persistencia.interfaces.IClienteDAO;
import gym.servicios.dto.ClienteDTO;
import gym.servicios.interfaces.IClienteBO;
import gym.servicios.mappers.Mappers;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 *
 * @author PC GAMER MASTER RACE
 */
public class ClienteBO implements IClienteBO {
    
    /**
     * Fachada utilizada para acceder a la capa de persistencia.
     */
    private final IPersistenciaFachada fachada;

    /**
     * Expresión para validar identificadores ObjectId de MongoDB.
     */
    private static final Pattern PATRON_ID = Pattern.compile("^[0-9a-fA-F]{24}$");

    /**
     * Constructor que inicializa la fachada de persistencia.
     */
    public ClienteBO() {
        this.fachada = new PersistenciaFachada();
    }

    /**
     * Registra un nuevo cliente.
     *
     * @param cliente Datos del cliente que se desea registrar.
     * @throws NegocioException Si los datos son inválidos o ocurre un error.
     */
    @Override
    public void registrarCliente(ClienteDTO cliente) throws NegocioException {
        if (cliente == null) {
            throw new NegocioException("Los datos del cliente son obligatorios.");
        }

        validarDatosCliente(cliente);
        validarIdMembresia(cliente.getIdMembresia());

        try {
            Cliente entidad = Mappers.convertirAEntidad(cliente);
            fachada.obtenerClienteDAO().guardar(entidad);

        } catch (PersistenciaException e) {
            throw new NegocioException("Error al registrar el cliente.", e);
        }
    }

    /**
     * Actualiza los datos de un cliente existente.
     *
     * @param cliente Datos actualizados del cliente.
     * @throws NegocioException Si los datos son inválidos o ocurre un error.
     */
    @Override
    public void actualizarCliente(ClienteDTO cliente) throws NegocioException {
        if (cliente == null) {
            throw new NegocioException("Los datos del cliente son obligatorios.");
        }

        validarIdCliente(cliente.getIdCliente());
        validarDatosCliente(cliente);
        validarIdMembresia(cliente.getIdMembresia());

        try {
            Cliente entidad = Mappers.convertirAEntidad(cliente);
            fachada.obtenerClienteDAO().actualizar(entidad);

        } catch (PersistenciaException e) {
            throw new NegocioException("Error al actualizar el cliente.", e);
        }
    }

    /**
     * Busca un cliente mediante su identificador.
     *
     * @param id Identificador del cliente.
     * @return Datos del cliente encontrado.
     * @throws NegocioException Si el identificador es inválido o no existe.
     */
    @Override
    public ClienteDTO obtenerClientePorId(String id) throws NegocioException {
        validarIdCliente(id);

        try {
            Cliente cliente = fachada.obtenerClienteDAO().obtenerPorId(id);

            if (cliente == null) {
                throw new NegocioException("No se encontró el cliente.");
            }

            return Mappers.convertirADTO(cliente);

        } catch (PersistenciaException e) {
            throw new NegocioException("Error al consultar el cliente.", e);
        }
    }

    /**
     * Obtiene todos los clientes registrados.
     *
     * @return Lista de clientes en formato DTO.
     * @throws NegocioException Si ocurre un error al consultar los clientes.
     */
    @Override
    public List<ClienteDTO> obtenerTodosLosClientes() throws NegocioException {
        try {
            List<Cliente> clientes = fachada.obtenerClienteDAO().obtenerTodos();

            if (clientes == null) {
                throw new NegocioException("No fue posible obtener los clientes.");
            }

            List<ClienteDTO> clientesDTO = new ArrayList<>();

            for (Cliente cliente : clientes) {
                if (cliente != null) {
                    clientesDTO.add(Mappers.convertirADTO(cliente));
                }
            }

            return clientesDTO;

        } catch (PersistenciaException e) {
            throw new NegocioException("Error al obtener los clientes.", e);
        }
    }

    /**
     * Valida el formato del identificador de un cliente.
     *
     * @param id Identificador del cliente.
     * @throws NegocioException Si el identificador es inválido.
     */
    private void validarIdCliente(String id) throws NegocioException {
        if (id == null || id.isBlank()) {
            throw new NegocioException("El ID del cliente es obligatorio.");
        }

        if (!PATRON_ID.matcher(id).matches()) {
            throw new NegocioException("El ID del cliente no tiene un formato válido.");
        }
    }

    /**
     * Valida el identificador de la membresía cuando se proporciona.
     *
     * @param idMembresia Identificador de la membresía.
     * @throws NegocioException Si el identificador tiene un formato inválido.
     */
    private void validarIdMembresia(String idMembresia)throws NegocioException {
        if (idMembresia != null && !idMembresia.isBlank() && !PATRON_ID.matcher(idMembresia).matches()) {
            throw new NegocioException("El ID de la membresía no tiene un formato válido.");
        }
    }

    /**
     * Valida los datos obligatorios del cliente.
     *
     * @param cliente Cliente que se desea validar.
     * @throws NegocioException Si algún dato obligatorio es inválido.
     */
    private void validarDatosCliente(ClienteDTO cliente)throws NegocioException {

        if (cliente.getNombre() == null || cliente.getNombre().isBlank()) {
            throw new NegocioException("El nombre del cliente es obligatorio.");
        }

        if (cliente.getApellido() == null || cliente.getApellido().isBlank()) {
            throw new NegocioException("El apellido del cliente es obligatorio.");
        }

        if (cliente.getTelefono() == null || cliente.getTelefono().isBlank()) {
            throw new NegocioException("El teléfono del cliente es obligatorio.");
        }

        if (cliente.getFechaIngreso() == null) {
            throw new NegocioException("La fecha de ingreso es obligatoria.");
        }
    }
    
}

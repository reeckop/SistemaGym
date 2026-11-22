/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gym.servicios.servicios;

import gym.excepciones.NegocioException;
import gym.persistencia.entidades.Cliente;
import gym.persistencia.entidades.Membresia;
import gym.persistencia.excepciones.PersistenciaException;
import gym.persistencia.unitofwork.IUnitOfWork;
import gym.persistencia.unitofwork.UnitOfWork;
import gym.servicios.dto.ClienteDTO;
import gym.servicios.mappers.Mappers;
import java.util.ArrayList;
import java.util.List;
import gym.servicios.interfaces.IClienteServicios;
import java.time.LocalDate;
import java.util.Objects;
import java.util.regex.Pattern;

/**
 *
 * @author PC GAMER MASTER RACE
 */
public class ClienteServicios implements IClienteServicios {
    
    @Override
    public void registrarCliente(ClienteDTO cliente) throws NegocioException {
        validarCliente(cliente);

        IUnitOfWork unitOfWork = new UnitOfWork();

        try {
            Cliente existente = unitOfWork.clienteDAO().obtenerPorId(cliente.getIdCliente());

            if (existente != null) {
                throw new NegocioException("El cliente ya está registrado.");
            }

            Membresia membresia = obtenerMembresia(unitOfWork, cliente.getIdMembresia());

            Cliente entidad = Mappers.convertirAEntidad(cliente, membresia);
            
            if (membresia != null) {
                entidad.setFechaInicioMembresia(java.time.LocalDate.now());
                entidad.setFechaFinMembresia(entidad.getFechaInicioMembresia().plusMonths(1));
            }

            unitOfWork.clienteDAO().guardar(entidad);

            unitOfWork.commit();

        } catch (NegocioException e) {
            unitOfWork.rollback();
            throw e;

        } catch (PersistenciaException e) {
            unitOfWork.rollback();
            throw new NegocioException("Error al registrar el cliente.", e);
        }
    }

    @Override
    public void actualizarCliente(ClienteDTO cliente) throws NegocioException {
        validarCliente(cliente);

        IUnitOfWork unitOfWork = new UnitOfWork();

        try {
            Cliente existente = unitOfWork.clienteDAO().obtenerPorId(cliente.getIdCliente());

            if (existente == null) {
                throw new NegocioException("El cliente no existe.");
            }

            Membresia membresia = obtenerMembresia(unitOfWork, cliente.getIdMembresia());

            Cliente entidad = Mappers.convertirAEntidad(cliente, membresia);

            String idMembresiaAnterior = existente.getMembresia() == null ? null : existente.getMembresia().getIdMembresia();

            String idMembresiaNueva = membresia == null ? null : membresia.getIdMembresia();

            if (Objects.equals(idMembresiaAnterior, idMembresiaNueva)) {
                entidad.setFechaInicioMembresia(existente.getFechaInicioMembresia());
                entidad.setFechaFinMembresia(existente.getFechaFinMembresia());
            } else if (membresia != null) {
                entidad.setFechaInicioMembresia(LocalDate.now());
                entidad.setFechaFinMembresia(entidad.getFechaInicioMembresia().plusMonths(1));
            } else {
                entidad.setFechaInicioMembresia(null);
                entidad.setFechaFinMembresia(null);
            }

            unitOfWork.clienteDAO().actualizar(entidad);

            unitOfWork.commit();

        } catch (NegocioException e) {
            unitOfWork.rollback();
            throw e;
        } catch (PersistenciaException e) {
            unitOfWork.rollback();
            throw new NegocioException("Error al actualizar el cliente.", e);
        }
    }

    @Override
    public ClienteDTO consultarCliente(String id) throws NegocioException {

        if (id == null || id.isBlank()) {
            throw new NegocioException("El ID del cliente es obligatorio.");
        }

        IUnitOfWork unitOfWork = new UnitOfWork();

        try {
            Cliente cliente = unitOfWork.clienteDAO().obtenerPorId(id);

            if (cliente == null) {
                throw new NegocioException("No se encontro el cliente.");
            }

            ClienteDTO clienteDTO = Mappers.convertirADTO(cliente);

            unitOfWork.commit();

            return clienteDTO;

        } catch (NegocioException e) {
            unitOfWork.rollback();
            throw e;

        } catch (PersistenciaException e) {
            unitOfWork.rollback();
            throw new NegocioException("Error al consultar el cliente.", e);
        }
    }

    @Override
    public List<ClienteDTO> consultarTodosLosClientes() throws NegocioException {

        IUnitOfWork unitOfWork = new UnitOfWork();

        try {
            List<Cliente> clientes = unitOfWork.clienteDAO().obtenerTodos();

            List<ClienteDTO> clientesDTO = new ArrayList<>();

            if (clientes != null) {
                for (Cliente cliente : clientes) {
                    if (cliente != null) {
                        clientesDTO.add(Mappers.convertirADTO(cliente));
                    }
                }
            }

            unitOfWork.commit();

            return clientesDTO;

        } catch (PersistenciaException e) {
            unitOfWork.rollback();
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

    private Membresia obtenerMembresia(IUnitOfWork unitOfWork, String idMembresia) throws PersistenciaException, NegocioException {
        if (idMembresia == null || idMembresia.isBlank()) {
            return null;
        }

        List<Membresia> membresias = unitOfWork.membresiaDAO().obtenerTodos();

        if (membresias != null) {
            for (Membresia membresia : membresias) {
                if (membresia != null && idMembresia.equals(membresia.getIdMembresia())) {
                    return membresia;
                }
            }
        }
        throw new NegocioException("No se encontro la membresia indicada.");
    }
}

    
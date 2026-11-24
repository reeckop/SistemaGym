package gym.persistencia.interfaces;

import gym.persistencia.entidades.Cliente;
import gym.persistencia.excepciones.PersistenciaException;
import java.util.List;

/**
 * Define las operaciones de persistencia relacionadas con cliente.
 *
 * @author PC GAMER MASTER RACE
 */
public interface IClienteDAO {
    void guardar(Cliente cliente) throws PersistenciaException;
    void actualizar(Cliente cliente) throws PersistenciaException;
    Cliente obtenerPorId(String id) throws PersistenciaException;
    List<Cliente> obtenerTodos() throws PersistenciaException;
}

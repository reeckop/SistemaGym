package gym.persistencia.dao;

import gym.persistencia.entidades.Cliente;
import gym.persistencia.excepciones.PersistenciaException;
import java.util.List;

public interface IClienteDAO {
    void guardar(Cliente cliente) throws PersistenciaException;
    void actualizar(Cliente cliente) throws PersistenciaException;
    Cliente obtenerPorId(String id) throws PersistenciaException;
    List<Cliente> obtenerTodos() throws PersistenciaException;
}

package gym.persistencia.dao;

import gym.persistencia.entidades.Cliente;
import java.util.List;

public interface IClienteDAO {
    void guardar(Cliente cliente);
    void actualizar(Cliente cliente);
    Cliente obtenerPorId(int id);
    List<Cliente> obtenerTodos();
}

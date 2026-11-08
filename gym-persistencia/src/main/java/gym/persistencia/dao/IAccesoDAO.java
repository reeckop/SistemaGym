package gym.persistencia.dao;

import gym.persistencia.entidades.RegistroIngreso;
import java.util.List;

public interface IAccesoDAO {
    void guardar(RegistroIngreso registro);
    List<RegistroIngreso> obtenerPorCliente(int idCliente);
    List<RegistroIngreso> obtenerTodos();
}

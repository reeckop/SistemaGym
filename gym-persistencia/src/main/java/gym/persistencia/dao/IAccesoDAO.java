package gym.persistencia.dao;

import gym.persistencia.entidades.RegistroIngreso;
import gym.persistencia.excepciones.PersistenciaException;
import java.util.List;

public interface IAccesoDAO {
    void guardar(RegistroIngreso registro) throws PersistenciaException;
    List<RegistroIngreso> obtenerPorCliente(String idCliente) throws PersistenciaException;
    List<RegistroIngreso> obtenerTodos() throws PersistenciaException;
}

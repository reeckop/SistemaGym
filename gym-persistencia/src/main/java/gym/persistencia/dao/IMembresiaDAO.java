package gym.persistencia.dao;

import gym.persistencia.entidades.Membresia;
import gym.persistencia.excepciones.PersistenciaException;
import java.util.List;

public interface IMembresiaDAO {
    void guardar(Membresia membresia) throws PersistenciaException;
    void actualizar(Membresia membresia) throws PersistenciaException;
    void eliminar(String id) throws PersistenciaException;
    List<Membresia> obtenerTodos() throws PersistenciaException;
}

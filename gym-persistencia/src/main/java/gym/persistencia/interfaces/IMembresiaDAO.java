package gym.persistencia.interfaces;

import gym.persistencia.entidades.Membresia;
import gym.persistencia.excepciones.PersistenciaException;
import java.util.List;

/**
 * Define las operaciones de persistencia relacionadas con membresía.
 *
 * @author PC GAMER MASTER RACE
 */
public interface IMembresiaDAO {
    void guardar(Membresia membresia) throws PersistenciaException;
    void actualizar(Membresia membresia) throws PersistenciaException;
    void eliminar(String id) throws PersistenciaException;
    List<Membresia> obtenerTodos() throws PersistenciaException;
}

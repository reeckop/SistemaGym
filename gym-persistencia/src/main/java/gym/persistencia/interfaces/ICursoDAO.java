package gym.persistencia.interfaces;

import gym.persistencia.entidades.Curso;
import gym.persistencia.excepciones.PersistenciaException;
import java.util.List;

/**
 * Define las operaciones de persistencia relacionadas con curso.
 *
 * @author PC GAMER MASTER RACE
 */
public interface ICursoDAO {
    void guardar(Curso curso) throws PersistenciaException;
    void actualizar(Curso curso) throws PersistenciaException;
    void eliminar(String id) throws PersistenciaException;
    List<Curso> obtenerTodos() throws PersistenciaException;
}

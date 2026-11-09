package gym.persistencia.interfaces;

import gym.persistencia.entidades.Inscripcion;
import gym.persistencia.excepciones.PersistenciaException;
import java.util.List;

public interface IInscripcionDAO {
    void guardar(Inscripcion inscripcion) throws PersistenciaException;
    void actualizar(Inscripcion inscripcion) throws PersistenciaException;
    void eliminar(String id) throws PersistenciaException;
    List<Inscripcion> obtenerTodos() throws PersistenciaException;
}

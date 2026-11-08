package gym.persistencia.dao;

import gym.persistencia.entidades.Inscripcion;
import java.util.List;

public interface IInscripcionDAO {
    void guardar(Inscripcion inscripcion);
    void actualizar(Inscripcion inscripcion);
    void eliminar(int id);
    List<Inscripcion> obtenerTodos();
}

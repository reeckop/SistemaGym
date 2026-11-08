package gym.persistencia.dao;

import gym.persistencia.entidades.Curso;
import java.util.List;

public interface ICursoDAO {
    void guardar(Curso curso);
    void actualizar(Curso curso);
    void eliminar(int id);
    List<Curso> obtenerTodos();
}

package gym.persistencia.dao;

import gym.persistencia.entidades.Membresia;
import java.util.List;

public interface IMembresiaDAO {
    void guardar(Membresia membresia);
    void actualizar(Membresia membresia);
    void eliminar(int id);
    List<Membresia> obtenerTodos();
}

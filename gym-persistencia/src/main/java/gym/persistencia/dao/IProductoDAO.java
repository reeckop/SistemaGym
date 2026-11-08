package gym.persistencia.dao;

import gym.persistencia.entidades.Producto;
import java.util.List;

public interface IProductoDAO {
    void guardar(Producto producto);
    void actualizar(Producto producto);
    void eliminar(int id);
    List<Producto> obtenerTodos();
}

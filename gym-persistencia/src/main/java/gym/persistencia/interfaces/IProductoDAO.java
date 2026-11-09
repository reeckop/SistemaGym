package gym.persistencia.interfaces;

import gym.persistencia.entidades.Producto;
import gym.persistencia.excepciones.PersistenciaException;
import java.util.List;

public interface IProductoDAO {
    void guardar(Producto producto) throws PersistenciaException;
    void actualizar(Producto producto) throws PersistenciaException;
    void eliminar(String id) throws PersistenciaException;
    List<Producto> obtenerTodos() throws PersistenciaException;
}

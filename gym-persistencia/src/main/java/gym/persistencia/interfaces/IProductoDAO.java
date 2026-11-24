package gym.persistencia.interfaces;

import gym.persistencia.entidades.Producto;
import gym.persistencia.excepciones.PersistenciaException;
import java.util.List;

/**
 * Define las operaciones de persistencia relacionadas con producto.
 *
 * @author PC GAMER MASTER RACE
 */
public interface IProductoDAO {
    void guardar(Producto producto) throws PersistenciaException;
    void actualizar(Producto producto) throws PersistenciaException;
    void eliminar(String id) throws PersistenciaException;
    Producto obtenerPorId(String id) throws PersistenciaException;
    List<Producto> obtenerTodos() throws PersistenciaException;
}

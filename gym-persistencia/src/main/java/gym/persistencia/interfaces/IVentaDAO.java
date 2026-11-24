package gym.persistencia.interfaces;

import gym.persistencia.entidades.Venta;
import gym.persistencia.excepciones.PersistenciaException;
import java.util.List;

/**
 * Define las operaciones de persistencia relacionadas con venta.
 *
 * @author PC GAMER MASTER RACE
 */
public interface IVentaDAO {
    void guardar(Venta venta) throws PersistenciaException;
    void actualizarVenta(Venta venta) throws PersistenciaException;
    Venta obtenerPorId(String id) throws PersistenciaException;
    List<Venta> obtenerTodas() throws PersistenciaException;
}

package gym.persistencia.dao;

import gym.persistencia.entidades.Venta;
import gym.persistencia.excepciones.PersistenciaException;
import java.util.List;

public interface IVentaDAO {
    void guardar(Venta venta) throws PersistenciaException;
    Venta obtenerPorId(String id) throws PersistenciaException;
    List<Venta> obtenerTodas() throws PersistenciaException;
}

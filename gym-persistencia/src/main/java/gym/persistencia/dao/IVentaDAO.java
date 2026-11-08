package gym.persistencia.dao;

import gym.persistencia.entidades.Venta;
import java.util.List;

public interface IVentaDAO {
    void guardar(Venta venta);
    Venta obtenerPorId(int id);
    List<Venta> obtenerTodas();
}

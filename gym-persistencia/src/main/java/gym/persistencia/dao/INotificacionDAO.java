package gym.persistencia.dao;

import gym.persistencia.entidades.Notificacion;
import java.util.List;

public interface INotificacionDAO {
    void guardar(Notificacion notificacion);
    List<Notificacion> obtenerPorCliente(int idCliente);
    List<Notificacion> obtenerTodas();
}

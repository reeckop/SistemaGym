package gym.persistencia.interfaces;

import gym.persistencia.entidades.Notificacion;
import gym.persistencia.excepciones.PersistenciaException;
import java.util.List;

public interface INotificacionDAO {
    void guardar(Notificacion notificacion) throws PersistenciaException;
    List<Notificacion> obtenerPorCliente(String idCliente) throws PersistenciaException;
    List<Notificacion> obtenerTodas() throws PersistenciaException;
}

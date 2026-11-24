package gym.persistencia.interfaces;

import gym.persistencia.entidades.Notificacion;
import gym.persistencia.excepciones.PersistenciaException;
import java.util.List;

/**
 * Define las operaciones de persistencia relacionadas con notificación.
 *
 * @author PC GAMER MASTER RACE
 */
public interface INotificacionDAO {
    void guardar(Notificacion notificacion) throws PersistenciaException;
    void actualizarNotificacion(Notificacion notificacion)throws PersistenciaException;
    List<Notificacion> obtenerPorCliente(String idCliente) throws PersistenciaException;
    List<Notificacion> obtenerTodas() throws PersistenciaException;
}

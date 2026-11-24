/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gym.persistencia.dao;

import com.mongodb.MongoException;
import com.mongodb.client.ClientSession;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import gym.persistencia.interfaces.INotificacionDAO;
import gym.persistencia.entidades.Notificacion;
import gym.persistencia.excepciones.PersistenciaException;
import java.util.ArrayList;
import java.util.List;
import org.bson.types.ObjectId;

/**
 * Implementa las operaciones de persistencia para notificación.
 *
 * @author PC GAMER MASTER RACE
 */
public class NotificacionDAO extends BaseDAO implements INotificacionDAO {

    private static final String NOMBRE_COLECCION = "notificaciones";
    
    public NotificacionDAO(ClientSession sesion) { 
        super(sesion); 
    }

    @Override
    public void guardar(Notificacion notificacion) throws PersistenciaException {
        try {
            MongoCollection<Notificacion> coleccion = obtenerColeccion(NOMBRE_COLECCION, Notificacion.class);
            coleccion.insertOne(notificacion);
        } catch (MongoException e) {
            throw new PersistenciaException("Error al guardar la notificacion.");
        }
    }

    @Override
    public List<Notificacion> obtenerPorCliente(String idCliente) throws PersistenciaException {
        try {
            MongoCollection<Notificacion> coleccion = obtenerColeccion(NOMBRE_COLECCION, Notificacion.class);
            return coleccion.find(Filters.eq("cliente._id", new ObjectId(idCliente))).into(new ArrayList<>());
        } catch (MongoException e) {
            throw new PersistenciaException("Error al obtener las notificaciones del cliente.");
        }
    }

    @Override
    public List<Notificacion> obtenerTodas() throws PersistenciaException {
        try {
            MongoCollection<Notificacion> coleccion = obtenerColeccion(NOMBRE_COLECCION, Notificacion.class);
            return coleccion.find().into(new ArrayList<>());
        } catch (MongoException e) {
            throw new PersistenciaException("Error al obtener todas las notificaciones.");
        }
    }

    @Override
    public void actualizarNotificacion(Notificacion notificacion) throws PersistenciaException {
        try {
            MongoCollection<Notificacion> coleccion = obtenerColeccion(NOMBRE_COLECCION, Notificacion.class);
            coleccion.replaceOne(Filters.eq("_id", new ObjectId(notificacion.getIdNotificacion())), notificacion);
        } catch (MongoException e) {
            throw new PersistenciaException("Error al actualizar la notificacion.");
        }
    }
    
}

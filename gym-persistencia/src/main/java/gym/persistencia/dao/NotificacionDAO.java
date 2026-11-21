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
 *
 * @author PC GAMER MASTER RACE
 */
public class NotificacionDAO extends BaseDAO implements INotificacionDAO {

    public NotificacionDAO() {
    }

    public NotificacionDAO(ClientSession sesion) {
        super(sesion);
    }

    private static final String NOMBRE_COLECCION = "notificaciones";

    @Override
    public void guardar(Notificacion notificacion) throws PersistenciaException {
        try {
            MongoCollection<Notificacion> coleccion = obtenerColeccion(NOMBRE_COLECCION, Notificacion.class);
            insertOne(coleccion, notificacion);
        } catch (MongoException e) {
            throw new PersistenciaException("Error al guardar la notificación.");
        }
    }

    @Override
    public List<Notificacion> obtenerPorCliente(String idCliente) throws PersistenciaException {
        try {
            MongoCollection<Notificacion> coleccion = obtenerColeccion(NOMBRE_COLECCION, Notificacion.class);
            return find(coleccion, Filters.eq("cliente._id", new ObjectId(idCliente))).into(new ArrayList<>());
        } catch (MongoException e) {
            throw new PersistenciaException("Error al obtener las notificaciones del cliente.");
        }
    }

    @Override
    public List<Notificacion> obtenerTodas() throws PersistenciaException {
        try {
            MongoCollection<Notificacion> coleccion = obtenerColeccion(NOMBRE_COLECCION, Notificacion.class);
            return find(coleccion).into(new ArrayList<>());
        } catch (MongoException e) {
            throw new PersistenciaException("Error al obtener todas las notificaciones.");
        }
    }

    @Override
    public void actualizarNotificacion(Notificacion notificacion) throws PersistenciaException {
        try {
            MongoCollection<Notificacion> coleccion = obtenerColeccion(NOMBRE_COLECCION, Notificacion.class);
            replaceOne(coleccion, Filters.eq("_id", new ObjectId(notificacion.getIdNotificacion())), notificacion);
        } catch (MongoException e) {
            throw new PersistenciaException("Error al actualizar la notificacion.");
        }
    }
    
}

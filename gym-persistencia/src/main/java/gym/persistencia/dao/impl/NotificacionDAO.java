/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gym.persistencia.dao.impl;

import com.mongodb.MongoException;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.result.InsertOneResult;
import gym.infraestructura.persistencia.MongoConnectionManager;
import static gym.infraestructura.persistencia.MongoConnectionManager.obtenerCodecs;
import gym.persistencia.dao.INotificacionDAO;
import gym.persistencia.entidades.Notificacion;
import gym.persistencia.excepciones.PersistenciaException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 *
 * @author PC GAMER MASTER RACE
 */
public class NotificacionDAO implements INotificacionDAO {
    private static final Logger LOGGER = Logger.getLogger(NotificacionDAO.class.getName());

    private static final String NOMBRE_COLECCION = "notificaciones";

    /**
     * Obtiene la base de datos configurada para el sistema.
     *
     * @param cliente Conexión activa con MongoDB.
     * @return Base de datos del sistema.
     */
    private MongoDatabase obtenerBaseDatos(MongoClient cliente) {
        MongoDatabase empresaBD = cliente.getDatabase(MongoConnectionManager.BASE_DATOS).withCodecRegistry(obtenerCodecs());
        return empresaBD;
    }

    /**
     * Obtiene la colección de notificaciones.
     *
     * @param baseDatos Base de datos del sistema.
     * @return Colección de notificaciones.
     */
    private MongoCollection<Notificacion> obtenerColeccion(MongoDatabase baseDatos) {
        MongoCollection<Notificacion> coleccion = baseDatos.getCollection(NOMBRE_COLECCION, Notificacion.class);
        return coleccion;
    }

    /**
     * Guarda una nueva notificación en MongoDB.
     *
     * @param notificacion Notificación que se desea registrar.
     * @throws PersistenciaException Si ocurre un error al guardar.
     */
    @Override
    public void guardar(Notificacion notificacion) throws PersistenciaException {
        if (notificacion == null) {
            throw new PersistenciaException("La notificación no puede ser nula.");
        }

        try (MongoClient conexion = MongoConnectionManager.crearConexion()) {
            MongoDatabase baseDatos = obtenerBaseDatos(conexion);
            MongoCollection<Notificacion> coleccion = obtenerColeccion(baseDatos);

            InsertOneResult resultado = coleccion.insertOne(notificacion);

            if (!resultado.wasAcknowledged()) {
                throw new PersistenciaException("No se pudo confirmar el registro de la notificación.");
            }

        } catch (MongoException ex) {
            LOGGER.severe(ex.getMessage());
            throw new PersistenciaException("Error al guardar la notificación.", ex);
        }
    }

    /**
     * Obtiene las notificaciones de un cliente específico.
     *
     * @param idCliente Identificador del cliente.
     * @return Lista de notificaciones del cliente.
     * @throws PersistenciaException Si ocurre un error en la consulta.
     */
    @Override
    public List<Notificacion> obtenerPorCliente(String idCliente) throws PersistenciaException {
        List<Notificacion> notificaciones = new ArrayList<>();

        try (MongoClient conexion = MongoConnectionManager.crearConexion()) {
            MongoDatabase baseDatos = obtenerBaseDatos(conexion);
            MongoCollection<Notificacion> coleccion = obtenerColeccion(baseDatos);

            coleccion.find(Filters.eq("cliente.idCliente", idCliente)).into(notificaciones);

            return notificaciones;

        } catch (MongoException ex) {
            LOGGER.severe(ex.getMessage());
            throw new PersistenciaException("Error al consultar las notificaciones del cliente.", ex);
        }
    }

    /**
     * Obtiene todas las notificaciones registradas en MongoDB.
     *
     * @return Lista de todas las notificaciones.
     * @throws PersistenciaException Si ocurre un error en la consulta.
     */
    @Override
    public List<Notificacion> obtenerTodas() throws PersistenciaException {
        List<Notificacion> notificaciones = new ArrayList<>();

        try (MongoClient conexion = MongoConnectionManager.crearConexion()) {
            MongoDatabase baseDatos = obtenerBaseDatos(conexion);
            MongoCollection<Notificacion> coleccion = obtenerColeccion(baseDatos);

            coleccion.find().into(notificaciones);

            return notificaciones;

        } catch (MongoException ex) {
            LOGGER.severe(ex.getMessage());
            throw new PersistenciaException("Error al consultar las notificaciones.", ex);
        }
    }
    
}

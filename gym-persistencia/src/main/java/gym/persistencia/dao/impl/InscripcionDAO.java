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
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.InsertOneResult;
import com.mongodb.client.result.UpdateResult;
import gym.infraestructura.persistencia.MongoConnectionManager;
import static gym.infraestructura.persistencia.MongoConnectionManager.obtenerCodecs;
import gym.persistencia.dao.IInscripcionDAO;
import gym.persistencia.entidades.Inscripcion;
import gym.persistencia.excepciones.PersistenciaException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 *
 * @author PC GAMER MASTER RACE
 */
public class InscripcionDAO implements IInscripcionDAO {
    private static final Logger LOGGER = Logger.getLogger(InscripcionDAO.class.getName());

    private static final String NOMBRE_COLECCION = "inscripciones";

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
     * Obtiene la colección de inscripciones.
     *
     * @param baseDatos Base de datos del sistema.
     * @return Colección de inscripciones.
     */
    private MongoCollection<Inscripcion> obtenerColeccion(MongoDatabase baseDatos) {
        MongoCollection<Inscripcion> coleccion = baseDatos.getCollection(NOMBRE_COLECCION, Inscripcion.class);
        return coleccion;
    }

    /**
     * Guarda una nueva inscripción en MongoDB.
     *
     * @param inscripcion Inscripción que se desea registrar.
     * @throws PersistenciaException Si ocurre un error al guardar.
     */
    @Override
    public void guardar(Inscripcion inscripcion) throws PersistenciaException {

        if (inscripcion == null) {
            throw new PersistenciaException("La inscripción no puede ser nula.");
        }

        try (MongoClient conexion = MongoConnectionManager.crearConexion()) {
            MongoDatabase baseDatos = obtenerBaseDatos(conexion);
            MongoCollection<Inscripcion> coleccion = obtenerColeccion(baseDatos);

            InsertOneResult resultado = coleccion.insertOne(inscripcion);

            if (!resultado.wasAcknowledged()) {
                throw new PersistenciaException("No se pudo confirmar el registro de la inscripción.");
            }

        } catch (MongoException ex) {
            LOGGER.severe(ex.getMessage());
            throw new PersistenciaException("Error al guardar la inscripción.", ex);
        }
    }

    /**
     * Actualiza la información de una inscripción existente.
     *
     * @param inscripcion Inscripción con la información actualizada.
     * @throws PersistenciaException Si ocurre un error al actualizar.
     */
    @Override
    public void actualizar(Inscripcion inscripcion) throws PersistenciaException {
        if (inscripcion == null) {
            throw new PersistenciaException("La inscripción no puede ser nula.");
        }

        try (MongoClient conexion = MongoConnectionManager.crearConexion()) {
            MongoDatabase baseDatos = obtenerBaseDatos(conexion);
            MongoCollection<Inscripcion> coleccion = obtenerColeccion(baseDatos);

            UpdateResult resultado = coleccion.replaceOne(Filters.eq("idInscripcion", inscripcion.getIdInscripcion()),inscripcion);

            if (!resultado.wasAcknowledged()) {
                throw new PersistenciaException("No se pudo confirmar la actualización de la inscripción.");
            }

            if (resultado.getMatchedCount() == 0) {
                throw new PersistenciaException("No se encontró la inscripción que se desea actualizar.");
            }

        } catch (MongoException ex) {
            LOGGER.severe(ex.getMessage());
            throw new PersistenciaException("Error al actualizar la inscripción.", ex);
        }
    }

    /**
     * Elimina una inscripción mediante su identificador.
     *
     * @param id Identificador de la inscripción que se desea eliminar.
     * @throws PersistenciaException Si ocurre un error al eliminar.
     */
    @Override
    public void eliminar(String id) throws PersistenciaException {
        try (MongoClient conexion = MongoConnectionManager.crearConexion()) {
            MongoDatabase baseDatos = obtenerBaseDatos(conexion);
            MongoCollection<Inscripcion> coleccion = obtenerColeccion(baseDatos);

            DeleteResult resultado = coleccion.deleteOne(Filters.eq("idInscripcion", id));

            if (!resultado.wasAcknowledged()) {
                throw new PersistenciaException("No se pudo confirmar la eliminación de la inscripción.");
            }

            if (resultado.getDeletedCount() == 0) {
                throw new PersistenciaException("No se encontró la inscripción que se desea eliminar.");
            }

        } catch (MongoException ex) {
            LOGGER.severe(ex.getMessage());
            throw new PersistenciaException("Error al eliminar la inscripción.", ex);
        }
    }

    /**
     * Obtiene todas las inscripciones registradas en MongoDB.
     *
     * @return Lista de inscripciones encontradas.
     * @throws PersistenciaException Si ocurre un error en la consulta.
     */
    @Override
    public List<Inscripcion> obtenerTodos() throws PersistenciaException {

        List<Inscripcion> inscripciones = new ArrayList<>();

        try (MongoClient conexion = MongoConnectionManager.crearConexion()) {
            MongoDatabase baseDatos = obtenerBaseDatos(conexion);
            MongoCollection<Inscripcion> coleccion = obtenerColeccion(baseDatos);

            coleccion.find().into(inscripciones);

            return inscripciones;

        } catch (MongoException ex) {
            LOGGER.severe(ex.getMessage());
            throw new PersistenciaException("Error al consultar las inscripciones.", ex);
        }
    }
    
}

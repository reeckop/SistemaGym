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
import gym.persistencia.dao.IMembresiaDAO;
import gym.persistencia.entidades.Membresia;
import gym.persistencia.excepciones.PersistenciaException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import org.bson.types.ObjectId;

public class MembresiaDAO implements IMembresiaDAO {
    private static final Logger LOGGER = Logger.getLogger(MembresiaDAO.class.getName());

    private static final String NOMBRE_COLECCION = "membresias";

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
     * Obtiene la colección de membresías.
     *
     * @param baseDatos Base de datos del sistema.
     * @return Colección de membresías.
     */
    private MongoCollection<Membresia> obtenerColeccion(MongoDatabase baseDatos) {
        MongoCollection<Membresia> coleccion = baseDatos.getCollection(NOMBRE_COLECCION, Membresia.class);
        return coleccion;
    }

    /**
     * Guarda una nueva membresía en MongoDB.
     *
     * @param membresia Membresía que se desea registrar.
     * @throws PersistenciaException Si ocurre un error al guardar.
     */
    @Override
    public void guardar(Membresia membresia) throws PersistenciaException {
        if (membresia == null) {
            throw new PersistenciaException("La membresía no puede ser nula.");
        }

        try (MongoClient conexion = MongoConnectionManager.crearConexion()) {
            MongoDatabase baseDatos = obtenerBaseDatos(conexion);
            MongoCollection<Membresia> coleccion = obtenerColeccion(baseDatos);

            InsertOneResult resultado = coleccion.insertOne(membresia);

            if (!resultado.wasAcknowledged()) {
                throw new PersistenciaException("No se pudo confirmar el registro de la membresía.");
            }

        } catch (MongoException ex) {
            LOGGER.severe(ex.getMessage());
            throw new PersistenciaException("Error al guardar la membresía.", ex);
        }
    }

    /**
     * Actualiza la información de una membresía existente.
     *
     * @param membresia Membresía con la información actualizada.
     * @throws PersistenciaException Si ocurre un error al actualizar.
     */
    @Override
    public void actualizar(Membresia membresia) throws PersistenciaException {
        if (membresia == null) {
            throw new PersistenciaException("La membresía no puede ser nula.");
        }

        try (MongoClient conexion = MongoConnectionManager.crearConexion()) {
            MongoDatabase baseDatos = obtenerBaseDatos(conexion);
            MongoCollection<Membresia> coleccion = obtenerColeccion(baseDatos);

            UpdateResult resultado = coleccion.replaceOne(Filters.eq("_id", new ObjectId(membresia.getIdMembresia())),membresia);

            if (!resultado.wasAcknowledged()) {
                throw new PersistenciaException("No se pudo confirmar la actualización de la membresía.");
            }

            if (resultado.getMatchedCount() == 0) {
                throw new PersistenciaException("No se encontró la membresía que se desea actualizar.");
            }

        } catch (MongoException ex) {
            LOGGER.severe(ex.getMessage());
            throw new PersistenciaException("Error al actualizar la membresía.", ex);
        }
    }

    /**
     * Elimina una membresía mediante su identificador.
     *
     * @param id Identificador de la membresía que se desea eliminar.
     * @throws PersistenciaException Si ocurre un error al eliminar.
     */
    @Override
    public void eliminar(String id) throws PersistenciaException {
        try (MongoClient conexion = MongoConnectionManager.crearConexion()) {
            MongoDatabase baseDatos = obtenerBaseDatos(conexion);
            MongoCollection<Membresia> coleccion = obtenerColeccion(baseDatos);

            DeleteResult resultado = coleccion.deleteOne(Filters.eq("_id", new ObjectId(id)));

            if (!resultado.wasAcknowledged()) {
                throw new PersistenciaException("No se pudo confirmar la eliminación de la membresía.");
            }

            if (resultado.getDeletedCount() == 0) {
                throw new PersistenciaException("No se encontró la membresía que se desea eliminar.");
            }

        } catch (MongoException ex) {
            LOGGER.severe(ex.getMessage());
            throw new PersistenciaException("Error al eliminar la membresía.", ex);
        }
    }

    /**
     * Obtiene todas las membresías registradas en MongoDB.
     *
     * @return Lista de membresías encontradas.
     * @throws PersistenciaException Si ocurre un error en la consulta.
     */
    @Override
    public List<Membresia> obtenerTodos() throws PersistenciaException {
        List<Membresia> membresias = new ArrayList<>();

        try (MongoClient conexion = MongoConnectionManager.crearConexion()) {
            MongoDatabase baseDatos = obtenerBaseDatos(conexion);
            MongoCollection<Membresia> coleccion = obtenerColeccion(baseDatos);

            coleccion.find().into(membresias);

            return membresias;

        } catch (MongoException ex) {
            LOGGER.severe(ex.getMessage());
            throw new PersistenciaException("Error al consultar las membresías.", ex);
        }
    }

   
}

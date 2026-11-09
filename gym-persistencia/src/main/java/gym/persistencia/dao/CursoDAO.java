package gym.persistencia.dao;

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
import gym.persistencia.interfaces.ICursoDAO;
import gym.persistencia.entidades.Curso;
import gym.persistencia.excepciones.PersistenciaException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import org.bson.types.ObjectId;

public class CursoDAO implements ICursoDAO {
    private static final Logger LOGGER = Logger.getLogger(CursoDAO.class.getName());

    private static final String NOMBRE_COLECCION = "cursos";

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
     * Obtiene la colección de cursos.
     *
     * @param baseDatos Base de datos del sistema.
     * @return Colección de cursos.
     */
    private MongoCollection<Curso> obtenerColeccion(MongoDatabase baseDatos) {
        MongoCollection<Curso> coleccion = baseDatos.getCollection(NOMBRE_COLECCION, Curso.class);
        return coleccion;
    }
    
    /**
     * Guarda un nuevo curso en MongoDB.
     *
     * @param curso Curso que se desea registrar.
     * @throws PersistenciaException Si ocurre un error al guardar.
     */
    @Override
    public void guardar(Curso curso) throws PersistenciaException {
        if (curso == null) {
            throw new PersistenciaException("El curso no puede ser nulo.");
        }

        try (MongoClient conexion = MongoConnectionManager.crearConexion()) {
            MongoDatabase baseDatos = obtenerBaseDatos(conexion);
            MongoCollection<Curso> coleccion = obtenerColeccion(baseDatos);

            InsertOneResult resultado = coleccion.insertOne(curso);

            if (!resultado.wasAcknowledged()) {
                throw new PersistenciaException("No se pudo confirmar el registro del curso.");
            }

        } catch (MongoException ex) {
            LOGGER.severe(ex.getMessage());
            throw new PersistenciaException("Error al guardar el curso.", ex);
        }
    }

    /**
     * Actualiza la información de un curso existente.
     *
     * @param curso Curso con la información actualizada.
     * @throws PersistenciaException Si ocurre un error al actualizar.
     */
    @Override
    public void actualizar(Curso curso) throws PersistenciaException {

        if (curso == null) {
            throw new PersistenciaException(
                    "El curso no puede ser nulo."
            );
        }

        try (MongoClient conexion = MongoConnectionManager.crearConexion()) {

            MongoDatabase baseDatos = obtenerBaseDatos(conexion);
            MongoCollection<Curso> coleccion = obtenerColeccion(baseDatos);

            UpdateResult resultado = coleccion.replaceOne(Filters.eq("_id", new ObjectId(curso.getIdCurso())), curso);

            if (!resultado.wasAcknowledged()) {
                throw new PersistenciaException("No se pudo confirmar la actualización del curso.");
            }

            if (resultado.getMatchedCount() == 0) {
                throw new PersistenciaException("No se encontró el curso que se desea actualizar.");
            }

        } catch (MongoException ex) {
            LOGGER.severe(ex.getMessage());
            throw new PersistenciaException("Error al actualizar el curso.", ex);
        }
    }

    /**
     * Elimina un curso mediante su identificador.
     *
     * @param id Identificador del curso que se desea eliminar.
     * @throws PersistenciaException Si ocurre un error al eliminar.
     */
    @Override
    public void eliminar(String id) throws PersistenciaException {
        try (MongoClient conexion = MongoConnectionManager.crearConexion()) {
            MongoDatabase baseDatos = obtenerBaseDatos(conexion);
            MongoCollection<Curso> coleccion = obtenerColeccion(baseDatos);

            DeleteResult resultado = coleccion.deleteOne(Filters.eq("_id", new ObjectId(id)));

            if (!resultado.wasAcknowledged()) {
                throw new PersistenciaException("No se pudo confirmar la eliminación del curso.");
            }

            if (resultado.getDeletedCount() == 0) {
                throw new PersistenciaException("No se encontró el curso que se desea eliminar.");
            }

        } catch (MongoException ex) {
            LOGGER.severe(ex.getMessage());
            throw new PersistenciaException("Error al eliminar el curso.", ex);
        }
    }

    /**
     * Obtiene todos los cursos registrados en MongoDB.
     *
     * @return Lista de cursos encontrados.
     * @throws PersistenciaException Si ocurre un error en la consulta.
     */
    @Override
    public List<Curso> obtenerTodos() throws PersistenciaException {
        List<Curso> cursos = new ArrayList<>();

        try (MongoClient conexion = MongoConnectionManager.crearConexion()) {
            MongoDatabase baseDatos = obtenerBaseDatos(conexion);
            MongoCollection<Curso> coleccion = obtenerColeccion(baseDatos);

            coleccion.find().into(cursos);

            return cursos;

        } catch (MongoException ex) {
            LOGGER.severe(ex.getMessage());
            throw new PersistenciaException("Error al consultar los cursos.", ex);
        }
    }

    
}

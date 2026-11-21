package gym.persistencia.dao;

import com.mongodb.MongoException;
import com.mongodb.client.ClientSession;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.result.UpdateResult;
import gym.persistencia.interfaces.ICursoDAO;
import gym.persistencia.entidades.Curso;
import gym.persistencia.excepciones.PersistenciaException;
import java.util.ArrayList;
import java.util.List;
import org.bson.types.ObjectId;

public class CursoDAO extends BaseDAO implements ICursoDAO {

    private static final String NOMBRE_COLECCION = "cursos";

    public CursoDAO(ClientSession sesion) {
        super(sesion);
    }

    @Override
    public void guardar(Curso curso) throws PersistenciaException {
        try {
            MongoCollection<Curso> coleccion = obtenerColeccion(NOMBRE_COLECCION, Curso.class);
            coleccion.insertOne(obtenerSesion(), curso);
        } catch (MongoException e) {
            throw new PersistenciaException("Error al guardar el curso.");
        }
    }

    @Override
    public void actualizar(Curso curso) throws PersistenciaException {
        try {
            MongoCollection<Curso> coleccion = obtenerColeccion(NOMBRE_COLECCION, Curso.class);
            ObjectId id = new ObjectId(curso.getIdCurso());
            UpdateResult resultado = coleccion.replaceOne(obtenerSesion(), Filters.eq("_id", id), curso);

            if (resultado.getMatchedCount() == 0) {
                throw new PersistenciaException("No se encontro el Curso que se desea actualizar.");
            }
        } catch (MongoException | IllegalArgumentException e) {
            throw new PersistenciaException("Error al actualizar el curso.");
        }
    }

    @Override
    public void eliminar(String id) throws PersistenciaException {
        try {
            MongoCollection<Curso> coleccion = obtenerColeccion(NOMBRE_COLECCION, Curso.class);
            coleccion.deleteOne(obtenerSesion(), Filters.eq("_id", new ObjectId(id)));
        } catch (MongoException | IllegalArgumentException e) {
            throw new PersistenciaException("Error al eliminar el curso.");
        }
    }

    @Override
    public List<Curso> obtenerTodos() throws PersistenciaException {
        try {
            MongoCollection<Curso> coleccion = obtenerColeccion(NOMBRE_COLECCION, Curso.class);
            return coleccion.find(obtenerSesion()).into(new ArrayList<>());
        } catch (MongoException e) {
            throw new PersistenciaException("Error al obtener los cursos.");
        }
    }
}
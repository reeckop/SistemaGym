package gym.persistencia.dao;

import com.mongodb.MongoException;
import com.mongodb.client.ClientSession;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.result.UpdateResult;
import gym.persistencia.interfaces.IMembresiaDAO;
import gym.persistencia.entidades.Membresia;
import gym.persistencia.excepciones.PersistenciaException;
import java.util.ArrayList;
import java.util.List;
import org.bson.types.ObjectId;

public class MembresiaDAO extends BaseDAO implements IMembresiaDAO {

    public MembresiaDAO() {
    }

    public MembresiaDAO(ClientSession sesion) {
        super(sesion);
    }

    private static final String NOMBRE_COLECCION = "membresias";

    @Override
    public void guardar(Membresia membresia) throws PersistenciaException {
        try {
            MongoCollection<Membresia> coleccion = obtenerColeccion(NOMBRE_COLECCION, Membresia.class);
            insertOne(coleccion, membresia);
        } catch (MongoException e) {
            throw new PersistenciaException("Error al guardar la membresía.");
        }
    }

    @Override
    public void actualizar(Membresia membresia) throws PersistenciaException {
        try {
            MongoCollection<Membresia> coleccion = obtenerColeccion(NOMBRE_COLECCION, Membresia.class);
            ObjectId id = new ObjectId(membresia.getIdMembresia());
            UpdateResult resultado = replaceOne(coleccion, Filters.eq("_id", id), membresia);

            if (resultado.getMatchedCount() == 0) {
                throw new PersistenciaException("No se encontró la membresía que se desea actualizar.");
            }
        } catch (MongoException e) {
            throw new PersistenciaException("Error al actualizar la membresía.");
        }
    }

    @Override
    public void eliminar(String id) throws PersistenciaException {
        try {
            MongoCollection<Membresia> coleccion = obtenerColeccion(NOMBRE_COLECCION, Membresia.class);
            deleteOne(coleccion, Filters.eq("_id", new ObjectId(id)));
        } catch (MongoException e) {
            throw new PersistenciaException("Error al eliminar la membresía.");
        }
    }

    @Override
    public List<Membresia> obtenerTodos() throws PersistenciaException {
        try {
            MongoCollection<Membresia> coleccion = obtenerColeccion(NOMBRE_COLECCION, Membresia.class);
            return find(coleccion).into(new ArrayList<>());
        } catch (MongoException e) {
            throw new PersistenciaException("Error al obtener las membresías.");
        }
    }
}

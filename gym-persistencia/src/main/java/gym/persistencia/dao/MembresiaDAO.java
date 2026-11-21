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

    private static final String NOMBRE_COLECCION = "membresias";

    public MembresiaDAO(ClientSession sesion) {
        super(sesion);
    }

    @Override
    public void guardar(Membresia membresia) throws PersistenciaException {
        try {
            MongoCollection<Membresia> coleccion = obtenerColeccion(NOMBRE_COLECCION, Membresia.class);
            coleccion.insertOne(obtenerSesion(), membresia);
        } catch (MongoException e) {
            throw new PersistenciaException("Error al guardar la membresia.");
        }
    }

    @Override
    public void actualizar(Membresia membresia) throws PersistenciaException {
        try {
            MongoCollection<Membresia> coleccion = obtenerColeccion(NOMBRE_COLECCION, Membresia.class);
            ObjectId id = new ObjectId(membresia.getIdMembresia());
            UpdateResult resultado = coleccion.replaceOne(obtenerSesion(), Filters.eq("_id", id), membresia);

            if (resultado.getMatchedCount() == 0) {
                throw new PersistenciaException("No se encontro la membresia que se desea actualizar.");
            }
        } catch (MongoException | IllegalArgumentException e) {
            throw new PersistenciaException("Error al actualizar la membresia.");
        }
    }

    @Override
    public void eliminar(String id) throws PersistenciaException {
        try {
            MongoCollection<Membresia> coleccion = obtenerColeccion(NOMBRE_COLECCION, Membresia.class);
            coleccion.deleteOne(obtenerSesion(), Filters.eq("_id", new ObjectId(id)));
        } catch (MongoException | IllegalArgumentException e) {
            throw new PersistenciaException("Error al eliminar la membresia.");
        }
    }

    @Override
    public List<Membresia> obtenerTodos() throws PersistenciaException {
        try {
            MongoCollection<Membresia> coleccion = obtenerColeccion(NOMBRE_COLECCION, Membresia.class);
            return coleccion.find(obtenerSesion()).into(new ArrayList<>());
        } catch (MongoException e) {
            throw new PersistenciaException("Error al obtener las membresias.");
        }
    }
}

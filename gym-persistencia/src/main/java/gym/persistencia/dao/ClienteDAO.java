package gym.persistencia.dao;

import com.mongodb.MongoException;
import com.mongodb.client.ClientSession;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.result.UpdateResult;
import gym.persistencia.interfaces.IClienteDAO;
import gym.persistencia.entidades.Cliente;
import gym.persistencia.excepciones.PersistenciaException;
import java.util.ArrayList;
import java.util.List;
import org.bson.types.ObjectId;

public class ClienteDAO extends BaseDAO implements IClienteDAO {

    public ClienteDAO() {
    }

    public ClienteDAO(ClientSession sesion) {
        super(sesion);
    }

    private static final String NOMBRE_COLECCION = "clientes";

    @Override
    public void guardar(Cliente cliente) {
        MongoCollection<Cliente> coleccion = obtenerColeccion(NOMBRE_COLECCION, Cliente.class);
        insertOne(coleccion, cliente);
    }

    @Override
    public void actualizar(Cliente cliente) throws PersistenciaException {
        try {
            MongoCollection<Cliente> coleccion = obtenerColeccion(NOMBRE_COLECCION, Cliente.class);
            ObjectId id = new ObjectId(cliente.getIdCliente());
            UpdateResult resultado = replaceOne(coleccion, Filters.eq("_id", id), cliente);

            if (resultado.getMatchedCount() == 0) {
                throw new PersistenciaException("No se encontró el Cliente que se desea actualizar.");
            }
        } catch (MongoException e) {
            throw new PersistenciaException("Error al actualizar el cliente.");
        }
    }

    @Override
    public Cliente obtenerPorId(String id) throws PersistenciaException {
        try {
            MongoCollection<Cliente> coleccion = obtenerColeccion(NOMBRE_COLECCION, Cliente.class);
            return find(coleccion, Filters.eq("_id", new ObjectId(id))).first();
        } catch (MongoException e) {
            throw new PersistenciaException("Error al obtener el cliente.");
        }
    }

    @Override
    public List<Cliente> obtenerTodos() throws PersistenciaException {
        try {
            MongoCollection<Cliente> coleccion = obtenerColeccion(NOMBRE_COLECCION, Cliente.class);
            return find(coleccion).into(new ArrayList<>());
        } catch (MongoException e) {
            throw new PersistenciaException("Error al obtener los clientes.");
        }
    }
}

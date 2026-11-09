package gym.persistencia.dao.impl;

import com.mongodb.MongoException;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.result.InsertOneResult;
import com.mongodb.client.result.UpdateResult;
import gym.infraestructura.persistencia.MongoConnectionManager;
import static gym.infraestructura.persistencia.MongoConnectionManager.obtenerCodecs;
import gym.persistencia.dao.IClienteDAO;
import gym.persistencia.entidades.Cliente;
import gym.persistencia.excepciones.PersistenciaException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class ClienteDAO implements IClienteDAO {

    private static final Logger LOGGER = Logger.getLogger(ClienteDAO.class.getName());

    private static final String NOMBRE_COLECCION = "clientes";

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
     * Obtiene la colección de registros de clientes.
     *
     * @param baseDatos Base de datos del sistema.
     * @return Colección de registros de acceso.
     */
    private MongoCollection<Cliente> obtenerColeccion(MongoDatabase baseDatos) {
        MongoCollection<Cliente> coleccion = baseDatos.getCollection(NOMBRE_COLECCION, Cliente.class);
        return coleccion;
    }

    /**
     * Guarda un nuevo cliente en MongoDB.
     *
     * @param cliente Cliente que se desea registrar.
     * @throws PersistenciaException Si ocurre un error al guardar.
     */
    @Override
    public void guardar(Cliente cliente) throws PersistenciaException {
        if (cliente == null) {
            throw new PersistenciaException("El cliente no puede ser nulo.");
        }

        try (MongoClient conexion = MongoConnectionManager.crearConexion()) {

            MongoDatabase baseDatos = obtenerBaseDatos(conexion);
            MongoCollection<Cliente> coleccion = obtenerColeccion(baseDatos);

            InsertOneResult resultado = coleccion.insertOne(cliente);

            if (!resultado.wasAcknowledged()) {
                throw new PersistenciaException("No se pudo confirmar el registro del cliente.");
            }

        } catch (MongoException ex) {
            LOGGER.severe(ex.getMessage());
            throw new PersistenciaException("Error al guardar el cliente.", ex);
        }
    }

    /**
     * Actualiza la información de un cliente existente.
     *
     * @param cliente Cliente con la información actualizada.
     * @throws PersistenciaException Si ocurre un error al actualizar.
     */
    @Override
    public void actualizar(Cliente cliente) throws PersistenciaException {
        if (cliente == null) {
            throw new PersistenciaException("El cliente no puede ser nulo.");
        }

        try (MongoClient conexion = MongoConnectionManager.crearConexion()) {

            MongoDatabase baseDatos = obtenerBaseDatos(conexion);
            MongoCollection<Cliente> coleccion = obtenerColeccion(baseDatos);

            UpdateResult resultado = coleccion.replaceOne(Filters.eq("idCliente", cliente.getIdCliente()),cliente);

            if (!resultado.wasAcknowledged()) {
                throw new PersistenciaException("No se pudo confirmar la actualización del cliente.");
            }

            if (resultado.getMatchedCount() == 0) {
                throw new PersistenciaException("No se encontró el cliente que se desea actualizar.");
            }

        } catch (MongoException ex) {
            LOGGER.severe(ex.getMessage());
            throw new PersistenciaException("Error al actualizar el cliente.", ex);
        }
    }

    /**
     * Busca un cliente mediante su identificador.
     *
     * @param id Identificador del cliente.
     * @return Cliente encontrado o null si no existe.
     * @throws PersistenciaException Si ocurre un error en la consulta.
     */
    @Override
    public Cliente obtenerPorId(String id) throws PersistenciaException {
        try (MongoClient conexion = MongoConnectionManager.crearConexion()) {

            MongoDatabase baseDatos = obtenerBaseDatos(conexion);
            MongoCollection<Cliente> coleccion = obtenerColeccion(baseDatos);

            Cliente cliente = coleccion.find(Filters.eq("idCliente", id)).first();

            return cliente;

        } catch (MongoException ex) {
            LOGGER.severe(ex.getMessage());
            throw new PersistenciaException("Error al consultar el cliente.", ex);
        }
    }

    /**
     * Obtiene todos los clientes registrados en MongoDB.
     *
     * @return Lista de clientes encontrados.
     * @throws PersistenciaException Si ocurre un error en la consulta.
     */
    @Override
    public List<Cliente> obtenerTodos() throws PersistenciaException {
        List<Cliente> clientes = new ArrayList<>();

        try (MongoClient conexion = MongoConnectionManager.crearConexion()) {

            MongoDatabase baseDatos = obtenerBaseDatos(conexion);
            MongoCollection<Cliente> coleccion = obtenerColeccion(baseDatos);

            coleccion.find().into(clientes);

            return clientes;

        } catch (MongoException ex) {
            LOGGER.severe(ex.getMessage());
            throw new PersistenciaException("Error al consultar los clientes.", ex);
        }
    }
    
    

    
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
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
import gym.persistencia.interfaces.IProductoDAO;
import gym.persistencia.entidades.Producto;
import gym.persistencia.entidades.RegistroIngreso;
import gym.persistencia.excepciones.PersistenciaException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import org.bson.types.ObjectId;

/**
 *
 * @author PC GAMER MASTER RACE
 */
public class ProductosDAO implements IProductoDAO{
    private static final Logger LOGGER = Logger.getLogger(ProductosDAO.class.getName());

    private static final String NOMBRE_COLECCION = "productos";

    /**
     * Obtiene la base de datos del sistema.
     *
     * @param producto Conexión con MongoDB.
     * @return Base de datos.
     */
    private MongoDatabase obtenerBaseDatos(MongoClient producto) {
        MongoDatabase empresaBD = producto.getDatabase(MongoConnectionManager.BASE_DATOS).withCodecRegistry(obtenerCodecs());
        return empresaBD;
    }

    /**
     * Obtiene la colección de productos.
     *
     * @param baseDatos Base de datos del sistema.
     * @return Colección de productos.
     */
    private MongoCollection<Producto> obtenerColeccion(MongoDatabase baseDatos) {
        MongoCollection<Producto> coleccion = baseDatos.getCollection(NOMBRE_COLECCION, Producto.class);
        return coleccion;
    }

    /**
     * Guarda un producto en MongoDB.
     *
     * @param producto Producto que se desea guardar.
     * @throws PersistenciaException Si ocurre un error al guardar.
     */
    @Override
    public void guardar(Producto producto) throws PersistenciaException {
        if (producto == null) {
            throw new PersistenciaException("El producto no puede ser nulo.");
        }

        try (MongoClient conexion = MongoConnectionManager.crearConexion()) {
            MongoDatabase baseDatos = obtenerBaseDatos(conexion);
            MongoCollection<Producto> coleccion = obtenerColeccion(baseDatos);

            InsertOneResult resultado = coleccion.insertOne(producto);

            if (!resultado.wasAcknowledged()) {
                throw new PersistenciaException("No se pudo confirmar el registro del producto.");
            }

        } catch (MongoException ex) {
            LOGGER.severe(ex.getMessage());
            throw new PersistenciaException("Error al guardar el producto.", ex);
        }
    }

    /**
     * Actualiza un producto existente.
     *
     * @param producto Producto con los datos actualizados.
     * @throws PersistenciaException Si ocurre un error al actualizar.
     */
    @Override
    public void actualizar(Producto producto) throws PersistenciaException {
        if (producto == null) {
            throw new PersistenciaException("El producto no puede ser nulo.");
        }

        if (producto.getIdProducto() == null) {
            throw new PersistenciaException("El identificador del producto no puede ser nulo.");
        }

        try (MongoClient conexion = MongoConnectionManager.crearConexion()) {
            MongoDatabase baseDatos = obtenerBaseDatos(conexion);
            MongoCollection<Producto> coleccion = obtenerColeccion(baseDatos);

            UpdateResult resultado = coleccion.replaceOne(Filters.eq("_id", new ObjectId(producto.getIdProducto())),producto);

            if (!resultado.wasAcknowledged() || resultado.getMatchedCount() == 0) {
                throw new PersistenciaException("No se encontró el producto para actualizar.");
            }

        } catch (MongoException ex) {
            LOGGER.severe(ex.getMessage());
            throw new PersistenciaException("Error al actualizar el producto.", ex);
        }
    }

    /**
     * Elimina un producto por su identificador.
     *
     * @param id Identificador del producto.
     * @throws PersistenciaException Si ocurre un error al eliminar.
     */
    @Override
    public void eliminar(String id) throws PersistenciaException {
        if (id == null) {
            throw new PersistenciaException("El identificador del producto no puede ser nulo."
            );
        }

        try (MongoClient conexion = MongoConnectionManager.crearConexion()) {
            MongoDatabase baseDatos = obtenerBaseDatos(conexion);
            MongoCollection<Producto> coleccion = obtenerColeccion(baseDatos);

            DeleteResult resultado = coleccion.deleteOne(Filters.eq("_id", new ObjectId(id)));

            if (!resultado.wasAcknowledged() || resultado.getDeletedCount() == 0) {
                throw new PersistenciaException("No se encontró el producto para eliminar.");
            }

        } catch (MongoException ex) {
            LOGGER.severe(ex.getMessage());
            throw new PersistenciaException("Error al eliminar el producto.", ex);
        }
    }

    /**
     * Obtiene todos los productos registrados.
     *
     * @return Lista de productos.
     * @throws PersistenciaException Si ocurre un error en la consulta.
     */
    @Override
    public List<Producto> obtenerTodos() throws PersistenciaException {
        List<Producto> productos = new ArrayList<>();

        try (MongoClient conexion = MongoConnectionManager.crearConexion()) {
            MongoDatabase baseDatos = obtenerBaseDatos(conexion);
            MongoCollection<Producto> coleccion = obtenerColeccion(baseDatos);

            coleccion.find().into(productos);

            return productos;

        } catch (MongoException ex) {
            LOGGER.severe(ex.getMessage());
            throw new PersistenciaException("Error al consultar los productos.", ex);
        }
    }

    
    
}

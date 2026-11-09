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
import gym.persistencia.dao.IVentaDAO;
import gym.persistencia.entidades.Venta;
import gym.persistencia.excepciones.PersistenciaException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import org.bson.types.ObjectId;

/**
 *
 * @author PC GAMER MASTER RACE
 */
public class VentasDAO implements IVentaDAO {
    private static final Logger LOGGER = Logger.getLogger(VentasDAO.class.getName());

    private static final String NOMBRE_COLECCION = "ventas";

    /**
     * Obtiene la base de datos del sistema.
     *
     * @param venta Conexión con MongoDB.
     * @return Base de datos.
     */
    private MongoDatabase obtenerBaseDatos(MongoClient venta) {
        MongoDatabase empresaBD = venta.getDatabase(MongoConnectionManager.BASE_DATOS).withCodecRegistry(obtenerCodecs());
        return empresaBD;
    }

    /**
     * Obtiene la colección de ventas.
     *
     * @param baseDatos Base de datos del sistema.
     * @return Colección de ventas.
     */
    private MongoCollection<Venta> obtenerColeccion(MongoDatabase baseDatos) {
        MongoCollection<Venta> coleccion = baseDatos.getCollection(NOMBRE_COLECCION, Venta.class);
        return coleccion;
    }

    /**
     * Guarda una venta en MongoDB.
     *
     * @param venta Venta que se desea guardar.
     * @throws PersistenciaException Si ocurre un error al guardar.
     */
    @Override
    public void guardar(Venta venta) throws PersistenciaException {
        if (venta == null) {
            throw new PersistenciaException("La venta no puede ser nula.");
        }

        try (MongoClient conexion = MongoConnectionManager.crearConexion()) {
            MongoDatabase baseDatos = obtenerBaseDatos(conexion);
            MongoCollection<Venta> coleccion = obtenerColeccion(baseDatos);

            InsertOneResult resultado = coleccion.insertOne(venta);

            if (!resultado.wasAcknowledged()) {
                throw new PersistenciaException("No se pudo confirmar el registro de la venta.");
            }

        } catch (MongoException ex) {
            LOGGER.severe(ex.getMessage());
            throw new PersistenciaException("Error al guardar la venta.", ex);
        }
    }

    /**
     * Obtiene una venta por su identificador.
     *
     * @param id Identificador de la venta.
     * @return Venta encontrada o null si no existe.
     * @throws PersistenciaException Si ocurre un error en la consulta.
     */
    @Override
    public Venta obtenerPorId(String id) throws PersistenciaException {
        if (id == null) {
            throw new PersistenciaException("El identificador de la venta no puede ser nulo.");
        }

        try (MongoClient conexion = MongoConnectionManager.crearConexion()) {
            MongoDatabase baseDatos = obtenerBaseDatos(conexion);
            MongoCollection<Venta> coleccion = obtenerColeccion(baseDatos);

            return coleccion.find(Filters.eq("_id", new ObjectId(id))).first();

        } catch (MongoException ex) {
            LOGGER.severe(ex.getMessage());
            throw new PersistenciaException("Error al consultar la venta.", ex);
        }
    }

    /**
     * Obtiene todas las ventas registradas.
     *
     * @return Lista de ventas.
     * @throws PersistenciaException Si ocurre un error en la consulta.
     */
    @Override
    public List<Venta> obtenerTodas() throws PersistenciaException {
        List<Venta> ventas = new ArrayList<>();

        try (MongoClient conexion = MongoConnectionManager.crearConexion()) {
            MongoDatabase baseDatos = obtenerBaseDatos(conexion);
            MongoCollection<Venta> coleccion = obtenerColeccion(baseDatos);

            coleccion.find().into(ventas);

            return ventas;

        } catch (MongoException ex) {
            LOGGER.severe(ex.getMessage());
            throw new PersistenciaException("Error al consultar las ventas.", ex);
        }
    }
    
}

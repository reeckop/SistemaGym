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
import gym.persistencia.dao.IAccesoDAO;
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
public class AccesoDAO implements IAccesoDAO {
    private static final Logger LOGGER = Logger.getLogger(AccesoDAO.class.getName());

    private static final String NOMBRE_COLECCION = "accesos";
    
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
     * Obtiene la colección de registros de acceso.
     *
     * @param baseDatos Base de datos del sistema.
     * @return Colección de registros de acceso.
     */
    private MongoCollection<RegistroIngreso> obtenerColeccion(MongoDatabase baseDatos) {
        MongoCollection<RegistroIngreso> coleccion = baseDatos.getCollection(NOMBRE_COLECCION, RegistroIngreso.class);
        return coleccion;
    }

     /**
     * Guarda un nuevo registro de ingreso en MongoDB.
     *
     * @param registro Registro de ingreso que se desea guardar.
     * @throws PersistenciaException Si ocurre un error al guardar.
     */
    @Override
    public void guardar(RegistroIngreso registro) throws PersistenciaException {
         if (registro == null) {
            throw new PersistenciaException("El registro de acceso no puede ser nulo.");
        }

        try (MongoClient conexion = MongoConnectionManager.crearConexion()) {
            MongoDatabase baseDatos = obtenerBaseDatos(conexion);
            MongoCollection<RegistroIngreso> coleccion = obtenerColeccion(baseDatos);

            InsertOneResult resultado = coleccion.insertOne(registro);

            if (!resultado.wasAcknowledged()) {
                throw new PersistenciaException("No se pudo confirmar el registro de acceso.");
            }
            
        } catch (MongoException ex) {
            LOGGER.severe(ex.getMessage());
            throw new PersistenciaException("Error al guardar el registro de acceso.", ex);
        }
    }

    /**
     * Obtiene los registros de acceso de un cliente específico.
     *
     * @param idCliente Identificador del cliente.
     * @return Lista de registros de acceso del cliente.
     * @throws PersistenciaException Si ocurre un error en la consulta.
     */
    @Override
    public List<RegistroIngreso> obtenerPorCliente(String idCliente) throws PersistenciaException {
        List<RegistroIngreso> registros = new ArrayList<>();

        try (MongoClient conexion = MongoConnectionManager.crearConexion()) {
            MongoDatabase baseDatos = obtenerBaseDatos(conexion);
            MongoCollection<RegistroIngreso> coleccion = obtenerColeccion(baseDatos);

            coleccion.find(Filters.eq("cliente._id", new ObjectId(idCliente))).into(registros);

            return registros;

        } catch (MongoException ex) {
            LOGGER.severe(ex.getMessage());
            throw new PersistenciaException("Error al consultar los registros de acceso del cliente.", ex);
        }
    }

    /**
     * Obtiene todos los registros de acceso almacenados en MongoDB.
     *
     * @return Lista de todos los registros de acceso.
     * @throws PersistenciaException Si ocurre un error en la consulta.
     */
    @Override
    public List<RegistroIngreso> obtenerTodos() throws PersistenciaException {
        List<RegistroIngreso> registros = new ArrayList<>();

        try (MongoClient conexion = MongoConnectionManager.crearConexion()) {
            MongoDatabase baseDatos = obtenerBaseDatos(conexion);
            MongoCollection<RegistroIngreso> coleccion = obtenerColeccion(baseDatos);

            coleccion.find().into(registros);

            return registros;

        } catch (MongoException ex) {
            LOGGER.severe(ex.getMessage());
            throw new PersistenciaException("Error al consultar los registros de acceso.", ex);
        }
    }
    
}

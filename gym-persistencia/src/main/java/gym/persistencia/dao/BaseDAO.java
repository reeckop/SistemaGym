/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gym.persistencia.dao;

import com.mongodb.client.ClientSession;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.result.UpdateResult;
import org.bson.conversions.Bson;
import com.mongodb.client.MongoDatabase;
import gym.infraestructura.persistencia.MongoConnectionManager;

/**
 *
 * @author PC GAMER MASTER RACE
 */
public abstract class BaseDAO {
    private final ClientSession sesion;

    protected BaseDAO() {
        this(null);
    }

    protected BaseDAO(ClientSession sesion) {
        this.sesion = sesion;
    }

    protected <T> void insertOne(MongoCollection<T> coleccion, T entidad) {
        if (sesion == null) {
            coleccion.insertOne(entidad);
        } else {
            coleccion.insertOne(sesion, entidad);
        }
    }

    protected <T> UpdateResult replaceOne(MongoCollection<T> coleccion, Bson filtro, T entidad) {
        return sesion == null
                ? coleccion.replaceOne(filtro, entidad)
                : coleccion.replaceOne(sesion, filtro, entidad);
    }

    protected <T> void deleteOne(MongoCollection<T> coleccion, Bson filtro) {
        if (sesion == null) {
            coleccion.deleteOne(filtro);
        } else {
            coleccion.deleteOne(sesion, filtro);
        }
    }

    protected <T> FindIterable<T> find(MongoCollection<T> coleccion, Bson filtro) {
        return sesion == null ? coleccion.find(filtro) : coleccion.find(sesion, filtro);
    }

    protected <T> FindIterable<T> find(MongoCollection<T> coleccion) {
        return sesion == null ? coleccion.find() : coleccion.find(sesion);
    }
    /**
     * Obtiene la base de datos configurada para el sistema.
     *
     * @return Base de datos del sistema.
     */
    protected MongoDatabase obtenerBaseDatos() {
        return MongoConnectionManager.obtenerBaseDatos();
    }

    /**
     * Obtiene una colección de MongoDB según la entidad indicada.
     *
     * @param nombreColeccion Nombre de la colección.
     * @param tipoClase Clase de la entidad.
     * @return Colección de MongoDB.
     */
    protected <T> MongoCollection<T> obtenerColeccion(String nombreColeccion, Class<T> tipoClase) {
        return obtenerBaseDatos().getCollection(nombreColeccion, tipoClase);
    }
    
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gym.persistencia.dao;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import gym.infraestructura.persistencia.MongoConnectionManager;

/**
 *
 * @author PC GAMER MASTER RACE
 */
public abstract class BaseDAO {
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

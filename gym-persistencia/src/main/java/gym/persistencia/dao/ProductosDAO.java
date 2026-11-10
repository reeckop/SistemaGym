/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gym.persistencia.dao;

import com.mongodb.MongoException;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.result.UpdateResult;
import gym.persistencia.interfaces.IProductoDAO;
import gym.persistencia.entidades.Producto;
import gym.persistencia.excepciones.PersistenciaException;
import java.util.ArrayList;
import java.util.List;
import org.bson.types.ObjectId;

/**
 *
 * @author PC GAMER MASTER RACE
 */
public class ProductosDAO extends BaseDAO implements IProductoDAO {

    private static final String NOMBRE_COLECCION = "productos";

    @Override
    public void guardar(Producto producto) throws PersistenciaException {
        try {
            MongoCollection<Producto> coleccion = obtenerColeccion(NOMBRE_COLECCION, Producto.class);
            coleccion.insertOne(producto);
        } catch (MongoException e) {
            throw new PersistenciaException("Error al guardar el producto.");
        }
    }

    @Override
    public void actualizar(Producto producto) throws PersistenciaException {
        try {
            MongoCollection<Producto> coleccion = obtenerColeccion(NOMBRE_COLECCION, Producto.class);
            ObjectId id = new ObjectId(producto.getIdProducto());
            UpdateResult resultado = coleccion.replaceOne(Filters.eq("_id", id), producto);

            if (resultado.getMatchedCount() == 0) {
                throw new PersistenciaException("No se encontró el producto que se desea actualizar.");
            }
        } catch (MongoException e) {
            throw new PersistenciaException("Error al actualizar el producto.");
        }
    }

    @Override
    public void eliminar(String id) throws PersistenciaException {
        try {
            MongoCollection<Producto> coleccion = obtenerColeccion(NOMBRE_COLECCION, Producto.class);
            coleccion.deleteOne(Filters.eq("_id", new ObjectId(id)));
        } catch (MongoException e) {
            throw new PersistenciaException("Error al eliminar el producto.");
        }
    }

    @Override
    public List<Producto> obtenerTodos() throws PersistenciaException {
        try {
            MongoCollection<Producto> coleccion = obtenerColeccion(NOMBRE_COLECCION, Producto.class);
            return coleccion.find().into(new ArrayList<>());
        } catch (MongoException e) {
            throw new PersistenciaException("Error al obtener los productos.");
        }
    }
    
}

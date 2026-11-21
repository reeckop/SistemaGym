/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gym.persistencia.dao;

import com.mongodb.MongoException;
import com.mongodb.client.ClientSession;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.result.UpdateResult;
import gym.persistencia.interfaces.IVentaDAO;
import gym.persistencia.entidades.Venta;
import gym.persistencia.excepciones.PersistenciaException;
import java.util.ArrayList;
import java.util.List;
import org.bson.types.ObjectId;

/**
 *
 * @author PC GAMER MASTER RACE
 */
public class VentasDAO extends BaseDAO implements IVentaDAO {

    public VentasDAO() {
    }

    public VentasDAO(ClientSession sesion) {
        super(sesion);
    }

    private static final String NOMBRE_COLECCION = "ventas";

    @Override
    public void guardar(Venta venta) throws PersistenciaException {
        try {
            MongoCollection<Venta> coleccion = obtenerColeccion(NOMBRE_COLECCION, Venta.class);
            insertOne(coleccion, venta);
        } catch (MongoException e) {
            throw new PersistenciaException("Error al guardar la venta.");
        }
    }
    
    @Override
    public void actualizarVenta(Venta venta) throws PersistenciaException {
        try {
            MongoCollection<Venta> coleccion = obtenerColeccion(NOMBRE_COLECCION, Venta.class);
            ObjectId id = new ObjectId(venta.getIdVenta());
            UpdateResult resultado = replaceOne(coleccion, Filters.eq("_id", id), venta);

            if (resultado.getMatchedCount() == 0) {
                throw new PersistenciaException("No se encontró la venta que se desea actualizar.");
            }

        } catch (MongoException e) {
            throw new PersistenciaException("Error al actualizar la venta.", e);
        }
    }

    @Override
    public Venta obtenerPorId(String id) throws PersistenciaException {
        try {
            MongoCollection<Venta> coleccion = obtenerColeccion(NOMBRE_COLECCION, Venta.class);
            return find(coleccion, Filters.eq("_id", new ObjectId(id))).first();
        } catch (MongoException e) {
            throw new PersistenciaException("Error al obtener la venta.");
        }
    }

    @Override
    public List<Venta> obtenerTodas() throws PersistenciaException {
        try {
            MongoCollection<Venta> coleccion = obtenerColeccion(NOMBRE_COLECCION, Venta.class);
            return find(coleccion).into(new ArrayList<>());
        } catch (MongoException e) {
            throw new PersistenciaException("Error al obtener las ventas.");
        }
    }
    
}

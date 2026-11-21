/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gym.persistencia.dao;

import com.mongodb.MongoException;
import com.mongodb.client.ClientSession;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import gym.persistencia.entidades.Pago;
import gym.persistencia.excepciones.PersistenciaException;
import gym.persistencia.interfaces.IPagoDAO;
import java.util.ArrayList;
import java.util.List;
import org.bson.types.ObjectId;

/**
 *
 * @author PC GAMER MASTER RACE
 */
public class PagoDAO extends BaseDAO implements IPagoDAO {

    private static final String NOMBRE_COLECCION = "pagos";
    
    public PagoDAO(ClientSession sesion) { 
        super(sesion); 
    }

    @Override
    public void guardar(Pago pago) throws PersistenciaException {
        try {
            MongoCollection<Pago> coleccion = obtenerColeccion(NOMBRE_COLECCION, Pago.class);
            coleccion.insertOne(pago);
        } catch (MongoException e) {
            throw new PersistenciaException("Error al guardar el pago.");
        }
    }

    @Override
    public void actualizar(Pago pago) throws PersistenciaException {
        try {
            MongoCollection<Pago> coleccion = obtenerColeccion(NOMBRE_COLECCION, Pago.class);
            coleccion.replaceOne(Filters.eq("_id", new ObjectId(pago.getIdPago())), pago);
        } catch (MongoException e) {
            throw new PersistenciaException("Error al actualizar el pago.");
        }
    }

    @Override
    public Pago obtenerPorId(String id) throws PersistenciaException {
        try {
            MongoCollection<Pago> coleccion = obtenerColeccion(NOMBRE_COLECCION, Pago.class);
            return coleccion.find(Filters.eq("_id", new ObjectId(id))).first();
        } catch (MongoException e) {
            throw new PersistenciaException("Error al obtener el pago.");
        }
    }

    @Override
    public List<Pago> obtenerTodos() throws PersistenciaException {
        try {
            MongoCollection<Pago> coleccion = obtenerColeccion(NOMBRE_COLECCION, Pago.class);
            return coleccion.find().into(new ArrayList<>());
        } catch (MongoException e) {
            throw new PersistenciaException("Error al obtener todos los pagos.");
        }
    }
}

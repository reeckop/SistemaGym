/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gym.persistencia.dao;

import com.mongodb.MongoException;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import gym.persistencia.interfaces.IAccesoDAO;
import gym.persistencia.entidades.RegistroIngreso;
import gym.persistencia.excepciones.PersistenciaException;
import java.util.ArrayList;
import java.util.List;
import org.bson.types.ObjectId;

/**
 *
 * @author PC GAMER MASTER RACE
 */
public class  AccesoDAO extends BaseDAO implements IAccesoDAO {

    private static final String NOMBRE_COLECCION = "accesos";

    @Override
    public void guardar(RegistroIngreso registroIngreso) throws PersistenciaException {
        try {
            MongoCollection<RegistroIngreso> coleccion = obtenerColeccion(NOMBRE_COLECCION, RegistroIngreso.class);
            coleccion.insertOne(registroIngreso);
        } catch (MongoException e) {
            throw new PersistenciaException("Error al guardar el registro de acceso.");
        }
    }

    @Override
    public List<RegistroIngreso> obtenerPorCliente(String idCliente) throws PersistenciaException {
        try {
            MongoCollection<RegistroIngreso> coleccion = obtenerColeccion(NOMBRE_COLECCION, RegistroIngreso.class);
            return coleccion.find(Filters.eq("cliente._id", new ObjectId(idCliente))).into(new ArrayList<>());
        } catch (MongoException | IllegalArgumentException e) {
            throw new PersistenciaException("Error al obtener los registros de acceso del cliente.");
        }
    }

    @Override
    public List<RegistroIngreso> obtenerTodos() throws PersistenciaException {
        try {
            MongoCollection<RegistroIngreso> coleccion = obtenerColeccion(NOMBRE_COLECCION, RegistroIngreso.class);
            return coleccion.find().into(new ArrayList<>());
        } catch (MongoException e) {
            throw new PersistenciaException("Error al obtener los registros de acceso.");
        }
    }
}

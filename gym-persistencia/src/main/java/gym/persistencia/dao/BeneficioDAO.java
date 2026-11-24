/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gym.persistencia.dao;

import com.mongodb.MongoException;
import com.mongodb.client.ClientSession;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import gym.persistencia.entidades.Beneficio;
import gym.persistencia.excepciones.PersistenciaException;
import gym.persistencia.interfaces.IBeneficioDAO;
import java.util.ArrayList;
import java.util.List;
import org.bson.types.ObjectId;

/**
 * Implementa las operaciones de persistencia para beneficio.
 *
 * @author PC GAMER MASTER RACE
 */
public class BeneficioDAO extends BaseDAO implements IBeneficioDAO {

    private static final String NOMBRE_COLECCION = "beneficios";

    public BeneficioDAO(ClientSession sesion) {
        super(sesion);
    }

    @Override
    public void guardar(Beneficio beneficio) throws PersistenciaException {
        try {
            MongoCollection<Beneficio> coleccion = obtenerColeccion(NOMBRE_COLECCION, Beneficio.class);
            coleccion.insertOne(obtenerSesion(), beneficio);
        } catch (MongoException e) {
            throw new PersistenciaException("Error al guardar el beneficio.");
        }
    }

    @Override
    public void actualizar(Beneficio beneficio) throws PersistenciaException {
        try {
            MongoCollection<Beneficio> coleccion = obtenerColeccion(NOMBRE_COLECCION, Beneficio.class);
            coleccion.replaceOne(obtenerSesion(), Filters.eq("_id", new ObjectId(beneficio.getIdBeneficio())), beneficio);
        } catch (MongoException | IllegalArgumentException e) {
            throw new PersistenciaException("Error al actualizar el beneficio.");
        }
    }

    @Override
    public void eliminar(String id) throws PersistenciaException {
        try {
            MongoCollection<Beneficio> coleccion = obtenerColeccion(NOMBRE_COLECCION, Beneficio.class);
            coleccion.deleteOne(obtenerSesion(), Filters.eq("_id", new ObjectId(id)));
        } catch (MongoException | IllegalArgumentException e) {
            throw new PersistenciaException("Error al eliminar el beneficio.");
        }
    }

    @Override
    public Beneficio obtenerPorId(String id) throws PersistenciaException {
        try {
            MongoCollection<Beneficio> coleccion = obtenerColeccion(NOMBRE_COLECCION, Beneficio.class);
            return coleccion.find(obtenerSesion(), Filters.eq("_id", new ObjectId(id))).first();
        } catch (MongoException | IllegalArgumentException e) {
            throw new PersistenciaException("Error al obtener el beneficio.");
        }
    }

    @Override
    public List<Beneficio> obtenerTodos() throws PersistenciaException {
        try {
            MongoCollection<Beneficio> coleccion = obtenerColeccion(NOMBRE_COLECCION, Beneficio.class);
            return coleccion.find(obtenerSesion()).into(new ArrayList<>());
        } catch (MongoException e) {
            throw new PersistenciaException("Error al obtener los beneficios.");
        }
    }
}
    

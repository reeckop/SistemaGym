/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gym.persistencia.dao;

import com.mongodb.MongoException;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.result.UpdateResult;
import gym.persistencia.interfaces.IInscripcionDAO;
import gym.persistencia.entidades.Inscripcion;
import gym.persistencia.excepciones.PersistenciaException;
import java.util.ArrayList;
import java.util.List;
import org.bson.types.ObjectId;

/**
 *
 * @author PC GAMER MASTER RACE
 */
public class InscripcionDAO extends BaseDAO implements IInscripcionDAO {

     private static final String NOMBRE_COLECCION = "inscripciones";

    @Override
    public void guardar(Inscripcion inscripcion) throws PersistenciaException {
        try {
            MongoCollection<Inscripcion> coleccion = obtenerColeccion(NOMBRE_COLECCION, Inscripcion.class);
            coleccion.insertOne(inscripcion);
        } catch (MongoException e) {
            throw new PersistenciaException("Error al guardar la inscripción.");
        }
    }

    @Override
    public void actualizar(Inscripcion inscripcion) throws PersistenciaException {
        try {
            MongoCollection<Inscripcion> coleccion = obtenerColeccion(NOMBRE_COLECCION, Inscripcion.class);
            ObjectId id = new ObjectId(inscripcion.getIdInscripcion());
            UpdateResult resultado = coleccion.replaceOne(Filters.eq("_id", id), inscripcion);

            if (resultado.getMatchedCount() == 0) {
                throw new PersistenciaException("No se encontró la inscripción que se desea actualizar.");
            }
        } catch (MongoException e) {
            throw new PersistenciaException("Error al actualizar la inscripción.");
        }
    }

    @Override
    public void eliminar(String id) throws PersistenciaException {
        try {
            MongoCollection<Inscripcion> coleccion = obtenerColeccion(NOMBRE_COLECCION, Inscripcion.class);
            coleccion.deleteOne(Filters.eq("_id", new ObjectId(id)));
        } catch (MongoException e) {
            throw new PersistenciaException("Error al eliminar la inscripción.");
        }
    }

    @Override
    public List<Inscripcion> obtenerTodos() throws PersistenciaException {
        try {
            MongoCollection<Inscripcion> coleccion = obtenerColeccion(NOMBRE_COLECCION, Inscripcion.class);
            return coleccion.find().into(new ArrayList<>());
        } catch (MongoException e) {
            throw new PersistenciaException("Error al obtener las inscripciones.");
        }
    }
    
}

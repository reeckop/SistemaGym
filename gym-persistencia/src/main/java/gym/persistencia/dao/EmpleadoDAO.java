/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gym.persistencia.dao;

import com.mongodb.client.ClientSession;
import com.mongodb.client.MongoCollection;
import static com.mongodb.client.model.Filters.eq;
import gym.persistencia.entidades.Empleado;
import gym.persistencia.excepciones.PersistenciaException;
import gym.persistencia.interfaces.IEmpleadoDAO;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author PC GAMER MASTER RACE
 */
public class EmpleadoDAO extends BaseDAO implements IEmpleadoDAO {

    private final MongoCollection<Empleado> coleccion;

    public EmpleadoDAO(ClientSession sesion) {
        super(sesion);
        coleccion = obtenerColeccion("empleados", Empleado.class);
    }

    @Override
    public void guardar(Empleado empleado) throws PersistenciaException {
        try {
            coleccion.insertOne(obtenerSesion(), empleado);
        } catch (Exception e) {
            throw new PersistenciaException("No fue posible guardar el empleado.", e);
        }
    }

    @Override
    public void actualizar(Empleado empleado) throws PersistenciaException {
        try {
            coleccion.replaceOne(obtenerSesion(), eq("_id", empleado.getIdEmpleado()), empleado);
        } catch (Exception e) {
            throw new PersistenciaException("No fue posible actualizar el empleado.", e);
        }
    }

    @Override
    public void eliminar(String id) throws PersistenciaException {
        try {
            coleccion.deleteOne(obtenerSesion(), eq("_id", id));
        } catch (Exception e) {
            throw new PersistenciaException("No fue posible eliminar el empleado.", e);
        }
    }

    @Override
    public Empleado obtenerPorId(String id) throws PersistenciaException {
        try {
            return coleccion.find(obtenerSesion(), eq("_id", id)).first();
        } catch (Exception e) {
            throw new PersistenciaException("No fue posible consultar el empleado.", e);
        }
    }

    @Override
    public Empleado obtenerPorNip(String nip) throws PersistenciaException {
        try {
            return coleccion.find(obtenerSesion(), eq("nip", nip)).first();
        } catch (Exception e) {
            throw new PersistenciaException("No fue posible consultar el empleado por NIP.", e);
        }
    }

    @Override
    public List<Empleado> obtenerTodos() throws PersistenciaException {
        try {
            return coleccion.find(obtenerSesion()).into(new ArrayList<>());
        } catch (Exception e) {
            throw new PersistenciaException("No fue posible consultar los empleados.", e);
        }
    }
}

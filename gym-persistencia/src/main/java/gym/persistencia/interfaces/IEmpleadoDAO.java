/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package gym.persistencia.interfaces;

import gym.persistencia.entidades.Empleado;
import gym.persistencia.excepciones.PersistenciaException;
import java.util.List;

/**
 * Define las operaciones de persistencia relacionadas con empleado.
 *
 * @author PC GAMER MASTER RACE
 */
public interface IEmpleadoDAO {
    
    void guardar(Empleado empleado) throws PersistenciaException;

    void actualizar(Empleado empleado) throws PersistenciaException;

    void eliminar(String id) throws PersistenciaException;

    Empleado obtenerPorId(String id) throws PersistenciaException;

    Empleado obtenerPorNip(String nip) throws PersistenciaException;

    List<Empleado> obtenerTodos() throws PersistenciaException;
    
}

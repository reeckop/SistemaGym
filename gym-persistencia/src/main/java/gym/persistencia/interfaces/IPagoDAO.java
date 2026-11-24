/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package gym.persistencia.interfaces;

import gym.persistencia.entidades.Pago;
import gym.persistencia.excepciones.PersistenciaException;
import java.util.List;

/**
 * Define las operaciones de persistencia relacionadas con pago.
 *
 * @author PC GAMER MASTER RACE
 */
public interface IPagoDAO {
    void guardar(Pago pago) throws PersistenciaException;
    void actualizar(Pago pago) throws PersistenciaException;
    Pago obtenerPorId(String id) throws PersistenciaException;
    List<Pago> obtenerTodos() throws PersistenciaException;
}

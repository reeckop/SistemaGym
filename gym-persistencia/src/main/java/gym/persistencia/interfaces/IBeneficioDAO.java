/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package gym.persistencia.interfaces;

import gym.persistencia.entidades.Beneficio;
import gym.persistencia.excepciones.PersistenciaException;
import java.util.List;

/**
 *
 * @author PC GAMER MASTER RACE
 */
public interface IBeneficioDAO {
    void guardar(Beneficio beneficio) throws PersistenciaException;
    void actualizar(Beneficio beneficio) throws PersistenciaException;
    void eliminar(String id) throws PersistenciaException;
    Beneficio obtenerPorId(String id) throws PersistenciaException;
    List<Beneficio> obtenerTodos() throws PersistenciaException;
    
}

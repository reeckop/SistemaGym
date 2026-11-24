/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package gym.servicios.interfaces;

import gym.excepciones.NegocioException;
import gym.servicios.dto.InscripcionDTO;
import java.util.List;

/**
 * Define las operaciones de negocio relacionadas con inscripción.
 *
 * @author PC GAMER MASTER RACE
 */
public interface IInscripcionServicios {
    void registrarInscripcion(InscripcionDTO inscripcion) throws NegocioException;
    void actualizarInscripcion(InscripcionDTO inscripcion) throws NegocioException;
    void eliminarInscripcion(String id) throws NegocioException;
    List<InscripcionDTO> consultarTodasLasInscripciones() throws NegocioException;
}

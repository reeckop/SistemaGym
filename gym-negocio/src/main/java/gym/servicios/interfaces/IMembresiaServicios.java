/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package gym.servicios.interfaces;

import gym.excepciones.NegocioException;
import gym.servicios.dto.MembresiaDTO;
import java.util.List;

/**
 * Define las operaciones de negocio relacionadas con membresía.
 *
 * @author PC GAMER MASTER RACE
 */
public interface IMembresiaServicios {
    void registrarMembresia(MembresiaDTO membresia) throws NegocioException;
    void actualizarMembresia(MembresiaDTO membresia) throws NegocioException;
    void eliminarMembresia(String id) throws NegocioException;
    List<MembresiaDTO> consultarTodasLasMembresias() throws NegocioException;
    
}

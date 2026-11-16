/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package gym.servicios.interfaces;

import gym.excepciones.NegocioException;
import gym.servicios.dto.CursoDTO;
import java.util.List;

/**
 *
 * @author PC GAMER MASTER RACE
 */
public interface ICursoServicios {
    void registrarCurso(CursoDTO curso) throws NegocioException;
    void actualizarCurso(CursoDTO curso) throws NegocioException;
    void eliminarCurso(String id) throws NegocioException;
    List<CursoDTO> consultarTodosLosCursos() throws NegocioException;
}

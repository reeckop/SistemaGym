/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package gym.servicios.interfaces;

import gym.excepciones.NegocioException;
import gym.servicios.dto.BeneficioDTO;
import java.util.List;

/**
 *
 * @author PC GAMER MASTER RACE
 */
public interface IBeneficioServicios {
    void registrarBeneficio(BeneficioDTO beneficio) throws NegocioException;
    void actualizarBeneficio(BeneficioDTO beneficio) throws NegocioException;
    void eliminarBeneficio(String id) throws NegocioException;
    BeneficioDTO consultarBeneficio(String id) throws NegocioException;
    List<BeneficioDTO> consultarTodosLosBeneficios() throws NegocioException;   
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package gym.servicios.interfaces;

import gym.excepciones.NegocioException;
import gym.servicios.dto.PagoDTO;
import java.util.List;

/**
 *
 * @author PC GAMER MASTER RACE
 */
public interface IPagoServicios {
    void registrarPago(PagoDTO pago) throws NegocioException;
    void actualizarPago(PagoDTO pago) throws NegocioException;
    PagoDTO consultarPago(String id) throws NegocioException;
    List<PagoDTO> consultarTodosLosPagos() throws NegocioException;
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package gym.servicios.interfaces;

import gym.excepciones.NegocioException;
import gym.servicios.dto.PagoDTO;
import gym.servicios.dto.VentaDTO;
import java.util.List;

/**
 *
 * @author PC GAMER MASTER RACE
 */
public interface IVentaServicios {
    void agregarProducto(String idVenta, String idProducto, int cantidad)throws NegocioException;
    double calcularTotal(String idVenta)throws NegocioException;
    double aplicarDescuento(String idVenta, String idCliente) throws NegocioException;
    void confirmarVenta(String idVenta) throws NegocioException;
    void registrarPago(String idVenta, PagoDTO pago) throws NegocioException;
    List<VentaDTO> consultarVentas()throws NegocioException;
}


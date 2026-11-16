/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package gym.servicios.interfaces;

import gym.excepciones.NegocioException;
import gym.servicios.dto.ProductoDTO;
import java.util.List;

/**
 *
 * @author PC GAMER MASTER RACE
 */
public interface IProductoServicios {
    void registrarProducto(ProductoDTO producto) throws NegocioException;
    void actualizarProducto(ProductoDTO producto) throws NegocioException;
    void eliminarProducto(String id) throws NegocioException;
    List<ProductoDTO> consultarTodosLosProductos() throws NegocioException;
}

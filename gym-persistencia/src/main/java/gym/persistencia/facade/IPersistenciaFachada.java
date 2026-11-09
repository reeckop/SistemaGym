/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package gym.persistencia.facade;

import gym.persistencia.interfaces.IAccesoDAO;
import gym.persistencia.interfaces.IClienteDAO;
import gym.persistencia.interfaces.ICursoDAO;
import gym.persistencia.interfaces.IInscripcionDAO;
import gym.persistencia.interfaces.IMembresiaDAO;
import gym.persistencia.interfaces.INotificacionDAO;
import gym.persistencia.interfaces.IProductoDAO;
import gym.persistencia.interfaces.IVentaDAO;

/**
 *
 * @author PC GAMER MASTER RACE
 */
public interface IPersistenciaFachada {
    IClienteDAO obtenerClienteDAO();
    IMembresiaDAO obtenerMembresiaDAO();
    ICursoDAO obtenerCursoDAO();
    IProductoDAO obtenerProductoDAO();
    IVentaDAO obtenerVentaDAO();
    IAccesoDAO obtenerAccesoDAO();
    IInscripcionDAO obtenerInscripcionDAO();
    INotificacionDAO obtenerNotificacionDAO();
    
}

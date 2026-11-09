/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package gym.persistencia.facade;

import gym.persistencia.dao.IAccesoDAO;
import gym.persistencia.dao.IClienteDAO;
import gym.persistencia.dao.ICursoDAO;
import gym.persistencia.dao.IInscripcionDAO;
import gym.persistencia.dao.IMembresiaDAO;
import gym.persistencia.dao.INotificacionDAO;
import gym.persistencia.dao.IProductoDAO;
import gym.persistencia.dao.IVentaDAO;

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

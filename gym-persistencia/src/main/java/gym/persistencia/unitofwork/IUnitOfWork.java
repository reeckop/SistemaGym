/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package gym.persistencia.unitofwork;

import gym.persistencia.interfaces.IAccesoDAO;
import gym.persistencia.interfaces.IBeneficioDAO;
import gym.persistencia.interfaces.IClienteDAO;
import gym.persistencia.interfaces.ICursoDAO;
import gym.persistencia.interfaces.IEmpleadoDAO;
import gym.persistencia.interfaces.IInscripcionDAO;
import gym.persistencia.interfaces.IMembresiaDAO;
import gym.persistencia.interfaces.INotificacionDAO;
import gym.persistencia.interfaces.IPagoDAO;
import gym.persistencia.interfaces.IProductoDAO;
import gym.persistencia.interfaces.IVentaDAO;

/**
 *
 * @author PC GAMER MASTER RACE
 */
public interface IUnitOfWork {
    IClienteDAO clienteDAO();

    IAccesoDAO accesoDAO();

    ICursoDAO cursoDAO();

    IInscripcionDAO inscripcionDAO();

    IMembresiaDAO membresiaDAO();

    INotificacionDAO notificacionDAO();

    IProductoDAO productoDAO();

    IVentaDAO ventaDAO();

    IPagoDAO pagoDAO();

    IBeneficioDAO beneficioDAO();
    
    IEmpleadoDAO empleadoDAO();

    void commit();
    
    void rollback();

    void commitAsyc();
}
    

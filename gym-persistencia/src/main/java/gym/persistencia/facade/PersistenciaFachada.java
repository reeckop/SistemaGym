/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
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
import gym.persistencia.dao.impl.AccesoDAO;
import gym.persistencia.dao.impl.ClienteDAO;
import gym.persistencia.dao.impl.CursoDAO;
import gym.persistencia.dao.impl.InscripcionDAO;
import gym.persistencia.dao.impl.MembresiaDAO;
import gym.persistencia.dao.impl.NotificacionDAO;
import gym.persistencia.dao.impl.ProductosDAO;
import gym.persistencia.dao.impl.VentasDAO;


/**
 *
 * @author PC GAMER MASTER RACE
 */
public class PersistenciaFachada implements IPersistenciaFachada {
    private final IClienteDAO clienteDAO;
    private final IMembresiaDAO membresiaDAO;
    private final ICursoDAO cursoDAO;

    private final IProductoDAO productoDAO;
    private final IVentaDAO ventaDAO;
    private final IAccesoDAO accesoDAO;
    private final IInscripcionDAO inscripcionDAO;
    private final INotificacionDAO notificacionDAO;

    public PersistenciaFachada() {

        this.clienteDAO = new ClienteDAO();
        this.membresiaDAO = new MembresiaDAO();
        this.cursoDAO = new CursoDAO();
        this.productoDAO = new ProductosDAO();
        this.ventaDAO = new VentasDAO();
        this.accesoDAO = new AccesoDAO();
        this.inscripcionDAO = new InscripcionDAO();
        this.notificacionDAO = new NotificacionDAO();
    }

    @Override
    public IClienteDAO obtenerClienteDAO() {
        return clienteDAO;
    }

    @Override
    public IMembresiaDAO obtenerMembresiaDAO() {
        return membresiaDAO;
    }

    @Override
    public ICursoDAO obtenerCursoDAO() {
        return cursoDAO;
    }

    @Override
    public IProductoDAO obtenerProductoDAO() {
        return productoDAO;
    }

    @Override
    public IVentaDAO obtenerVentaDAO() {
        return ventaDAO;
    }

    @Override
    public IAccesoDAO obtenerAccesoDAO() {
        return accesoDAO;
    }

    @Override
    public IInscripcionDAO obtenerInscripcionDAO() {
        return inscripcionDAO;
    }

    @Override
    public INotificacionDAO obtenerNotificacionDAO() {
        return notificacionDAO;
    }
    
    
}

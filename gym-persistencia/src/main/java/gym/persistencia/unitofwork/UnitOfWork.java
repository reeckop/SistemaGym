/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gym.persistencia.unitofwork;

import com.mongodb.MongoException;
import com.mongodb.client.ClientSession;
import gym.persistencia.dao.AccesoDAO;
import gym.persistencia.dao.BaseDAO;
import gym.persistencia.dao.BeneficioDAO;
import gym.persistencia.dao.ClienteDAO;
import gym.persistencia.dao.CursoDAO;
import gym.persistencia.dao.EmpleadoDAO;
import gym.persistencia.dao.InscripcionDAO;
import gym.persistencia.dao.MembresiaDAO;
import gym.persistencia.dao.NotificacionDAO;
import gym.persistencia.dao.PagoDAO;
import gym.persistencia.dao.ProductosDAO;
import gym.persistencia.dao.VentasDAO;
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
 * Agrupa los DAO y la sesión de MongoDB de una operación.
 *
 * @author PC GAMER MASTER RACE
 */
public class UnitOfWork implements IUnitOfWork {

    private final ClientSession sesion;

    private final IClienteDAO clienteDAO;
    private final IAccesoDAO accesoDAO;
    private final ICursoDAO cursoDAO;
    private final IInscripcionDAO inscripcionDAO;
    private final IMembresiaDAO membresiaDAO;
    private final INotificacionDAO notificacionDAO;
    private final IProductoDAO productoDAO;
    private final IVentaDAO ventaDAO;
    private final IPagoDAO pagoDAO;
    private final IBeneficioDAO beneficioDAO;
    private final IEmpleadoDAO empleadoDAO;

    public UnitOfWork() {
        sesion = BaseDAO.iniciarSesion();

        clienteDAO = new ClienteDAO(sesion);
        accesoDAO = new AccesoDAO(sesion);
        cursoDAO = new CursoDAO(sesion);
        inscripcionDAO = new InscripcionDAO(sesion);
        membresiaDAO = new MembresiaDAO(sesion);
        notificacionDAO = new NotificacionDAO(sesion);
        productoDAO = new ProductosDAO(sesion);
        ventaDAO = new VentasDAO(sesion);
        pagoDAO = new PagoDAO(sesion);
        beneficioDAO = new BeneficioDAO(sesion);
        empleadoDAO = new EmpleadoDAO(sesion);
    }

    @Override
    public IClienteDAO clienteDAO() {
        return clienteDAO;
    }

    @Override
    public IAccesoDAO accesoDAO() {
        return accesoDAO;
    }

    @Override
    public ICursoDAO cursoDAO() {
        return cursoDAO;
    }

    @Override
    public IInscripcionDAO inscripcionDAO() {
        return inscripcionDAO;
    }

    @Override
    public IMembresiaDAO membresiaDAO() {
        return membresiaDAO;
    }

    @Override
    public INotificacionDAO notificacionDAO() {
        return notificacionDAO;
    }

    @Override
    public IProductoDAO productoDAO() {
        return productoDAO;
    }

    @Override
    public IVentaDAO ventaDAO() {
        return ventaDAO;
    }

    @Override
    public IPagoDAO pagoDAO() {
        return pagoDAO;
    }

    @Override
    public IBeneficioDAO beneficioDAO() {
        return beneficioDAO;
    }
    
    @Override
    public IEmpleadoDAO empleadoDAO() {
        return empleadoDAO;
    }

    @Override
    public void commit() {
        try {
            if (sesion.hasActiveTransaction()) {
                sesion.commitTransaction();
            }
        } finally {
            sesion.close();
        }
    }

    @Override
    public void rollback() {
        try {
            if (sesion.hasActiveTransaction()) {
                sesion.abortTransaction();
            }
        } finally {
            sesion.close();
        }
    }

    @Override
    public void commitAsyc() {
        throw new UnsupportedOperationException("Operacion no implementada");
    }

    
}

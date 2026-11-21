/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
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
import gym.persistencia.dao.AccesoDAO;
import gym.persistencia.dao.BeneficioDAO;
import gym.persistencia.dao.ClienteDAO;
import gym.persistencia.dao.CursoDAO;
import gym.persistencia.dao.InscripcionDAO;
import gym.persistencia.dao.MembresiaDAO;
import gym.persistencia.dao.NotificacionDAO;
import gym.persistencia.dao.PagoDAO;
import gym.persistencia.dao.ProductosDAO;
import gym.persistencia.dao.VentasDAO;
import gym.persistencia.entidades.Beneficio;
import gym.persistencia.entidades.Cliente;
import gym.persistencia.entidades.Curso;
import gym.persistencia.entidades.Inscripcion;
import gym.persistencia.entidades.Membresia;
import gym.persistencia.entidades.Notificacion;
import gym.persistencia.entidades.Pago;
import gym.persistencia.entidades.Producto;
import gym.persistencia.entidades.RegistroIngreso;
import gym.persistencia.entidades.Venta;
import gym.persistencia.excepciones.PersistenciaException;
import gym.persistencia.interfaces.IBeneficioDAO;
import gym.persistencia.interfaces.IPagoDAO;
import java.util.List;


/**
 *
 * @author PC GAMER MASTER RACE
 */
public class PersistenciaFachada implements IPersistenciaFachada {
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

    public PersistenciaFachada() {
        this.clienteDAO = new ClienteDAO();
        this.accesoDAO = new AccesoDAO();
        this.cursoDAO = new CursoDAO();
        this.inscripcionDAO = new InscripcionDAO();
        this.membresiaDAO = new MembresiaDAO();
        this.notificacionDAO = new NotificacionDAO();
        this.productoDAO = new ProductosDAO();
        this.ventaDAO = new VentasDAO();
        this.pagoDAO = new PagoDAO();
        this.beneficioDAO = new BeneficioDAO();
    }
    
    // CLIENTE
    @Override
    public void guardarCliente(Cliente cliente) throws PersistenciaException {
        clienteDAO.guardar(cliente);
    }

    @Override
    public void actualizarCliente(Cliente cliente) throws PersistenciaException {
        clienteDAO.actualizar(cliente);
    }

    @Override
    public Cliente obtenerClientePorId(String id) throws PersistenciaException {
        return clienteDAO.obtenerPorId(id);
    }

    @Override
    public List<Cliente> obtenerTodosLosClientes() throws PersistenciaException {
        return clienteDAO.obtenerTodos();
    }

    // MEMBRESIA
    @Override
    public void guardarMembresia(Membresia membresia) throws PersistenciaException {
        membresiaDAO.guardar(membresia);
    }

    @Override
    public void actualizarMembresia(Membresia membresia) throws PersistenciaException {
        membresiaDAO.actualizar(membresia);
    }

    @Override
    public void eliminarMembresia(String id) throws PersistenciaException {
        membresiaDAO.eliminar(id);
    }

    @Override
    public List<Membresia> obtenerTodasLasMembresias() throws PersistenciaException {
        return membresiaDAO.obtenerTodos();
    }

    // CURSO
    @Override
    public void guardarCurso(Curso curso) throws PersistenciaException {
        cursoDAO.guardar(curso);
    }

    @Override
    public void actualizarCurso(Curso curso) throws PersistenciaException {
        cursoDAO.actualizar(curso);
    }

    @Override
    public void eliminarCurso(String id) throws PersistenciaException {
        cursoDAO.eliminar(id);
    }

    @Override
    public List<Curso> obtenerTodosLosCursos() throws PersistenciaException {
        return cursoDAO.obtenerTodos();
    }

    // INSCRIPCION
    @Override
    public void guardarInscripcion(Inscripcion inscripcion) throws PersistenciaException {
        inscripcionDAO.guardar(inscripcion);
    }

    @Override
    public void actualizarInscripcion(Inscripcion inscripcion) throws PersistenciaException {
        inscripcionDAO.actualizar(inscripcion);
    }

    @Override
    public void eliminarInscripcion(String id) throws PersistenciaException {
        inscripcionDAO.eliminar(id);
    }

    @Override
    public List<Inscripcion> obtenerTodasLasInscripciones() throws PersistenciaException {
        return inscripcionDAO.obtenerTodos();
    }

    // REGISTRO DE INGRESO
    @Override
    public void guardarRegistroIngreso(RegistroIngreso registroIngreso) throws PersistenciaException {
        accesoDAO.guardar(registroIngreso);
    }

    @Override
    public List<RegistroIngreso> obtenerRegistrosIngresoPorCliente(String idCliente) throws PersistenciaException {
        return accesoDAO.obtenerPorCliente(idCliente);
    }

    @Override
    public List<RegistroIngreso> obtenerTodosLosRegistrosIngreso() throws PersistenciaException {
        return accesoDAO.obtenerTodos();
    }

    // PRODUCTO
    @Override
    public void guardarProducto(Producto producto) throws PersistenciaException {
        productoDAO.guardar(producto);
    }

    @Override
    public void actualizarProducto(Producto producto) throws PersistenciaException {
        productoDAO.actualizar(producto);
    }

    @Override
    public void eliminarProducto(String id) throws PersistenciaException {
        productoDAO.eliminar(id);
    }
    
    @Override
    public Producto obtenerProductoPorId(String id) throws PersistenciaException {
        return productoDAO.obtenerPorId(id);
    }

    @Override
    public List<Producto> obtenerTodosLosProductos() throws PersistenciaException {
        return productoDAO.obtenerTodos();
    }

    // VENTA
    @Override
    public void guardarVenta(Venta venta) throws PersistenciaException {
        ventaDAO.guardar(venta);
    }
    
    @Override
    public void actualizarVenta(Venta venta) throws PersistenciaException {
        ventaDAO.actualizarVenta(venta);
    }

    @Override
    public Venta obtenerVentaPorId(String id) throws PersistenciaException {
        return ventaDAO.obtenerPorId(id);
    }

    @Override
    public List<Venta> obtenerTodasLasVentas() throws PersistenciaException {
        return ventaDAO.obtenerTodas();
    }

    // NOTIFICACION
    @Override
    public void guardarNotificacion(Notificacion notificacion) throws PersistenciaException {
        notificacionDAO.guardar(notificacion);
    }

    @Override
    public void actualizarNotificacion(Notificacion notificacion) throws PersistenciaException {
        notificacionDAO.actualizarNotificacion(notificacion);
    }

    @Override
    public List<Notificacion> obtenerNotificacionesPorCliente(String idCliente) throws PersistenciaException {
        return notificacionDAO.obtenerPorCliente(idCliente);
    }

    @Override
    public List<Notificacion> obtenerTodasLasNotificaciones() throws PersistenciaException {
        return notificacionDAO.obtenerTodas();
    }

    // PAGO
    @Override
    public void guardarPago(Pago pago) throws PersistenciaException {
        pagoDAO.guardar(pago);
    }

    @Override
    public void actualizarPago(Pago pago) throws PersistenciaException {
        pagoDAO.actualizar(pago);
    }

    @Override
    public Pago obtenerPagoPorId(String id) throws PersistenciaException {
        return pagoDAO.obtenerPorId(id);
    }

    @Override
    public List<Pago> obtenerTodosLosPagos() throws PersistenciaException {
        return pagoDAO.obtenerTodos();
    }

    // BENEFICIO
    @Override
    public void guardarBeneficio(Beneficio beneficio) throws PersistenciaException {
        beneficioDAO.guardar(beneficio);
    }

    @Override
    public void actualizarBeneficio(Beneficio beneficio) throws PersistenciaException {
        beneficioDAO.actualizar(beneficio);
    }

    @Override
    public void eliminarBeneficio(String id) throws PersistenciaException {
        beneficioDAO.eliminar(id);
    }

    @Override
    public Beneficio obtenerBeneficioPorId(String id) throws PersistenciaException {
        return beneficioDAO.obtenerPorId(id);
    }

    @Override
    public List<Beneficio> obtenerTodosLosBeneficios() throws PersistenciaException {
        return beneficioDAO.obtenerTodos();
    }
    
}



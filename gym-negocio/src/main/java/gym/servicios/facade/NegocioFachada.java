/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gym.servicios.facade;

import gym.excepciones.NegocioException;
import gym.servicios.dto.BeneficioDTO;
import gym.servicios.dto.ClienteDTO;
import gym.servicios.dto.CursoDTO;
import gym.servicios.dto.InscripcionDTO;
import gym.servicios.dto.MembresiaDTO;
import gym.servicios.dto.NotificacionDTO;
import gym.servicios.dto.PagoDTO;
import gym.servicios.dto.ProductoDTO;
import gym.servicios.dto.RegistroIngresoDTO;
import gym.servicios.dto.VentaDTO;
import gym.servicios.interfaces.IBeneficioServicios;
import gym.servicios.interfaces.IClienteServicios;
import gym.servicios.interfaces.ICursoServicios;
import gym.servicios.interfaces.IInscripcionServicios;
import gym.servicios.interfaces.IMembresiaServicios;
import gym.servicios.interfaces.INotificacionServicios;
import gym.servicios.interfaces.IPagoServicios;
import gym.servicios.interfaces.IProductoServicios;
import gym.servicios.interfaces.IRegistroIngresoServicios;
import gym.servicios.interfaces.IVentaServicios;
import java.util.List;

/**
 *
 * @author PC GAMER MASTER RACE
 */
public class NegocioFachada implements INegocioFachada{
    
    private final INotificacionServicios notificacionServicios;
    private final IRegistroIngresoServicios registroIngresoServicios;
    private final IClienteServicios clienteServicios;
    private final IMembresiaServicios membresiaServicios;
    private final ICursoServicios cursoServicios;
    private final IInscripcionServicios inscripcionServicios;
    private final IProductoServicios productoServicios;
    private final IVentaServicios ventaServicios;
    private final IPagoServicios pagoServicios;
    private final IBeneficioServicios beneficioServicios;

    public NegocioFachada(INotificacionServicios notificacionServicios, IRegistroIngresoServicios registroIngresoServicios, IClienteServicios clienteServicios, IMembresiaServicios membresiaServicios, ICursoServicios cursoServicios, IInscripcionServicios inscripcionServicios, IProductoServicios productoServicios, IVentaServicios ventaServicios, IPagoServicios pagoServicios, IBeneficioServicios beneficioServicios) {
        this.notificacionServicios = notificacionServicios;
        this.registroIngresoServicios = registroIngresoServicios;
        this.clienteServicios = clienteServicios;
        this.membresiaServicios = membresiaServicios;
        this.cursoServicios = cursoServicios;
        this.inscripcionServicios = inscripcionServicios;
        this.productoServicios = productoServicios;
        this.ventaServicios = ventaServicios;
        this.pagoServicios = pagoServicios;
        this.beneficioServicios = beneficioServicios;
    }

    // NOTIFICACIONES
    @Override
    public void registrarNotificacion(NotificacionDTO notificacion) throws NegocioException {
        notificacionServicios.registrarNotificacion(notificacion);
    }

    @Override
    public void actualizarNotificacion(NotificacionDTO notificacion) throws NegocioException {
        notificacionServicios.actualizarNotificacion(notificacion);
    }

    @Override
    public List<NotificacionDTO> consultarNotificacionesPorCliente(String idCliente) throws NegocioException {
        return notificacionServicios.consultarNotificacionesPorCliente(idCliente);
    }

    @Override
    public List<NotificacionDTO> consultarTodasLasNotificaciones() throws NegocioException {
        return notificacionServicios.consultarTodasLasNotificaciones();
    }

    // REGISTROS DE INGRESO
    @Override
    public void registrarIngreso(RegistroIngresoDTO registro) throws NegocioException {
        registroIngresoServicios.registrarIngreso(registro);
    }

    @Override
    public List<RegistroIngresoDTO> consultarIngresosPorCliente(String idCliente) throws NegocioException {
        return registroIngresoServicios.consultarIngresosPorCliente(idCliente);
    }

    @Override
    public List<RegistroIngresoDTO> consultarTodosLosIngresos() throws NegocioException {
        return registroIngresoServicios.consultarTodosLosIngresos();
    }

    // CLIENTES
    @Override
    public void registrarCliente(ClienteDTO cliente) throws NegocioException {
        clienteServicios.registrarCliente(cliente);
    }

    @Override
    public void actualizarCliente(ClienteDTO cliente) throws NegocioException {
        clienteServicios.actualizarCliente(cliente);
    }

    @Override
    public ClienteDTO consultarCliente(String id) throws NegocioException {
        return clienteServicios.consultarCliente(id);
    }

    @Override
    public List<ClienteDTO> consultarTodosLosClientes() throws NegocioException {
        return clienteServicios.consultarTodosLosClientes();
    }

    // MEMBRESÍAS
    @Override
    public void registrarMembresia(MembresiaDTO membresia) throws NegocioException {
        membresiaServicios.registrarMembresia(membresia);
    }

    @Override
    public void actualizarMembresia(MembresiaDTO membresia) throws NegocioException {
        membresiaServicios.actualizarMembresia(membresia);
    }

    @Override
    public void eliminarMembresia(String id) throws NegocioException {
        membresiaServicios.eliminarMembresia(id);
    }

    @Override
    public List<MembresiaDTO> consultarTodasLasMembresias() throws NegocioException {
        return membresiaServicios.consultarTodasLasMembresias();
    }

    // CURSOS
    @Override
    public void registrarCurso(CursoDTO curso) throws NegocioException {
        cursoServicios.registrarCurso(curso);
    }

    @Override
    public void actualizarCurso(CursoDTO curso) throws NegocioException {
        cursoServicios.actualizarCurso(curso);
    }

    @Override
    public void eliminarCurso(String id) throws NegocioException {
        cursoServicios.eliminarCurso(id);
    }

    @Override
    public List<CursoDTO> consultarTodosLosCursos() throws NegocioException {
        return cursoServicios.consultarTodosLosCursos();
    }

    // INSCRIPCIONES
    @Override
    public void registrarInscripcion(InscripcionDTO inscripcion) throws NegocioException {
        inscripcionServicios.registrarInscripcion(inscripcion);
    }

    @Override
    public void actualizarInscripcion(InscripcionDTO inscripcion) throws NegocioException {
        inscripcionServicios.actualizarInscripcion(inscripcion);
    }

    @Override
    public void eliminarInscripcion(String id) throws NegocioException {
        inscripcionServicios.eliminarInscripcion(id);
    }

    @Override
    public List<InscripcionDTO> consultarTodasLasInscripciones() throws NegocioException {
        return inscripcionServicios.consultarTodasLasInscripciones();
    }

    // PRODUCTOS
    @Override
    public void registrarProducto(ProductoDTO producto) throws NegocioException {
        productoServicios.registrarProducto(producto);
    }

    @Override
    public void actualizarProducto(ProductoDTO producto) throws NegocioException {
        productoServicios.actualizarProducto(producto);
    }

    @Override
    public void eliminarProducto(String id) throws NegocioException {
        productoServicios.eliminarProducto(id);
    }

    @Override
    public List<ProductoDTO> consultarTodosLosProductos() throws NegocioException {
        return productoServicios.consultarTodosLosProductos();
    }

    // VENTAS
    @Override
    public void agregarProducto(String idVenta, String idProducto, int cantidad) throws NegocioException {
        ventaServicios.agregarProducto(idVenta, idProducto, cantidad);
    }

    @Override
    public double calcularTotal(String idVenta) throws NegocioException {
        return ventaServicios.calcularTotal(idVenta);
    }

    @Override
    public double aplicarDescuento(String idVenta, String idCliente) throws NegocioException {
        return ventaServicios.aplicarDescuento(idVenta, idCliente);
    }

    @Override
    public void confirmarVenta(String idVenta) throws NegocioException {
        ventaServicios.confirmarVenta(idVenta);
    }

    @Override
    public void registrarPago(String idVenta, PagoDTO pago) throws NegocioException {
        ventaServicios.registrarPago(idVenta, pago);
    }

    @Override
    public List<VentaDTO> consultarVentas() throws NegocioException {
        return ventaServicios.consultarVentas();
    }

    // PAGOS
    @Override
    public void registrarPago(PagoDTO pago) throws NegocioException {
        pagoServicios.registrarPago(pago);
    }

    @Override
    public void actualizarPago(PagoDTO pago) throws NegocioException {
        pagoServicios.actualizarPago(pago);
    }

    @Override
    public PagoDTO consultarPago(String id) throws NegocioException {
        return pagoServicios.consultarPago(id);
    }

    @Override
    public List<PagoDTO> consultarTodosLosPagos() throws NegocioException {
        return pagoServicios.consultarTodosLosPagos();
    }

    // BENEFICIOS
    @Override
    public void registrarBeneficio(BeneficioDTO beneficio) throws NegocioException {
        beneficioServicios.registrarBeneficio(beneficio);
    }

    @Override
    public void actualizarBeneficio(BeneficioDTO beneficio) throws NegocioException {
        beneficioServicios.actualizarBeneficio(beneficio);
    }

    @Override
    public void eliminarBeneficio(String id) throws NegocioException {
        beneficioServicios.eliminarBeneficio(id);
    }

    @Override
    public BeneficioDTO consultarBeneficio(String id) throws NegocioException {
        return beneficioServicios.consultarBeneficio(id);
    }

    @Override
    public List<BeneficioDTO> consultarTodosLosBeneficios() throws NegocioException {
        return beneficioServicios.consultarTodosLosBeneficios();
    }
    
}

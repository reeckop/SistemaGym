/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
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
import java.util.List;

/**
 *
 * @author PC GAMER MASTER RACE
 */
public interface INegocioFachada {
    // Notificaciones
    void registrarNotificacion(NotificacionDTO notificacion) throws NegocioException;
    void actualizarNotificacion(NotificacionDTO notificacion) throws NegocioException;
    List<NotificacionDTO> consultarNotificacionesPorCliente(String idCliente) throws NegocioException;
    List<NotificacionDTO> consultarTodasLasNotificaciones() throws NegocioException;

    // Registros de ingreso
    void registrarIngreso(RegistroIngresoDTO registro) throws NegocioException;
    List<RegistroIngresoDTO> consultarIngresosPorCliente(String idCliente) throws NegocioException;
    List<RegistroIngresoDTO> consultarTodosLosIngresos() throws NegocioException;

    // Clientes
    void registrarCliente(ClienteDTO cliente) throws NegocioException;
    void actualizarCliente(ClienteDTO cliente) throws NegocioException;
    ClienteDTO consultarCliente(String id) throws NegocioException;
    List<ClienteDTO> consultarTodosLosClientes() throws NegocioException;

    // Membresías
    void registrarMembresia(MembresiaDTO membresia) throws NegocioException;
    void actualizarMembresia(MembresiaDTO membresia) throws NegocioException;
    void eliminarMembresia(String id) throws NegocioException;
    List<MembresiaDTO> consultarTodasLasMembresias() throws NegocioException;

    // Cursos
    void registrarCurso(CursoDTO curso) throws NegocioException;
    void actualizarCurso(CursoDTO curso) throws NegocioException;
    void eliminarCurso(String id) throws NegocioException;
    List<CursoDTO> consultarTodosLosCursos() throws NegocioException;

    // Inscripciones
    void registrarInscripcion(InscripcionDTO inscripcion) throws NegocioException;
    void actualizarInscripcion(InscripcionDTO inscripcion) throws NegocioException;
    void eliminarInscripcion(String id) throws NegocioException;
    List<InscripcionDTO> consultarTodasLasInscripciones() throws NegocioException;

    // Productos
    void registrarProducto(ProductoDTO producto) throws NegocioException;
    void actualizarProducto(ProductoDTO producto) throws NegocioException;
    void eliminarProducto(String id) throws NegocioException;
    List<ProductoDTO> consultarTodosLosProductos() throws NegocioException;

    // Ventas
    void agregarProducto(String idVenta, String idProducto, int cantidad) throws NegocioException;
    double calcularTotal(String idVenta) throws NegocioException;
    double aplicarDescuento(String idVenta, String idCliente) throws NegocioException;
    void confirmarVenta(String idVenta) throws NegocioException;
    void registrarPago(String idVenta, PagoDTO pago) throws NegocioException;
    List<VentaDTO> consultarVentas() throws NegocioException;

    // Pagos
    void registrarPago(PagoDTO pago) throws NegocioException;
    void actualizarPago(PagoDTO pago) throws NegocioException;
    PagoDTO consultarPago(String id) throws NegocioException;
    List<PagoDTO> consultarTodosLosPagos() throws NegocioException;

    // Beneficios
    void registrarBeneficio(BeneficioDTO beneficio) throws NegocioException;
    void actualizarBeneficio(BeneficioDTO beneficio) throws NegocioException;
    void eliminarBeneficio(String id) throws NegocioException;
    BeneficioDTO consultarBeneficio(String id) throws NegocioException;
    List<BeneficioDTO> consultarTodosLosBeneficios() throws NegocioException;
    
}

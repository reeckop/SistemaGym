/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package gym.persistencia.facade;

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
import java.util.List;

/**
 *
 * @author PC GAMER MASTER RACE
 */
public interface IPersistenciaFachada {
    
    // CLIENTE
    void guardarCliente(Cliente cliente) throws PersistenciaException;
    void actualizarCliente(Cliente cliente) throws PersistenciaException;
    Cliente obtenerClientePorId(String id) throws PersistenciaException;
    List<Cliente> obtenerTodosLosClientes() throws PersistenciaException;

    // MEMBRESIA
    void guardarMembresia(Membresia membresia) throws PersistenciaException;
    void actualizarMembresia(Membresia membresia) throws PersistenciaException;
    void eliminarMembresia(String id) throws PersistenciaException;
    List<Membresia> obtenerTodasLasMembresias() throws PersistenciaException;

    // CURSO
    void guardarCurso(Curso curso) throws PersistenciaException;
    void actualizarCurso(Curso curso) throws PersistenciaException;
    void eliminarCurso(String id) throws PersistenciaException;
    List<Curso> obtenerTodosLosCursos() throws PersistenciaException;

    // INSCRIPCION
    void guardarInscripcion(Inscripcion inscripcion) throws PersistenciaException;
    void actualizarInscripcion(Inscripcion inscripcion) throws PersistenciaException;
    void eliminarInscripcion(String id) throws PersistenciaException;
    List<Inscripcion> obtenerTodasLasInscripciones() throws PersistenciaException;

    // REGISTRO DE INGRESO
    void guardarRegistroIngreso(RegistroIngreso registroIngreso) throws PersistenciaException;
    List<RegistroIngreso> obtenerRegistrosIngresoPorCliente(String idCliente) throws PersistenciaException;
    List<RegistroIngreso> obtenerTodosLosRegistrosIngreso() throws PersistenciaException;

    // PRODUCTO
    void guardarProducto(Producto producto) throws PersistenciaException;
    void actualizarProducto(Producto producto) throws PersistenciaException;
    void eliminarProducto(String id) throws PersistenciaException;
    Producto obtenerProductoPorId(String id) throws PersistenciaException;
    List<Producto> obtenerTodosLosProductos() throws PersistenciaException;

    // VENTA
    void guardarVenta(Venta venta) throws PersistenciaException;
    void actualizarVenta(Venta venta) throws PersistenciaException;
    Venta obtenerVentaPorId(String id) throws PersistenciaException;
    List<Venta> obtenerTodasLasVentas() throws PersistenciaException;

    // NOTIFICACION
    void guardarNotificacion(Notificacion notificacion) throws PersistenciaException;
    void actualizarNotificacion(Notificacion notificacion) throws PersistenciaException;
    List<Notificacion> obtenerNotificacionesPorCliente(String idCliente) throws PersistenciaException;
    List<Notificacion> obtenerTodasLasNotificaciones() throws PersistenciaException;

    // PAGO
    void guardarPago(Pago pago) throws PersistenciaException;
    void actualizarPago(Pago pago) throws PersistenciaException;
    Pago obtenerPagoPorId(String id) throws PersistenciaException;
    List<Pago> obtenerTodosLosPagos() throws PersistenciaException;

    // BENEFICIO
    void guardarBeneficio(Beneficio beneficio) throws PersistenciaException;
    void actualizarBeneficio(Beneficio beneficio) throws PersistenciaException;
    void eliminarBeneficio(String id) throws PersistenciaException;
    Beneficio obtenerBeneficioPorId(String id) throws PersistenciaException;
    List<Beneficio> obtenerTodosLosBeneficios() throws PersistenciaException;
    
}
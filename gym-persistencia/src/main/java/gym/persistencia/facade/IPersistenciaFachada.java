/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package gym.persistencia.facade;

import gym.persistencia.entidades.Cliente;
import gym.persistencia.entidades.Curso;
import gym.persistencia.entidades.Inscripcion;
import gym.persistencia.entidades.Membresia;
import gym.persistencia.entidades.Notificacion;
import gym.persistencia.entidades.Producto;
import gym.persistencia.entidades.RegistroIngreso;
import gym.persistencia.entidades.Venta;
import gym.persistencia.interfaces.IAccesoDAO;
import gym.persistencia.interfaces.IClienteDAO;
import gym.persistencia.interfaces.ICursoDAO;
import gym.persistencia.interfaces.IInscripcionDAO;
import gym.persistencia.interfaces.IMembresiaDAO;
import gym.persistencia.interfaces.INotificacionDAO;
import gym.persistencia.interfaces.IProductoDAO;
import gym.persistencia.interfaces.IVentaDAO;
import java.util.List;

/**
 *
 * @author PC GAMER MASTER RACE
 */
public interface IPersistenciaFachada {
    // =========================
    // CLIENTE
    // =========================

    void guardarCliente(Cliente cliente);

    void actualizarCliente(Cliente cliente);

    Cliente obtenerClientePorId(String id);

    List<Cliente> obtenerTodosLosClientes();


    // =========================
    // MEMBRESIA
    // =========================

    void guardarMembresia(Membresia membresia);

    void actualizarMembresia(Membresia membresia);

    Membresia obtenerMembresiaPorId(String id);

    List<Membresia> obtenerTodasLasMembresias();


    // =========================
    // CURSO
    // =========================

    void guardarCurso(Curso curso);

    void actualizarCurso(Curso curso);

    Curso obtenerCursoPorId(String id);

    List<Curso> obtenerTodosLosCursos();


    // =========================
    // INSCRIPCION
    // =========================

    void guardarInscripcion(Inscripcion inscripcion);

    void actualizarInscripcion(Inscripcion inscripcion);

    Inscripcion obtenerInscripcionPorId(String id);

    List<Inscripcion> obtenerTodasLasInscripciones();


    // =========================
    // REGISTRO DE INGRESO
    // =========================

    void guardarRegistroIngreso(RegistroIngreso registroIngreso);

    void actualizarRegistroIngreso(RegistroIngreso registroIngreso);

    RegistroIngreso obtenerRegistroIngresoPorId(String id);

    List<RegistroIngreso> obtenerTodosLosRegistrosIngreso();


    // =========================
    // PRODUCTO
    // =========================

    void guardarProducto(Producto producto);

    void actualizarProducto(Producto producto);

    Producto obtenerProductoPorId(String id);

    List<Producto> obtenerTodosLosProductos();


    // =========================
    // VENTA
    // =========================

    void guardarVenta(Venta venta);

    Venta obtenerVentaPorId(String id);

    List<Venta> obtenerTodasLasVentas();


    // =========================
    // NOTIFICACION
    // =========================

    void guardarNotificacion(Notificacion notificacion);

    void actualizarNotificacion(Notificacion notificacion);

    Notificacion obtenerNotificacionPorId(String id);

    List<Notificacion> obtenerNotificacionesPorCliente(String idCliente);

    List<Notificacion> obtenerTodasLasNotificaciones();
    
}

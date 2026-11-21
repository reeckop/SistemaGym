/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gym.servicios.servicios;

import gym.excepciones.NegocioException;
import gym.persistencia.entidades.Cliente;
import gym.persistencia.entidades.DetalleVenta;
import gym.persistencia.entidades.Pago;
import gym.persistencia.entidades.Producto;
import gym.persistencia.entidades.Venta;
import gym.persistencia.excepciones.PersistenciaException;
import gym.persistencia.unitofwork.IUnitOfWork;
import gym.persistencia.unitofwork.UnitOfWork;
import gym.servicios.dto.PagoDTO;
import gym.servicios.dto.VentaDTO;
import gym.servicios.interfaces.IVentaServicios;
import gym.servicios.mappers.Mappers;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author PC GAMER MASTER RACE
 */
public class VentaServicios implements IVentaServicios {

    @Override
    public void agregarProducto(String idVenta, String idProducto, int cantidad) throws NegocioException {
        validarId(idVenta);
        validarIdProducto(idProducto);
        validarCantidad(cantidad);

        IUnitOfWork unitOfWork = new UnitOfWork();

        try {
            Venta venta = obtenerVenta(unitOfWork, idVenta);
            Producto producto = unitOfWork.productoDAO().obtenerPorId(idProducto);

            if (producto == null) {
                throw new NegocioException("El producto no existe.");
            }

            if (producto.getStock() < cantidad) {
                throw new NegocioException("No hay suficiente stock del producto.");
            }

            double subtotal = producto.getPrecio() * cantidad;

            DetalleVenta detalle = new DetalleVenta(producto, cantidad, producto.getPrecio(), subtotal);

            if (venta.getDetalles() == null) {
                venta.setDetalles(new ArrayList<>());
            }

            venta.getDetalles().add(detalle);

            calcularImportes(venta);

            unitOfWork.ventaDAO().actualizarVenta(venta);
            unitOfWork.commit();

        } catch (PersistenciaException e) {
            unitOfWork.rollback();
            throw new NegocioException("No fue posible agregar el producto a la venta.", e);
        }
    }

    @Override
    public double calcularTotal(String idVenta) throws NegocioException {
        validarId(idVenta);

        IUnitOfWork unitOfWork = new UnitOfWork();

        try {
            Venta venta = obtenerVenta(unitOfWork, idVenta);

            calcularImportes(venta);

            unitOfWork.ventaDAO().actualizarVenta(venta);
            unitOfWork.commit();

            return venta.getTotal();

        } catch (PersistenciaException e) {
            unitOfWork.rollback();
            throw new NegocioException("No fue posible calcular el total de la venta.", e);
        }
    }

    @Override
    public double aplicarDescuento(String idVenta, String idCliente) throws NegocioException {
        validarId(idVenta);
        validarIdCliente(idCliente);

        IUnitOfWork unitOfWork = new UnitOfWork();

        try {
            Venta venta = obtenerVenta(unitOfWork, idVenta);
            Cliente cliente = unitOfWork.clienteDAO().obtenerPorId(idCliente);

            if (cliente == null) {
                throw new NegocioException("El cliente no existe.");
            }

            calcularImportes(venta);

            unitOfWork.ventaDAO().actualizarVenta(venta);
            unitOfWork.commit();

            return venta.getDescuento();

        } catch (PersistenciaException e) {
            unitOfWork.rollback();
            throw new NegocioException("No fue posible aplicar el descuento.", e);
        }
    }

    @Override
    public void confirmarVenta(String idVenta) throws NegocioException {
        validarId(idVenta);

        IUnitOfWork unitOfWork = new UnitOfWork();

        try {
            Venta venta = obtenerVenta(unitOfWork, idVenta);

            if (venta.getDetalles() == null || venta.getDetalles().isEmpty()) {
                throw new NegocioException("La venta debe contener al menos un producto.");
            }

            calcularImportes(venta);

            for (DetalleVenta detalle : venta.getDetalles()) {
                Producto producto = detalle.getProducto();

                if (producto.getStock() < detalle.getCantidad()) {
                    throw new NegocioException("No hay suficiente stock del producto: " + producto.getNombre());
                }

                producto.setStock(producto.getStock() - detalle.getCantidad());

                unitOfWork.productoDAO().actualizar(producto);
            }

            unitOfWork.ventaDAO().actualizarVenta(venta);
            unitOfWork.commit();

        } catch (PersistenciaException e) {
            unitOfWork.rollback();
            throw new NegocioException("No fue posible confirmar la venta.", e);
        }
    }

    @Override
    public void registrarPago(String idVenta, PagoDTO pago) throws NegocioException {
        validarId(idVenta);
        validarPago(pago);

        IUnitOfWork unitOfWork = new UnitOfWork();

        try {
            Venta venta = obtenerVenta(unitOfWork, idVenta);

            if (pago.getMonto() < venta.getTotal()) {
                throw new NegocioException("El monto del pago es insuficiente.");
            }

            Pago pagoEntidad = Mappers.convertirAEntidad(pago);

            venta.setPago(pagoEntidad);

            unitOfWork.ventaDAO().actualizarVenta(venta);
            unitOfWork.commit();

        } catch (PersistenciaException e) {
            unitOfWork.rollback();
            throw new NegocioException("No fue posible registrar el pago.", e);
        }
    }

    @Override
    public List<VentaDTO> consultarVentas() throws NegocioException {
        IUnitOfWork unitOfWork = new UnitOfWork();

        try {
            List<VentaDTO> ventasDTO = new ArrayList<>();
            List<Venta> ventas = unitOfWork.ventaDAO().obtenerTodas();

            if (ventas != null) {
                for (Venta venta : ventas) {
                    if (venta != null) {
                        ventasDTO.add(Mappers.convertirADTO(venta));
                    }
                }
            }

            unitOfWork.commit();
            return ventasDTO;

        } catch (PersistenciaException e) {
            unitOfWork.rollback();
            throw new NegocioException("No fue posible consultar las ventas.", e);
        }
    }

    private void calcularImportes(Venta venta) {
        double subtotal = 0;

        if (venta.getDetalles() != null) {
            for (DetalleVenta detalle : venta.getDetalles()) {
                subtotal += detalle.getSubtotal();
            }
        }

        venta.setSubtotal(subtotal);
        venta.setTotal(subtotal - venta.getDescuento());
    }

    private Venta obtenerVenta(IUnitOfWork unitOfWork, String idVenta) throws NegocioException, PersistenciaException {
        validarId(idVenta);

        Venta venta = unitOfWork.ventaDAO().obtenerPorId(idVenta);

        if (venta == null) {
            throw new NegocioException("La venta no existe.");
        }

        return venta;
    }

    private void validarId(String id) throws NegocioException {
        if (id == null || id.isBlank()) {
            throw new NegocioException("El ID de la venta es obligatorio.");
        }
    }

    private void validarIdProducto(String idProducto) throws NegocioException {
        if (idProducto == null || idProducto.isBlank()) {
            throw new NegocioException("El ID del producto es obligatorio.");
        }
    }

    private void validarIdCliente(String idCliente) throws NegocioException {
        if (idCliente == null || idCliente.isBlank()) {
            throw new NegocioException("El ID del cliente es obligatorio.");
        }
    }

    private void validarCantidad(int cantidad) throws NegocioException {
        if (cantidad <= 0) {
            throw new NegocioException("La cantidad debe ser mayor a cero.");
        }
    }

    private void validarPago(PagoDTO pago) throws NegocioException {
        if (pago == null) {
            throw new NegocioException("Los datos del pago son obligatorios.");
        }

        if (pago.getMonto() <= 0) {
            throw new NegocioException("El monto del pago debe ser mayor a cero.");
        }

        if (pago.getFechaHora() == null) {
            throw new NegocioException("La fecha y hora del pago son obligatorias.");
        }

        if (pago.getEstado() == null) {
            throw new NegocioException("El estado del pago es obligatorio.");
        }

        if (pago.getMetodoPago() == null) {
            throw new NegocioException("El método de pago es obligatorio.");
        }
    }
}

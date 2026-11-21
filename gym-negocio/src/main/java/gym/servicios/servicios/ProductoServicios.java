/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gym.servicios.servicios;

import gym.excepciones.NegocioException;
import gym.persistencia.entidades.Producto;
import gym.persistencia.excepciones.PersistenciaException;
import gym.persistencia.unitofwork.IUnitOfWork;
import gym.persistencia.unitofwork.UnitOfWork;
import gym.servicios.dto.ProductoDTO;
import gym.servicios.interfaces.IProductoServicios;
import gym.servicios.mappers.Mappers;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author PC GAMER MASTER RACE
 */
public class ProductoServicios implements IProductoServicios {

    @Override
    public void registrarProducto(ProductoDTO producto) throws NegocioException {
        validarProducto(producto);

        IUnitOfWork unitOfWork = new UnitOfWork();

        try {
            unitOfWork.productoDAO().guardar(Mappers.convertirAEntidad(producto));
            unitOfWork.commit();

        } catch (PersistenciaException e) {
            unitOfWork.rollback();
            throw new NegocioException("No fue posible registrar el producto.", e);
        }
    }

    @Override
    public void actualizarProducto(ProductoDTO producto) throws NegocioException {
        validarProducto(producto);
        validarId(producto.getIdProducto());

        IUnitOfWork unitOfWork = new UnitOfWork();

        try {
            unitOfWork.productoDAO().actualizar(Mappers.convertirAEntidad(producto));
            unitOfWork.commit();

        } catch (PersistenciaException e) {
            unitOfWork.rollback();
            throw new NegocioException("No fue posible actualizar el producto.", e);
        }
    }

    @Override
    public void eliminarProducto(String id) throws NegocioException {
        validarId(id);

        IUnitOfWork unitOfWork = new UnitOfWork();

        try {
            unitOfWork.productoDAO().eliminar(id);
            unitOfWork.commit();

        } catch (PersistenciaException e) {
            unitOfWork.rollback();
            throw new NegocioException("No fue posible eliminar el producto.", e);
        }
    }

    @Override
    public List<ProductoDTO> consultarTodosLosProductos() throws NegocioException {
        IUnitOfWork unitOfWork = new UnitOfWork();

        try {
            List<ProductoDTO> resultado = new ArrayList<>();
            List<Producto> productos = unitOfWork.productoDAO().obtenerTodos();

            if (productos != null) {
                for (Producto producto : productos) {
                    if (producto != null) {
                        resultado.add(Mappers.convertirADTO(producto));
                    }
                }
            }

            unitOfWork.commit();
            return resultado;

        } catch (PersistenciaException e) {
            unitOfWork.rollback();
            throw new NegocioException("No fue posible consultar los productos.", e);
        }
    }

    private void validarProducto(ProductoDTO producto) throws NegocioException {
        if (producto == null) {
            throw new NegocioException("Los datos del producto son obligatorios.");
        }

        if (producto.getNombre() == null || producto.getNombre().isBlank()) {
            throw new NegocioException("El nombre del producto es obligatorio.");
        }

        if (producto.getCategoria() == null || producto.getCategoria().isBlank()) {
            throw new NegocioException("La categoría es obligatoria.");
        }

        if (producto.getPrecio() < 0) {
            throw new NegocioException("El precio no puede ser negativo.");
        }

        if (producto.getStock() < 0) {
            throw new NegocioException("El stock no puede ser negativo.");
        }
    }

    private void validarId(String id) throws NegocioException {
        if (id == null || id.isBlank()) {
            throw new NegocioException("El ID de producto es obligatorio.");
        }
    }
}

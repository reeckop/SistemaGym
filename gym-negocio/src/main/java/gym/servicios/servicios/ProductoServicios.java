/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gym.servicios.servicios;

import gym.excepciones.NegocioException;
import gym.persistencia.entidades.Producto;
import gym.persistencia.excepciones.PersistenciaException;
import gym.persistencia.unitofwork.IUnitOfWork;
import gym.persistencia.unitofwork.IUnitOfWorkFactory;
import gym.persistencia.unitofwork.UnitOfWorkFactory;
import gym.servicios.dto.ProductoDTO;
import gym.servicios.interfaces.IProductoServicios;
import gym.servicios.mappers.Mappers;
import java.util.ArrayList;
import java.util.List;

/**
 * Gestiona las reglas de negocio de producto.
 *
 * @author PC GAMER MASTER RACE
 */
public class ProductoServicios implements IProductoServicios {
    private final IUnitOfWorkFactory unidadDeTrabajoFactory;

    public ProductoServicios() {
        this(UnitOfWorkFactory.obtenerInstancia());
    }

    public ProductoServicios(IUnitOfWorkFactory unidadDeTrabajoFactory) {
        this.unidadDeTrabajoFactory = java.util.Objects.requireNonNull(unidadDeTrabajoFactory);
    }

    @Override
    public void registrarProducto(ProductoDTO producto) throws NegocioException {
        validarProducto(producto);

        IUnitOfWork unitOfWork = unidadDeTrabajoFactory.crear();

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

        IUnitOfWork unitOfWork = unidadDeTrabajoFactory.crear();

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

        IUnitOfWork unitOfWork = unidadDeTrabajoFactory.crear();

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
        IUnitOfWork unitOfWork = unidadDeTrabajoFactory.crear();

        try {
            List<Producto> existentes = unitOfWork.productoDAO().obtenerTodos();
            java.util.Map<String, Producto> productosPorId = new java.util.HashMap<>();
            java.util.Map<String, Producto> productosPorNombre = new java.util.HashMap<>();
            if (existentes != null) {
                for (Producto producto : existentes) {
                    if (producto != null) {
                        productosPorId.put(producto.getIdProducto(), producto);
                        if (producto.getNombre() != null) {
                            productosPorNombre.put(
                                    producto.getNombre().trim().toLowerCase(java.util.Locale.ROOT),
                                    producto);
                        }
                    }
                }
            }
            // Catálogo inicial para que las tres categorías tengan inventario demostrable.
            for (ProductoDTO inicial : productosIniciales()) {
                Producto existente = productosPorId.get(inicial.getIdProducto());
                if (existente == null) {
                    String nombre = inicial.getNombre().trim().toLowerCase(java.util.Locale.ROOT);
                    existente = productosPorNombre.get(nombre);
                }

                if (existente == null) {
                    unitOfWork.productoDAO().guardar(Mappers.convertirAEntidad(inicial));
                } else if (!inicial.getImagen().equals(existente.getImagen())) {
                    existente.setImagen(inicial.getImagen());
                    unitOfWork.productoDAO().actualizar(existente);
                }
            }
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

    private List<ProductoDTO> productosIniciales() {
        return List.of(
                new ProductoDTO("671000000000000000000001", "Mancuernas ajustables", "Accesorios", 899, 12, "/gym/ventas/productos/mancuernas.png"),
                new ProductoDTO("671000000000000000000002", "Cuerda para saltar", "Accesorios", 159, 25, "/gym/ventas/productos/cuerda.png"),
                new ProductoDTO("671000000000000000000003", "Proteína whey", "Suplementos", 749, 15, "/gym/ventas/productos/whey.png"),
                new ProductoDTO("671000000000000000000004", "Creatina monohidratada", "Suplementos", 429, 18, "/gym/ventas/productos/creatina.png"),
                new ProductoDTO("671000000000000000000005", "Playera deportiva", "Ropa", 299, 20, "/gym/ventas/productos/playera.png"),
                new ProductoDTO("671000000000000000000006", "Guantes de entrenamiento", "Ropa", 249, 14, "/gym/ventas/productos/guantes.png")
        );
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

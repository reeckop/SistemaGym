/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package gym.persistencia.daoTest;

import gym.persistencia.dao.ProductosDAO;
import gym.persistencia.entidades.Producto;
import gym.persistencia.excepciones.PersistenciaException;
import java.util.List;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;

/**
 *
 * @author PC GAMER MASTER RACE
 */
public class ProductosDAOTest {
    
    private ProductosDAO productosDAO;

    @BeforeEach
    void setUp() {
        productosDAO = new ProductosDAO();
    }

    @Test
    void testGuardar() throws PersistenciaException  {
        String id = new ObjectId().toString();

        Producto producto = new Producto(
                id, "Proteína", "Suplementos", 800.00, 10, "proteina.png");

        productosDAO.guardar(producto);

        List<Producto> resultado = productosDAO.obtenerTodos();

        assertTrue(resultado.stream()
                .anyMatch(p -> id.equals(p.getIdProducto())));

        productosDAO.eliminar(id);
    }

    @Test
    void testActualizar() throws PersistenciaException {
        String id = new ObjectId().toString();

        Producto producto = new Producto(
                id, "Proteína", "Suplementos", 800.00, 10, "proteina.png");

        productosDAO.guardar(producto);

        producto.setNombre("Proteína actualizada");
        producto.setPrecio(900.00);

        productosDAO.actualizar(producto);

        List<Producto> resultado = productosDAO.obtenerTodos();

        assertTrue(resultado.stream().anyMatch(p ->
                id.equals(p.getIdProducto())
                && "Proteína actualizada".equals(p.getNombre())
                && p.getPrecio() == 900.00));

        productosDAO.eliminar(id);
    }

    @Test
    void testEliminar() throws PersistenciaException {
        String id = new ObjectId().toString();

        Producto producto = new Producto(
                id, "Proteína", "Suplementos", 800.00, 10, "proteina.png");

        productosDAO.guardar(producto);
        productosDAO.eliminar(id);

        List<Producto> resultado = productosDAO.obtenerTodos();

        assertFalse(resultado.stream()
                .anyMatch(p -> id.equals(p.getIdProducto())));
    }

    @Test
    void testObtenerTodos() throws PersistenciaException {
        List<Producto> resultado = productosDAO.obtenerTodos();

        assertNotNull(resultado);
    }

    
    
}

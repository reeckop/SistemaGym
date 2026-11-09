/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package gym.persistencia.dao.impl;

import gym.persistencia.entidades.Producto;
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
    void testGuardar() {
        Producto producto = new Producto(
                new ObjectId().toHexString(),
                "Producto de prueba",
                "Suplementos",
                250.0,
                10,
                "imagen.jpg"
        );

        assertDoesNotThrow(() -> productosDAO.guardar(producto));
    }

    @Test
    void testActualizar() {
        Producto producto = new Producto(
                new ObjectId().toHexString(),
                "Producto para actualizar",
                "Suplementos",
                250.0,
                10,
                "imagen.jpg"
        );

        assertDoesNotThrow(() -> {
            productosDAO.guardar(producto);
            producto.setNombre("Producto actualizado");
            productosDAO.actualizar(producto);
        });
    }

    @Test
    void testEliminar() {
        Producto producto = new Producto(
                new ObjectId().toHexString(),
                "Producto para eliminar",
                "Suplementos",
                250.0,
                10,
                "imagen.jpg"
        );

        assertDoesNotThrow(() -> {
            productosDAO.guardar(producto);
            productosDAO.eliminar(producto.getIdProducto());
        });
    }

    @Test
    void testObtenerTodos() {
        assertNotNull(
                assertDoesNotThrow(() -> productosDAO.obtenerTodos())
        );
    }
    
}

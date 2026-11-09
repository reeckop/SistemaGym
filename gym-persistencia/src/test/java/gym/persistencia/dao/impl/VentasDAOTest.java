/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package gym.persistencia.dao.impl;

import gym.persistencia.entidades.Venta;
import java.time.LocalDateTime;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;

/**
 *
 * @author PC GAMER MASTER RACE
 */
public class VentasDAOTest {
    
    private VentasDAO ventasDAO;

    @BeforeEach
    void setUp() {
        ventasDAO = new VentasDAO();
    }

    @Test
    void testGuardar() {
        Venta venta = new Venta(
                new ObjectId().toHexString(),
                LocalDateTime.now(),
                500.0,
                0.0,
                500.0
        );

        assertDoesNotThrow(() -> ventasDAO.guardar(venta));
    }

    @Test
    void testObtenerPorId() {
        Venta venta = new Venta(
                new ObjectId().toHexString(),
                LocalDateTime.now(),
                500.0,
                0.0,
                500.0
        );

        assertDoesNotThrow(() -> ventasDAO.guardar(venta));

        Venta resultado = assertDoesNotThrow(
                () -> ventasDAO.obtenerPorId(venta.getIdVenta())
        );

        assertNotNull(resultado);
    }

    @Test
    void testObtenerTodas() {
        assertNotNull(
                assertDoesNotThrow(() -> ventasDAO.obtenerTodas())
        );
    }
    
}

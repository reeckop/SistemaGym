/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package gym.persistencia.daoTest;

import gym.persistencia.dao.VentasDAO;
import gym.persistencia.entidades.Venta;
import gym.persistencia.excepciones.PersistenciaException;
import java.time.LocalDateTime;
import java.util.List;
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
    void testGuardar() throws PersistenciaException  {
        String id = new ObjectId().toString();

        Venta venta = new Venta(
                id, LocalDateTime.now(), 1000.00, 100.00, 900.00);

        ventasDAO.guardar(venta);

        Venta resultado = ventasDAO.obtenerPorId(id);

        assertNotNull(resultado);
        assertEquals(id, resultado.getIdVenta());
    }

    @Test
    void testObtenerPorId() throws PersistenciaException {
        String id = new ObjectId().toString();

        Venta venta = new Venta(
                id, LocalDateTime.now(), 1500.00, 200.00, 1300.00);

        ventasDAO.guardar(venta);

        Venta resultado = ventasDAO.obtenerPorId(id);

        assertNotNull(resultado);
        assertEquals(1500.00, resultado.getSubtotal());
    }

    @Test
    void testObtenerTodas() throws PersistenciaException {
        List<Venta> resultado = ventasDAO.obtenerTodas();

        assertNotNull(resultado);
    }
}

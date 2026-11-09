/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package gym.persistencia.daoTest;

import gym.persistencia.dao.MembresiaDAO;
import gym.persistencia.entidades.Membresia;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;

/**
 *
 * @author PC GAMER MASTER RACE
 */
public class MembresiaDAOTest {
    
    private MembresiaDAO membresiaDAO;

    @BeforeEach
    void setUp() {
        membresiaDAO = new MembresiaDAO();
    }

    @Test
    void testGuardar() {
        Membresia membresia = new Membresia(
                new ObjectId().toHexString(),
                "Membresia de prueba",
                500.0
        );

        assertDoesNotThrow(() -> membresiaDAO.guardar(membresia));
    }

    @Test
    void testActualizar() {
        Membresia membresia = new Membresia(
                new ObjectId().toHexString(),
                "Membresia para actualizar",
                500.0
        );

        assertDoesNotThrow(() -> {
            membresiaDAO.guardar(membresia);
            membresia.setNombre("Membresia actualizada");
            membresiaDAO.actualizar(membresia);
        });
    }

    @Test
    void testEliminar() {
        Membresia membresia = new Membresia(
                new ObjectId().toHexString(),
                "Membresia para eliminar",
                500.0
        );

        assertDoesNotThrow(() -> {
            membresiaDAO.guardar(membresia);
            membresiaDAO.eliminar(membresia.getIdMembresia());
        });
    }

    @Test
    void testObtenerTodos() {
        assertNotNull(
                assertDoesNotThrow(() -> membresiaDAO.obtenerTodos())
        );
    }
    
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package gym.persistencia.dao.impl;

import gym.persistencia.entidades.RegistroIngreso;
import java.time.LocalDateTime;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;

/**
 *
 * @author PC GAMER MASTER RACE
 */
public class AccesoDAOTest {
    
     private AccesoDAO accesoDAO;

    @BeforeEach
    void setUp() {
        accesoDAO = new AccesoDAO();
    }

    @Test
    void testGuardar() {
        RegistroIngreso registro = new RegistroIngreso(
                new ObjectId().toHexString(),
                LocalDateTime.now(),
                null,
                "ACTIVO"
        );

        assertDoesNotThrow(() -> accesoDAO.guardar(registro));
    }

    @Test
    void testObtenerPorCliente() {
        assertNotNull(
                assertDoesNotThrow(() ->
                        accesoDAO.obtenerPorCliente(new ObjectId().toHexString())
                )
        );
    }

    @Test
    void testObtenerTodos() {
        assertNotNull(
                assertDoesNotThrow(() -> accesoDAO.obtenerTodos())
        );
    }
}
    

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package gym.persistencia.dao.impl;

import gym.persistencia.entidades.Inscripcion;
import java.time.LocalDate;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;

/**
 *
 * @author PC GAMER MASTER RACE
 */
public class InscripcionDAOTest {
    
    private InscripcionDAO inscripcionDAO;

    @BeforeEach
    void setUp() {
        inscripcionDAO = new InscripcionDAO();
    }

    @Test
    void testGuardar() {
        Inscripcion inscripcion = new Inscripcion(
                new ObjectId().toHexString(),
                LocalDate.now(),
                "ACTIVA"
        );

        assertDoesNotThrow(() -> inscripcionDAO.guardar(inscripcion));
    }

    @Test
    void testActualizar() {
        Inscripcion inscripcion = new Inscripcion(
                new ObjectId().toHexString(),
                LocalDate.now(),
                "ACTIVA"
        );

        assertDoesNotThrow(() -> {
            inscripcionDAO.guardar(inscripcion);
            inscripcion.setEstado("CANCELADA");
            inscripcionDAO.actualizar(inscripcion);
        });
    }

    @Test
    void testEliminar() {
        Inscripcion inscripcion = new Inscripcion(
                new ObjectId().toHexString(),
                LocalDate.now(),
                "ACTIVA"
        );

        assertDoesNotThrow(() -> {
            inscripcionDAO.guardar(inscripcion);
            inscripcionDAO.eliminar(inscripcion.getIdInscripcion());
        });
    }

    @Test
    void testObtenerTodos() {
        assertNotNull(
                assertDoesNotThrow(() -> inscripcionDAO.obtenerTodos())
        );
    }
    
}

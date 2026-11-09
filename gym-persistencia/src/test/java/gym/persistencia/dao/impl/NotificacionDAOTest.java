/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package gym.persistencia.dao.impl;

import gym.persistencia.entidades.Notificacion;
import java.time.LocalDateTime;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;

/**
 *
 * @author PC GAMER MASTER RACE
 */
public class NotificacionDAOTest {
    
    private NotificacionDAO notificacionDAO;

    @BeforeEach
    void setUp() {
        notificacionDAO = new NotificacionDAO();
    }

    @Test
    void testGuardar() {
        Notificacion notificacion = new Notificacion(
                new ObjectId().toHexString(),
                "INFORMACION",
                "Notificacion de prueba",
                LocalDateTime.now(),
                "PENDIENTE"
        );

        assertDoesNotThrow(() -> notificacionDAO.guardar(notificacion));
    }

    @Test
    void testObtenerPorCliente() {
        assertNotNull(
                assertDoesNotThrow(() ->
                        notificacionDAO.obtenerPorCliente(new ObjectId().toHexString())
                )
        );
    }

    @Test
    void testObtenerTodas() {
        assertNotNull(
                assertDoesNotThrow(() -> notificacionDAO.obtenerTodas())
        );
    }
    
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package gym.persistencia.daoTest;

import gym.persistencia.dao.ClienteDAO;
import gym.persistencia.dao.NotificacionDAO;
import gym.persistencia.entidades.Cliente;
import gym.persistencia.entidades.Notificacion;
import gym.persistencia.excepciones.PersistenciaException;
import java.time.LocalDate;
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
public class NotificacionDAOTest {
    
    private NotificacionDAO notificacionDAO;
    private ClienteDAO clienteDAO;

    @BeforeEach
    void setUp() {
        notificacionDAO = new NotificacionDAO();
        clienteDAO = new ClienteDAO();
    }

    @Test
    void testGuardar() throws PersistenciaException  {
        String id = new ObjectId().toString();

        Notificacion notificacion = new Notificacion(
                id, "Aviso", "Prueba de notificación",
                LocalDateTime.now(), null);

        notificacionDAO.guardar(notificacion);

        List<Notificacion> resultado = notificacionDAO.obtenerTodas();

        assertTrue(resultado.stream()
                .anyMatch(n -> id.equals(n.getIdNotificacion())));
    }

    @Test
    void testObtenerPorCliente() throws PersistenciaException {
        String idCliente = new ObjectId().toString();
        String idNotificacion = new ObjectId().toString();

        Cliente cliente = new Cliente(
                idCliente, "Carlos", "Lopez",
                "6441234567", LocalDate.now());

        clienteDAO.guardar(cliente);

        Notificacion notificacion = new Notificacion(
                idNotificacion, "Aviso", "Prueba de notificación",
                LocalDateTime.now(), null, cliente);

        notificacionDAO.guardar(notificacion);

        List<Notificacion> resultado =
                notificacionDAO.obtenerPorCliente(idCliente);

        assertNotNull(resultado);
        assertTrue(resultado.stream()
                .anyMatch(n -> idNotificacion.equals(n.getIdNotificacion())));
    }

    @Test
    void testObtenerTodas() throws PersistenciaException {
        List<Notificacion> resultado = notificacionDAO.obtenerTodas();

        assertNotNull(resultado);
    }
   
}

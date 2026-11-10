/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package gym.persistencia.daoTest;

import gym.persistencia.dao.InscripcionDAO;
import gym.persistencia.entidades.Inscripcion;
import gym.persistencia.excepciones.PersistenciaException;
import java.time.LocalDate;
import java.util.List;
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
    void testGuardar() throws PersistenciaException {
        String id = new ObjectId().toString();

        Inscripcion inscripcion =
                new Inscripcion(id, LocalDate.now(), null);
        
        Inscripcion inscripcion2 =
                new Inscripcion(id, LocalDate.now(), null);

        inscripcionDAO.guardar(inscripcion);

        List<Inscripcion> resultado = inscripcionDAO.obtenerTodos();

        assertTrue(resultado.stream()
                .anyMatch(i -> id.equals(i.getIdInscripcion())));

    }

    @Test
    void testActualizar() throws PersistenciaException  {
        String id = new ObjectId().toString();

        Inscripcion inscripcion =
                new Inscripcion(id, LocalDate.now(), null);

        inscripcionDAO.guardar(inscripcion);

        inscripcion.setFecha(LocalDate.now().plusDays(1));
        inscripcionDAO.actualizar(inscripcion);

        List<Inscripcion> resultado = inscripcionDAO.obtenerTodos();

        assertTrue(resultado.stream().anyMatch(i ->
                id.equals(i.getIdInscripcion())
                && i.getFecha().equals(inscripcion.getFecha())));

    }

    @Test
    void testEliminar() throws PersistenciaException {
        String id = new ObjectId().toString();

        Inscripcion inscripcion =
                new Inscripcion(id, LocalDate.now(), null);

        inscripcionDAO.guardar(inscripcion);
        inscripcionDAO.eliminar(id);

        List<Inscripcion> resultado = inscripcionDAO.obtenerTodos();

        assertFalse(resultado.stream()
                .anyMatch(i -> id.equals(i.getIdInscripcion())));
    }

    @Test
    void testObtenerTodos() throws PersistenciaException {
        List<Inscripcion> resultado = inscripcionDAO.obtenerTodos();

        assertNotNull(resultado);
    }

    
    
}

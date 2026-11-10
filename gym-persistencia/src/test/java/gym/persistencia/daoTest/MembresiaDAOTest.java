/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package gym.persistencia.daoTest;

import gym.persistencia.dao.MembresiaDAO;
import gym.persistencia.entidades.Membresia;
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
public class MembresiaDAOTest {
    
    private MembresiaDAO membresiaDAO;

    @BeforeEach
    public void init() {
        membresiaDAO = new MembresiaDAO();
    }

    @Test
    public void testGuardar() throws PersistenciaException {
        String id = new ObjectId().toString();

        Membresia membresia = new Membresia(
                id,
                "Mensual",
                500.00,
                "mensual.png",
                null,
                LocalDate.now(),
                LocalDate.now().plusMonths(1)
        );

        membresiaDAO.guardar(membresia);

        List<Membresia> resultado = membresiaDAO.obtenerTodos();

        assertNotNull(resultado);
        assertTrue(resultado.stream().anyMatch(
                m -> id.equals(m.getIdMembresia())
        ));

        membresiaDAO.eliminar(id);
    }

    @Test
    public void testObtenerTodos() throws PersistenciaException {
        List<Membresia> membresias = membresiaDAO.obtenerTodos();

        assertNotNull(membresias);
    }

    @Test
    public void testActualizar() throws PersistenciaException {
        String id = new ObjectId().toString();

        Membresia membresia = new Membresia(
                id,
                "Mensual",
                500.00,
                "mensual.png",
                null,
                LocalDate.now(),
                LocalDate.now().plusMonths(1)
        );

        membresiaDAO.guardar(membresia);

        membresia.setNombre("Anual");
        membresia.setPrecio(5000.00);

        membresiaDAO.actualizar(membresia);

        List<Membresia> resultado = membresiaDAO.obtenerTodos();

        assertTrue(resultado.stream().anyMatch(
                m -> id.equals(m.getIdMembresia())
                        && "Anual".equals(m.getNombre())
                        && m.getPrecio() == 5000.00
        ));

        membresiaDAO.eliminar(id);
    }

    @Test
    public void testEliminar() throws PersistenciaException {
        String id = new ObjectId().toString();

        Membresia membresia = new Membresia(
                id,
                "Temporal",
                300.00,
                "temporal.png",
                null,
                LocalDate.now(),
                LocalDate.now().plusMonths(1)
        );

        membresiaDAO.guardar(membresia);
        membresiaDAO.eliminar(id);

        List<Membresia> resultado = membresiaDAO.obtenerTodos();

        assertFalse(resultado.stream().anyMatch(
                m -> id.equals(m.getIdMembresia())
        ));
    }
}

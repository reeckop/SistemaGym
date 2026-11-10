/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package gym.persistencia.daoTest;

import gym.persistencia.dao.CursoDAO;
import gym.persistencia.entidades.Curso;
import gym.persistencia.excepciones.PersistenciaException;
import java.util.List;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;

/**
 *
 * @author PC GAMER MASTER RACE
 */
public class CursoDAOTest {
    
    private CursoDAO cursoDAO;

    @BeforeEach
    void setUp() {
        cursoDAO = new CursoDAO();
    }

    @Test
    void testGuardar() throws PersistenciaException  {
        String id = new ObjectId().toString();

        Curso curso = new Curso(id, "Yoga", 500.00, 20, "yoga.png");

        cursoDAO.guardar(curso);

        List<Curso> resultado = cursoDAO.obtenerTodos();

        assertTrue(resultado.stream()
                .anyMatch(c -> id.equals(c.getIdCurso())));

    }

    @Test
    void testActualizar() throws PersistenciaException {
        String id = new ObjectId().toString();

        Curso curso = new Curso(id, "Yoga", 500.00, 20, "yoga.png");

        cursoDAO.guardar(curso);

        curso.setNombre("Pilates");
        curso.setPrecio(600.00);

        cursoDAO.actualizar(curso);

        List<Curso> resultado = cursoDAO.obtenerTodos();

        assertTrue(resultado.stream().anyMatch(c ->
                id.equals(c.getIdCurso())
                && "Pilates".equals(c.getNombre())
                && c.getPrecio() == 600.00));

    }

    @Test
    void testEliminar() throws PersistenciaException {
        String id = new ObjectId().toString();

        Curso curso = new Curso(id, "Yoga", 500.00, 20, "yoga.png");

        cursoDAO.guardar(curso);
        cursoDAO.eliminar(id);

        List<Curso> resultado = cursoDAO.obtenerTodos();

        assertFalse(resultado.stream()
                .anyMatch(c -> id.equals(c.getIdCurso())));
    }

    @Test
    void testObtenerTodos() throws PersistenciaException {
        List<Curso> resultado = cursoDAO.obtenerTodos();

        assertNotNull(resultado);
    }

    
    
}

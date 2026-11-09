/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package gym.persistencia.daoTest;

import gym.persistencia.dao.CursoDAO;
import gym.persistencia.entidades.Curso;
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
    void testGuardar() {
        Curso curso = new Curso(
                new ObjectId().toHexString(),
                "Curso de prueba",
                "Fitness",
                500.0,
                20,
                "imagen.jpg"
        );

        assertDoesNotThrow(() -> cursoDAO.guardar(curso));
    }

    @Test
    void testActualizar() {
        Curso curso = new Curso(
                new ObjectId().toHexString(),
                "Curso para actualizar",
                "Fitness",
                500.0,
                20,
                "imagen.jpg"
        );

        assertDoesNotThrow(() -> {
            cursoDAO.guardar(curso);
            curso.setNombre("Curso actualizado");
            cursoDAO.actualizar(curso);
        });
    }

    @Test
    void testEliminar() {
        Curso curso = new Curso(
                new ObjectId().toHexString(),
                "Curso para eliminar",
                "Fitness",
                500.0,
                20,
                "imagen.jpg"
        );

        assertDoesNotThrow(() -> {
            cursoDAO.guardar(curso);
            cursoDAO.eliminar(curso.getIdCurso());
        });
    }

    @Test
    void testObtenerTodos() {
        assertNotNull(
                assertDoesNotThrow(() -> cursoDAO.obtenerTodos())
        );
    }
    
}

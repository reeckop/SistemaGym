/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package gym.persistencia.daoTest;

import gym.persistencia.dao.ClienteDAO;
import gym.persistencia.entidades.Cliente;
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
public class ClienteDAOTest {
    
    private ClienteDAO clienteDAO;

    @BeforeEach
    public void init() {
        clienteDAO = new ClienteDAO();
    }
    
     @Test
    public void testGuardar() throws PersistenciaException {
        String id = new ObjectId().toString();

        Cliente cliente = new Cliente(
                id,
                "Carlos",
                "Lopez",
                "6441234567",
                LocalDate.now()
        );

        clienteDAO.guardar(cliente);

        Cliente resultado = clienteDAO.obtenerPorId(id);

        assertNotNull(resultado);
        assertEquals("Carlos", resultado.getNombre());
    }

    @Test
    public void testObtenerPorId() throws PersistenciaException {
        String id = new ObjectId().toString();

        Cliente cliente = new Cliente(
                id,
                "Juan",
                "Perez",
                "6449876543",
                LocalDate.now()
        );

        clienteDAO.guardar(cliente);

        Cliente resultado = clienteDAO.obtenerPorId(id);

        assertNotNull(resultado);
        assertEquals(id, resultado.getIdCliente());

    }

    @Test
    public void testObtenerTodos() throws PersistenciaException {
        List<Cliente> clientes = clienteDAO.obtenerTodos();
        assertNotNull(clientes);
    }

    @Test
    public void testActualizar() throws PersistenciaException {
        String id = new ObjectId().toString();
        Cliente cliente = new Cliente(
                id,
                "Pedro",
                "Garcia",
                "6441112233",
                LocalDate.now()
        );

        clienteDAO.guardar(cliente);

        cliente.setNombre("Pedro Actualizado");

        clienteDAO.actualizar(cliente);

        Cliente resultado = clienteDAO.obtenerPorId(id);

        assertNotNull(resultado);
        assertEquals("Pedro Actualizado", resultado.getNombre());
    }
}

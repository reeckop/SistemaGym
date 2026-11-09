/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package gym.persistencia.daoTest;

import gym.persistencia.dao.ClienteDAO;
import gym.persistencia.entidades.Cliente;
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
    public void testGuardarClienteFuncionaOk() {

        Cliente cliente = new Cliente(
                new ObjectId().toHexString(),
                "Luis",
                "Garcia",
                "6441234567",
                LocalDate.of(2004, 5, 20)
        );

        assertDoesNotThrow(() -> {
            clienteDAO.guardar(cliente);

            Cliente encontrado =
                    clienteDAO.obtenerPorId(cliente.getIdCliente());

            assertNotNull(encontrado);
            assertEquals(cliente.getIdCliente(), encontrado.getIdCliente());
        });
    }

    @Test
    public void testActualizarClienteFuncionaOk() {

        Cliente cliente = new Cliente(
                new ObjectId().toHexString(),
                "Luis",
                "Garcia",
                "6441234567",
                LocalDate.now()
        );

        assertDoesNotThrow(() -> {
            clienteDAO.guardar(cliente);

            cliente.setNombre("Luis Alberto");
            clienteDAO.actualizar(cliente);

            Cliente actualizado =
                    clienteDAO.obtenerPorId(cliente.getIdCliente());

            assertNotNull(actualizado);
            assertEquals("Luis Alberto", actualizado.getNombre());
        });
    }

    @Test
    public void testObtenerPorIdFuncionaOk() {

        Cliente cliente = new Cliente(
                new ObjectId().toHexString(),
                "Ana",
                "Lopez",
                "6449876543",
                LocalDate.now()
        );

        assertDoesNotThrow(() -> {
            clienteDAO.guardar(cliente);

            Cliente encontrado =
                    clienteDAO.obtenerPorId(cliente.getIdCliente());

            assertNotNull(encontrado);
            assertEquals(cliente.getIdCliente(), encontrado.getIdCliente());
        });
    }

    @Test
    public void testObtenerTodosFuncionaOk() {

        Cliente cliente = new Cliente(
                new ObjectId().toHexString(),
                "Carlos",
                "Perez",
                "6441112233",
                LocalDate.now()
        );

        assertDoesNotThrow(() -> {
            clienteDAO.guardar(cliente);

            List<Cliente> clientes = clienteDAO.obtenerTodos();

            assertNotNull(clientes);
            assertTrue(clientes.stream().anyMatch(
                    c -> cliente.getIdCliente().equals(c.getIdCliente())
            ));
        });
    }
}

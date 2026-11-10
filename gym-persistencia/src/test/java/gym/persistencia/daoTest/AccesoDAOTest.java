/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package gym.persistencia.daoTest;

import gym.persistencia.dao.AccesoDAO;
import gym.persistencia.dao.ClienteDAO;
import gym.persistencia.entidades.Cliente;
import gym.persistencia.entidades.RegistroIngreso;
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
public class AccesoDAOTest {
    
    private AccesoDAO accesoDAO;
    private ClienteDAO clienteDAO;

    @BeforeEach
    public void init() {
        accesoDAO = new AccesoDAO();
        clienteDAO = new ClienteDAO();
    }

    @Test
    public void testGuardar() throws PersistenciaException {
        String idCliente = new ObjectId().toString();

        Cliente cliente = new Cliente(
                idCliente,
                "Carlos",
                "Lopez",
                "6441234567",
                LocalDate.now()
        );

        clienteDAO.guardar(cliente);

        RegistroIngreso registro = new RegistroIngreso(
                new ObjectId().toString(),
                LocalDateTime.now(),
                null,
                null,
                cliente
        );

        accesoDAO.guardar(registro);

        List<RegistroIngreso> resultado =
                accesoDAO.obtenerPorCliente(idCliente);

        assertNotNull(resultado);
        assertFalse(resultado.isEmpty());
    }

    @Test
    public void testObtenerPorCliente() throws PersistenciaException {
        String idCliente = new ObjectId().toString();

        Cliente cliente = new Cliente(
                idCliente,
                "Juan",
                "Perez",
                "6449876543",
                LocalDate.now()
        );

        clienteDAO.guardar(cliente);

        RegistroIngreso registro = new RegistroIngreso(
                new ObjectId().toString(),
                LocalDateTime.now(),
                null,
                null,
                cliente
        );

        accesoDAO.guardar(registro);

        List<RegistroIngreso> resultado =
                accesoDAO.obtenerPorCliente(idCliente);

        assertNotNull(resultado);
        assertFalse(resultado.isEmpty());
        assertEquals(idCliente,
                resultado.get(0).getCliente().getIdCliente());
    }

    @Test
    public void testObtenerTodos() throws PersistenciaException {
        List<RegistroIngreso> registros = accesoDAO.obtenerTodos();

        assertNotNull(registros);
    }

    
}
    

package gym.persistencia.entidades;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;

class EntidadesTest {

    @Test
    void testCalculoVentaYDetalle() {
        Producto p1 = new Producto(1, "Proteina Whey", "Suplementos", 500.0, "whey.png");
        Producto p2 = new Producto(2, "Bebida Isotonica", "Bebidas", 50.0, "bebida.png");

        Venta venta = new Venta(100, LocalDateTime.now(), 0, 0, 0);
        venta.agregarProducto(p1, 2); // 1000.0
        venta.agregarProducto(p2, 3); // 150.0

        assertEquals(1150.0, venta.getSubtotal(), 0.01);
        assertEquals(1150.0, venta.getTotal(), 0.01);

        // Cliente con membresia y descuento del 10%
        Beneficio beneficio = new Beneficio(1, "10% en Suplementos", 10.0, "DESCUENTO");
        Membresia membresia = new Membresia(1, "VIP", 800.0);
        membresia.agregarBeneficio(beneficio);

        Cliente cliente = new Cliente(1, "Carlos", "Perez", "6441234567", LocalDate.now(), membresia);
        venta.setCliente(cliente);
        venta.calcularTotal();

        assertEquals(115.0, venta.getDescuento(), 0.01);
        assertEquals(1035.0, venta.getTotal(), 0.01);
    }

    @Test
    void testHorarioYCurso() {
        Horario h1 = new Horario(1, LocalDate.now(), LocalTime.of(8, 0), LocalTime.of(9, 30));
        assertTrue(h1.verificarDisponibilidad());

        Curso curso = new Curso(1, "Spinning Matutino", 300.0, 15, "spinning.png");
        curso.agregarHorario(h1);

        assertTrue(curso.verificarCupo());
        assertEquals(1, curso.consultarHorarios().size());
    }

    @Test
    void testRegistroIngreso() {
        RegistroIngreso acceso = new RegistroIngreso(1, null, null, "PERMITIDO");
        assertTrue(acceso.validarAcceso());

        acceso.registrarEntrada();
        assertEquals("DENTRO", acceso.getEstado());
        assertNotNull(acceso.getFechaHoraEntrada());

        acceso.registrarSalida();
        assertEquals("SALIDO", acceso.getEstado());
        assertNotNull(acceso.getFechaHoraSalida());
    }
}

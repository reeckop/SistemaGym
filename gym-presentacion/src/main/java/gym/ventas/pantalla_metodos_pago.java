package gym.ventas;

import gym.excepciones.NegocioException;
import gym.navegacion.ControlNavegacion;
import gym.servicios.dto.MetodoPagoDTO;
import gym.servicios.dto.PagoDTO;
import gym.servicios.dto.DetalleVentaDTO;
import gym.servicios.enums.EstadoPago;
import gym.servicios.interfaces.IVentaServicios;
import gym.servicios.pagos.ResultadoPago;
import gym.servicios.pagos.StripeSimulado;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.time.LocalDateTime;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import org.bson.types.ObjectId;

/**
 *  Selección del método y registro del pago de la venta.
 *
 * @author PC GAMER MASTER RACE
 */
public class pantalla_metodos_pago extends JFrame {
    private final StripeSimulado pasarelaPago = new StripeSimulado();
    private final pantalla_ventas_carrito pantallaCarrito;
    private final pantalla_ventas_principal pantallaPrincipal;
    private final IVentaServicios ventaServicios;
    private final CarritoVentas carrito;
    private final String idCliente;
    private boolean procesando;

    public pantalla_metodos_pago(pantalla_ventas_carrito pantallaCarrito,
            pantalla_ventas_principal pantallaPrincipal, IVentaServicios ventaServicios,
            CarritoVentas carrito, String idCliente) {
        this.pantallaCarrito = pantallaCarrito;
        this.pantallaPrincipal = pantallaPrincipal;
        this.ventaServicios = ventaServicios;
        this.carrito = carrito;
        this.idCliente = idCliente;
        construirPantalla();
    }

    private void construirPantalla() {
        setTitle("Métodos de pago | Fit Life Gym");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(680, 410);
        setMinimumSize(new Dimension(580, 360));
        setLocationRelativeTo(pantallaCarrito);
        getContentPane().setBackground(pantalla_ventas_principal.FONDO);
        setLayout(new BorderLayout(12, 12));

        JLabel titulo = new JLabel("MÉTODOS DE PAGO", JLabel.CENTER);
        titulo.setForeground(Color.WHITE);
        titulo.setFont(new Font("Arial", Font.BOLD, 28));
        titulo.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, pantalla_ventas_principal.ROJO));
        add(titulo, BorderLayout.NORTH);

        JPanel metodos = new JPanel(new GridLayout(1, 2, 26, 12));
        metodos.setBackground(pantalla_ventas_principal.FONDO);
        metodos.setBorder(BorderFactory.createEmptyBorder(48, 54, 38, 54));
        JButton efectivo = pantalla_ventas_principal.boton("💵  Efectivo");
        efectivo.setFont(new Font("Arial", Font.BOLD, 21));
        efectivo.setBackground(new Color(85, 85, 85));
        efectivo.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evento) {
                procesarPago("Efectivo");
            }
        });
        JButton tarjeta = pantalla_ventas_principal.boton("💳  Tarjeta (simular Stripe)");
        tarjeta.setFont(new Font("Arial", Font.BOLD, 21));
        tarjeta.setBackground(new Color(85, 85, 85));
        tarjeta.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evento) {
                procesarPago("Tarjeta");
            }
        });
        metodos.add(efectivo);
        metodos.add(tarjeta);
        add(metodos, BorderLayout.CENTER);

        JPanel abajo = new JPanel(new BorderLayout());
        abajo.setBackground(pantalla_ventas_principal.FONDO);
        JLabel monto = new JLabel(String.format(java.util.Locale.US, "Total a pagar: $%.2f", carrito.obtenerTotal()));
        monto.setForeground(Color.WHITE);
        monto.setFont(new Font("Arial", Font.BOLD, 18));
        monto.setBorder(BorderFactory.createEmptyBorder(0, 24, 12, 0));
        JButton volver = pantalla_ventas_principal.boton("Volver");
        volver.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evento) {
                ControlNavegacion.obtenerInstancia().regresar(pantalla_metodos_pago.this, pantallaCarrito);
            }
        });
        JPanel derecha = new JPanel();
        derecha.setBackground(pantalla_ventas_principal.FONDO);
        derecha.add(volver);
        derecha.setBorder(BorderFactory.createEmptyBorder(0, 0, 12, 20));
        abajo.add(monto, BorderLayout.WEST);
        abajo.add(derecha, BorderLayout.EAST);
        add(abajo, BorderLayout.SOUTH);
    }

    private void procesarPago(String nombreMetodo) {
        if (procesando) return;
        procesando = true;
        try {
            boolean simularAprobacion = true;
            if ("Tarjeta".equals(nombreMetodo)) {
                Object[] opciones = {"Simular aprobación", "Simular rechazo", "Cancelar"};
                int resultado = JOptionPane.showOptionDialog(this,
                        "Stripe está en modo simulado; no se realizará ningún cobro real.",
                        "Simulación de tarjeta", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE,
                        null, opciones, opciones[0]);
                if (resultado == 2 || resultado == JOptionPane.CLOSED_OPTION) {
                    procesando = false;
                    return;
                }
                simularAprobacion = resultado == 0;
            }
            ResultadoPago resultadoPago = pasarelaPago.procesar(carrito.obtenerTotal(), simularAprobacion);
            if (resultadoPago == ResultadoPago.RECHAZADO) {
                procesando = false;
                JOptionPane.showMessageDialog(this,
                        "Pago rechazado por Stripe (simulación). No se registró la venta ni se modificó el stock.",
                        "Pago rechazado", JOptionPane.WARNING_MESSAGE);
                return;
            }

            PagoDTO pago = new PagoDTO();
            pago.setIdPago(new ObjectId().toHexString());
            pago.setMonto(carrito.obtenerTotal());
            pago.setFechaHora(LocalDateTime.now());
            pago.setEstado(EstadoPago.COMPLETADO);
            pago.setMetodoPago(new MetodoPagoDTO(new ObjectId().toHexString(), nombreMetodo));
            java.util.List<DetalleVentaDTO> detalles = new java.util.ArrayList<>();
            for (CarritoVentas.Item item : carrito.obtenerItems()) {
                detalles.add(new DetalleVentaDTO(item.getProducto(), item.getCantidad(),
                        item.getProducto().getPrecio(), item.obtenerSubtotal()));
            }
            ventaServicios.procesarVenta(idCliente == null || idCliente.isBlank() ? null : idCliente, detalles, pago);

            JOptionPane.showMessageDialog(this,
                    "¡Pago registrado correctamente!\nTotal: " + String.format(java.util.Locale.US, "$%.2f", carrito.obtenerTotal()),
                    "Venta completada", JOptionPane.INFORMATION_MESSAGE);
            pantallaPrincipal.finalizarFlujo(pantallaCarrito, this);
        } catch (NegocioException e) {
            procesando = false;
            JOptionPane.showMessageDialog(this, e.getMessage(), "No se pudo completar la venta", JOptionPane.ERROR_MESSAGE);
        } catch (RuntimeException e) {
            procesando = false;
            JOptionPane.showMessageDialog(this, "Ocurrió un error al procesar la venta: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}

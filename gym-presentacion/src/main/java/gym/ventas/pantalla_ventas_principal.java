package gym.ventas;

import gym.navegacion.ControlNavegacion;
import gym.servicios.interfaces.IClienteServicios;
import gym.servicios.interfaces.IProductoServicios;
import gym.servicios.interfaces.IVentaServicios;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *  Selector de categoría para el flujo de venta.
 *
 * @author PC GAMER MASTER RACE
 */
public class pantalla_ventas_principal extends JFrame {
    static final Color FONDO = new Color(17, 17, 17);
    static final Color ROJO = new Color(225, 0, 0);
    static final Color TEXTO = Color.WHITE;

    private final JFrame pantallaAnterior;
    private final IProductoServicios productoServicios;
    private final IClienteServicios clienteServicios;
    private final IVentaServicios ventaServicios;
    private final CarritoVentas carrito = new CarritoVentas();
    private JButton carritoBoton;

    public pantalla_ventas_principal(JFrame pantallaAnterior, IProductoServicios productoServicios,
            IVentaServicios ventaServicios, IClienteServicios clienteServicios) {
        this.pantallaAnterior = pantallaAnterior;
        this.productoServicios = productoServicios;
        this.ventaServicios = ventaServicios;
        this.clienteServicios = clienteServicios;
        construirPantalla();
    }

    private void construirPantalla() {
        setTitle("Ventas | Fit Life Gym");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setMinimumSize(new Dimension(900, 560));
        setSize(1060, 650);
        setLocationRelativeTo(pantallaAnterior);
        getContentPane().setBackground(FONDO);
        setLayout(new BorderLayout(18, 18));

        JLabel titulo = new JLabel("VENTAS", JLabel.CENTER);
        titulo.setForeground(TEXTO);
        titulo.setFont(new Font("Arial", Font.BOLD, 32));
        titulo.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, ROJO));
        add(titulo, BorderLayout.NORTH);

        JPanel categorias = new JPanel(new GridLayout(1, 3, 24, 12));
        categorias.setBackground(FONDO);
        categorias.setBorder(BorderFactory.createEmptyBorder(20, 24, 8, 24));
        categorias.add(crearTarjeta("Accesorios", "mancuernas.png", "ACCESORIOS"));
        categorias.add(crearTarjeta("Suplementos", "whey.png", "SUPLEMENTOS"));
        categorias.add(crearTarjeta("Ropa", "playera.png", "ROPA"));
        add(categorias, BorderLayout.CENTER);

        JPanel acciones = new JPanel();
        acciones.setBackground(FONDO);
        JButton volver = boton("Volver");
        volver.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evento) {
                ControlNavegacion.obtenerInstancia().regresar(pantalla_ventas_principal.this, pantallaAnterior);
            }
        });
        carritoBoton = boton("Carrito (" + carrito.obtenerCantidadTotal() + ")");
        carritoBoton.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evento) {
                abrirCarrito();
            }
        });
        acciones.add(volver);
        acciones.add(carritoBoton);
        acciones.setBorder(BorderFactory.createEmptyBorder(0, 0, 14, 0));
        add(acciones, BorderLayout.SOUTH);
    }

    private JPanel crearTarjeta(String categoria, String archivoImagen, String nombre) {
        JPanel tarjeta = new JPanel(new BorderLayout(8, 12));
        tarjeta.setBackground(new Color(28, 28, 28));
        tarjeta.setBorder(BorderFactory.createEmptyBorder(16, 16, 18, 16));
        JLabel encabezado = new JLabel(categoria, JLabel.CENTER);
        encabezado.setForeground(TEXTO);
        encabezado.setFont(new Font("Arial", Font.BOLD, 22));
        JPanel imagen = ImagenesVentas.crearPanel(
                "/gym/ventas/productos/" + archivoImagen, FONDO);
        imagen.setPreferredSize(new Dimension(300, 320));
        tarjeta.add(encabezado, BorderLayout.NORTH);
        tarjeta.add(imagen, BorderLayout.CENTER);
        JButton seleccionar = boton("SELECCIONAR");
        seleccionar.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evento) {
                pantalla_ventas_accesorios pantalla = new pantalla_ventas_accesorios(
                        pantalla_ventas_principal.this, categoria,
                        productoServicios, ventaServicios, carrito);
                ControlNavegacion.obtenerInstancia().abrir(pantalla_ventas_principal.this, pantalla);
            }
        });
        tarjeta.add(seleccionar, BorderLayout.SOUTH);
        return tarjeta;
    }

    void abrirCarrito() {
        actualizarCantidadCarrito();
        pantalla_ventas_carrito pantalla = new pantalla_ventas_carrito(
                this, carrito, ventaServicios, clienteServicios);
        ControlNavegacion.obtenerInstancia().abrir(this, pantalla);
    }

    void actualizarCantidadCarrito() {
        if (carritoBoton != null) carritoBoton.setText("Carrito (" + carrito.obtenerCantidadTotal() + ")");
    }

    IClienteServicios obtenerClienteServicios() {
        return clienteServicios;
    }

    void finalizarFlujo(JFrame carritoPantalla, JFrame pagoPantalla) {
        if (pagoPantalla != null) pagoPantalla.dispose();
        if (carritoPantalla != null) carritoPantalla.dispose();
        ControlNavegacion.obtenerInstancia().regresar(this, pantallaAnterior);
    }

    static JButton boton(String texto) {
        JButton boton = new JButton(texto);
        boton.setBackground(ROJO);
        boton.setForeground(Color.WHITE);
        boton.setFocusPainted(false);
        boton.setFont(new Font("Arial", Font.BOLD, 13));
        return boton;
    }
}

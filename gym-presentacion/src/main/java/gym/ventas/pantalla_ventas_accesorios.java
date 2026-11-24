package gym.ventas;

import gym.excepciones.NegocioException;
import gym.navegacion.ControlNavegacion;
import gym.servicios.dto.ProductoDTO;
import gym.servicios.interfaces.IProductoServicios;
import gym.servicios.interfaces.IVentaServicios;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

/**
 *  Catálogo filtrado por categoría con opción para agregar productos.
 *
 * @author PC GAMER MASTER RACE
 */
public class pantalla_ventas_accesorios extends javax.swing.JFrame {
    private final pantalla_ventas_principal pantallaPrincipal;
    private final String categoria;
    private final IProductoServicios productoServicios;
    private final IVentaServicios ventaServicios;
    private final CarritoVentas carrito;
    private JPanel productos;
    private javax.swing.JButton carritoBoton;

    public pantalla_ventas_accesorios(pantalla_ventas_principal pantallaPrincipal, String categoria,
            IProductoServicios productoServicios, IVentaServicios ventaServicios, CarritoVentas carrito) {
        this.pantallaPrincipal = pantallaPrincipal;
        this.categoria = categoria;
        this.productoServicios = productoServicios;
        this.ventaServicios = ventaServicios;
        this.carrito = carrito;
        construirPantalla();
        cargarProductos();
    }

    private void construirPantalla() {
        setTitle(categoria + " | Ventas");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setMinimumSize(new Dimension(850, 560));
        setSize(1000, 680);
        setLocationRelativeTo(pantallaPrincipal);
        getContentPane().setBackground(pantalla_ventas_principal.FONDO);
        setLayout(new BorderLayout(10, 10));

        JLabel titulo = new JLabel(categoria.toUpperCase(), JLabel.CENTER);
        titulo.setForeground(Color.WHITE);
        titulo.setFont(new Font("Arial", Font.BOLD, 28));
        titulo.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, pantalla_ventas_principal.ROJO));
        add(titulo, BorderLayout.NORTH);

        productos = new JPanel(new GridLayout(0, 3, 16, 16));
        productos.setBackground(pantalla_ventas_principal.FONDO);
        productos.setBorder(BorderFactory.createEmptyBorder(18, 18, 18, 18));
        JScrollPane scroll = new JScrollPane(productos);
        scroll.getViewport().setBackground(pantalla_ventas_principal.FONDO);
        scroll.setBorder(BorderFactory.createEmptyBorder());
        add(scroll, BorderLayout.CENTER);

        JPanel acciones = new JPanel();
        acciones.setBackground(pantalla_ventas_principal.FONDO);
        JButton volver = pantalla_ventas_principal.boton("Volver a categorías");
        volver.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evento) {
                ControlNavegacion.obtenerInstancia().regresar(pantalla_ventas_accesorios.this, pantallaPrincipal);
            }
        });
        carritoBoton = pantalla_ventas_principal.boton("Carrito (" + carrito.obtenerCantidadTotal() + ")");
        carritoBoton.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evento) {
                abrirCarrito();
            }
        });
        acciones.add(volver);
        acciones.add(carritoBoton);
        acciones.setBorder(BorderFactory.createEmptyBorder(0, 0, 12, 0));
        add(acciones, BorderLayout.SOUTH);
    }

    private void cargarProductos() {
        try {
            List<ProductoDTO> catalogo = productoServicios.consultarTodosLosProductos();
            List<ProductoDTO> filtrados = new ArrayList<>();
            for (ProductoDTO producto : catalogo) {
                if (categoriaCoincide(producto.getCategoria())) filtrados.add(producto);
            }
            if (filtrados.isEmpty()) {
                JLabel vacio = new JLabel("No hay productos disponibles en esta categoría.", JLabel.CENTER);
                vacio.setForeground(Color.LIGHT_GRAY);
                vacio.setFont(new Font("Arial", Font.PLAIN, 17));
                productos.setLayout(new BorderLayout());
                productos.add(vacio, BorderLayout.CENTER);
            } else {
                int columnas = Math.min(3, filtrados.size());
                productos.setLayout(new GridLayout(0, columnas, 16, 16));
                for (ProductoDTO producto : filtrados) productos.add(crearTarjeta(producto));
            }
        } catch (NegocioException e) {
            JLabel error = new JLabel("No se pudo cargar el catálogo: " + e.getMessage(), JLabel.CENTER);
            error.setForeground(Color.WHITE);
            productos.setLayout(new BorderLayout());
            productos.add(error, BorderLayout.CENTER);
        }
    }

    private boolean categoriaCoincide(String categoriaProducto) {
        return ImagenesVentas.imagenCategoria(categoriaProducto).equals(ImagenesVentas.imagenCategoria(categoria));
    }

    private JPanel crearTarjeta(ProductoDTO producto) {
        JPanel tarjeta = new JPanel(new BorderLayout(8, 7));
        tarjeta.setBackground(new Color(29, 29, 29));
        tarjeta.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(70, 70, 70)),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)));
        JPanel imagen = ImagenesVentas.crearPanel(producto, tarjeta.getBackground());
        imagen.setPreferredSize(new Dimension(300, 260));
        JLabel nombre = new JLabel(producto.getNombre(), JLabel.CENTER);
        nombre.setForeground(Color.WHITE);
        nombre.setFont(new Font("Arial", Font.BOLD, 15));
        JLabel precio = new JLabel(String.format(java.util.Locale.US, "$%.2f", producto.getPrecio()), JLabel.CENTER);
        precio.setForeground(Color.WHITE);
        JSpinner cantidad = new JSpinner(new SpinnerNumberModel(1, 1, Math.max(producto.getStock(), 1), 1));
        cantidad.setEnabled(producto.getStock() > 0);
        JButton agregar = pantalla_ventas_principal.boton(producto.getStock() > 0 ? "Agregar" : "Agotado");
        agregar.setEnabled(producto.getStock() > 0);
        agregar.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evento) {
                agregarProducto(producto, (Integer) cantidad.getValue());
            }
        });
        JPanel acciones = new JPanel(new BorderLayout(6, 6));
        acciones.setBackground(tarjeta.getBackground());
        acciones.add(precio, BorderLayout.NORTH);
        acciones.add(cantidad, BorderLayout.CENTER);
        acciones.add(agregar, BorderLayout.SOUTH);
        tarjeta.add(nombre, BorderLayout.NORTH);
        tarjeta.add(imagen, BorderLayout.CENTER);
        tarjeta.add(acciones, BorderLayout.SOUTH);
        return tarjeta;
    }

    private void agregarProducto(ProductoDTO producto, int cantidad) {
        int enCarrito = contarUnidadesEnCarrito(producto);
        if (enCarrito + cantidad > producto.getStock()) {
            javax.swing.JOptionPane.showMessageDialog(this,
                    "La cantidad supera las existencias disponibles (" + producto.getStock() + ").",
                    "Stock insuficiente", javax.swing.JOptionPane.WARNING_MESSAGE);
            return;
        }
        carrito.agregar(producto, cantidad);
        carritoBoton.setText("Carrito (" + carrito.obtenerCantidadTotal() + ")");
        pantallaPrincipal.actualizarCantidadCarrito();
        pantalla_venta_accesorio_agregado confirmacion =
                new pantalla_venta_accesorio_agregado(this, producto, cantidad);
        ControlNavegacion.obtenerInstancia().abrir(this, confirmacion);
    }

    private int contarUnidadesEnCarrito(ProductoDTO producto) {
        int unidades = 0;
        for (CarritoVentas.Item item : carrito.obtenerItems()) {
            String idDelItem = item.getProducto().getIdProducto();
            if (idDelItem.equals(producto.getIdProducto())) {
                unidades += item.getCantidad();
            }
        }
        return unidades;
    }

    void abrirCarrito() {
        pantalla_ventas_carrito pantalla = new pantalla_ventas_carrito(
                pantallaPrincipal, carrito, ventaServicios, pantallaPrincipal.obtenerClienteServicios());
        ControlNavegacion.obtenerInstancia().reemplazar(this, pantalla);
    }
}

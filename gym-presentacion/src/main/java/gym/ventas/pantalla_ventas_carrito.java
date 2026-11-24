package gym.ventas;

import gym.navegacion.ControlNavegacion;
import gym.excepciones.NegocioException;
import gym.servicios.dto.ClienteDTO;
import gym.servicios.interfaces.IVentaServicios;
import gym.servicios.interfaces.IClienteServicios;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

/**
 *  Revisión de productos, cliente y total antes del pago.
 *
 * @author PC GAMER MASTER RACE
 */
public class pantalla_ventas_carrito extends JFrame {
    private final pantalla_ventas_principal pantallaPrincipal;
    private final CarritoVentas carrito;
    private final IVentaServicios ventaServicios;
    private final IClienteServicios clienteServicios;
    private final JComboBox<ClienteDTO> clientes = new JComboBox<>();
    private final JTextArea productos = new JTextArea(8, 30);
    private final JLabel descuento = new JLabel("$0.00");
    private final JLabel total = new JLabel();

    public pantalla_ventas_carrito(pantalla_ventas_principal pantallaPrincipal,
            CarritoVentas carrito, IVentaServicios ventaServicios,
            IClienteServicios clienteServicios) {
        this.pantallaPrincipal = pantallaPrincipal;
        this.carrito = carrito;
        this.ventaServicios = ventaServicios;
        this.clienteServicios = clienteServicios;
        construirPantalla();
        cargarClientes();
        actualizarResumen();
    }

    private void construirPantalla() {
        setTitle("Carrito | Fit Life Gym");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(700, 580);
        setMinimumSize(new Dimension(620, 520));
        setLocationRelativeTo(pantallaPrincipal);
        getContentPane().setBackground(pantalla_ventas_principal.FONDO);
        setLayout(new BorderLayout(12, 12));

        JLabel encabezado = new JLabel("Carrito", JLabel.CENTER);
        encabezado.setForeground(Color.WHITE);
        encabezado.setFont(new Font("Arial", Font.BOLD, 28));
        encabezado.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, pantalla_ventas_principal.ROJO));
        add(encabezado, BorderLayout.NORTH);

        JPanel contenido = new JPanel(new GridBagLayout());
        contenido.setBackground(pantalla_ventas_principal.FONDO);
        contenido.setBorder(BorderFactory.createEmptyBorder(12, 20, 12, 20));
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(7, 7, 7, 7);
        c.anchor = GridBagConstraints.WEST;
        c.gridx = 0;
        c.gridy = 0;
        JLabel clienteLabel = etiqueta("Cliente (opcional):");
        contenido.add(clienteLabel, c);
        c.gridx = 1;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 1;
        configurarComboClientes();
        contenido.add(clientes, c);

        c.gridx = 0;
        c.gridy = 1;
        c.fill = GridBagConstraints.NONE;
        c.weightx = 0;
        contenido.add(etiqueta("Productos"), c);
        c.gridx = 1;
        c.fill = GridBagConstraints.BOTH;
        c.weightx = 1;
        c.weighty = 1;
        productos.setEditable(false);
        productos.setLineWrap(true);
        productos.setWrapStyleWord(true);
        productos.setBackground(new Color(38, 38, 38));
        productos.setForeground(Color.WHITE);
        productos.setFont(new Font("Monospaced", Font.PLAIN, 14));
        JScrollPane lista = new JScrollPane(productos);
        lista.setPreferredSize(new Dimension(390, 220));
        contenido.add(lista, c);

        c.gridy = 2;
        c.gridx = 0;
        c.fill = GridBagConstraints.NONE;
        c.weighty = 0;
        contenido.add(etiqueta("Descuento:"), c);
        c.gridx = 1;
        c.fill = GridBagConstraints.HORIZONTAL;
        estilizarValor(descuento);
        contenido.add(descuento, c);

        c.gridy = 3;
        c.gridx = 0;
        contenido.add(etiqueta("Total:"), c);
        c.gridx = 1;
        estilizarValor(total);
        contenido.add(total, c);
        add(contenido, BorderLayout.CENTER);

        JPanel acciones = new JPanel();
        acciones.setBackground(pantalla_ventas_principal.FONDO);
        JButton pagar = pantalla_ventas_principal.boton("+ ACEPTAR");
        pagar.setEnabled(!carrito.estaVacio());
        pagar.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evento) {
                abrirPago();
            }
        });
        JButton agregarMas = pantalla_ventas_principal.boton("Agregar más");
        agregarMas.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evento) {
                ControlNavegacion.obtenerInstancia().regresar(pantalla_ventas_carrito.this, pantallaPrincipal);
            }
        });
        acciones.add(pagar);
        acciones.add(agregarMas);
        acciones.setBorder(BorderFactory.createEmptyBorder(0, 0, 12, 0));
        add(acciones, BorderLayout.SOUTH);
    }

    private void actualizarResumen() {
        StringBuilder resumen = new StringBuilder();
        for (CarritoVentas.Item item : carrito.obtenerItems()) {
            resumen.append(item.getProducto().getNombre())
                    .append("  x").append(item.getCantidad())
                    .append("   $").append(String.format(java.util.Locale.US, "%.2f", item.obtenerSubtotal()))
                    .append('\n');
        }
        productos.setText(resumen.toString());
        total.setText(String.format(java.util.Locale.US, "$%.2f", carrito.obtenerTotal()));
    }

    private void abrirPago() {
        ClienteDTO clienteSeleccionado = (ClienteDTO) clientes.getSelectedItem();
        String idCliente = clienteSeleccionado == null ? null : clienteSeleccionado.getIdCliente();
        pantalla_metodos_pago pantalla = new pantalla_metodos_pago(
                this, pantallaPrincipal, ventaServicios, carrito, idCliente);
        ControlNavegacion.obtenerInstancia().abrir(this, pantalla);
    }

    private void cargarClientes() {
        try {
            for (ClienteDTO cliente : clienteServicios.consultarTodosLosClientes()) {
                clientes.addItem(cliente);
            }
            clientes.setSelectedIndex(-1);
        } catch (NegocioException excepcion) {
            javax.swing.JOptionPane.showMessageDialog(
                    this,
                    "No se pudieron cargar los clientes. La venta podrá continuar sin cliente.",
                    "Clientes no disponibles",
                    javax.swing.JOptionPane.WARNING_MESSAGE);
        }
    }

    private void configurarComboClientes() {
        clientes.setBackground(new Color(38, 38, 38));
        clientes.setForeground(Color.WHITE);
        clientes.setRenderer(new DefaultListCellRenderer() {
            @Override
            public java.awt.Component getListCellRendererComponent(
                    JList<?> lista, Object valor, int indice,
                    boolean seleccionado, boolean tieneFoco) {
                JLabel etiqueta = (JLabel) super.getListCellRendererComponent(
                        lista, valor, indice, seleccionado, tieneFoco);
                if (valor instanceof ClienteDTO) {
                    ClienteDTO cliente = (ClienteDTO) valor;
                    etiqueta.setText(cliente.getNombre() + " " + cliente.getApellido()
                            + "  |  " + cliente.getIdCliente());
                } else if (indice == -1) {
                    etiqueta.setText("Venta sin cliente asociado");
                }
                return etiqueta;
            }
        });
    }

    private JLabel etiqueta(String texto) {
        JLabel label = new JLabel(texto);
        label.setForeground(Color.WHITE);
        label.setFont(new Font("Arial", Font.BOLD, 14));
        return label;
    }

    private void estilizarValor(JLabel label) {
        label.setForeground(Color.WHITE);
        label.setOpaque(true);
        label.setBackground(new Color(38, 38, 38));
        label.setBorder(BorderFactory.createEmptyBorder(5, 8, 5, 8));
    }

}

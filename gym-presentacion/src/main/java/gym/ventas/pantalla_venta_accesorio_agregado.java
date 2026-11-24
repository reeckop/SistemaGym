package gym.ventas;

import gym.navegacion.ControlNavegacion;
import gym.servicios.dto.ProductoDTO;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *  Confirmación visual de un artículo agregado al carrito.
 *
 * @author PC GAMER MASTER RACE
 */
public class pantalla_venta_accesorio_agregado extends JFrame {
    private final pantalla_ventas_accesorios pantallaProductos;

    public pantalla_venta_accesorio_agregado(pantalla_ventas_accesorios pantallaProductos,
            ProductoDTO producto, int cantidad) {
        this.pantallaProductos = pantallaProductos;
        setTitle("Producto agregado");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(370, 280);
        setMinimumSize(new Dimension(350, 250));
        setLocationRelativeTo(pantallaProductos);
        getContentPane().setBackground(pantalla_ventas_principal.FONDO);
        setLayout(new BorderLayout(12, 12));

        JLabel mensaje = new JLabel("Producto agregado al carrito", JLabel.CENTER);
        mensaje.setForeground(Color.WHITE);
        mensaje.setFont(new Font("Arial", Font.BOLD, 21));
        mensaje.setBorder(BorderFactory.createEmptyBorder(18, 8, 4, 8));
        add(mensaje, BorderLayout.NORTH);

        JPanel resumen = new JPanel(new BorderLayout(10, 8));
        resumen.setBackground(pantalla_ventas_principal.FONDO);
        JLabel imagen = new JLabel(ImagenesVentas.obtener(producto, 100, 90));
        imagen.setOpaque(true);
        imagen.setBackground(Color.WHITE);
        JLabel texto = new JLabel("<html>" + producto.getNombre() + "<br>Cantidad: " + cantidad +
                "<br>Subtotal: $" + String.format(java.util.Locale.US, "%.2f", producto.getPrecio() * cantidad) + "</html>");
        texto.setForeground(Color.WHITE);
        resumen.add(imagen, BorderLayout.WEST);
        resumen.add(texto, BorderLayout.CENTER);
        resumen.setBorder(BorderFactory.createEmptyBorder(0, 30, 0, 20));
        add(resumen, BorderLayout.CENTER);

        JPanel botones = new JPanel();
        botones.setBackground(pantalla_ventas_principal.FONDO);
        JButton volver = pantalla_ventas_principal.boton("Volver");
        volver.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evento) {
                ControlNavegacion.obtenerInstancia().regresar(pantalla_venta_accesorio_agregado.this, pantallaProductos);
            }
        });
        JButton carritoBoton = pantalla_ventas_principal.boton("Ver carrito");
        carritoBoton.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evento) {
                ControlNavegacion.obtenerInstancia().regresar(pantalla_venta_accesorio_agregado.this, pantallaProductos);
                pantallaProductos.abrirCarrito();
            }
        });
        botones.add(volver);
        botones.add(carritoBoton);
        botones.setBorder(BorderFactory.createEmptyBorder(0, 0, 12, 0));
        add(botones, BorderLayout.SOUTH);
    }
}

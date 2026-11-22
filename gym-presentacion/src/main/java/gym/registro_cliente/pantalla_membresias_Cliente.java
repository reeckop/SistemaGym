/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package gym.registro_cliente;

import gym.login.pantalla_principal;
import gym.servicios.dto.ClienteDTO;
import gym.servicios.dto.MembresiaDTO;
import gym.servicios.enums.EstadoMembresia;
import gym.servicios.interfaces.IMembresiaServicios;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.io.File;
import java.util.List;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JOptionPane;
import javax.swing.WindowConstants;

/**
 *
 * @author drago
 */
public class pantalla_membresias_Cliente extends javax.swing.JFrame {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(pantalla_membresias_Cliente.class.getName());
    
    private final IMembresiaServicios membresiaServicios;
    private final ClienteDTO cliente;
    private final pantalla_registro_cliente2 pantallaAnterior;
    private final pantalla_principal pantallaPrincipal;

    private JPanel panel_membresias;

    public pantalla_membresias_Cliente(IMembresiaServicios servicios, ClienteDTO cliente, pantalla_registro_cliente2 pantallaAnterior, pantalla_principal pantallaPrincipal) {
        initComponents();

        this.membresiaServicios = servicios;
        this.cliente = cliente;
        this.pantallaAnterior = pantallaAnterior;
        this.pantallaPrincipal = pantallaPrincipal;

        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

        jLabel1.setText("SELECCIONAR MEMBRESIA");

        panel_membresias = new JPanel();
        panel_membresias.setBackground(new Color(17, 17, 17));
        panel_membresias.setLayout(new FlowLayout(FlowLayout.LEFT, 30, 30));

        jscrollpaneMembresias.setViewportView(panel_membresias);

        jscrollpaneMembresias.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        jscrollpaneMembresias.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

        cargarMembresias();
    }

    public void cargarMembresias() {
        panel_membresias.removeAll();

        try {

            List<MembresiaDTO> membresias = membresiaServicios.consultarTodasLasMembresias();
            boolean hayMembresias = false;

            if (membresias != null) {
                for (MembresiaDTO membresia : membresias) {
                    // Mostrar únicamente membresías activas
                    if (membresia.getEstado() == EstadoMembresia.ACTIVA) {
                        JPanel tarjeta = crearTarjeta(membresia);
                        panel_membresias.add(tarjeta);

                        hayMembresias = true;
                    }
                }
            }

            if (!hayMembresias) {
                JLabel mensaje = new JLabel("No hay membresías disponibles");
                mensaje.setForeground(Color.WHITE);

                panel_membresias.add(mensaje);
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al cargar las membresías: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }

        panel_membresias.revalidate();
        panel_membresias.repaint();
    }

    private JPanel crearTarjeta(MembresiaDTO membresia) {
        JPanel tarjeta = new JPanel();

        tarjeta.setPreferredSize(new Dimension(110, 150));
        tarjeta.setBackground(new Color(45, 45, 45));
        tarjeta.setLayout(new BoxLayout(tarjeta, BoxLayout.Y_AXIS));

        // Imagen
        JLabel imagen = new JLabel();

        imagen.setPreferredSize(new Dimension(85, 65));
        imagen.setMaximumSize(new Dimension(85, 65));
        imagen.setAlignmentX(JPanel.CENTER_ALIGNMENT);
        imagen.setHorizontalAlignment(JLabel.CENTER);

        if (membresia.getImagen() != null && !membresia.getImagen().isEmpty()) {
            File archivo = new File(membresia.getImagen());
            if (archivo.exists()) {
                ImageIcon icono = new ImageIcon(archivo.getAbsolutePath());

                Image imagenOriginal = icono.getImage();

                int anchoOriginal = icono.getIconWidth();
                int altoOriginal = icono.getIconHeight();

                if (anchoOriginal > 0 && altoOriginal > 0) {
                    double escala = Math.min(65.0 / anchoOriginal, 60.0 / altoOriginal);

                    int nuevoAncho = (int) (anchoOriginal * escala);
                    int nuevoAlto = (int) (altoOriginal * escala);

                    Image imagenEscalada = imagenOriginal.getScaledInstance(nuevoAncho, nuevoAlto, Image.SCALE_SMOOTH);

                    imagen.setIcon(new ImageIcon(imagenEscalada));
                }

            } else {
                imagen.setText("Sin imagen");
            }

        } else {
            imagen.setText("Sin imagen");
        }

        // Nombre
        JLabel nombre = new JLabel(membresia.getNombre());

        nombre.setForeground(Color.WHITE);
        nombre.setFont(new Font("Arial", Font.BOLD, 12));
        nombre.setAlignmentX(JPanel.CENTER_ALIGNMENT);

        // Precio
        JLabel precio = new JLabel("$" + membresia.getPrecio());

        precio.setForeground(Color.WHITE);
        precio.setFont(new Font("Arial", Font.BOLD, 13));
        precio.setAlignmentX(JPanel.CENTER_ALIGNMENT);

        // Botón seleccionar
        JButton seleccionar = new JButton("SELECCIONAR");

        seleccionar.setBackground(new Color(255, 0, 51));
        seleccionar.setForeground(Color.WHITE);
        seleccionar.setFont(new Font("Arial", Font.BOLD, 9));
        seleccionar.setFocusPainted(false);
        seleccionar.setBorderPainted(false);
        seleccionar.setAlignmentX(JPanel.CENTER_ALIGNMENT);

        seleccionar.setPreferredSize(new Dimension(90, 20));
        seleccionar.setMaximumSize(new Dimension(90, 20));

        // Evento seleccionar
        seleccionar.addActionListener(e -> {

            pantalla_registro_cliene3 pantalla = new pantalla_registro_cliene3(cliente, membresia, this, pantallaPrincipal);

            pantalla.setVisible(true);
            this.setVisible(false);
        });

        // Agregar componentes
        tarjeta.add(Box.createVerticalStrut(4));
        tarjeta.add(imagen);
        tarjeta.add(Box.createVerticalStrut(2));
        tarjeta.add(nombre);
        tarjeta.add(precio);
        tarjeta.add(Box.createVerticalStrut(5));
        tarjeta.add(seleccionar);

        return tarjeta;
    }


    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jSeparator2 = new javax.swing.JSeparator();
        jscrollpaneMembresias = new javax.swing.JScrollPane();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(0, 0, 0));

        jLabel1.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("MEMBRESIAS");

        jSeparator1.setBackground(new java.awt.Color(255, 0, 51));
        jSeparator1.setForeground(new java.awt.Color(255, 0, 51));

        jscrollpaneMembresias.setBackground(new java.awt.Color(0, 0, 0));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSeparator1)
            .addComponent(jSeparator2, javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(155, 155, 155)
                .addComponent(jLabel1)
                .addContainerGap(164, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jscrollpaneMembresias)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jscrollpaneMembresias, javax.swing.GroupLayout.DEFAULT_SIZE, 305, Short.MAX_VALUE)
                .addGap(36, 36, 36)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JScrollPane jscrollpaneMembresias;
    // End of variables declaration//GEN-END:variables
}

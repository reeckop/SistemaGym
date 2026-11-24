/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package gym.membresias;


import gym.navegacion.ControlNavegacion;
import gym.login.pantalla_principal;
import gym.servicios.dto.MembresiaDTO;
import gym.servicios.interfaces.IMembresiaServicios;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Dimension;
import java.awt.Font;
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
 * Muestra y administra las membresías disponibles.
 *
 * @author drago
 */
public class pantalla_membresias extends javax.swing.JFrame {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(pantalla_membresias.class.getName());
    
    private final IMembresiaServicios membresiaServicios;
    private final pantalla_principal pantallaPrincipal;

    private JPanel panel_membresias;

    public pantalla_membresias(IMembresiaServicios servicios, pantalla_principal pantallaPrincipal) {
        initComponents();

        this.membresiaServicios = servicios;
        this.pantallaPrincipal = pantallaPrincipal;

        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

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

            if (membresias != null && !membresias.isEmpty()) {
                for (MembresiaDTO membresia : membresias) {
                    JPanel tarjeta = crearTarjeta(membresia);
                    panel_membresias.add(tarjeta);
                }

            } else {
                JLabel mensaje = new JLabel("No hay membresías registradas");

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

        tarjeta.setPreferredSize(new Dimension(190, 245));
        tarjeta.setBackground(new Color(45, 45, 45));

        tarjeta.setLayout(new BoxLayout(tarjeta, BoxLayout.Y_AXIS));

        // Imagen de la membresía
        JLabel imagen = new JLabel();

        imagen.setPreferredSize(new Dimension(150, 145));
        imagen.setMaximumSize(new Dimension(150, 145));
        imagen.setAlignmentX(JPanel.CENTER_ALIGNMENT);

        imagen.setHorizontalAlignment(JLabel.CENTER);

        ImageIcon icono = ImagenMembresia.cargar(membresia.getImagen(), 140, 140);
        if (icono != null) imagen.setIcon(icono);
        else imagen.setText("Sin imagen");

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

        // Botón editar
        JButton editar = new JButton("Editar");

        editar.setBackground(new Color(255, 0, 51));
        editar.setForeground(Color.WHITE);
        editar.setFont(new Font("Arial", Font.BOLD, 10));
        editar.setFocusPainted(false);
        editar.setBorderPainted(false);
        editar.setAlignmentX(JPanel.CENTER_ALIGNMENT);

        editar.setPreferredSize(new Dimension(75, 17));
        editar.setMaximumSize(new Dimension(75, 17));
        
        editar.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evento) {
                EditarMembresia pantalla = new EditarMembresia(
                        membresiaServicios, pantalla_membresias.this, membresia);
                ControlNavegacion.obtenerInstancia().abrir(pantalla_membresias.this, pantalla);
            }
        });

        // Agregar componentes
        tarjeta.add(Box.createVerticalStrut(4));
        tarjeta.add(imagen);
        tarjeta.add(Box.createVerticalStrut(2));
        tarjeta.add(nombre);
        tarjeta.add(precio);
        tarjeta.add(Box.createVerticalStrut(3));
        tarjeta.add(editar);

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
        btn_agregar_membresia = new javax.swing.JButton();
        btn_volver_membresia = new javax.swing.JButton();
        jSeparator2 = new javax.swing.JSeparator();
        jscrollpaneMembresias = new javax.swing.JScrollPane();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(0, 0, 0));

        jLabel1.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("MEMBRESIAS");

        jSeparator1.setBackground(new java.awt.Color(255, 0, 51));
        jSeparator1.setForeground(new java.awt.Color(255, 0, 51));

        btn_agregar_membresia.setBackground(new java.awt.Color(255, 0, 51));
        btn_agregar_membresia.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        btn_agregar_membresia.setForeground(new java.awt.Color(255, 255, 255));
        btn_agregar_membresia.setText("+ AGREGAR");
        btn_agregar_membresia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_agregar_membresiaActionPerformed(evt);
            }
        });

        btn_volver_membresia.setBackground(new java.awt.Color(255, 0, 51));
        btn_volver_membresia.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        btn_volver_membresia.setForeground(new java.awt.Color(255, 255, 255));
        btn_volver_membresia.setText("VOLVER");
        btn_volver_membresia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_volver_membresiaActionPerformed(evt);
            }
        });

        jscrollpaneMembresias.setBackground(new java.awt.Color(0, 0, 0));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSeparator1)
            .addComponent(jSeparator2, javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(0, 300, Short.MAX_VALUE)
                        .addComponent(btn_agregar_membresia)
                        .addGap(238, 238, 238)
                        .addComponent(btn_volver_membresia, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jscrollpaneMembresias)))
                .addContainerGap())
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(288, 288, 288)
                .addComponent(jLabel1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jscrollpaneMembresias, javax.swing.GroupLayout.DEFAULT_SIZE, 423, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_agregar_membresia)
                    .addComponent(btn_volver_membresia, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
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

    private void btn_agregar_membresiaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_agregar_membresiaActionPerformed
        pantalla_agregar_membresia pantalla = new pantalla_agregar_membresia(membresiaServicios, this);

        ControlNavegacion.obtenerInstancia().abrir(this, pantalla);
    }//GEN-LAST:event_btn_agregar_membresiaActionPerformed

    private void btn_volver_membresiaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_volver_membresiaActionPerformed
        ControlNavegacion.obtenerInstancia().regresar(this, pantallaPrincipal);
    }//GEN-LAST:event_btn_volver_membresiaActionPerformed

    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_agregar_membresia;
    private javax.swing.JButton btn_volver_membresia;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JScrollPane jscrollpaneMembresias;
    // End of variables declaration//GEN-END:variables
}

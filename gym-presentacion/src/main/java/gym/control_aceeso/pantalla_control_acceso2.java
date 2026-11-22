/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package gym.control_aceeso;

import gym.login.pantalla_principal;
import gym.servicios.dto.ClienteDTO;
import gym.servicios.dto.MembresiaDTO;
import gym.servicios.dto.RegistroIngresoDTO;
import gym.servicios.enums.EstadoRegistroIngreso;
import gym.servicios.interfaces.IRegistroIngresoServicios;
import org.bson.types.ObjectId;
import gym.servicios.enums.EstadoMembresia;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import javax.swing.JOptionPane;
import javax.swing.WindowConstants;

/**
 *
 * @author drago
 */
public class pantalla_control_acceso2 extends javax.swing.JFrame {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(pantalla_control_acceso2.class.getName());
    
    private final ClienteDTO cliente;
    private final MembresiaDTO membresia;
    private final IRegistroIngresoServicios registroIngresoServicios;
    private final pantalla_control_acceso pantallaAnterior;
    private final pantalla_principal pantallaPrincipal;
    private boolean accesoPermitido;

    /**
     * Creates new form pantalla_control_acceso2
     */
    public pantalla_control_acceso2(ClienteDTO cliente, MembresiaDTO membresia, pantalla_control_acceso pantallaAnterior, pantalla_principal pantallaPrincipal, IRegistroIngresoServicios registroIngresoServicios) {
        initComponents();

        this.cliente = cliente;
        this.membresia = membresia;
        this.registroIngresoServicios = registroIngresoServicios;
        this.pantallaAnterior = pantallaAnterior;
        this.pantallaPrincipal = pantallaPrincipal;

        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

        cargarDatos();

        
    }
    
    private void cargarDatos() {
        campo_txt_num_usuario_.setText(cliente.getIdCliente());

        campo_txt_nombre_control_acceso.setText(cliente.getNombre() + " " + cliente.getApellido());

        // Bloquear los campos
        campo_txt_num_usuario_.setEditable(false);
        campo_txt_nombre_control_acceso.setEditable(false);
        jTextField3.setEditable(false);
        jTextField4.setEditable(false);
        jTextField5.setEditable(false);
        jTextField6.setEditable(false);
        jTextField7.setEditable(false);

        LocalDate hoy = LocalDate.now();
        LocalTime hora = LocalTime.now();

        jTextField6.setText(hoy.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));

        jTextField7.setText(hora.format(DateTimeFormatter.ofPattern("HH:mm")));

        accesoPermitido = false;

        if (membresia == null) {
            jTextField3.setText("Sin membresía");
            jTextField4.setText("SIN MEMBRESÍA");
            jTextField5.setText("0 días");

            return;
        }

        jTextField3.setText(membresia.getNombre());

        LocalDate fechaFin = cliente.getFechaFinMembresia();

        if (fechaFin == null) {
            jTextField4.setText("SIN VIGENCIA");
            jTextField5.setText("No disponible");

            return;
        }

        if (fechaFin.isBefore(hoy)) {
            jTextField4.setText("VENCIDA");
            jTextField5.setText("0 días");

            return;
        }

        if (membresia.getEstado() != EstadoMembresia.ACTIVA) {
            jTextField4.setText("INACTIVA");
            jTextField5.setText("No disponible");

            return;
        }

        long dias = ChronoUnit.DAYS.between(hoy, fechaFin);

        jTextField4.setText("ACTIVA");
        jTextField5.setText(dias + " días");

        accesoPermitido = true;
    }
    
    private void registrarEntrada() {
        if (!accesoPermitido) {
            JOptionPane.showMessageDialog(this, "El cliente no cuenta con una membresía activa y vigente.", "Acceso denegado", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            // Crear el registro de ingreso
            RegistroIngresoDTO registro = new RegistroIngresoDTO();

            registro.setIdAcceso(new ObjectId().toHexString());
            registro.setFechaHoraEntrada(LocalDateTime.now());
            registro.setFechaHoraSalida(null);
            registro.setEstado(EstadoRegistroIngreso.DENTRO);
            registro.setIdCliente(cliente.getIdCliente());

            // Guardar en MongoDB
            registroIngresoServicios.registrarIngreso(registro);

            JOptionPane.showMessageDialog(this, "¡Entrada registrada correctamente!\n\n" + "Cliente: " + cliente.getNombre() + " " + cliente.getApellido() + "\nMembresía: " + membresia.getNombre(), "Acceso permitido", JOptionPane.INFORMATION_MESSAGE);

            // Regresar a la pantalla de búsqueda
            pantallaAnterior.setVisible(true);
            this.dispose();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al registrar la entrada: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
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
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        campo_txt_num_usuario_ = new javax.swing.JTextField();
        campo_txt_nombre_control_acceso = new javax.swing.JTextField();
        jTextField3 = new javax.swing.JTextField();
        jTextField4 = new javax.swing.JTextField();
        jTextField5 = new javax.swing.JTextField();
        jTextField6 = new javax.swing.JTextField();
        jTextField7 = new javax.swing.JTextField();
        btn_registrar_entrada = new javax.swing.JButton();
        btn_volver_control_acceso = new javax.swing.JButton();
        jSeparator2 = new javax.swing.JSeparator();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(0, 0, 0));

        jLabel1.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("CONTROL DE ACCESO");
        jLabel1.setToolTipText("");

        jSeparator1.setForeground(new java.awt.Color(255, 0, 51));

        jLabel2.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("No. Usuario:");

        jLabel3.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Nombre:");

        jLabel4.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("Membresia:");

        jLabel5.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("Estado:");

        jLabel6.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("Vigencia:");

        jLabel7.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("Fecha:");

        jLabel8.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("Hora:");

        campo_txt_num_usuario_.setBackground(new java.awt.Color(0, 0, 0));
        campo_txt_num_usuario_.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        campo_txt_num_usuario_.setForeground(new java.awt.Color(255, 255, 255));
        campo_txt_num_usuario_.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        campo_txt_num_usuario_.setBorder(null);

        campo_txt_nombre_control_acceso.setBackground(new java.awt.Color(0, 0, 0));
        campo_txt_nombre_control_acceso.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        campo_txt_nombre_control_acceso.setForeground(new java.awt.Color(255, 255, 255));
        campo_txt_nombre_control_acceso.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        campo_txt_nombre_control_acceso.setBorder(null);

        jTextField3.setBackground(new java.awt.Color(0, 0, 0));
        jTextField3.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jTextField3.setForeground(new java.awt.Color(255, 255, 255));
        jTextField3.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextField3.setBorder(null);

        jTextField4.setBackground(new java.awt.Color(0, 0, 0));
        jTextField4.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jTextField4.setForeground(new java.awt.Color(255, 255, 255));
        jTextField4.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextField4.setBorder(null);

        jTextField5.setBackground(new java.awt.Color(0, 0, 0));
        jTextField5.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jTextField5.setForeground(new java.awt.Color(255, 255, 255));
        jTextField5.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextField5.setBorder(null);

        jTextField6.setBackground(new java.awt.Color(0, 0, 0));
        jTextField6.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jTextField6.setForeground(new java.awt.Color(255, 255, 255));
        jTextField6.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextField6.setBorder(null);

        jTextField7.setBackground(new java.awt.Color(0, 0, 0));
        jTextField7.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jTextField7.setForeground(new java.awt.Color(255, 255, 255));
        jTextField7.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextField7.setBorder(null);

        btn_registrar_entrada.setBackground(new java.awt.Color(255, 0, 51));
        btn_registrar_entrada.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btn_registrar_entrada.setForeground(new java.awt.Color(255, 255, 255));
        btn_registrar_entrada.setText("+ Registrar Entrada");
        btn_registrar_entrada.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_registrar_entradaActionPerformed(evt);
            }
        });

        btn_volver_control_acceso.setBackground(new java.awt.Color(255, 0, 51));
        btn_volver_control_acceso.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btn_volver_control_acceso.setForeground(new java.awt.Color(255, 255, 255));
        btn_volver_control_acceso.setText("VOLVER");
        btn_volver_control_acceso.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_volver_control_accesoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSeparator1, javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(126, 126, 126)
                        .addComponent(jLabel1))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(92, 92, 92)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel3)
                            .addComponent(jLabel2)
                            .addComponent(jLabel4)
                            .addComponent(jLabel5)
                            .addComponent(jLabel6)
                            .addComponent(jLabel7)
                            .addComponent(jLabel8))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(campo_txt_num_usuario_)
                            .addComponent(campo_txt_nombre_control_acceso)
                            .addComponent(jTextField3)
                            .addComponent(jTextField4)
                            .addComponent(jTextField5)
                            .addComponent(jTextField6)
                            .addComponent(jTextField7, javax.swing.GroupLayout.DEFAULT_SIZE, 199, Short.MAX_VALUE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(197, 197, 197)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(btn_volver_control_acceso, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btn_registrar_entrada, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap(130, Short.MAX_VALUE))
            .addComponent(jSeparator2, javax.swing.GroupLayout.Alignment.TRAILING)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(51, 51, 51)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(campo_txt_num_usuario_, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(campo_txt_nombre_control_acceso, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(jTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(jTextField6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(jTextField7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(34, 34, 34)
                .addComponent(btn_registrar_entrada)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btn_volver_control_acceso)
                .addGap(18, 18, 18)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(35, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btn_registrar_entradaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_registrar_entradaActionPerformed
        registrarEntrada();
    }//GEN-LAST:event_btn_registrar_entradaActionPerformed

    private void btn_volver_control_accesoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_volver_control_accesoActionPerformed
        pantallaAnterior.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_btn_volver_control_accesoActionPerformed

    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_registrar_entrada;
    private javax.swing.JButton btn_volver_control_acceso;
    private javax.swing.JTextField campo_txt_nombre_control_acceso;
    private javax.swing.JTextField campo_txt_num_usuario_;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField jTextField4;
    private javax.swing.JTextField jTextField5;
    private javax.swing.JTextField jTextField6;
    private javax.swing.JTextField jTextField7;
    // End of variables declaration//GEN-END:variables
}

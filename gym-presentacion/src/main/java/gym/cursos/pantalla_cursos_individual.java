/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package gym.cursos;

import gym.excepciones.NegocioException;
import gym.servicios.dto.ClienteDTO;
import gym.servicios.dto.CursoDTO;
import gym.servicios.dto.HorarioDTO;
import gym.servicios.dto.InscripcionDTO;
import gym.servicios.enums.EstadoInscripcion;
import gym.servicios.interfaces.IClienteServicios;
import gym.servicios.interfaces.IInscripcionServicios;
import gym.servicios.servicios.ClienteServicios;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Permite elegir un horario e inscribir a un cliente en el curso.
 *
 * @author drago
 */
public class pantalla_cursos_individual extends javax.swing.JFrame {

    private CursoDTO curso;
    private IInscripcionServicios inscripcionServicios;
    private IClienteServicios clienteServicios;
    private javax.swing.JComboBox<ClienteDTO> clientes = new javax.swing.JComboBox<>();
    private java.util.List<HorarioDTO> horarios = java.util.List.of();
    private javax.swing.JFrame anterior;

    /**
     * Creates new form pantalla_cursos_individual
     */
    public pantalla_cursos_individual() {
        initComponents();
    }

    public pantalla_cursos_individual(
            javax.swing.JFrame anterior,
            CursoDTO curso,
            IInscripcionServicios inscripcionServicios) {
        this(anterior, curso, primerHorario(curso), inscripcionServicios);
    }

    public pantalla_cursos_individual(
            javax.swing.JFrame anterior,
            CursoDTO curso,
            HorarioDTO horarioElegido,
            IInscripcionServicios inscripcionServicios) {
        this(anterior, curso, horarioElegido, inscripcionServicios, new ClienteServicios());
    }

    public pantalla_cursos_individual(
            javax.swing.JFrame anterior,
            CursoDTO curso,
            HorarioDTO horarioElegido,
            IInscripcionServicios inscripcionServicios,
            IClienteServicios clienteServicios) {
        this();
        this.anterior = anterior;
        this.curso = curso;
        this.inscripcionServicios = inscripcionServicios;
        this.clienteServicios = clienteServicios;

        setTitle("Horarios | " + curso.getNombre());
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(760, 500);
        setLocationRelativeTo(anterior);
        nombre_curso.setText(curso.getNombre().toUpperCase());
        horarios = horarioElegido == null ? java.util.List.of() : java.util.List.of(horarioElegido);
        configurarHorario(0, campo_txt_fecha_hora1, btn_seleccionar1);
        configurarHorario(1, campo_txt_fecha_hora2, btn_seleccionar2);
        campo_txt_fecha_hora2.setVisible(false);
        btn_seleccionar2.setVisible(false);

        javax.swing.JPanel original = (javax.swing.JPanel) getContentPane().getComponent(0);
        javax.swing.JPanel raiz = new javax.swing.JPanel(new java.awt.BorderLayout());
        raiz.setBackground(java.awt.Color.BLACK);
        raiz.add(crearSelectorCliente(), java.awt.BorderLayout.NORTH);
        raiz.add(original, java.awt.BorderLayout.CENTER);

        javax.swing.JButton volver = new javax.swing.JButton("Volver a cursos");
        volver.setBackground(new java.awt.Color(205, 0, 0));
        volver.setForeground(java.awt.Color.WHITE);
        volver.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evento) {
                gym.navegacion.ControlNavegacion.obtenerInstancia().regresar(pantalla_cursos_individual.this, anterior);
            }
        });
        javax.swing.JPanel pie = new javax.swing.JPanel();
        pie.setBackground(java.awt.Color.BLACK);
        pie.add(volver);
        raiz.add(pie, java.awt.BorderLayout.SOUTH);
        setContentPane(raiz);
        cargarClientes();
    }

    private javax.swing.JPanel crearSelectorCliente() {
        javax.swing.JPanel panel = new javax.swing.JPanel(new java.awt.BorderLayout(12, 0));
        panel.setBackground(java.awt.Color.BLACK);
        panel.setBorder(javax.swing.BorderFactory.createEmptyBorder(14, 24, 10, 24));

        javax.swing.JLabel etiqueta = new javax.swing.JLabel("Cliente para inscribir:");
        etiqueta.setForeground(java.awt.Color.WHITE);
        panel.add(etiqueta, java.awt.BorderLayout.WEST);

        clientes.setBackground(new java.awt.Color(48, 48, 48));
        clientes.setForeground(java.awt.Color.WHITE);
        clientes.setRenderer(new javax.swing.DefaultListCellRenderer() {
            @Override
            public java.awt.Component getListCellRendererComponent(
                    javax.swing.JList<?> lista, Object valor, int indice,
                    boolean seleccionado, boolean tieneFoco) {
                javax.swing.JLabel texto = (javax.swing.JLabel) super.getListCellRendererComponent(
                        lista, valor, indice, seleccionado, tieneFoco);
                if (valor instanceof ClienteDTO) {
                    ClienteDTO cliente = (ClienteDTO) valor;
                    texto.setText(cliente.getNombre() + " " + cliente.getApellido()
                            + " | " + cliente.getIdCliente());
                } else if (indice == -1) {
                    texto.setText("Selecciona un cliente");
                }
                return texto;
            }
        });
        panel.add(clientes, java.awt.BorderLayout.CENTER);
        return panel;
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
                    "No se pudieron cargar los clientes: " + excepcion.getMessage(),
                    "Clientes",
                    javax.swing.JOptionPane.ERROR_MESSAGE);
        }
    }

    private void configurarHorario(int indice, javax.swing.JTextField campo, javax.swing.JButton boton) {
        if (indice >= horarios.size()) {
            return;
        }
        HorarioDTO horario = horarios.get(indice);
        campo.setEditable(false);
        campo.setText(horario.getFecha().format(DateTimeFormatter.ofPattern("d 'de' MMMM"))
                + "   " + horario.getHoraInicio() + " - " + horario.getHoraFin());
        boton.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evento) {
                inscribir(horario);
            }
        });
    }

    private static HorarioDTO primerHorario(CursoDTO curso) {
        if (curso == null || curso.getHorarios() == null || curso.getHorarios().isEmpty()) {
            return null;
        }
        return curso.getHorarios().get(0);
    }

    private void inscribir(HorarioDTO horario) {
        ClienteDTO clienteSeleccionado = (ClienteDTO) clientes.getSelectedItem();
        if (clienteSeleccionado == null) {
            javax.swing.JOptionPane.showMessageDialog(this,
                    "Selecciona al cliente que se inscribirá al curso.",
                    "Inscripción",
                    javax.swing.JOptionPane.WARNING_MESSAGE);
            return;
        }
        try {
            InscripcionDTO inscripcion = new InscripcionDTO(
                    null,
                    LocalDate.now(),
                    EstadoInscripcion.ACTIVA,
                    clienteSeleccionado.getIdCliente(),
                    horario);
            inscripcionServicios.registrarInscripcion(inscripcion);
            javax.swing.JOptionPane.showMessageDialog(this,
                    "Inscripción registrada para " + curso.getNombre() + ".");
        } catch (NegocioException evento) {
            javax.swing.JOptionPane.showMessageDialog(this,
                    evento.getMessage(),
                    "No se pudo inscribir",
                    javax.swing.JOptionPane.WARNING_MESSAGE);
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
        nombre_curso = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        campo_txt_fecha_hora1 = new javax.swing.JTextField();
        campo_txt_fecha_hora2 = new javax.swing.JTextField();
        btn_seleccionar1 = new javax.swing.JButton();
        btn_seleccionar2 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(0, 0, 0));

        nombre_curso.setFont(new java.awt.Font("Arial", 1, 36)); // NOI18N
        nombre_curso.setForeground(new java.awt.Color(255, 255, 255));
        nombre_curso.setText("YOGA");

        jSeparator1.setForeground(new java.awt.Color(255, 0, 0));

        campo_txt_fecha_hora1.setBackground(new java.awt.Color(51, 51, 51));
        campo_txt_fecha_hora1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        campo_txt_fecha_hora1.setForeground(new java.awt.Color(255, 255, 255));
        campo_txt_fecha_hora1.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        campo_txt_fecha_hora1.setText("9 de septiembre  12:00 - 2:00 PM");

        campo_txt_fecha_hora2.setBackground(new java.awt.Color(51, 51, 51));
        campo_txt_fecha_hora2.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        campo_txt_fecha_hora2.setForeground(new java.awt.Color(255, 255, 255));
        campo_txt_fecha_hora2.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        campo_txt_fecha_hora2.setText("9 de septiembre  2:00 - 4:00 PM");
        campo_txt_fecha_hora2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                campo_txt_fecha_hora2ActionPerformed(evt);
            }
        });

        btn_seleccionar1.setBackground(new java.awt.Color(255, 0, 0));
        btn_seleccionar1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btn_seleccionar1.setForeground(new java.awt.Color(255, 255, 255));
        btn_seleccionar1.setText("SELECCIONAR");

        btn_seleccionar2.setBackground(new java.awt.Color(255, 0, 0));
        btn_seleccionar2.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btn_seleccionar2.setForeground(new java.awt.Color(255, 255, 255));
        btn_seleccionar2.setText("SELECCIONAR");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSeparator1, javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(300, 300, 300)
                        .addComponent(nombre_curso))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(71, 71, 71)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(campo_txt_fecha_hora1, javax.swing.GroupLayout.DEFAULT_SIZE, 410, Short.MAX_VALUE)
                            .addComponent(campo_txt_fecha_hora2))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(btn_seleccionar1, javax.swing.GroupLayout.DEFAULT_SIZE, 129, Short.MAX_VALUE)
                            .addComponent(btn_seleccionar2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap(74, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(nombre_curso)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(31, 31, 31)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btn_seleccionar1, javax.swing.GroupLayout.DEFAULT_SIZE, 36, Short.MAX_VALUE)
                    .addComponent(campo_txt_fecha_hora1))
                .addGap(26, 26, 26)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btn_seleccionar2, javax.swing.GroupLayout.DEFAULT_SIZE, 36, Short.MAX_VALUE)
                    .addComponent(campo_txt_fecha_hora2))
                .addContainerGap(129, Short.MAX_VALUE))
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

    private void campo_txt_fecha_hora2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_campo_txt_fecha_hora2ActionPerformed
    }//GEN-LAST:event_campo_txt_fecha_hora2ActionPerformed



    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_seleccionar1;
    private javax.swing.JButton btn_seleccionar2;
    private javax.swing.JTextField campo_txt_fecha_hora1;
    private javax.swing.JTextField campo_txt_fecha_hora2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JLabel nombre_curso;
    // End of variables declaration//GEN-END:variables
}

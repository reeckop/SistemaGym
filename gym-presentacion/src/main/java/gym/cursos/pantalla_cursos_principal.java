/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package gym.cursos;

import gym.excepciones.NegocioException;
import gym.navegacion.ControlNavegacion;
import gym.servicios.dto.CursoDTO;
import gym.servicios.interfaces.IClienteServicios;
import gym.servicios.interfaces.ICursoServicios;
import gym.servicios.interfaces.IInscripcionServicios;
import gym.servicios.servicios.ClienteServicios;
import gym.servicios.servicios.CursoServicios;
import gym.servicios.servicios.InscripcionServicios;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.net.URL;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * Muestra el catálogo de cursos y sus horarios disponibles.
 *
 * @author drago
 */
public class pantalla_cursos_principal extends javax.swing.JFrame {

    private final javax.swing.JFrame pantallaAnterior;
    private final ICursoServicios cursoServicios;
    private final IInscripcionServicios inscripcionServicios;
    private final IClienteServicios clienteServicios;
    private List<CursoDTO> cursos = List.of();
    private String filtro = "Todos";
    private JPanel contenidoCursos;
    private JPanel tarjetasCursos;

    /**
     * Creates new form pantalla_cursos_principal
     */
    public pantalla_cursos_principal() {
        this(null, new CursoServicios(), new InscripcionServicios(), new ClienteServicios());
    }

    public pantalla_cursos_principal(
            javax.swing.JFrame pantallaAnterior,
            ICursoServicios cursoServicios,
            IInscripcionServicios inscripcionServicios) {
        this(pantallaAnterior, cursoServicios, inscripcionServicios, new ClienteServicios());
    }

    public pantalla_cursos_principal(
            javax.swing.JFrame pantallaAnterior,
            ICursoServicios cursoServicios,
            IInscripcionServicios inscripcionServicios,
            IClienteServicios clienteServicios) {
        this.pantallaAnterior = pantallaAnterior;
        this.cursoServicios = cursoServicios;
        this.inscripcionServicios = inscripcionServicios;
        this.clienteServicios = clienteServicios;
        initComponents();
        setTitle("Sistema Gym | Cursos");
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setMinimumSize(new Dimension(900, 620));
        setSize(1180, 760);
        JScrollPane.getViewport().setBackground(new Color(16, 16, 16));
        JScrollPane.setBorder(BorderFactory.createEmptyBorder());
        jLabel1.setText("SISTEMA GYM / MÓDULO CLASES");
        btn_cursos_volver.setText("Volver");
        btn_cursos_agregar.setText("+ Agregar curso");
        organizarPantalla();
        btn_cursos_volver.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evento) {
                ControlNavegacion.obtenerInstancia().regresar(pantalla_cursos_principal.this, pantallaAnterior);
            }
        });
        btn_cursos_agregar.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evento) {
                abrirFormulario();
            }
        });
        contenidoCursos = new JPanel(new BorderLayout(10, 12));
        contenidoCursos.setBackground(new Color(16, 16, 16));
        contenidoCursos.add(filtros(), BorderLayout.NORTH);
        tarjetasCursos = new JPanel(new GridLayout(0, 2, 16, 16));
        tarjetasCursos.setBackground(new Color(16, 16, 16));
        contenidoCursos.add(tarjetasCursos, BorderLayout.CENTER);
        JScrollPane.getViewport().setBackground(new Color(16, 16, 16));
        JScrollPane.setBorder(BorderFactory.createEmptyBorder());
        JScrollPane.setViewportView(contenidoCursos);
        cargarCursos();
    }

    private void organizarPantalla() {
        JPanel encabezado = new JPanel(new BorderLayout());
        encabezado.setBackground(new Color(16, 16, 16));
        encabezado.add(jLabel1, BorderLayout.CENTER);
        encabezado.add(jSeparator1, BorderLayout.SOUTH);

        JPanel acciones = new JPanel(new FlowLayout(FlowLayout.CENTER, 18, 12));
        acciones.setBackground(new Color(16, 16, 16));
        acciones.add(btn_cursos_agregar);
        acciones.add(btn_cursos_volver);

        JPanel contenido = new JPanel(new BorderLayout());
        contenido.setBackground(new Color(16, 16, 16));
        contenido.add(encabezado, BorderLayout.NORTH);
        contenido.add(JScrollPane, BorderLayout.CENTER);
        contenido.add(acciones, BorderLayout.SOUTH);
        setContentPane(contenido);
    }

    private JPanel filtros() {
        JPanel panel = new JPanel(new GridLayout(1, 5, 10, 0));
        panel.setOpaque(false);
        for (String nombre : List.of("Todos", "Box", "Spinning", "Yoga", "Zumba")) {
            javax.swing.JButton boton = new javax.swing.JButton(nombre);
            boton.setBackground(new Color(48, 48, 48));
            boton.setForeground(Color.WHITE);
            boton.setFocusPainted(false);
            boton.addActionListener(new java.awt.event.ActionListener() {
                @Override
                public void actionPerformed(java.awt.event.ActionEvent evento) {
                    filtro = nombre;
                    mostrarTarjetas();
                }
            });
            panel.add(boton);
        }
        return panel;
    }

    private void cargarCursos() {
        try {
            cursos = cursoServicios.consultarTodosLosCursos();
            mostrarTarjetas();
        } catch (NegocioException evento) {
            javax.swing.JOptionPane.showMessageDialog(
                    this,
                    evento.getMessage(),
                    "Cursos",
                    javax.swing.JOptionPane.ERROR_MESSAGE);
        }
    }

    private void mostrarTarjetas() {
        tarjetasCursos.removeAll();
        tarjetasCursos.setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));
        for (CursoDTO curso : cursos) {
            boolean perteneceAlFiltro = curso.getNombre().toLowerCase(java.util.Locale.ROOT)
                    .contains(filtro.toLowerCase(java.util.Locale.ROOT));
            if ("Todos".equals(filtro) || perteneceAlFiltro) {
                tarjetasCursos.add(crearTarjeta(curso));
            }
        }
        if (tarjetasCursos.getComponentCount()==0) {
            JLabel vacio = new JLabel("No hay cursos en esta categoría.", JLabel.CENTER);
            vacio.setForeground(Color.WHITE);
            tarjetasCursos.add(vacio);
        }
        contenidoCursos.revalidate();
        contenidoCursos.repaint();
    }

    private JPanel crearTarjeta(final CursoDTO curso) {
        JPanel tarjeta = new JPanel(new BorderLayout(8, 8));
        tarjeta.setBackground(new Color(27, 27, 27));
        tarjeta.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(68, 68, 68)),
                BorderFactory.createEmptyBorder(12, 12, 12, 12)));

        JLabel nombre = new JLabel(curso.getNombre().toUpperCase(), JLabel.CENTER);
        nombre.setForeground(Color.WHITE);
        nombre.setFont(new Font("Arial", Font.BOLD, 19));

        JPanel foto = crearPanelImagen(imagenCurso(curso));

        JPanel horarios = new JPanel(new GridLayout(0, 1, 5, 5));
        horarios.setOpaque(false);
        List<gym.servicios.dto.HorarioDTO> listaHorarios = curso.getHorarios();
        if (listaHorarios != null) {
            for (final gym.servicios.dto.HorarioDTO horario : listaHorarios) {
                if (horario == null || horario.getFecha() == null
                        || horario.getHoraInicio() == null || horario.getHoraFin() == null) {
                    continue;
                }
                horarios.add(crearBotonHorario(curso, horario));
            }
        }

        tarjeta.add(nombre, BorderLayout.NORTH);
        tarjeta.add(foto, BorderLayout.CENTER);
        tarjeta.add(horarios, BorderLayout.SOUTH);
        tarjeta.setPreferredSize(new Dimension(500, 350));
        return tarjeta;
    }

    private javax.swing.JButton crearBotonHorario(
            final CursoDTO curso, final gym.servicios.dto.HorarioDTO horario) {
        String texto = horario.getFecha().format(java.time.format.DateTimeFormatter.ofPattern("d 'de' MMMM"))
                + ": " + horario.getHoraInicio() + " - " + horario.getHoraFin();
        javax.swing.JButton elegir = new javax.swing.JButton(texto + "   |   Seleccionar");
        elegir.setBackground(new Color(205, 0, 0));
        elegir.setForeground(Color.WHITE);
        elegir.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evento) {
                pantalla_cursos_individual pantalla = new pantalla_cursos_individual(
                        pantalla_cursos_principal.this, curso, horario,
                        inscripcionServicios, clienteServicios);
                ControlNavegacion.obtenerInstancia().abrir(pantalla_cursos_principal.this, pantalla);
            }
        });
        return elegir;
    }

    private JPanel crearPanelImagen(String ruta) {
        URL recurso = ruta == null ? null : getClass().getResource(ruta);
        if (recurso == null) {
            recurso = getClass().getResource("/gym/ventas/productos/accesorios.jpg");
        }

        final Image imagen = new ImageIcon(recurso).getImage();
        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics grafico) {
                super.paintComponent(grafico);
                int anchoImagen = imagen.getWidth(this);
                int altoImagen = imagen.getHeight(this);
                if (anchoImagen <= 0 || altoImagen <= 0) {
                    return;
                }

                double escala = Math.max(
                        (double) getWidth() / anchoImagen,
                        (double) getHeight() / altoImagen);
                int ancho = (int) Math.round(anchoImagen * escala);
                int alto = (int) Math.round(altoImagen * escala);
                int x = (getWidth() - ancho) / 2;
                int y = (getHeight() - alto) / 2;
                grafico.drawImage(imagen, x, y, ancho, alto, this);
            }
        };
        panel.setBackground(new Color(24, 24, 24));
        panel.setPreferredSize(new Dimension(480, 210));
        return panel;
    }

    private String imagenCurso(CursoDTO curso) {
        String nombre = curso.getNombre() == null
                ? ""
                : curso.getNombre().toLowerCase(java.util.Locale.ROOT);

        if (nombre.contains("spinning")) {
            return "/gym/cursos/spinning.png";
        }
        if (nombre.contains("yoga")) {
            return "/gym/cursos/yoga.png";
        }
        if (nombre.contains("zumba")) {
            return "/gym/cursos/zumba.png";
        }
        if (nombre.contains("box") || nombre.contains("boxeo")) {
            return "/gym/cursos/box.png";
        }
        return curso.getImagen();
    }

    private void abrirFormulario() {
        pantalla_cursos_agregar formulario = new pantalla_cursos_agregar(this, cursoServicios);
        ControlNavegacion.obtenerInstancia().abrir(this, formulario);
    }

    void recargarCursos() {
        cargarCursos();
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
        btn_cursos_agregar = new javax.swing.JButton();
        btn_cursos_volver = new javax.swing.JButton();
        JScrollPane = new javax.swing.JScrollPane();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(0, 0, 0));

        jLabel1.setFont(new java.awt.Font("Arial", 1, 36)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("CURSOS");

        jSeparator1.setForeground(new java.awt.Color(255, 0, 0));

        btn_cursos_agregar.setBackground(new java.awt.Color(255, 0, 0));
        btn_cursos_agregar.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btn_cursos_agregar.setForeground(new java.awt.Color(255, 255, 255));
        btn_cursos_agregar.setText("+ Agregar Cursos");

        btn_cursos_volver.setBackground(new java.awt.Color(255, 0, 0));
        btn_cursos_volver.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btn_cursos_volver.setForeground(new java.awt.Color(255, 255, 255));
        btn_cursos_volver.setText("Volver");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSeparator1, javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGap(0, 266, Short.MAX_VALUE)
                        .addComponent(btn_cursos_agregar, javax.swing.GroupLayout.PREFERRED_SIZE, 173, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(121, 121, 121)
                        .addComponent(btn_cursos_volver, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(268, 268, 268)
                        .addComponent(jLabel1)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(JScrollPane)))
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
                .addComponent(JScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 267, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_cursos_volver, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_cursos_agregar, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(29, 29, 29))
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
    private javax.swing.JScrollPane JScrollPane;
    private javax.swing.JButton btn_cursos_agregar;
    private javax.swing.JButton btn_cursos_volver;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JSeparator jSeparator1;
    // End of variables declaration//GEN-END:variables
}

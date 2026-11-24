/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package gym.membresias;

import gym.servicios.dto.BeneficioDTO;
import gym.servicios.dto.MembresiaDTO;
import gym.servicios.enums.EstadoMembresia;
import gym.servicios.interfaces.IMembresiaServicios;
import gym.navegacion.ControlNavegacion;
import gym.servicios.servicios.MembresiaServicios;
import java.awt.Color;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.bson.types.ObjectId;

/**
 * Permite modificar los datos y beneficios de una membresía.
 *
 * @author drago
 */
public class EditarMembresia extends javax.swing.JFrame {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(EditarMembresia.class.getName());

    private final IMembresiaServicios membresiaServicios;
    private final pantalla_membresias pantallaAnterior;
    private final MembresiaDTO membresiaOriginal;

    private File imagenSeleccionada;

    public EditarMembresia(IMembresiaServicios servicios, pantalla_membresias pantallaAnterior, MembresiaDTO membresia) {

            initComponents();

            this.membresiaServicios = servicios;
            this.pantallaAnterior = pantallaAnterior;
            this.membresiaOriginal = membresia;

            setLocationRelativeTo(null);
            setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

            jLabel1.setText("EDITAR MEMBRESIA");

            configurarBeneficios();
            configurarApariencia();
            cargarDatosMembresia();

            btn_seleccionar_Imagen_membreciaN.addActionListener(new java.awt.event.ActionListener() {
                @Override
                public void actionPerformed(java.awt.event.ActionEvent evento) {
                    seleccionarImagen();
                }
            });
            btn_aceptar_agregar_membresia.addActionListener(new java.awt.event.ActionListener() {
                @Override
                public void actionPerformed(java.awt.event.ActionEvent evento) {
                    actualizarMembresia();
                }
            });
        }

    private void cargarDatosMembresia() {
        campo_txt_nombre_membresiaN.setText(membresiaOriginal.getNombre());

        campo_txt_precio_membresiaN.setText(String.valueOf(membresiaOriginal.getPrecio()));

        // Cargar imagen existente
        if (membresiaOriginal.getImagen() != null && !membresiaOriginal.getImagen().isEmpty()) {
            File archivo = new File(membresiaOriginal.getImagen());

            if (archivo.exists()) {
                mostrarImagen(archivo);
            }
        }

        // Cargar beneficios existentes
        List<BeneficioDTO> beneficios = membresiaOriginal.getBeneficios();

        if (beneficios == null) {
            return;
        }

        for (BeneficioDTO beneficio : beneficios) {
            switch (beneficio.getNombre()) {

                case "Descuento en productos":
                    checkB_descuentos_productos.setSelected(true);
                    campo_txt_descuentos_productos.setText(String.valueOf(beneficio.getValor()));
                    break;

                case "Sesiones gratuitas":
                    checkB_sesiones_gratis.setSelected(true);
                    campo_texto_sesiones_gratis.setText(String.valueOf(beneficio.getValor()));
                    break;

                case "Descuento en cursos":
                    checkB_descuento_cursos.setSelected(true);
                    campo_texto_descuento_cursos.setText(String.valueOf(beneficio.getValor()));
                    break;

                case "Invitaciones mensuales":
                    checkB_invitaciones_mensuales.setSelected(true);
                    campo_texto_invitaciones_mensuales.setText(String.valueOf(beneficio.getValor()));
                    break;
            }
        }
    }
    
    private void mostrarImagen(File archivo) {
        ImageIcon imagen = new ImageIcon(archivo.getAbsolutePath());

        int ancho = jPanel2.getWidth() - 20;
        int alto = jPanel2.getHeight() - 20;

        if (ancho <= 0) ancho = 230;
        if (alto <= 0) alto = 160;

        double escala = Math.min((double) ancho / imagen.getIconWidth(),(double) alto / imagen.getIconHeight());

        int nuevoAncho = (int) (imagen.getIconWidth() * escala);
        int nuevoAlto = (int) (imagen.getIconHeight() * escala);

        Image imagenEscalada = imagen.getImage().getScaledInstance(nuevoAncho, nuevoAlto, Image.SCALE_SMOOTH);

        txt_img_membresiaN.setText("");
        txt_img_membresiaN.setIcon(new ImageIcon(imagenEscalada));
    }
    
    private void configurarBeneficios() {
        campo_txt_descuentos_productos.setEnabled(false);
        campo_texto_sesiones_gratis.setEnabled(false);
        campo_texto_descuento_cursos.setEnabled(false);
        campo_texto_invitaciones_mensuales.setEnabled(false);

        checkB_descuentos_productos.addItemListener(
                new ControlCampoBeneficio(checkB_descuentos_productos, campo_txt_descuentos_productos));
        checkB_sesiones_gratis.addItemListener(
                new ControlCampoBeneficio(checkB_sesiones_gratis, campo_texto_sesiones_gratis));
        checkB_descuento_cursos.addItemListener(
                new ControlCampoBeneficio(checkB_descuento_cursos, campo_texto_descuento_cursos));
        checkB_invitaciones_mensuales.addItemListener(
                new ControlCampoBeneficio(checkB_invitaciones_mensuales, campo_texto_invitaciones_mensuales));
    }

    private void validarBeneficios() {
        if (checkB_descuentos_productos.isSelected()) {
            validarValor(campo_txt_descuentos_productos, 100, "Descuento en productos");
        }

        if (checkB_sesiones_gratis.isSelected()) {
            validarValor(campo_texto_sesiones_gratis, Double.MAX_VALUE, "Sesiones gratuitas");
        }

        if (checkB_descuento_cursos.isSelected()) {
            validarValor(campo_texto_descuento_cursos, 100, "Descuento en cursos");
        }

        if (checkB_invitaciones_mensuales.isSelected()) {
            validarValor(campo_texto_invitaciones_mensuales, Double.MAX_VALUE, "Invitaciones mensuales");
        }
    }

    private void validarValor(JTextField campo, double maximo, String nombre) {
        if (campo.getText().trim().isEmpty()) {
            throw new NumberFormatException("Ingresa el valor de " + nombre);
        }

        double valor = Double.parseDouble(campo.getText().trim());

        if (valor <= 0 || valor > maximo) {
            throw new NumberFormatException(nombre + " tiene un valor inválido.");
        }

        if (maximo == Double.MAX_VALUE && valor != Math.floor(valor)) {
            throw new NumberFormatException(nombre + " debe ser un número entero.");
        }
    }

    private List<BeneficioDTO> obtenerBeneficios() {
        List<BeneficioDTO> beneficios = new ArrayList<>();

        if (checkB_descuentos_productos.isSelected()) {
            double valor = Double.parseDouble(campo_txt_descuentos_productos.getText().trim());

            beneficios.add(new BeneficioDTO(new ObjectId().toHexString(), "Descuento en productos", valor, "%"));
        }

        if (checkB_sesiones_gratis.isSelected()) {
            double valor = Double.parseDouble(
                    campo_texto_sesiones_gratis.getText().trim()
            );

            beneficios.add(new BeneficioDTO(new ObjectId().toHexString(), "Sesiones gratuitas", valor, "sesiones"));
        }

        if (checkB_descuento_cursos.isSelected()) {
            double valor = Double.parseDouble(
                    campo_texto_descuento_cursos.getText().trim()
            );

            beneficios.add(new BeneficioDTO(new ObjectId().toHexString(), "Descuento en cursos", valor, "%"));
        }

        if (checkB_invitaciones_mensuales.isSelected()) {
            double valor = Double.parseDouble(
                    campo_texto_invitaciones_mensuales.getText().trim()
            );

            beneficios.add(new BeneficioDTO(new ObjectId().toHexString(), "Invitaciones mensuales", valor, "invitaciones"));
        }

        return beneficios;
    }
    
    private void seleccionarImagen() {
        JFileChooser selector = new JFileChooser();

        FileNameExtensionFilter filtro = new FileNameExtensionFilter( "Imagenes JPG, JPEG y PNG", "jpg", "jpeg", "png");

        selector.setFileFilter(filtro);

        int resultado = selector.showOpenDialog(this);

        if (resultado == JFileChooser.APPROVE_OPTION) {

            imagenSeleccionada = selector.getSelectedFile();

            mostrarImagen(imagenSeleccionada);
        }
    }
    
    private String guardarImagen() throws IOException {
        if (imagenSeleccionada == null) {
            return null;
        }

        Path carpeta = Paths.get("imagenes", "membresias");

        Files.createDirectories(carpeta);

        String nombre = imagenSeleccionada.getName();

        String extension = nombre.substring(nombre.lastIndexOf("."));

        String nuevoNombre = UUID.randomUUID() + extension;

        Path destino = carpeta.resolve(nuevoNombre);

        Files.copy(imagenSeleccionada.toPath(), destino, StandardCopyOption.REPLACE_EXISTING);

        return destino.toString().replace("\\", "/");
    }
    
    private void actualizarMembresia() {
        try {

            String nombre = campo_txt_nombre_membresiaN.getText().trim();

            if (nombre.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Ingresa el nombre de la membresía.");
                return;
            }

            String textoPrecio = campo_txt_precio_membresiaN.getText().trim();

            if (textoPrecio.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Ingresa el precio de la membresía.");
                return;
            }

            double precio = Double.parseDouble(textoPrecio);

            if (precio <= 0) {
                JOptionPane.showMessageDialog(this, "El precio debe ser mayor que cero.");
                return;
            }

            validarBeneficios();

            // Si no se seleccionó una imagen nueva, conservar la anterior
            String rutaImagen = membresiaOriginal.getImagen();

            if (imagenSeleccionada != null) {
                rutaImagen = guardarImagen();
            }

            // Crear DTO conservando el ID original
            MembresiaDTO membresiaActualizada = new MembresiaDTO(
                    membresiaOriginal.getIdMembresia(),
                    nombre,
                    precio,
                    rutaImagen,
                    membresiaOriginal.getEstado(),
                    membresiaOriginal.getFechaInicio(),
                    membresiaOriginal.getFechaFin(),
                    obtenerBeneficios()
            );

            membresiaServicios.actualizarMembresia(membresiaActualizada);

            JOptionPane.showMessageDialog(this, "Membresia actualizada correctamente.");

            if (pantallaAnterior != null) {
                pantallaAnterior.cargarMembresias();
            }
            ControlNavegacion.obtenerInstancia().regresar(this, pantallaAnterior);

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Verifica que los valores numéricos sean correctos.");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "No se pudo actualizar la membresía: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void configurarApariencia() {
        // Fondo transparente de los checkbox
        checkB_descuentos_productos.setOpaque(false);
        checkB_sesiones_gratis.setOpaque(false);
        checkB_descuento_cursos.setOpaque(false);
        checkB_invitaciones_mensuales.setOpaque(false);

        // Texto blanco de los checkbox
        checkB_descuentos_productos.setForeground(Color.WHITE);
        checkB_sesiones_gratis.setForeground(Color.WHITE);
        checkB_descuento_cursos.setForeground(Color.WHITE);
        checkB_invitaciones_mensuales.setForeground(Color.WHITE);

        // Configurar campos de texto
        JTextField[] campos = {
            campo_txt_nombre_membresiaN,
            campo_txt_precio_membresiaN,
            campo_txt_descuentos_productos,
            campo_texto_sesiones_gratis,
            campo_texto_descuento_cursos,
            campo_texto_invitaciones_mensuales
        };

        for (JTextField campo : campos) {
            campo.setBackground(new Color(51, 51, 51));
            campo.setForeground(Color.WHITE);
            campo.setCaretColor(Color.WHITE);
            campo.setSelectionColor(new Color(255, 0, 51));
        }

        // Habilitar el campo del precio
        campo_txt_precio_membresiaN.setEnabled(true);
        campo_txt_precio_membresiaN.setEditable(true);
        campo_txt_precio_membresiaN.setFocusable(true);

        // Configurar panel de vista previa
        jPanel2.removeAll();

        jPanel2.setLayout(new BorderLayout());
        jPanel2.setPreferredSize(new Dimension(250, 180));
        jPanel2.setBackground(new Color(51, 51, 51));

        // Centrar imagen y texto
        txt_img_membresiaN.setHorizontalAlignment(SwingConstants.CENTER);

        txt_img_membresiaN.setVerticalAlignment(SwingConstants.CENTER);

        txt_img_membresiaN.setText("Vista Previa");
        txt_img_membresiaN.setIcon(null);

        jPanel2.add(txt_img_membresiaN, BorderLayout.CENTER);
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
        txt_nombre_membreciaN = new javax.swing.JLabel();
        campo_txt_nombre_membresiaN = new javax.swing.JTextField();
        txt_precio_membresiaN = new javax.swing.JLabel();
        campo_txt_precio_membresiaN = new javax.swing.JTextField();
        txt_imagen_membresiaN = new javax.swing.JLabel();
        btn_seleccionar_Imagen_membreciaN = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        txt_img_membresiaN = new javax.swing.JLabel();
        checkB_descuentos_productos = new javax.swing.JCheckBox();
        jLabel6 = new javax.swing.JLabel();
        campo_txt_descuentos_productos = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        checkB_sesiones_gratis = new javax.swing.JCheckBox();
        campo_texto_sesiones_gratis = new javax.swing.JTextField();
        checkB_descuento_cursos = new javax.swing.JCheckBox();
        campo_texto_descuento_cursos = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        checkB_invitaciones_mensuales = new javax.swing.JCheckBox();
        campo_texto_invitaciones_mensuales = new javax.swing.JTextField();
        jSeparator2 = new javax.swing.JSeparator();
        btn_aceptar_agregar_membresia = new javax.swing.JButton();
        btn_volver_agregar_membresia = new javax.swing.JButton();
        btnEliminar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(0, 0, 0));

        jLabel1.setFont(new java.awt.Font("Arial", 0, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("AGREGAR MEMBRESIA");

        jSeparator1.setForeground(new java.awt.Color(255, 0, 0));

        txt_nombre_membreciaN.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        txt_nombre_membreciaN.setForeground(new java.awt.Color(255, 255, 255));
        txt_nombre_membreciaN.setText("Nombre de membresia: ");

        campo_txt_nombre_membresiaN.setBackground(new java.awt.Color(51, 51, 51));
        campo_txt_nombre_membresiaN.setForeground(new java.awt.Color(255, 255, 255));
        campo_txt_nombre_membresiaN.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        campo_txt_nombre_membresiaN.setToolTipText("");
        campo_txt_nombre_membresiaN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                campo_txt_nombre_membresiaNActionPerformed(evt);
            }
        });

        txt_precio_membresiaN.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        txt_precio_membresiaN.setForeground(new java.awt.Color(255, 255, 255));
        txt_precio_membresiaN.setText("Precio: ");

        campo_txt_precio_membresiaN.setBackground(new java.awt.Color(51, 51, 51));
        campo_txt_precio_membresiaN.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        txt_imagen_membresiaN.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        txt_imagen_membresiaN.setForeground(new java.awt.Color(255, 255, 255));
        txt_imagen_membresiaN.setText("Imagen: ");

        btn_seleccionar_Imagen_membreciaN.setBackground(new java.awt.Color(51, 51, 51));
        btn_seleccionar_Imagen_membreciaN.setForeground(new java.awt.Color(255, 255, 255));
        btn_seleccionar_Imagen_membreciaN.setText("Seleccionar Imagen");

        jPanel2.setBackground(new java.awt.Color(51, 51, 51));

        txt_img_membresiaN.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        txt_img_membresiaN.setForeground(new java.awt.Color(255, 255, 255));
        txt_img_membresiaN.setText("Vista Previa");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(47, 47, 47)
                .addComponent(txt_img_membresiaN)
                .addContainerGap(54, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(61, 61, 61)
                .addComponent(txt_img_membresiaN, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(78, Short.MAX_VALUE))
        );

        checkB_descuentos_productos.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        checkB_descuentos_productos.setForeground(new java.awt.Color(255, 255, 255));
        checkB_descuentos_productos.setText("Descuentos en productos: ");
        checkB_descuentos_productos.setToolTipText("");
        checkB_descuentos_productos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                checkB_descuentos_productosActionPerformed(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("Beneficios:");

        campo_txt_descuentos_productos.setBackground(new java.awt.Color(51, 51, 51));
        campo_txt_descuentos_productos.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        campo_txt_descuentos_productos.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("%");

        checkB_sesiones_gratis.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        checkB_sesiones_gratis.setForeground(new java.awt.Color(255, 255, 255));
        checkB_sesiones_gratis.setText("Sesiones gratuitas: ");
        checkB_sesiones_gratis.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                checkB_sesiones_gratisActionPerformed(evt);
            }
        });

        campo_texto_sesiones_gratis.setBackground(new java.awt.Color(51, 51, 51));
        campo_texto_sesiones_gratis.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        checkB_descuento_cursos.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        checkB_descuento_cursos.setForeground(new java.awt.Color(255, 255, 255));
        checkB_descuento_cursos.setText("Descuento en cursos: ");

        campo_texto_descuento_cursos.setBackground(new java.awt.Color(51, 51, 51));
        campo_texto_descuento_cursos.setForeground(new java.awt.Color(255, 255, 255));
        campo_texto_descuento_cursos.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("%");

        checkB_invitaciones_mensuales.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        checkB_invitaciones_mensuales.setForeground(new java.awt.Color(255, 255, 255));
        checkB_invitaciones_mensuales.setText("Invitaciones mensuales:");

        campo_texto_invitaciones_mensuales.setBackground(new java.awt.Color(51, 51, 51));
        campo_texto_invitaciones_mensuales.setForeground(new java.awt.Color(255, 255, 255));
        campo_texto_invitaciones_mensuales.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        btn_aceptar_agregar_membresia.setBackground(new java.awt.Color(255, 0, 51));
        btn_aceptar_agregar_membresia.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btn_aceptar_agregar_membresia.setForeground(new java.awt.Color(255, 255, 255));
        btn_aceptar_agregar_membresia.setText("Aceptar");

        btn_volver_agregar_membresia.setBackground(new java.awt.Color(255, 0, 51));
        btn_volver_agregar_membresia.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btn_volver_agregar_membresia.setForeground(new java.awt.Color(255, 255, 255));
        btn_volver_agregar_membresia.setText("Volver");
        btn_volver_agregar_membresia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_volver_agregar_membresiaActionPerformed(evt);
            }
        });

        btnEliminar.setBackground(new java.awt.Color(255, 0, 51));
        btnEliminar.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnEliminar.setForeground(new java.awt.Color(255, 255, 255));
        btnEliminar.setText("Eliminar");
        btnEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSeparator1, javax.swing.GroupLayout.Alignment.TRAILING)
            .addComponent(jSeparator2, javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(btnEliminar, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(126, 126, 126)
                        .addComponent(btn_aceptar_agregar_membresia, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(21, 21, 21)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(txt_precio_membresiaN)
                            .addComponent(txt_nombre_membreciaN)
                            .addComponent(txt_imagen_membresiaN))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(campo_txt_nombre_membresiaN)
                            .addComponent(campo_txt_precio_membresiaN)
                            .addComponent(btn_seleccionar_Imagen_membreciaN, javax.swing.GroupLayout.DEFAULT_SIZE, 249, Short.MAX_VALUE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel6)
                                .addGap(145, 145, 145)
                                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(checkB_sesiones_gratis)
                                .addGap(51, 51, 51)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(campo_texto_sesiones_gratis)
                                    .addComponent(campo_texto_descuento_cursos)))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(checkB_invitaciones_mensuales)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(campo_texto_invitaciones_mensuales, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel7)
                            .addComponent(jLabel8)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(checkB_descuentos_productos)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(campo_txt_descuentos_productos, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(checkB_descuento_cursos)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 37, Short.MAX_VALUE)
                .addComponent(btn_volver_agregar_membresia, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addGap(184, 184, 184))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_nombre_membreciaN)
                    .addComponent(campo_txt_nombre_membresiaN, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_precio_membresiaN)
                    .addComponent(campo_txt_precio_membresiaN, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_imagen_membresiaN)
                    .addComponent(btn_seleccionar_Imagen_membreciaN))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 111, Short.MAX_VALUE)
                        .addComponent(jLabel7)
                        .addGap(41, 41, 41))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel6)
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(checkB_descuentos_productos, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(campo_txt_descuentos_productos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(checkB_sesiones_gratis)
                            .addComponent(campo_texto_sesiones_gratis, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(9, 9, 9)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(checkB_descuento_cursos)
                            .addComponent(campo_texto_descuento_cursos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel8)
                    .addComponent(checkB_invitaciones_mensuales)
                    .addComponent(campo_texto_invitaciones_mensuales, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btn_aceptar_agregar_membresia, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnEliminar))
                    .addComponent(btn_volver_agregar_membresia))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(21, 21, 21))
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

    private void campo_txt_nombre_membresiaNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_campo_txt_nombre_membresiaNActionPerformed
    }//GEN-LAST:event_campo_txt_nombre_membresiaNActionPerformed

    private void checkB_descuentos_productosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_checkB_descuentos_productosActionPerformed
    }//GEN-LAST:event_checkB_descuentos_productosActionPerformed

    private void checkB_sesiones_gratisActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_checkB_sesiones_gratisActionPerformed
    }//GEN-LAST:event_checkB_sesiones_gratisActionPerformed

    private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarActionPerformed
        int respuesta = JOptionPane.showConfirmDialog(this, "¿Estás seguro de que deseas eliminar esta membresía?", "Confirmar eliminación", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);

        if (respuesta == JOptionPane.YES_OPTION) {
            try {

                membresiaServicios.eliminarMembresia(membresiaOriginal.getIdMembresia());

                JOptionPane.showMessageDialog(this, "Membresía eliminada correctamente.");

                if (pantallaAnterior != null) {
                    pantallaAnterior.cargarMembresias();
                }
                ControlNavegacion.obtenerInstancia().regresar(this, pantallaAnterior);

            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "No se pudo eliminar la membresía: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }//GEN-LAST:event_btnEliminarActionPerformed

    private void btn_volver_agregar_membresiaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_volver_agregar_membresiaActionPerformed
        ControlNavegacion.obtenerInstancia().regresar(this, pantallaAnterior);
    }//GEN-LAST:event_btn_volver_agregar_membresiaActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnEliminar;
    private javax.swing.JButton btn_aceptar_agregar_membresia;
    private javax.swing.JButton btn_seleccionar_Imagen_membreciaN;
    private javax.swing.JButton btn_volver_agregar_membresia;
    private javax.swing.JTextField campo_texto_descuento_cursos;
    private javax.swing.JTextField campo_texto_invitaciones_mensuales;
    private javax.swing.JTextField campo_texto_sesiones_gratis;
    private javax.swing.JTextField campo_txt_descuentos_productos;
    private javax.swing.JTextField campo_txt_nombre_membresiaN;
    private javax.swing.JTextField campo_txt_precio_membresiaN;
    private javax.swing.JCheckBox checkB_descuento_cursos;
    private javax.swing.JCheckBox checkB_descuentos_productos;
    private javax.swing.JCheckBox checkB_invitaciones_mensuales;
    private javax.swing.JCheckBox checkB_sesiones_gratis;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JLabel txt_imagen_membresiaN;
    private javax.swing.JLabel txt_img_membresiaN;
    private javax.swing.JLabel txt_nombre_membreciaN;
    private javax.swing.JLabel txt_precio_membresiaN;
    // End of variables declaration//GEN-END:variables
}

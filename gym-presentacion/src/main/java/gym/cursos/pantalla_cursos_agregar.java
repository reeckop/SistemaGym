package gym.cursos;

import gym.excepciones.NegocioException;
import gym.navegacion.ControlNavegacion;
import gym.servicios.dto.CursoDTO;
import gym.servicios.dto.HorarioDTO;
import gym.servicios.interfaces.ICursoServicios;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;
import org.bson.types.ObjectId;

/**
 * Permite registrar un curso y configurar sus horarios.
 *
 * @author PC GAMER MASTER RACE
 */
public class pantalla_cursos_agregar extends JFrame {

    private final pantalla_cursos_principal pantallaAnterior;
    private final ICursoServicios cursoServicios;

    private final JTextField campoNombre = new JTextField();
    private final JTextField campoPrecio = new JTextField("150");
    private final JTextField campoCupo = new JTextField("20");
    private final JTextField campoFecha = new JTextField(LocalDate.now().plusDays(1).toString());
    private final JTextField campoHoraInicio = new JTextField("08:00");
    private final JTextField campoHoraFin = new JTextField("10:00");

    private final JComboBox<String> comboDisciplina = new JComboBox<>(new String[]{"Box", "Spinning", "Yoga", "Zumba"});
    private final JComboBox<String> comboDias = new JComboBox<>(new String[]{
        "Lunes a Viernes",
        "Lunes, Miércoles y Viernes",
        "Martes y Jueves",
        "Sábado y Domingo"
    });

    private String rutaImagen = "/gym/ventas/productos/accesorios.jpg";
    private JLabel etiquetaImagen;

    public pantalla_cursos_agregar(pantalla_cursos_principal pantallaAnterior, ICursoServicios cursoServicios) {
        this.pantallaAnterior = pantallaAnterior;
        this.cursoServicios = cursoServicios;
        construirPantalla();
    }

    private void construirPantalla() {
        setTitle("Agregar curso");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(760, 600);
        setLocationRelativeTo(pantallaAnterior);

        JPanel fondo = new JPanel(new BorderLayout(16, 16));
        fondo.setBackground(new Color(16, 16, 16));
        fondo.setBorder(BorderFactory.createEmptyBorder(24, 32, 24, 32));
        setContentPane(fondo);

        JLabel titulo = new JLabel("AGREGAR CURSO", JLabel.CENTER);
        titulo.setForeground(Color.WHITE);
        titulo.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 26));
        fondo.add(titulo, BorderLayout.NORTH);

        JPanel campos = new JPanel(new GridLayout(0, 2, 12, 12));
        campos.setOpaque(false);
        agregarCampo(campos, "Nombre del curso", campoNombre);
        agregarCampo(campos, "Disciplina", comboDisciplina);
        agregarCampo(campos, "Precio ($)", campoPrecio);
        agregarCampo(campos, "Cupo", campoCupo);
        agregarCampo(campos, "Días", comboDias);
        agregarCampo(campos, "Fecha inicial (AAAA-MM-DD)", campoFecha);
        agregarCampo(campos, "Hora de inicio (HH:mm)", campoHoraInicio);
        agregarCampo(campos, "Hora de fin (HH:mm)", campoHoraFin);

        JButton botonImagen = new JButton("Seleccionar imagen");
        etiquetaImagen = new JLabel("Imagen local del proyecto", JLabel.CENTER);
        etiquetaImagen.setForeground(Color.LIGHT_GRAY);
        botonImagen.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evento) {
                seleccionarImagen();
            }
        });
        campos.add(botonImagen);
        campos.add(etiquetaImagen);
        fondo.add(campos, BorderLayout.CENTER);

        JPanel acciones = new JPanel();
        acciones.setOpaque(false);
        JButton botonAceptar = crearBoton("+ Aceptar");
        JButton botonVolver = crearBoton("Volver");
        botonAceptar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evento) {
                guardarCurso();
            }
        });
        botonVolver.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evento) {
                ControlNavegacion.obtenerInstancia().regresar(pantalla_cursos_agregar.this, pantallaAnterior);
            }
        });
        acciones.add(botonAceptar);
        acciones.add(botonVolver);
        fondo.add(acciones, BorderLayout.SOUTH);
    }

    private void agregarCampo(JPanel panel, String texto, java.awt.Component campo) {
        JLabel etiqueta = new JLabel(texto);
        etiqueta.setForeground(Color.WHITE);
        panel.add(etiqueta);
        if (campo instanceof JTextField) {
            campo.setBackground(new Color(40, 40, 40));
            campo.setForeground(Color.WHITE);
        }
        panel.add(campo);
    }

    private JButton crearBoton(String texto) {
        JButton boton = new JButton(texto);
        boton.setBackground(new Color(205, 0, 0));
        boton.setForeground(Color.WHITE);
        return boton;
    }

    private void seleccionarImagen() {
        JFileChooser selector = new JFileChooser();
        selector.setFileFilter(new FileNameExtensionFilter("Imágenes (PNG, JPG, GIF)", "png", "jpg", "jpeg", "gif"));
        int resultado = selector.showOpenDialog(this);
        if (resultado == JFileChooser.APPROVE_OPTION) {
            rutaImagen = selector.getSelectedFile().getAbsolutePath();
            etiquetaImagen.setText(selector.getSelectedFile().getName());
        }
    }

    private void guardarCurso() {
        try {
            String nombre = campoNombre.getText().trim();
            if (nombre.isEmpty()) {
                throw new IllegalArgumentException("Escribe el nombre del curso.");
            }

            double precio = Double.parseDouble(campoPrecio.getText().trim());
            int cupo = Integer.parseInt(campoCupo.getText().trim());
            LocalDate fechaInicial = LocalDate.parse(campoFecha.getText().trim());
            LocalTime horaInicio = LocalTime.parse(campoHoraInicio.getText().trim());
            LocalTime horaFin = LocalTime.parse(campoHoraFin.getText().trim());

            validarDatos(precio, cupo, fechaInicial, horaInicio, horaFin);

            List<HorarioDTO> horarios = crearHorarios(fechaInicial, horaInicio, horaFin);
            if (horarios.isEmpty()) {
                throw new IllegalArgumentException("La fecha no coincide con los días seleccionados.");
            }

            String categoria = (String) comboDisciplina.getSelectedItem();
            CursoDTO curso = new CursoDTO(
                    new ObjectId().toHexString(),
                    categoria + " - " + nombre,
                    precio,
                    cupo,
                    guardarImagenLocal(),
                    horarios);
            cursoServicios.registrarCurso(curso);

            JOptionPane.showMessageDialog(this, "Curso guardado correctamente.");
            pantallaAnterior.recargarCursos();
            ControlNavegacion.obtenerInstancia().regresar(this, pantallaAnterior);
        } catch (NumberFormatException evento) {
            mostrarError("Precio y cupo deben ser números válidos.");
        } catch (java.time.DateTimeException evento) {
            mostrarError("Usa una fecha AAAA-MM-DD y horas HH:mm.");
        } catch (IllegalArgumentException evento) {
            mostrarError(evento.getMessage());
        } catch (NegocioException evento) {
            mostrarError(evento.getMessage());
        }
    }

    private void validarDatos(double precio, int cupo, LocalDate fecha, LocalTime horaInicio, LocalTime horaFin) {
        if (precio <= 0 || cupo <= 0) {
            throw new IllegalArgumentException("Precio y cupo deben ser mayores que cero.");
        }
        if (!horaInicio.isBefore(horaFin)) {
            throw new IllegalArgumentException("La hora de inicio debe ser anterior a la hora final.");
        }
        if (fecha.isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("La fecha debe ser hoy o posterior.");
        }
        if (fecha.equals(LocalDate.now()) && !horaInicio.isAfter(LocalTime.now())) {
            throw new IllegalArgumentException("La hora de inicio debe ser posterior a la hora actual.");
        }
    }

    private List<HorarioDTO> crearHorarios(LocalDate fechaInicial, LocalTime horaInicio, LocalTime horaFin) {
        List<HorarioDTO> horarios = new ArrayList<>();
        String diasSeleccionados = (String) comboDias.getSelectedItem();

        for (int desplazamiento = 0; desplazamiento < 7; desplazamiento++) {
            LocalDate fecha = fechaInicial.plusDays(desplazamiento);
            int diaSemana = fecha.getDayOfWeek().getValue();
            if (coincideDia(diasSeleccionados, diaSemana)) {
                HorarioDTO horario = new HorarioDTO(
                        new ObjectId().toHexString(), fecha, horaInicio, horaFin);
                horarios.add(horario);
            }
        }
        return horarios;
    }

    private boolean coincideDia(String seleccion, int diaSemana) {
        if ("Lunes a Viernes".equals(seleccion)) {
            return diaSemana <= 5;
        }
        if ("Lunes, Miércoles y Viernes".equals(seleccion)) {
            return diaSemana == 1 || diaSemana == 3 || diaSemana == 5;
        }
        if ("Martes y Jueves".equals(seleccion)) {
            return diaSemana == 2 || diaSemana == 4;
        }
        return "Sábado y Domingo".equals(seleccion) && diaSemana >= 6;
    }

    private String guardarImagenLocal() {
        if (rutaImagen.startsWith("/gym/")) {
            return rutaImagen;
        }

        try {
            Path origen = Path.of(rutaImagen);
            String nombreArchivo = origen.getFileName().toString();
            int puntoExtension = nombreArchivo.lastIndexOf('.');
            String extension = puntoExtension >= 0 ? nombreArchivo.substring(puntoExtension).toLowerCase(Locale.ROOT) : ".png";
            String nuevoNombre = "curso-" + new ObjectId().toHexString() + extension;
            Path destino = Path.of("gym-presentacion", "src", "main", "resources", "gym", "cursos", nuevoNombre);

            Files.createDirectories(destino.getParent());
            Files.copy(origen, destino, StandardCopyOption.REPLACE_EXISTING);
            return "/gym/cursos/" + nuevoNombre;
        } catch (IOException | RuntimeException evento) {
            return rutaImagen;
        }
    }

    private void mostrarError(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "No se guardó el curso", JOptionPane.WARNING_MESSAGE);
    }
}

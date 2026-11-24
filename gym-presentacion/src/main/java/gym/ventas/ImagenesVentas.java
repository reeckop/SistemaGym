package gym.ventas;

import gym.servicios.dto.ProductoDTO;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.net.URL;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

/**
 * Carga y ajusta las imágenes de los productos de venta.
 *
 * @author PC GAMER MASTER RACE
 */
final class ImagenesVentas {

    private ImagenesVentas() {
    }

    static JPanel crearPanel(ProductoDTO producto, Color fondo) {
        Image imagen = cargarImagenProducto(producto);
        return crearPanel(imagen, fondo);
    }

    static ImageIcon obtener(ProductoDTO producto, int ancho, int alto) {
        Image imagen = cargarImagenProducto(producto);
        int anchoOriginal = imagen.getWidth(null);
        int altoOriginal = imagen.getHeight(null);
        double escala = Math.min((double) ancho / anchoOriginal, (double) alto / altoOriginal);
        int anchoEscalado = (int) Math.round(anchoOriginal * escala);
        int altoEscalado = (int) Math.round(altoOriginal * escala);
        return new ImageIcon(imagen.getScaledInstance(
                anchoEscalado, altoEscalado, Image.SCALE_SMOOTH));
    }

    static JPanel crearPanel(String ruta, Color fondo) {
        URL recurso = ImagenesVentas.class.getResource(ruta);
        if (recurso == null) {
            recurso = ImagenesVentas.class.getResource("/gym/ventas/productos/accesorios.jpg");
        }
        return crearPanel(new ImageIcon(recurso).getImage(), fondo);
    }

    private static JPanel crearPanel(final Image imagen, Color fondo) {
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
        panel.setOpaque(true);
        panel.setBackground(fondo);
        panel.setPreferredSize(new Dimension(300, 230));
        return panel;
    }

    private static Image cargarImagenProducto(ProductoDTO producto) {
        String ruta = rutaImagenProducto(producto);
        if (ruta != null && !ruta.isBlank()) {
            URL recurso = ImagenesVentas.class.getResource(
                    ruta.startsWith("/") ? ruta : "/gym/ventas/productos/" + ruta);
            if (recurso != null) {
                return new ImageIcon(recurso).getImage();
            }

            File archivo = new File(ruta);
            if (archivo.isFile()) {
                return new ImageIcon(ruta).getImage();
            }
        }

        String imagenCategoria = imagenCategoria(producto.getCategoria());
        URL recurso = ImagenesVentas.class.getResource(
                "/gym/ventas/productos/" + imagenCategoria);
        return new ImageIcon(recurso).getImage();
    }

    private static String rutaImagenProducto(ProductoDTO producto) {
        String nombre = producto.getNombre() == null
                ? ""
                : producto.getNombre().toLowerCase(java.util.Locale.ROOT);

        if (nombre.contains("mancuerna") || nombre.contains("dumbbell")) {
            return "/gym/ventas/productos/mancuernas.png";
        }
        if (nombre.contains("cuerda") || nombre.contains("jump rope")) {
            return "/gym/ventas/productos/cuerda.png";
        }
        if (nombre.contains("creatina")) {
            return "/gym/ventas/productos/creatina.png";
        }
        if (nombre.contains("whey") || nombre.contains("proteína") || nombre.contains("proteina")) {
            return "/gym/ventas/productos/whey.png";
        }
        if (nombre.contains("playera") || nombre.contains("camiseta") || nombre.contains("shirt")) {
            return "/gym/ventas/productos/playera.png";
        }
        if (nombre.contains("guante")) {
            return "/gym/ventas/productos/guantes.png";
        }
        return producto.getImagen();
    }

    static String imagenCategoria(String categoria) {
        String valor = categoria == null ? "" : categoria.toLowerCase(java.util.Locale.ROOT);
        if (valor.contains("suplement") || valor.contains("prote")) {
            return "suplementos.jpg";
        }
        if (valor.contains("ropa") || valor.contains("shirt") || valor.contains("playera")) {
            return "ropa.jpg";
        }
        return "accesorios.jpg";
    }
}

package gym.membresias;

import java.awt.Image;
import java.io.File;
import javax.swing.ImageIcon;

/**
 * Carga y ajusta las imágenes de las membresías.
 *
 * @author PC GAMER MASTER RACE
 */
public final class ImagenMembresia {
    private ImagenMembresia() {
    }

    public static ImageIcon cargar(String ruta, int ancho, int alto) {
        if (ruta == null || ruta.isBlank()) return null;
        ImageIcon icono = null;
        java.net.URL recurso = ImagenMembresia.class.getResource(ruta.startsWith("/") ? ruta : "/gym/membresias/" + ruta);
        if (recurso != null) {
            icono = new ImageIcon(recurso);
        } else {
            File archivo = new File(ruta);
            if (archivo.isFile()) icono = new ImageIcon(archivo.getAbsolutePath());
        }
        if (icono == null || icono.getIconWidth() <= 0 || icono.getIconHeight() <= 0) return null;
        double escala = Math.min((double) ancho / icono.getIconWidth(), (double) alto / icono.getIconHeight());
        int nuevoAncho = Math.max(1, (int) (icono.getIconWidth() * escala));
        int nuevoAlto = Math.max(1, (int) (icono.getIconHeight() * escala));
        Image imagen = icono.getImage().getScaledInstance(nuevoAncho, nuevoAlto, Image.SCALE_SMOOTH);
        return new ImageIcon(imagen);
    }
}

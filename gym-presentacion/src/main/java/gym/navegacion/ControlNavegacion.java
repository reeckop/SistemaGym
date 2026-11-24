package gym.navegacion;


import gym.navegacion.ControlNavegacion;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Objects;
import javax.swing.JFrame;

/**
 *  Coordina el cambio y el regreso entre las ventanas de la aplicación.
 *
 * @author PC GAMER MASTER RACE
 */
public final class ControlNavegacion {
    private static final ControlNavegacion INSTANCIA = new ControlNavegacion();

    private ControlNavegacion() {
    }

    public static ControlNavegacion obtenerInstancia() {
        return INSTANCIA;
    }

    public void abrir(JFrame actual, JFrame siguiente) {
        Objects.requireNonNull(siguiente, "La pantalla siguiente es obligatoria");
        if (actual != null) {
            actual.setVisible(false);
            siguiente.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            siguiente.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosing(WindowEvent evento) {
                    if (actual.isDisplayable()) {
                        actual.setVisible(true);
                    }
                }
            });
        }
        siguiente.setLocationRelativeTo(actual);
        siguiente.setVisible(true);
    }

    public void regresar(JFrame actual, JFrame anterior) {
        if (actual != null) {
            actual.dispose();
        }
        if (anterior != null && anterior.isDisplayable()) {
            anterior.setVisible(true);
            anterior.toFront();
        }
    }

    public void reemplazar(JFrame actual, JFrame siguiente) {
        Objects.requireNonNull(siguiente, "La pantalla siguiente es obligatoria");
        if (actual != null) {
            actual.dispose();
        }
        siguiente.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        siguiente.setLocationRelativeTo(null);
        siguiente.setVisible(true);
    }
}

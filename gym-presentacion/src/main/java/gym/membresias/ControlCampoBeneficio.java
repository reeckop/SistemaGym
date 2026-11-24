package gym.membresias;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import javax.swing.JCheckBox;
import javax.swing.JTextField;
/**
 * Valida los campos utilizados para capturar beneficios.
 *
 * @author drago
 */
final class ControlCampoBeneficio implements ItemListener {

    private final JCheckBox casilla;
    private final JTextField campo;

    ControlCampoBeneficio(JCheckBox casilla, JTextField campo) {
        this.casilla = casilla;
        this.campo = campo;
    }

    @Override
    public void itemStateChanged(ItemEvent evento) {
        campo.setEnabled(casilla.isSelected());
    }
}

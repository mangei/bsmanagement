package cw.boardingschoolmanagement.gui.component;

import javax.swing.Action;
import javax.swing.Icon;
import javax.swing.JRadioButton;
import javax.swing.JToolTip;

/**
 *
 * @author ManuelG
 */
public class CWRadioButton extends JRadioButton{

    CWRadioButton(String text, Icon icon, boolean selected) {
        super(text, icon, selected);
    }

    CWRadioButton(String text, Icon icon) {
        super(text, icon);
    }

    CWRadioButton(String text, boolean selected) {
        super(text, selected);
    }

    CWRadioButton(String text) {
        super(text);
    }

    CWRadioButton(Icon icon, boolean selected) {
        super(icon, selected);
    }

    CWRadioButton(Action a) {
        super(a);
    }

    CWRadioButton(Icon icon) {
        super(icon);
    }

    CWRadioButton() {
    }


    @Override
    public JToolTip createToolTip() {
        return new CWToolTip();
    }
}

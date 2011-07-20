package cw.boardingschoolmanagement.gui.component;

import javax.swing.Action;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JToolTip;

/**
 *
 * @author ManuelG
 */
public class CWButton extends JButton {

    CWButton(String text, Icon icon) {
        super(text, icon);
    }

    CWButton(Action a) {
        super(a);
    }

    CWButton(String text) {
        super(text);
    }

    CWButton(Icon icon) {
        super(icon);
    }

    CWButton() {
        super();
    }

    @Override
    public JToolTip createToolTip() {
        return new CWToolTip();
    }
}

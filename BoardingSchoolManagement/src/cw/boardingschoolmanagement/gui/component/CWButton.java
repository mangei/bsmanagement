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

    public CWButton(String text, Icon icon) {
        super(text, icon);
    }

    public CWButton(Action a) {
        super(a);
    }

    public CWButton(String text) {
        super(text);
    }

    public CWButton(Icon icon) {
        super(icon);
    }

    public CWButton() {
    }

    @Override
    public JToolTip createToolTip() {
        return new CWToolTip();
    }
}

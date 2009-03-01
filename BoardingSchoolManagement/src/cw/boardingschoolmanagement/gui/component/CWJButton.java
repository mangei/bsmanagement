package cw.boardingschoolmanagement.gui.component;

import javax.swing.Action;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JToolTip;

/**
 *
 * @author ManuelG
 */
public class CWJButton extends JButton {

    public CWJButton(String text, Icon icon) {
        super(text, icon);
    }

    public CWJButton(Action a) {
        super(a);
    }

    public CWJButton(String text) {
        super(text);
    }

    public CWJButton(Icon icon) {
        super(icon);
    }

    public CWJButton() {
    }

    @Override
    public JToolTip createToolTip() {
        return new CWJToolTip();
    }
}

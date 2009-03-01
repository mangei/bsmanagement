package cw.boardingschoolmanagement.gui.component;

import javax.swing.Icon;
import javax.swing.JLabel;
import javax.swing.JToolTip;

/**
 *
 * @author ManuelG
 */
public class CWJLabel extends JLabel {

    public CWJLabel() {
    }

    public CWJLabel(Icon image) {
        super(image);
    }

    public CWJLabel(Icon image, int horizontalAlignment) {
        super(image, horizontalAlignment);
    }

    public CWJLabel(String text) {
        super(text);
    }

    public CWJLabel(String text, int horizontalAlignment) {
        super(text, horizontalAlignment);
    }

    public CWJLabel(String text, Icon icon, int horizontalAlignment) {
        super(text, icon, horizontalAlignment);
    }

    @Override
    public JToolTip createToolTip() {
        return new CWJToolTip();
    }
}

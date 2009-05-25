package cw.boardingschoolmanagement.gui.component;

import javax.swing.Icon;
import javax.swing.JLabel;
import javax.swing.JToolTip;

/**
 *
 * @author ManuelG
 */
public class CWLabel extends JLabel {

    public CWLabel() {
    }

    public CWLabel(Icon image) {
        super(image);
    }

    public CWLabel(Icon image, int horizontalAlignment) {
        super(image, horizontalAlignment);
    }

    public CWLabel(String text) {
        super(text);
    }

    public CWLabel(String text, int horizontalAlignment) {
        super(text, horizontalAlignment);
    }

    public CWLabel(String text, Icon icon, int horizontalAlignment) {
        super(text, icon, horizontalAlignment);
    }

    @Override
    public JToolTip createToolTip() {
        return new CWToolTip();
    }
}

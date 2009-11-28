package cw.boardingschoolmanagement.gui.component;

import javax.swing.Icon;
import javax.swing.JLabel;
import javax.swing.JToolTip;

/**
 *
 * @author ManuelG
 */
public class CWLabel extends JLabel {

    CWLabel() {
    }

    CWLabel(Icon image) {
        super(image);
    }

    CWLabel(Icon image, int horizontalAlignment) {
        super(image, horizontalAlignment);
    }

    CWLabel(String text) {
        super(text);
    }

    CWLabel(String text, int horizontalAlignment) {
        super(text, horizontalAlignment);
    }

    CWLabel(String text, Icon icon, int horizontalAlignment) {
        super(text, icon, horizontalAlignment);
    }

    @Override
    public JToolTip createToolTip() {
        return new CWToolTip();
    }
    }

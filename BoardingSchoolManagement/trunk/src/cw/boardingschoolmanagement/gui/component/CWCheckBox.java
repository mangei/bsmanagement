package cw.boardingschoolmanagement.gui.component;

import javax.swing.Action;
import javax.swing.Icon;
import javax.swing.JCheckBox;

/**
 *
 * @author ManuelG
 */
public class CWCheckBox extends JCheckBox {

    CWCheckBox(String text, Icon icon, boolean selected) {
        super(text, icon, selected);
    }

    CWCheckBox(String text, Icon icon) {
        super(text, icon);
    }

    CWCheckBox(String text, boolean selected) {
        super(text, selected);
    }

    CWCheckBox(Action a) {
        super(a);
    }

    CWCheckBox(String text) {
        super(text);
    }

    CWCheckBox(Icon icon, boolean selected) {
        super(icon, selected);
    }

    CWCheckBox(Icon icon) {
        super(icon);
    }

    CWCheckBox() {
    }
}

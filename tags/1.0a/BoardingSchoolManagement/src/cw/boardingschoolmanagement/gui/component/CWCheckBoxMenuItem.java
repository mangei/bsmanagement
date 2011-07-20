package cw.boardingschoolmanagement.gui.component;

import javax.swing.Action;
import javax.swing.Icon;
import javax.swing.JCheckBoxMenuItem;

/**
 *
 * @author ManuelG
 */
public class CWCheckBoxMenuItem extends JCheckBoxMenuItem {

    public CWCheckBoxMenuItem(String text, Icon icon, boolean b) {
        super(text, icon, b);
    }

    public CWCheckBoxMenuItem(String text, boolean b) {
        super(text, b);
    }

    public CWCheckBoxMenuItem(String text, Icon icon) {
        super(text, icon);
    }

    public CWCheckBoxMenuItem(Action a) {
        super(a);
    }

    public CWCheckBoxMenuItem(String text) {
        super(text);
    }

    public CWCheckBoxMenuItem(Icon icon) {
        super(icon);
    }

    public CWCheckBoxMenuItem() {
    }

}

package cw.boardingschoolmanagement.gui.component;

import javax.swing.Action;
import javax.swing.Icon;
import javax.swing.JMenuItem;

/**
 *
 * @author ManuelG
 */
public class CWMenuItem extends JMenuItem {

    CWMenuItem(String text, int mnemonic) {
        super(text, mnemonic);
    }

    CWMenuItem(String text, Icon icon) {
        super(text, icon);
    }

    CWMenuItem(Action a) {
        super(a);
    }

    CWMenuItem(String text) {
        super(text);
    }

    CWMenuItem(Icon icon) {
        super(icon);
    }

    CWMenuItem() {
    }
}

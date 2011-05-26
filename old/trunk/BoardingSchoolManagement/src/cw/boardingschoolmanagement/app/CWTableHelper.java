package cw.boardingschoolmanagement.app;

import cw.boardingschoolmanagement.gui.model.ExtendedListSelectionModel;
import javax.swing.DefaultListModel;

/**
 *
 * @author ManuelG
 */
public class CWTableHelper {

    private CWTableHelper() {
    }

    public static Object getSelection(DefaultListModel tm, ExtendedListSelectionModel lm) {
        return tm.get(lm.getSelectedIndex());
    }
}

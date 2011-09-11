package cw.boardingschoolmanagement.app;

import javax.swing.DefaultListModel;

import cw.boardingschoolmanagement.gui.model.ExtendedListSelectionModel;

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

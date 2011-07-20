package cw.boardingschoolmanagement.gui.component;

import org.jdesktop.swingx.JXTreeTable;
import org.jdesktop.swingx.treetable.TreeTableModel;

/**
 *
 * @author ManuelG
 */
public class CWTreeTable extends JXTreeTable {

    public CWTreeTable() {
        super();
    }

    public CWTreeTable(TreeTableModel treeModel) {
        super(treeModel);
    }
}

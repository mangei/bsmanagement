package cw.boardingschoolmanagement.gui.model;

import javax.swing.DefaultListSelectionModel;

import org.jdesktop.swingx.JXTable;

/**
 *
 * @author ManuelG
 */
public class ExtendedListSelectionModel extends DefaultListSelectionModel {

    private JXTable table;

    public ExtendedListSelectionModel() {
    }

    public ExtendedListSelectionModel(JXTable table) {
        this.table = table;
    }

    public boolean hasSelection() {
        return !isSelectionEmpty();
    }

    public int getSelectedCount() {
        if(isSelectionEmpty()) {
            return 0;
        }

        int selected = 0;

        for(int i=getMinSelectionIndex(), j= getMaxSelectionIndex(); i<=j; i++) {
            if(isSelectedIndex(i)) {
                selected++;
            }
        }

        return selected;
    }


    public int getSelectedIndex() {
        if(isSelectionEmpty()) {
            return -1;
        }

        int idx = -1;
        if(table != null) {
            idx = table.convertRowIndexToModel(getMinSelectionIndex());
        } else {
            idx = getMinSelectionIndex();
        }

        return idx;
    }

    public int[] getSelectedIndizes() {
        if(isSelectionEmpty()) {
            return new int[0];
        }

        int[] li = new int[getSelectedCount()];

        for(int i=getMinSelectionIndex(), j= getMaxSelectionIndex(), k=0; i<=j; i++) {
            if(isSelectedIndex(i)) {
                if(table != null) {
                    li[k] = table.convertRowIndexToModel(i);
                } else {
                    li[k] = i;
                }
                k++;
            }
        }

        return li;
    }

    public int convertRowIndexToModel(int idx) {
        return table.convertRowIndexToModel(idx);
    }

}

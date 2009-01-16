package cw.boardingschoolmanagement.gui.model;

import javax.swing.DefaultListSelectionModel;

/**
 *
 * @author ManuelG
 */
public class ExtendedListSelectionModel extends DefaultListSelectionModel {

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
        return getMinSelectionIndex();
    }

    public int[] getSelectedIndizes() {
        if(isSelectionEmpty()) {
            return new int[0];
        }

        int[] li = new int[getSelectedCount()];

        for(int i=getMinSelectionIndex(), j= getMaxSelectionIndex(), k=0; i<=j; i++) {
            if(isSelectedIndex(i)) {
                li[k] = i;
                k++;
            }
        }

        return li;
    }


}

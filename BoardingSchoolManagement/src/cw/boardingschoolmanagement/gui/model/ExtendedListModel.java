package cw.boardingschoolmanagement.gui.model;

import java.util.Collection;
import java.util.Iterator;

import javax.swing.DefaultListModel;

/**
 * Extended TableModel with more functionallity.
 * @author ManuelG
 */
public class ExtendedListModel extends DefaultListModel {

    public void add(Object o) {
        addElement(o);
    }

    public void addAll(Collection c) {
        Iterator it = c.iterator();
        while(it.hasNext()) {
            add(it.next());
        }
    }

    public void remove(Object o) {
        removeElement(o);
    }

}

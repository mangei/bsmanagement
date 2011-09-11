package cw.boardingschoolmanagement.gui.model;

import java.util.Collection;

import javax.swing.table.DefaultTableModel;

/**
 * Extended TableModel with more functionallity.
 * @author ManuelG
 */
public class ExtendedTableModel1 extends DefaultTableModel {

    public Object get(int idx) {
        return dataVector.get(idx);
    }

    public boolean remove(Object o) {
        int idx = dataVector.indexOf(o);
        boolean ret = dataVector.remove(o);
        if(ret) {
            fireTableRowsDeleted(idx, idx);
        }
        return ret;
    }

    public synchronized boolean add(Object e) {
        boolean ret = dataVector.add(e);
        if(ret) {
            int idx = dataVector.indexOf(e);
            fireTableRowsInserted(idx, idx);
            System.out.println("update");
        }
        return ret;
    }

    public synchronized int size() {
        return dataVector.size();
    }

    public boolean contains(Object o) {
        return dataVector.contains(o);
    }

    public int indexOf(Object o) {
        return dataVector.indexOf(o);
    }

    public void clear() {
        dataVector.clear();
    }

    public synchronized boolean addAll(Collection c) {
        return dataVector.addAll(c);
    }

}

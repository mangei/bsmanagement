package cw.customermanagementmodul.pojo.manager;

//import XMLdao.XMLPropertiesDAO;
import java.util.ArrayList;
import java.util.List;
import javax.swing.AbstractListModel;
import javax.swing.event.TableModelEvent;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;
import cw.boardingschoolmanagement.pojo.Property;

/**
 *
 * @author CreativeWorkers.at
 */
public class PropertiesCollection
extends AbstractListModel
{

    private List<Property> list;
//    private AbstractListModelImpl listModel;
    private AbstractTableModelImpl tableModel;

    public PropertiesCollection() {
        list = new ArrayList<Property>();
//        listModel = new AbstractListModelImpl();
        tableModel = new AbstractTableModelImpl();
    }
    
    public void loadList() {
//        list = XMLPropertiesDAO.getInstance().getAllElements();
    }

    public void addProperty(Property p) {
        boolean added = list.add(p);
        if(added) {
            int index = list.size()-1;
//            XMLPropertiesDAO.getInstance().insertElement(p);
            fireIntervalAdded(this, index, index);
//            listModel.fireIntervalAdded(this, index, index);
            tableModel.fireTableRowsInserted(index, index);
        }
    }
    
    public void removeProperty(Property p) {
        int index = list.indexOf(p);
        if(index != -1) {
            boolean removed = list.remove(p);
            if(removed) {
                fireIntervalRemoved(this, index, index);
//                XMLPropertiesDAO.getInstance().removeElement(p);
//                listModel.fireIntervalRemoved(this, index, index);
                tableModel.fireTableRowsDeleted(index, index);
            }
        }
    }
    
    public void removeProperty(int index) {
        Property p = list.remove(index);
        if(p != null) {
            fireIntervalRemoved(this, index, index);
//            XMLPropertiesDAO.getInstance().removeElement(p);
//            listModel.fireIntervalRemoved(this, index, index);
            tableModel.fireTableRowsDeleted(index, index);
        }
    }
    
    public int getSize() {
        return list.size();
    }

    public Object getElementAt(int index) {
        return list.get(index);
    }

    public Property getPropertyAt(int index) {
        return list.get(index);
    }
    
    public int indexOf(Property p) {
        return list.indexOf(p);
    }

    public Property set(int index, Property element) {
        Property p = list.set(index, element);
//        XMLPropertiesDAO.getInstance().updateElement(p);
        fireContentsChanged(this, index, index);
//        listModel.fireContentsChanged(this, index, index);
        tableModel.fireTableRowsUpdated(index, index);
        return p;
    }

    public boolean contains(Property p) {
        return list.contains(p);
    }
    
    public List<Property> getProperties() {
        return list;
    }
    
    public int size() {
        return list.size();
    }

//    public ListModel getListModel() {
//        return listModel;
//    }

    public TableModel getTableModel() {
        return tableModel;
    }
    
//    private class AbstractListModelImpl extends AbstractListModel {
//
//        public int getSize() {
//            return list.size();
//        }
//
//        public Object getElementAt(int index) {
//            return list.get(index);
//        }
//
//        @Override
//        protected void fireContentsChanged(Object source, int index0, int index1) {
//            super.fireContentsChanged(source, index0, index1);
//        }
//
//        @Override
//        protected void fireIntervalAdded(Object source, int index0, int index1) {
//            super.fireIntervalAdded(source, index0, index1);
//        }
//
//        @Override
//        protected void fireIntervalRemoved(Object source, int index0, int index1) {
//            super.fireIntervalRemoved(source, index0, index1);
//        }
//        
//    }

    private class AbstractTableModelImpl extends AbstractTableModel {

        public int getRowCount() {
            return list.size();
        }

        public int getColumnCount() {
            return 2;
        }

        @Override
        public String getColumnName(int column) {
            switch(column) {
                case 0: return "Name";
                case 1: return "Wert";
                default: 
                    return "";
            }
        }

        @Override
        public boolean isCellEditable(int rowIndex, int columnIndex) {
            return false;
        }
        
        public Object getValueAt(int rowIndex, int columnIndex) {
            Property p = list.get(rowIndex);
            switch(columnIndex) {
                case 0: return p.getName();
                case 1: return p.getValue();
                default: 
                    return "";
            }
        }

        @Override
        public void fireTableCellUpdated(int row, int column) {
            super.fireTableCellUpdated(row, column);
        }

        @Override
        public void fireTableChanged(TableModelEvent e) {
            super.fireTableChanged(e);
        }

        @Override
        public void fireTableDataChanged() {
            super.fireTableDataChanged();
        }

        @Override
        public void fireTableRowsDeleted(int firstRow, int lastRow) {
            super.fireTableRowsDeleted(firstRow, lastRow);
        }

        @Override
        public void fireTableRowsInserted(int firstRow, int lastRow) {
            super.fireTableRowsInserted(firstRow, lastRow);
        }

        @Override
        public void fireTableRowsUpdated(int firstRow, int lastRow) {
            super.fireTableRowsUpdated(firstRow, lastRow);
        }

        @Override
        public void fireTableStructureChanged() {
            super.fireTableStructureChanged();
        }
        
    }

}

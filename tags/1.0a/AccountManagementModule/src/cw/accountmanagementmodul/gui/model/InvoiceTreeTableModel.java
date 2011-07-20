package cw.accountmanagementmodul.gui.model;

import cw.accountmanagementmodul.pojo.Invoice;
import cw.accountmanagementmodul.pojo.InvoiceItem;
import java.util.List;
import javax.swing.ListModel;
import org.jdesktop.swingx.treetable.AbstractTreeTableModel;

/**
 *
 * @author ManuelG
 */
public class InvoiceTreeTableModel
        extends AbstractTreeTableModel
{

    private ListModel listModel;

    public static final int COLUMN_NAME             = 0;
    public static final int COLUMN_CREATIONDATE     = 1;
    public static final int COLUMN_AMOUNT           = 2;
    public static final int COLUMN_STATUS           = 3;

    public InvoiceTreeTableModel(ListModel listModel) {
        super(listModel);
        this.listModel = listModel;
        System.out.println("MODEL:" + listModel.getSize());
    }

    public int getColumnCount() {
        return 4;
    }

    @Override
    public String getColumnName(int column) {
        switch (column) {
            case COLUMN_NAME:
                return "Name";
            case COLUMN_CREATIONDATE:
                return "Rechnungsdatum";
            case COLUMN_AMOUNT:
                return "Betrag";
            case COLUMN_STATUS:
                return "Status";
            default:
                return "";
        }
    }

    public Object getValueAt(Object node, int columnIndex) {
        if(node.equals(root)) {
            return "";
            
        } else if(node instanceof Invoice) {
            Invoice i = (Invoice) node;
            switch (columnIndex) {
                case COLUMN_NAME:
                    return i.getName();
                case COLUMN_CREATIONDATE:
                    return i.getCreationDate();
                case COLUMN_AMOUNT:
                    return i.getPostingGroup().getAmount();
                case COLUMN_STATUS:
                    return "-";
                default:
                    return "";
            }
        } else if(node instanceof InvoiceItem) {
            InvoiceItem i = (InvoiceItem) node;
            switch (columnIndex) {
                case COLUMN_NAME:
                    return i.getName();
                case COLUMN_CREATIONDATE:
                    return i.getCreationDate();
                case COLUMN_AMOUNT:
                    return i.getPosting().getAmount();
                case COLUMN_STATUS:
                    return "-";
                default:
                    return "";
            }
        }

        return "";
    }

    public Object getChild(Object parent, int index) {
        if(parent.equals(root)) {
            return ((ListModel) parent).getElementAt(index);
        } else if(parent instanceof Invoice) {
            return ((Invoice) parent).getInvoiceItems().get(index);
        }
        return null;
    }

    public int getChildCount(Object parent) {
        if(parent.equals(root)) {
            return ((ListModel) parent).getSize();
        } else if(parent instanceof Invoice) {
            return ((Invoice) parent).getInvoiceItems().size();
        } else if(parent instanceof InvoiceItem) {
            return 0;
        }
        return 0;
    }

    public int getIndexOfChild(Object parent, Object child) {
        if(parent.equals(root)) {
            System.out.println("INDEX OF CHILD from PARENT");
            return -1;
        } else if(parent instanceof Invoice) {
            return ((Invoice) parent).getInvoiceItems().indexOf(child);
        }
        return -1;
    }

}

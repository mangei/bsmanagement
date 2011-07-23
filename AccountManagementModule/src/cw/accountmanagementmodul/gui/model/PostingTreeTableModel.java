package cw.accountmanagementmodul.gui.model;

import cw.accountmanagementmodul.pojo.Posting;
import cw.accountmanagementmodul.pojo.PostingGroup;
import java.util.Date;
import org.jdesktop.swingx.treetable.AbstractTreeTableModel;
import org.jdesktop.swingx.treetable.TreeTableModel;

/**
 *
 * @author ManuelG
 */
public class PostingTreeTableModel
        extends AbstractTreeTableModel {

    public static final int COLUMN_NAME = 0;
    public static final int COLUMN_CREATIONDATE = 1;
    public static final int COLUMN_AMOUNT = 2;

    protected Posting postings;

    /**
     * Creates and fills up the TreeTableModel
     * @param postings An implementation of an AbstractPosting, usually a PostingGroup
     */
    public PostingTreeTableModel(Posting postings) {
        super(postings);
    }

    public int getColumnCount() {
        return 3;
    }

    @Override
    public String getColumnName(int column) {
        switch(column) {
            case COLUMN_NAME: 
                return "Name";
            case COLUMN_AMOUNT:
                return "Betrag";
            case COLUMN_CREATIONDATE:
                return "Datum";
        }
        return "";
    }

    @Override
    public Class getColumnClass(int column) {
        switch(column) {
            case COLUMN_NAME:
                return TreeTableModel.class;
            case COLUMN_AMOUNT:
                return Double.class;
            case COLUMN_CREATIONDATE:
                return Date.class;
        }
        return String.class;
    }

    public Object getValueAt(Object node, int column) {
        switch(column) {
            case COLUMN_NAME:
                return ((Posting) node).getName();
            case COLUMN_AMOUNT:
                return ((Posting) node).getAmount();
            case COLUMN_CREATIONDATE:
                return ((Posting) node).getCreationDate();
        }
        return "";
    }

    public Object getChild(Object parent, int index) {
        if(parent instanceof PostingGroup) {
            return ((PostingGroup) parent).getPostings().get(index);
        }
        return null;
    }

    public int getChildCount(Object parent) {
        // PostingGroup
        if(parent instanceof PostingGroup) {
            return ((PostingGroup) parent).getPostings().size();
        }
        // Posting
        return 0;
    }

    public int getIndexOfChild(Object parent, Object child) {
        // PostingGroup
        if(parent instanceof PostingGroup) {
            return ((PostingGroup) parent).getPostings().indexOf(child);
        }
        // Posting
        return -1;
    }

}

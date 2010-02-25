package cw.accountmanagementmodul.gui.model;

import com.jgoodies.binding.adapter.AbstractTableAdapter;
import cw.accountmanagementmodul.interfaces.PostingInterface;
import cw.accountmanagementmodul.pojo.Posting;
import cw.accountmanagementmodul.pojo.PostingGroup;
import javax.swing.ListModel;

/**
 *
 * @author ManuelG
 */
public class PostingInterfaceTableModel extends AbstractTableAdapter<PostingInterface> {

        private ListModel listModel;

        public PostingInterfaceTableModel(ListModel listModel) {
            super(listModel);
            this.listModel = listModel;
        }

        @Override
        public int getColumnCount() {
            return 7;
        }

        @Override
        public String getColumnName(int column) {
            switch (column) {
                case 0:
                    return "Bezeichnung";
                case 1:
                    return "Betrag";
                case 2:
                    return "Soll";
                case 3:
                    return "Haben";
                case 4:
                    return "Eingangsdatum";
                case 5:
                    return "Buchungsdatum";
                case 6:
                    return "Kategorie";
                default:
                    return "";
            }
        }

        @Override
        public boolean isCellEditable(int rowIndex, int columnIndex) {
            return false;
        }

        public Object getValueAt(int rowIndex, int columnIndex) {
            PostingInterface pI = (PostingInterface) listModel.getElementAt(rowIndex);
            
            if(pI instanceof Posting) {
                return getPostingValueAt((Posting)pI, columnIndex);
            } else if(pI instanceof PostingGroup) {
                return getPostingGroupValueAt((PostingGroup)pI, columnIndex);
            } return "";
        }

        private Object getPostingValueAt(Posting p, int columnIndex) {
            switch (columnIndex) {
                case 0:
                    return p.getDescription();
                case 1:
                    return p.getAmount();
                case 2:
                    return p.isLiabilities() ? p.getAmount() : "";
                case 3:
                    return p.isAssets() ? p.getAmount() : "";
                case 4:
                    return p.getPostingEntryDate();
                case 5:
                    return p.getPostingDate();
                case 6:
                    if(p.getPostingCategory() != null) {
                        return p.getPostingCategory();
                    } else {
                        return "";
                    }
                default:
                    return "";
            }
        }

        private Object getPostingGroupValueAt(PostingGroup pg, int columnIndex) {
            switch (columnIndex) {
                case 0:
                    return pg.getName();
                case 1:
                    return (pg.getLiabilites() - pg.getAssets());
                case 2:
                    return pg.getLiabilites();
                case 3:
                    return pg.getAssets();
                case 4:
                    return pg.getCreationDate();
                case 5:
                    return "";
                case 6:
                    return "";
                default:
                    return "";
            }
        }
    }

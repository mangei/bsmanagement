package cw.customermanagementmodul.gui.model;

import com.jgoodies.binding.adapter.AbstractTableAdapter;
import cw.customermanagementmodul.pojo.Posting;
import javax.swing.ListModel;

/**
 *
 * @author ManuelG
 */
public class PostingTableModel extends AbstractTableAdapter<Posting> {

        private ListModel listModel;

        public PostingTableModel(ListModel listModel) {
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
            Posting a = (Posting) listModel.getElementAt(rowIndex);
            switch (columnIndex) {
                case 0:
                    return a.getDescription();
                case 1:
                    return a.getAmount();
                case 2:
                    return a.isLiabilities() ? a.getAmount() : "";
                case 3:
                    return a.isAssets() ? a.getAmount() : "";
                case 4:
                    return a.getPostingEntryDate();
                case 5:
                    return a.getPostingDate();
                case 6:
                    if(a.getPostingCategory() != null) {
                        return a.getPostingCategory();
                    } else {
                        return "";
                    }
                default:
                    return "";
            }
        }
    }

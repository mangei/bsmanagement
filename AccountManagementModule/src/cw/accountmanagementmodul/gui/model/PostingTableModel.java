package cw.accountmanagementmodul.gui.model;

import com.jgoodies.binding.adapter.AbstractTableAdapter;
import cw.accountmanagementmodul.pojo.AccountPosting;
import javax.swing.ListModel;

/**
 *
 * @author ManuelG
 */
public class PostingTableModel extends AbstractTableAdapter<AccountPosting> {

        private ListModel listModel;

        public PostingTableModel(ListModel listModel) {
            super(listModel);
            this.listModel = listModel;
        }

        @Override
        public int getColumnCount() {
            return 4;
        }

        @Override
        public String getColumnName(int column) {
            switch (column) {
                case 0:
                    return "Bezeichnung";
                case 1:
                    return "Betrag";
                case 2:
                    return "Eingangsdatum";
                case 3:
                    return "Buchungsdatum";
                default:
                    return "";
            }
        }

        @Override
        public boolean isCellEditable(int rowIndex, int columnIndex) {
            return false;
        }

        public Object getValueAt(int rowIndex, int columnIndex) {
            AccountPosting a = (AccountPosting) listModel.getElementAt(rowIndex);
            switch (columnIndex) {
                case 0:
                    return a.getName();
                case 1:
                    return a.getAmount();
                case 2:
                    return a.getPostingEntryDate();
                case 3:
                    return a.getCreationDate();

                default:
                    return "";
            }
        }
    }

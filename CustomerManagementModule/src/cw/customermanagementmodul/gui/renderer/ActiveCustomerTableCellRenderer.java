package cw.customermanagementmodul.gui.renderer;

import cw.boardingschoolmanagement.app.CWUtils;
import java.awt.Component;
import javax.swing.Icon;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

/**
 *
 * @author ManuelG
 */
public class ActiveCustomerTableCellRenderer extends DefaultTableCellRenderer {

    private JLabel cell;
    private Icon activeIcon;
    private Icon inactiveIcon;

    public ActiveCustomerTableCellRenderer() {
        activeIcon = CWUtils.loadIcon("cw/customermanagementmodul/images/user.png");
        inactiveIcon = CWUtils.loadIcon("cw/customermanagementmodul/images/user_inactive.png");
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {

        cell = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

        boolean male = (Boolean)value;

        if(male) {
            cell.setIcon(activeIcon);
            cell.setText("aktiv");
        } else {
            cell.setIcon(inactiveIcon);
            cell.setText("inaktiv");
        }

        return cell;
    }

}

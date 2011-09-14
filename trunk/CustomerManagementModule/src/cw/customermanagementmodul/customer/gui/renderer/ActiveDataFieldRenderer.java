package cw.customermanagementmodul.customer.gui.renderer;

import java.awt.Component;

import javax.swing.Icon;
import javax.swing.JLabel;
import javax.swing.JTable;

import cw.boardingschoolmanagement.app.CWUtils;
import cw.boardingschoolmanagement.gui.model.CWDataFieldRenderer;

/**
 *
 * @author ManuelG
 */
public class ActiveDataFieldRenderer extends CWDataFieldRenderer {

    private JLabel cell;
    private Icon activeIcon;
    private Icon inactiveIcon;

    public ActiveDataFieldRenderer() {
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

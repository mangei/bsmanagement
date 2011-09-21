package cw.customermanagementmodul.customer.gui.renderer;

import java.awt.Component;

import javax.swing.Icon;
import javax.swing.JLabel;
import javax.swing.JTable;

import cw.boardingschoolmanagement.app.CWUtils;
import cw.boardingschoolmanagement.gui.model.CWDataFieldRenderer;
import cw.customermanagementmodul.customer.images.ImageDefinitionCustomer;

/**
 *
 * @author Manuel Geier
 */
public class ActiveDataFieldRenderer extends CWDataFieldRenderer {

    private JLabel cell;
    private Icon activeIcon;
    private Icon inactiveIcon;

    public ActiveDataFieldRenderer() {
        activeIcon = CWUtils.loadIcon(ImageDefinitionCustomer.CUSTOMER);
        inactiveIcon = CWUtils.loadIcon(ImageDefinitionCustomer.CUSTOMER_INACTIVE);
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

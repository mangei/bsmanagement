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
 * @author ManuelG
 */
public class GenderDataFieldRenderer extends CWDataFieldRenderer {

    private JLabel cell;
    private Icon maleIcon;
    private Icon femaleIcon;

    public GenderDataFieldRenderer() {
        maleIcon = CWUtils.loadIcon(ImageDefinitionCustomer.CUSTOMER_GENDER_MALE);
        femaleIcon = CWUtils.loadIcon(ImageDefinitionCustomer.CUSTOMER_GENDER_FEMALE);
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {

        cell = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

        boolean male = (Boolean)value;

        if(male) {
            cell.setIcon(maleIcon);
            cell.setText("Herr");
        } else {
            cell.setIcon(femaleIcon);
            cell.setText("Frau");
        }

        return cell;
    }

}

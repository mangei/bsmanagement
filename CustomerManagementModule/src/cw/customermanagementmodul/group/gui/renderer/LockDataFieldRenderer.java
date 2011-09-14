package cw.customermanagementmodul.group.gui.renderer;

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
public class LockDataFieldRenderer extends CWDataFieldRenderer {

    private JLabel cell;
    private Icon lockIcon;

    public LockDataFieldRenderer() {
        lockIcon = CWUtils.loadIcon("cw/customermanagementmodul/images/lock.png");
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {

        cell = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

        boolean locked = (Boolean)value;

        if(locked) {
            cell.setIcon(lockIcon);
            cell.setText("Gesperrt");
        } else {
            cell.setIcon(null);
            cell.setText("");
        }

        return cell;
    }

}

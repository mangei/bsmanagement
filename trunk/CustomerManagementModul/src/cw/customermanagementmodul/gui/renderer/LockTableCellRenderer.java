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
public class LockTableCellRenderer extends DefaultTableCellRenderer {

    private JLabel cell;
    private Icon lockIcon;

    public LockTableCellRenderer() {
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

package cw.boardingschoolmanagement.gui.renderer;

import java.awt.Component;

import javax.swing.Icon;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

import cw.boardingschoolmanagement.app.CWUtils;

/**
 *
 * @author ManuelG
 */
public class UpdateStatusTableCellRenderer extends DefaultTableCellRenderer {

    private JLabel cell;
    private Icon updateIcon;
    private Icon okIcon;

    public UpdateStatusTableCellRenderer() {
        updateIcon  = CWUtils.loadIcon("cw/boardingschoolmanagement/images/update_needUpdate.png");
        okIcon      = CWUtils.loadIcon("cw/boardingschoolmanagement/images/update_ok.png");
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {

        cell = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

        int status = (Integer) value;
        switch(status) {
            case 0:
                setText("");
                setIcon(okIcon);
                break;
            case 1:
                setText("aktualisieren");
                setIcon(updateIcon);
                break;
            default:
                setText("");
                setIcon(null);
        }

        return cell;
    }

}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package cw.coursemanagementmodul.renderer;

import cw.boardingschoolmanagement.app.CWUtils;
import java.awt.Component;
import javax.swing.Icon;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

/**
 *
 * @author André Salmhofer
 */
public class PostingHistoryTableCellRenderer extends DefaultTableCellRenderer{

    private JLabel cell;
    private Icon okIcon;
    private Icon warningIcon;
    private Icon infoIcon;

    public PostingHistoryTableCellRenderer() {
        okIcon = CWUtils.loadIcon("cw/coursemanagementmodul/images/ok.png");
        warningIcon = CWUtils.loadIcon("cw/coursemanagementmodul/images/warning.png");
        infoIcon = CWUtils.loadIcon("cw/coursemanagementmodul/images/info.png");
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        
        cell = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

        if(((Integer)value) == 0) {
            cell.setIcon(okIcon);
            cell.setText("OK");
        }
        if(((Integer)value) == -1) {
            cell.setIcon(warningIcon);
            cell.setText("Warnung");
        }
        if(((Integer)value) == 1) {
            cell.setIcon(infoIcon);
            cell.setText("unausgeglichens Saldo");
        }

        return cell;
    }
}

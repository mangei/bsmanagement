/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package cw.boardingschoolmanagement.gui.renderer;

import java.awt.Component;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

/**
 *
 * @author ManuelG
 */
public class DateTimeTableCellRenderer extends DefaultTableCellRenderer {

    private JLabel cell;
    private boolean dateOnly;

    public DateTimeTableCellRenderer() {
        this(false);
    }

    public DateTimeTableCellRenderer(boolean dateOnly) {
        this.cell = new JLabel();
        this.dateOnly = dateOnly;
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        System.out.println("TYPE: " + value);

        cell.setText(value.toString());

        return cell;
    }

}

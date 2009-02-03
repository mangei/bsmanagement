/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cw.roommanagementmodul.component;

import cw.boardingschoolmanagement.app.CalendarUtil;
import java.awt.Component;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

/**
 *
 * @author Dominik
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

        if (value instanceof Date) {
            GregorianCalendar gc = new GregorianCalendar();
            gc.setTime((Date) value);
            StringBuilder builder = new StringBuilder();
            int day, month;
            day = gc.get(Calendar.DATE);
            if (day < 10) {
                builder.append("0" + day);
            } else {
                builder.append(day);
            }

            builder.append(".");
            month = gc.get(Calendar.MONTH) + 1;
            if (month < 10) {
                builder.append("0" + month);
            } else {
                builder.append(month);
            }

            builder.append(".");
            builder.append(gc.get(Calendar.YEAR));
            if (!dateOnly) {
                builder.append(" ");
                builder.append(CalendarUtil.getHour(gc.get(Calendar.HOUR_OF_DAY)));
                builder.append(":");
                builder.append(CalendarUtil.getMinute(gc.get(Calendar.MINUTE)));
                builder.append(":");
                builder.append(CalendarUtil.getSecond(gc.get(Calendar.SECOND)));
            }
            cell.setText(builder.toString());
        } else {
            cell.setText("<<Not a Date-object>>");
        }

        return cell;
    }
}
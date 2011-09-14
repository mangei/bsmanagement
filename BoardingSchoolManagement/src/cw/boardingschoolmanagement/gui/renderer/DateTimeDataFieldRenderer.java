package cw.boardingschoolmanagement.gui.renderer;

import java.awt.Component;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.swing.JLabel;
import javax.swing.JTable;

import cw.boardingschoolmanagement.app.CalendarUtil;
import cw.boardingschoolmanagement.gui.model.CWDataFieldRenderer;

/**
 * Renderer for Date and Time
 *
 * @author Manuel Geier
 */
public class DateTimeDataFieldRenderer extends CWDataFieldRenderer {

    private JLabel cell;
    private boolean dateOnly;
    private String dateFormat;
    private boolean useDateFormat = false;

    public DateTimeDataFieldRenderer() {
        this(false);
    }

    public DateTimeDataFieldRenderer(boolean dateOnly) {
        this.dateOnly = dateOnly;
    }

    public DateTimeDataFieldRenderer(String dateFormat) {
        this.useDateFormat = true;
        this.dateFormat = dateFormat;
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {

        cell = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

        if(value instanceof Date) {

            if(useDateFormat) {
                SimpleDateFormat format = new SimpleDateFormat(dateFormat);
                cell.setText(format.format(value));
            } else {
                GregorianCalendar gc = new GregorianCalendar();
                gc.setTime((Date) value);
                StringBuilder builder = new StringBuilder();
//                builder.append(CalendarUtil.getDayOfWeekShort(gc.get(Calendar.DAY_OF_WEEK)));
//                builder.append(", ");
                builder.append(gc.get(Calendar.DAY_OF_MONTH));
                builder.append(".");
                builder.append(CalendarUtil.getMonth(gc.get(Calendar.MONTH)+1));
                builder.append(".");
                builder.append(gc.get(Calendar.YEAR));
                if(!dateOnly) {
                    builder.append(" ");
                    builder.append(CalendarUtil.getHour(gc.get(Calendar.HOUR_OF_DAY)));
                    builder.append(":");
                    builder.append(CalendarUtil.getMinute(gc.get(Calendar.MINUTE)));
                    builder.append(":");
                    builder.append(CalendarUtil.getSecond(gc.get(Calendar.SECOND)));
                }
                cell.setText(builder.toString());
            }
        } else {
            cell.setText("");
        }

        return cell;
    }

}

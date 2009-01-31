package cw.boardingschoolmanagement.gui;

import com.jgoodies.binding.value.ValueHolder;
import com.jgoodies.binding.value.ValueModel;
import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

/**
 *
 * @author ManuelG
 */
public class WelcomeHomeGUIExtentionPresentationModel {

    private static String MONDAY = "Montag";
    private static String TUESDAY = "Dienstag";
    private static String WEDNESDAY = "Mittwoch";
    private static String THURSDAY = "Donnerstag";
    private static String FRIDAY = "Freitag";
    private static String SATURDAY = "Samstag";
    private static String SUNDAY = "Sonntag";

    private static String JANUARY = "Jänner";
    private static String FEBRUARY = "Februar";
    private static String MARCH = "März";
    private static String APRIL = "April";
    private static String MAY = "Mai";
    private static String JUNE = "Juni";
    private static String JULY = "Juli";
    private static String AUGUST = "August";
    private static String SEPTEMBER = "September";
    private static String OCTOBER = "Oktober";
    private static String NOVEMBER = "November";
    private static String DECEMBER = "Dezember";

    private ValueModel welcomeMessageValueModel;
    private ValueModel timeMessageValueModel;

    public WelcomeHomeGUIExtentionPresentationModel() {
        initModels();
        initEventHandling();
    }

    private void initModels() {
        welcomeMessageValueModel = new ValueHolder("Willkommen in der Internatsverwaltung.");
        timeMessageValueModel = new ValueHolder("");

        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                Calendar c = Calendar.getInstance();
                StringBuilder str = new StringBuilder();
                str.append("<html>Heute ist <b>");
                switch(c.get(Calendar.DAY_OF_WEEK)) {
                    case Calendar.MONDAY:       str.append(MONDAY);      break;
                    case Calendar.TUESDAY:      str.append(TUESDAY);     break;
                    case Calendar.WEDNESDAY:    str.append(WEDNESDAY);   break;
                    case Calendar.THURSDAY:     str.append(THURSDAY);    break;
                    case Calendar.FRIDAY:       str.append(FRIDAY);      break;
                    case Calendar.SATURDAY:     str.append(SATURDAY);    break;
                    case Calendar.SUNDAY:       str.append(SUNDAY);      break;
                }
                str.append("</b>, der <b>");
                str.append(c.get(Calendar.DAY_OF_MONTH));
                str.append(". ");
                switch(c.get(Calendar.MONTH)) {
                    case Calendar.JANUARY:      str.append(JANUARY);       break;
                    case Calendar.FEBRUARY:     str.append(FEBRUARY);      break;
                    case Calendar.MARCH:        str.append(MARCH);         break;
                    case Calendar.APRIL:        str.append(APRIL);         break;
                    case Calendar.MAY:          str.append(MAY);           break;
                    case Calendar.JUNE:         str.append(JUNE);          break;
                    case Calendar.JULY:         str.append(JULY);          break;
                    case Calendar.AUGUST:       str.append(AUGUST);        break;
                    case Calendar.SEPTEMBER:    str.append(SEPTEMBER);     break;
                    case Calendar.OCTOBER:      str.append(OCTOBER);       break;
                    case Calendar.NOVEMBER:     str.append(NOVEMBER);      break;
                    case Calendar.DECEMBER:     str.append(DECEMBER);      break;
                }
                str.append(" ");
                str.append(c.get(Calendar.YEAR));
                str.append("</b> und es ist <b>");
                if(c.get(Calendar.HOUR_OF_DAY) < 10) {
                    str.append("0");
                }
                str.append(c.get(Calendar.HOUR_OF_DAY));
                str.append(":");
                if(c.get(Calendar.MINUTE) < 10) {
                    str.append("0");
                }
                str.append(c.get(Calendar.MINUTE));
                str.append(":");
                if(c.get(Calendar.SECOND) < 10) {
                    str.append("0");
                }
                str.append(c.get(Calendar.SECOND));
                str.append("</b> Uhr.</html>");
                timeMessageValueModel.setValue(str.toString());
            }
        },0, 1000);
    }

    private void initEventHandling() {

    }

    public ValueModel getTimeMessageValueModel() {
        return timeMessageValueModel;
    }

    public ValueModel getWelcomeMessageValueModel() {
        return welcomeMessageValueModel;
    }

}

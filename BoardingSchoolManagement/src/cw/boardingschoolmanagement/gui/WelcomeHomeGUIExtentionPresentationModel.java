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
                    case Calendar.MONDAY:       str.append("Montag");       break;
                    case Calendar.TUESDAY:      str.append("Dienstag");     break;
                    case Calendar.WEDNESDAY:    str.append("Mittwoch");     break;
                    case Calendar.THURSDAY:     str.append("Donnerstag");   break;
                    case Calendar.FRIDAY:       str.append("Freitag");      break;
                    case Calendar.SATURDAY:     str.append("Samstag");      break;
                    case Calendar.SUNDAY:       str.append("Sonntag");      break;
                }
                str.append("</b>, der <b>");
                str.append(c.get(Calendar.DAY_OF_MONTH));
                str.append(". ");
                switch(c.get(Calendar.MONTH)) {
                    case Calendar.JANUARY:      str.append("Jänner");       break;
                    case Calendar.FEBRUARY:     str.append("Februar");      break;
                    case Calendar.MARCH:        str.append("März");         break;
                    case Calendar.APRIL:        str.append("April");        break;
                    case Calendar.MAY:          str.append("Mai");          break;
                    case Calendar.JUNE:         str.append("Juni");         break;
                    case Calendar.JULY:         str.append("Juli");         break;
                    case Calendar.AUGUST:       str.append("August");       break;
                    case Calendar.SEPTEMBER:    str.append("September");    break;
                    case Calendar.OCTOBER:      str.append("Oktober");      break;
                    case Calendar.NOVEMBER:     str.append("November");     break;
                    case Calendar.DECEMBER:     str.append("Dezember");     break;
                }
                str.append(" ");
                str.append(c.get(Calendar.YEAR));
                str.append("</b> und es ist <b>");
                if(c.get(Calendar.HOUR_OF_DAY) < 10)
                    str.append("0");
                str.append(c.get(Calendar.HOUR_OF_DAY));
                str.append(":");
                if(c.get(Calendar.MINUTE) < 10)
                    str.append("0");
                str.append(c.get(Calendar.MINUTE));
                str.append(":");
                if(c.get(Calendar.SECOND) < 10)
                    str.append("0");
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

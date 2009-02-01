package cw.boardingschoolmanagement.gui;

import com.jgoodies.binding.value.ValueHolder;
import com.jgoodies.binding.value.ValueModel;
import cw.boardingschoolmanagement.app.CalendarUtil;
import java.util.Calendar;
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
                str.append(CalendarUtil.getDayOfWeek(c.get(Calendar.DAY_OF_WEEK)));
                str.append("</b>, der <b>");
                str.append(c.get(Calendar.DAY_OF_MONTH));
                str.append(". ");
                str.append(CalendarUtil.getMonth(c.get(Calendar.MONTH)));
                str.append(" ");
                str.append(c.get(Calendar.YEAR));
                str.append("</b> und es ist <b>");
                str.append(CalendarUtil.getHour(c.get(Calendar.HOUR_OF_DAY)));
                str.append(":");
                str.append(CalendarUtil.getMinute(c.get(Calendar.MINUTE)));
                str.append(":");
                str.append(CalendarUtil.getSecond(c.get(Calendar.SECOND)));
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

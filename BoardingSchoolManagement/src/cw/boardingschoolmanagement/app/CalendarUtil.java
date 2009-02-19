package cw.boardingschoolmanagement.app;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 *
 * @author ManuelG
 */
public class CalendarUtil {

    public static String MONDAY = "Montag";
    public static String TUESDAY = "Dienstag";
    public static String WEDNESDAY = "Mittwoch";
    public static String THURSDAY = "Donnerstag";
    public static String FRIDAY = "Freitag";
    public static String SATURDAY = "Samstag";
    public static String SUNDAY = "Sonntag";

    public static String MONDAY_SHORT = "Mo";
    public static String TUESDAY_SHORT = "Di";
    public static String WEDNESDAY_SHORT = "Mi";
    public static String THURSDAY_SHORT = "Do";
    public static String FRIDAY_SHORT = "Fr";
    public static String SATURDAY_SHORT = "Sa";
    public static String SUNDAY_SHORT = "So";

    public static String JANUARY = "Jänner";
    public static String FEBRUARY = "Februar";
    public static String MARCH = "März";
    public static String APRIL = "April";
    public static String MAY = "Mai";
    public static String JUNE = "Juni";
    public static String JULY = "Juli";
    public static String AUGUST = "August";
    public static String SEPTEMBER = "September";
    public static String OCTOBER = "Oktober";
    public static String NOVEMBER = "November";
    public static String DECEMBER = "Dezember";

    public static String JANUARY_SHORT = "Jän";
    public static String FEBRUARY_SHORT = "Feb";
    public static String MARCH_SHORT = "Mär";
    public static String APRIL_SHORT = "Apr";
    public static String MAY_SHORT = "Mai";
    public static String JUNE_SHORT = "Jun";
    public static String JULY_SHORT = "Jul";
    public static String AUGUST_SHORT = "Aug";
    public static String SEPTEMBER_SHORT = "Sep";
    public static String OCTOBER_SHORT = "Okt";
    public static String NOVEMBER_SHORT = "Nov";
    public static String DECEMBER_SHORT = "Dez";

    public static String DATEFORMAT_STANDARD = "dd.MM.yyyy";

    public static String getDayOfWeek(int dayOfWeek) {
        switch(dayOfWeek) {
            case Calendar.MONDAY:       return MONDAY;
            case Calendar.TUESDAY:      return TUESDAY;
            case Calendar.WEDNESDAY:    return WEDNESDAY;
            case Calendar.THURSDAY:     return THURSDAY;
            case Calendar.FRIDAY:       return FRIDAY;
            case Calendar.SATURDAY:     return SATURDAY;
            case Calendar.SUNDAY:       return SUNDAY;
        }
        return "";
    }

    public static String getDayOfWeekShort(int dayOfWeek) {
        switch(dayOfWeek) {
            case Calendar.MONDAY:       return MONDAY_SHORT;
            case Calendar.TUESDAY:      return TUESDAY_SHORT;
            case Calendar.WEDNESDAY:    return WEDNESDAY_SHORT;
            case Calendar.THURSDAY:     return THURSDAY_SHORT;
            case Calendar.FRIDAY:       return FRIDAY_SHORT;
            case Calendar.SATURDAY:     return SATURDAY_SHORT;
            case Calendar.SUNDAY:       return SUNDAY_SHORT;
        }
        return "";
    }

    public static String getMonth(int month) {
        switch(month) {
            case Calendar.JANUARY:      return JANUARY;
            case Calendar.FEBRUARY:     return FEBRUARY;
            case Calendar.MARCH:        return MARCH;
            case Calendar.APRIL:        return APRIL;
            case Calendar.MAY:          return MAY;
            case Calendar.JUNE:         return JUNE;
            case Calendar.JULY:         return JULY;
            case Calendar.AUGUST:       return AUGUST;
            case Calendar.SEPTEMBER:    return SEPTEMBER;
            case Calendar.OCTOBER:      return OCTOBER;
            case Calendar.NOVEMBER:     return NOVEMBER;
            case Calendar.DECEMBER:     return DECEMBER;
        }
        return "";
    }

    public static String getMonthShort(int month) {
        switch(month) {
            case Calendar.JANUARY:      return JANUARY_SHORT;
            case Calendar.FEBRUARY:     return FEBRUARY_SHORT;
            case Calendar.MARCH:        return MARCH_SHORT;
            case Calendar.APRIL:        return APRIL_SHORT;
            case Calendar.MAY:          return MAY_SHORT;
            case Calendar.JUNE:         return JUNE_SHORT;
            case Calendar.JULY:         return JULY_SHORT;
            case Calendar.AUGUST:       return AUGUST_SHORT;
            case Calendar.SEPTEMBER:    return SEPTEMBER_SHORT;
            case Calendar.OCTOBER:      return OCTOBER_SHORT;
            case Calendar.NOVEMBER:     return NOVEMBER_SHORT;
            case Calendar.DECEMBER:     return DECEMBER_SHORT;
        }
        return "";
    }

    public static String getHour(int hour) {
        StringBuilder str = new StringBuilder();
        if(hour < 10) {
            str.append("0");
        }
        str.append(hour);
        return str.toString();
    }

    public static String getMinute(int minute) {
        StringBuilder str = new StringBuilder();
        if(minute < 10) {
            str.append("0");
        }
        str.append(minute);
        return str.toString();
    }

    public static String getSecond(int second) {
        StringBuilder str = new StringBuilder();
        if(second < 10) {
            str.append("0");
        }
        str.append(second);
        return str.toString();
    }

    public static String formatDate(Date date) {
        return formatDate(date,DATEFORMAT_STANDARD);
    }

    public static String formatDate(Date date, String dateFormat) {
        if(date == null) {
            return "";
        }
        return new SimpleDateFormat(dateFormat).format(date);
    }
}

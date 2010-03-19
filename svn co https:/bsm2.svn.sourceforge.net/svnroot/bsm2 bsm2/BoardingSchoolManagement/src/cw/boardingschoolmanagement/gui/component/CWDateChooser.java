package cw.boardingschoolmanagement.gui.component;

import com.toedter.calendar.IDateEditor;
import com.toedter.calendar.JCalendar;
import com.toedter.calendar.JDateChooser;
import java.util.Date;

/**
 *
 * @author ManuelG
 */
public class CWDateChooser extends JDateChooser {

    CWDateChooser(JCalendar arg0, Date arg1, String arg2, IDateEditor arg3) {
        super(arg0, arg1, arg2, arg3);
    }

    CWDateChooser(String arg0, String arg1, char arg2) {
        super(arg0, arg1, arg2);
    }

    CWDateChooser(Date arg0, String arg1, IDateEditor arg2) {
        super(arg0, arg1, arg2);
    }

    CWDateChooser(Date arg0, String arg1) {
        super(arg0, arg1);
    }

    CWDateChooser(Date arg0) {
        super(arg0);
    }

    CWDateChooser(IDateEditor arg0) {
        super(arg0);
    }

    CWDateChooser() {
    }

}

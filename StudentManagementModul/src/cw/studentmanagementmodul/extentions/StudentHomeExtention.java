package cw.studentmanagementmodul.extentions;

import cw.studentmanagementmodul.gui.StudentHomeExtentionPresentationModel;
import cw.studentmanagementmodul.gui.StudentHomeExtentionView;
import cw.boardingschoolmanagement.extentions.interfaces.HomeExtention;
import javax.swing.JPanel;

/**
 *
 * @author ManuelG
 */
public class StudentHomeExtention implements HomeExtention {

    public JPanel getPanel() {
        return new StudentHomeExtentionView(new StudentHomeExtentionPresentationModel()).buildPanel();
    }

}

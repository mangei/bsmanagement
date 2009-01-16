package cw.studentmanagementmodul.extentions;

import cw.studentmanagementmodul.gui.StudentHomeGUIExtentionPresentationModel;
import cw.studentmanagementmodul.gui.StudentHomeGUIExtentionView;
import cw.boardingschoolmanagement.extentions.interfaces.HomeGUIExtention;
import javax.swing.JPanel;

/**
 *
 * @author ManuelG
 */
public class StudentHomeGUIExtention implements HomeGUIExtention {

    public JPanel getPanel() {
        return new StudentHomeGUIExtentionView(new StudentHomeGUIExtentionPresentationModel()).buildPanel();
    }

}

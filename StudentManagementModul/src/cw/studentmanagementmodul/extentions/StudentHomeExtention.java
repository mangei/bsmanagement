package cw.studentmanagementmodul.extentions;

import cw.boardingschoolmanagement.gui.HomePresentationModel;
import cw.studentmanagementmodul.gui.StudentHomeExtentionPresentationModel;
import cw.studentmanagementmodul.gui.StudentHomeExtentionView;
import cw.boardingschoolmanagement.extentions.interfaces.HomeExtention;
import javax.swing.JPanel;

/**
 *
 * @author ManuelG
 */
public class StudentHomeExtention implements HomeExtention {

    private StudentHomeExtentionPresentationModel model;
    private StudentHomeExtentionView view;

    public void initPresentationModel(HomePresentationModel homePresentationModel) {
        model = new StudentHomeExtentionPresentationModel();
        view = new StudentHomeExtentionView(model);
    }

    public JPanel getPanel() {
        return view.buildPanel();
    }

    public void dispose() {
        view.dispose();
    }

    public Object getModel() {
        return model;
    }

}

package cw.studentmanagementmodul.extention;

import cw.boardingschoolmanagement.gui.HomePresentationModel;
import cw.studentmanagementmodul.gui.StudentHomeExtentionPresentationModel;
import cw.studentmanagementmodul.gui.StudentHomeExtentionView;
import cw.boardingschoolmanagement.extention.point.HomeExtention;
import cw.boardingschoolmanagement.gui.component.CWPanel;

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

    public CWPanel getView() {
        return view;
    }

    public void dispose() {
        view.dispose();
    }

    public Object getModel() {
        return model;
    }

}

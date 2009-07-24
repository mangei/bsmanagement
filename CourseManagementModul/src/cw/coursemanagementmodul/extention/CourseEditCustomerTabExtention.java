package cw.coursemanagementmodul.extention;

import cw.boardingschoolmanagement.gui.component.CWPanel;
import cw.customermanagementmodul.gui.EditCustomerPresentationModel;
import java.util.List;
import cw.coursemanagementmodul.gui.EditCoursePartPresentationModel;
import cw.coursemanagementmodul.gui.EditCoursePartView;
import cw.coursemanagementmodul.pojo.CourseParticipant;
import cw.coursemanagementmodul.pojo.manager.CourseParticipantManager;
import cw.customermanagementmodul.extention.point.EditCustomerTabExtentionPoint;

/**
 *
 * @author André Salmhofer (CreativeWorkers)
 */
public class CourseEditCustomerTabExtention implements EditCustomerTabExtentionPoint {

    private static EditCoursePartPresentationModel model;
    private static EditCoursePartView view;
    private static CourseParticipant cp;

    public void initPresentationModel(EditCustomerPresentationModel editCustomerModel) {
        cp = CourseParticipantManager.getInstance().get(editCustomerModel.getBean());
        System.out.println("_____________NEW CP: ["+cp+"]: Kunde: " + editCustomerModel.getBean().getSurname());
        if(cp == null) {

            cp = new CourseParticipant(editCustomerModel.getBean());
        }
        model = new EditCoursePartPresentationModel(cp, editCustomerModel.getUnsaved());
        view = new EditCoursePartView(model);
    }

    public CWPanel getView() {
        return view;
    }

    public void save() {
        model.save();
    }

    public void reset() {
        model.reset();
    }

    public List<String> validate() {
        return null;
    }

    public void dispose() {
        view.dispose();
    }

    public int priority() {
        return 50;
    }

    public Object getModel() {
        return model;
    }

}

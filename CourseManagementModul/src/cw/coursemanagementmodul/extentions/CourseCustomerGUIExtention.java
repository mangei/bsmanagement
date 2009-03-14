package cw.coursemanagementmodul.extentions;

import cw.customermanagementmodul.gui.EditCustomerPresentationModel;
import java.util.List;
import javax.swing.JComponent;
import cw.coursemanagementmodul.gui.EditCoursePartPresentationModel;
import cw.coursemanagementmodul.gui.EditCoursePartView;
import cw.coursemanagementmodul.pojo.CourseParticipant;
import cw.coursemanagementmodul.pojo.manager.CourseParticipantManager;
import cw.customermanagementmodul.extentions.interfaces.EditCustomerTabExtention;

/**
 *
 * @author André Salmhofer
 */
public class CourseCustomerGUIExtention implements EditCustomerTabExtention {

    private static EditCoursePartPresentationModel model;
    private static EditCoursePartView view;
    private static CourseParticipant cp;

    public JComponent getView() {
        return view.buildPanel();
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

    public void initPresentationModel(EditCustomerPresentationModel editCustomerModel) {
        cp = CourseParticipantManager.getInstance().get(editCustomerModel.getBean());
        System.out.println("_____________NEW CP: ["+cp+"]: Kunde: " + editCustomerModel.getBean().getSurname());
        if(cp == null) {
            
            cp = new CourseParticipant(editCustomerModel.getBean());
        }
        model = new EditCoursePartPresentationModel(cp, editCustomerModel.getUnsaved());
        view = new EditCoursePartView(model);
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

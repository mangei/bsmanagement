package cw.coursemanagementmodul.extentions;

import com.jgoodies.binding.value.ValueModel;
import cw.customermanagementmodul.pojo.Customer;
import javax.swing.JComponent;
import cw.coursemanagementmodul.gui.EditCoursePartPresentationModel;
import cw.coursemanagementmodul.gui.EditCoursePartView;
import cw.coursemanagementmodul.pojo.CourseParticipant;
import cw.coursemanagementmodul.pojo.manager.CourseParticipantManager;
import cw.customermanagementmodul.extentions.interfaces.EditCustomerGUITabExtention;

/**
 *
 * @author André Salmhofer
 */
public class CourseCustomerGUIExtention implements EditCustomerGUITabExtention {

    private static EditCoursePartPresentationModel model;
    private static EditCoursePartView view;
    private static CourseParticipant cp;

    public void initPresentationModel(Customer customer, ValueModel unsaved) {
        cp = CourseParticipantManager.getInstance().get(customer);
        if(cp == null) {
            cp = new CourseParticipant(customer);
        }
        model = new EditCoursePartPresentationModel(cp,unsaved);
        view = new EditCoursePartView(model);

        System.out.println("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
    }

    public JComponent getView() {
        return view.buildPanel();
    }

    public void save() {
        model.save();
        CourseParticipantManager.getInstance().save(cp);
    }

    public void reset() {
        model.reset();
    }

}

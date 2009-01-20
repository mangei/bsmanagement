package cw.studentmanagementmodul.extentions;

import com.jgoodies.binding.value.ValueModel;
import cw.customermanagementmodul.extentions.interfaces.EditCustomerGUITabExtention;
import cw.studentmanagementmodul.gui.StudentCustomerGUIExtentionPresentationModel;
import cw.studentmanagementmodul.gui.StudentCustomerGUIExtentionView;
import javax.swing.JComponent;
import cw.customermanagementmodul.pojo.Customer;
import cw.studentmanagementmodul.pojo.Student;
import cw.studentmanagementmodul.pojo.manager.StudentManager;

/**
 *
 * @author Manuel Geier
 */
public class StudentEditCustomerGUITabExtention
implements EditCustomerGUITabExtention
{
    private static StudentCustomerGUIExtentionView view;
    private static StudentCustomerGUIExtentionPresentationModel presentationModel;
    private static Student student;
    
    public void initPresentationModel(Customer customer, ValueModel unsaved) {

        student = StudentManager.getInstance().get(customer);
        if(student == null) {
            student = new Student(customer);
        }
        presentationModel = new StudentCustomerGUIExtentionPresentationModel(student, unsaved);

    }

    public JComponent getView() {
        view = new StudentCustomerGUIExtentionView(presentationModel);
        return view.buildPanel();
    }

    public void save() {
        presentationModel.save();
        StudentManager.getInstance().save(student);
    }

    public void reset() {
        presentationModel.reset();
    }

}

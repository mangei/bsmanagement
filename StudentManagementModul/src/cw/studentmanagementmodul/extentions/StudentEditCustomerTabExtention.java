package cw.studentmanagementmodul.extentions;

import cw.customermanagementmodul.extentions.interfaces.EditCustomerTabExtention;
import cw.customermanagementmodul.gui.EditCustomerPresentationModel;
import cw.studentmanagementmodul.gui.StudentEditCustomerTabExtentionPresentationModel;
import cw.studentmanagementmodul.gui.StudentEditCustomerTabExtentionView;
import java.util.List;
import javax.swing.JComponent;
import cw.studentmanagementmodul.pojo.Student;
import cw.studentmanagementmodul.pojo.manager.StudentManager;

/**
 *
 * @author Manuel Geier
 */
public class StudentEditCustomerTabExtention
implements EditCustomerTabExtention
{
    private static StudentEditCustomerTabExtentionView view;
    private static StudentEditCustomerTabExtentionPresentationModel presentationModel;
    private static Student student;
    
        public void initPresentationModel(EditCustomerPresentationModel editCustomerModel) {

        student = StudentManager.getInstance().get(editCustomerModel.getBean());
        if(student == null) {
            student = new Student(editCustomerModel.getBean());
            
            // TODO TEST  'v'
//            StudentManager.getInstance().save(student);
        }
        presentationModel = new StudentEditCustomerTabExtentionPresentationModel(student, editCustomerModel.getUnsaved());

    }

    public JComponent getView() {
        view = new StudentEditCustomerTabExtentionView(presentationModel);
        return view.buildPanel();
    }

    public void save() {
        presentationModel.save();
        StudentManager.getInstance().save(student);
    }

    public void reset() {
        presentationModel.reset();
    }

    public List<String> validate() {
        return null;
    }

}

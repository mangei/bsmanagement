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
    private static StudentEditCustomerTabExtentionPresentationModel model;
    private static Student student;
    
        public void initPresentationModel(EditCustomerPresentationModel editCustomerModel) {

        student = StudentManager.getInstance().get(editCustomerModel.getBean());
        if(student == null) {
            student = new Student(editCustomerModel.getBean());
            
            // TODO TEST  'v'
//            StudentManager.getInstance().save(student);
        }
        model = new StudentEditCustomerTabExtentionPresentationModel(student, editCustomerModel.getUnsaved());

    }

    public JComponent getView() {
        view = new StudentEditCustomerTabExtentionView(model);
        return view.buildPanel();
    }

    public void save() {
        model.save();
        StudentManager.getInstance().save(student);
    }

    public void reset() {
        model.reset();
    }

    public List<String> validate() {
        return null;
    }

    public Object getModel() {
        return model;
    }

    public void dispose() {
        view.dispose();
    }

    public int priority() {
        return 0;
    }

}

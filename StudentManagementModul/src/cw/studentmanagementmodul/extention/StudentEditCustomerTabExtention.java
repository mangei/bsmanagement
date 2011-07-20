package cw.studentmanagementmodul.extention;

import cw.boardingschoolmanagement.gui.component.CWPanel;
import cw.customermanagementmodul.extention.point.EditCustomerTabExtentionPoint;
import cw.customermanagementmodul.gui.EditCustomerPresentationModel;
import cw.studentmanagementmodul.gui.StudentEditCustomerPresentationModel;
import cw.studentmanagementmodul.gui.StudentEditCustomerView;
import java.util.List;
import cw.studentmanagementmodul.pojo.Student;
import cw.studentmanagementmodul.pojo.manager.StudentManager;

/**
 *
 * @author Manuel Geier
 */
public class StudentEditCustomerTabExtention
implements EditCustomerTabExtentionPoint
{
    private StudentEditCustomerView view;
    private StudentEditCustomerPresentationModel model;
    private Student student;
    
    public void initPresentationModel(EditCustomerPresentationModel editCustomerModel) {

        student = StudentManager.getInstance().get(editCustomerModel.getBean());
        if(student == null) {
            student = new Student(editCustomerModel.getBean());
            
            // TODO TEST  'v'
//            StudentManager.getInstance().save(student);
        }
        
        model = new StudentEditCustomerPresentationModel(student, editCustomerModel.getUnsaved());
        view = new StudentEditCustomerView(model);
    }

    public CWPanel getView() {
        return view;
    }

    public void save() {
        model.save();
        StudentManager.getInstance().save(student);
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

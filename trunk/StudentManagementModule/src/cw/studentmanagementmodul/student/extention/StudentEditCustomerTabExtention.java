package cw.studentmanagementmodul.student.extention;

import cw.boardingschoolmanagement.extention.point.CWIEditPresentationModelExtentionPoint;
import cw.boardingschoolmanagement.gui.CWEditPresentationModel;
import cw.boardingschoolmanagement.gui.component.CWView;
import cw.customermanagementmodul.customer.gui.EditCustomerEditCustomerView;
import cw.studentmanagementmodul.student.gui.StudentEditCustomerPresentationModel;
import cw.studentmanagementmodul.student.gui.StudentEditCustomerView;
import cw.studentmanagementmodul.student.persistence.PMStudent;
import cw.studentmanagementmodul.student.persistence.Student;

/**
 *
 * @author Manuel Geier
 */
public class StudentEditCustomerTabExtention
implements CWIEditPresentationModelExtentionPoint<EditCustomerEditCustomerView>
{
    private StudentEditCustomerView view;
    private StudentEditCustomerPresentationModel model;
    private Student student;
    private EditCustomerEditCustomerView baseView;

	@Override
	public Class<?> getViewExtentionClass() {
		return EditCustomerEditCustomerView.class;
	}

	@Override
	public void initComponents(EditCustomerEditCustomerView baseView) {
		this.baseView = baseView;
		
		student = PMStudent.getInstance().get(baseView.getModel().getEditCustomerPresentationModel().getBean());
        if(student == null) {
            student = new Student(baseView.getModel().getEditCustomerPresentationModel().getBean());
        }
		
		model = new StudentEditCustomerPresentationModel(student, baseView.getModel().getEditCustomerPresentationModel().getUnsaved());
        view = new StudentEditCustomerView(model);
        view.initComponents();
	}

	@Override
	public void buildView() {
		view.buildView();
		baseView.addToContentPanel(view);
	}

	@Override
	public void dispose() {
		view.dispose();
	}

	@Override
	public CWEditPresentationModel<?> getModel() {
		return model;
	}

	@Override
	public CWView<?> getView() {
		return view;
	}

}

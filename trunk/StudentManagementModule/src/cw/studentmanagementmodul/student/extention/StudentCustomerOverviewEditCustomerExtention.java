package cw.studentmanagementmodul.student.extention;

import com.jgoodies.binding.value.ValueModel;
import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

import cw.boardingschoolmanagement.extention.point.CWIViewExtentionPoint;
import cw.boardingschoolmanagement.gui.CWPresentationModel;
import cw.boardingschoolmanagement.gui.component.CWComponentFactory;
import cw.boardingschoolmanagement.gui.component.CWLabel;
import cw.boardingschoolmanagement.gui.component.CWView;
import cw.customermanagementmodul.customer.gui.CustomerOverviewEditCustomerView;
import cw.studentmanagementmodul.student.gui.StudentEditCustomerPresentationModel;
import cw.studentmanagementmodul.student.persistence.Student;

/**
 *
 * @author Manuel Geier
 */
public class StudentCustomerOverviewEditCustomerExtention
        implements CWIViewExtentionPoint<CustomerOverviewEditCustomerView> {

	private CWView<CWPresentationModel> view;
    private CWPresentationModel model;

	@Override
	public Class<?> getViewExtentionClass() {
		return CustomerOverviewEditCustomerView.class;
	}

	@Override
	public void initComponents(CustomerOverviewEditCustomerView baseView) {
		model = new CWPresentationModel(baseView.getModel().getEntityManager()) {
			
			@Override
			public void dispose() {
				// TODO Auto-generated method stub
				
			}
		};
		view = new CWView<CWPresentationModel>(model) {
			
		    private CWLabel lStudentClass;
			
			public void initComponents() {
				super.initComponents();
			
		        ValueModel newValueModel;

		        lStudentClass = CWComponentFactory.createLabel( newValueModel =
		                ((StudentEditCustomerPresentationModel)
		                    ((StudentEditCustomerTabExtention)model
		                        .getEditCustomerPresentationModel()
		                        .getExtention(StudentEditCustomerTabExtention.class))
		                        .getModel()).getBufferedModel(Student.PROPERTYNAME_STUDENTCLASS)
		                    );

		        CWComponentFactory.createNullLabel(lStudentClass, "keiner Klasse zugeordnet");

		        getComponentContainer()
		        	.addComponent(lStudentClass);
				
			};
			
			public void buildView() {
				super.buildView();
				
				FormLayout layout = new FormLayout(
		                "right:pref, 4dlu, pref:grow",
		                "pref, 4dlu, pref"
		        );

		        PanelBuilder builder = new PanelBuilder(layout);
		        CellConstraints cc = new CellConstraints();

		        builder.addSeparator("Schuelerinformation",  cc.xyw(1, 1, 3));
		        builder.addLabel("Klasse:",                  cc.xy(1, 3));
		        builder.add(lStudentClass,                  cc.xy(3, 3));
		        
		        addToContentPanel(builder.getPanel());
			};
			
			public void dispose() {};
			
		};
	}

	@Override
	public void buildView() {
		view.buildView();
	}

	@Override
	public void dispose() {
		view.dispose();
	}

}

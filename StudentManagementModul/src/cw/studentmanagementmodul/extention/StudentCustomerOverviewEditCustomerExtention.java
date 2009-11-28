package cw.studentmanagementmodul.extention;

import com.jgoodies.binding.value.ValueModel;
import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;
import cw.boardingschoolmanagement.gui.component.CWComponentFactory;
import cw.boardingschoolmanagement.gui.component.CWLabel;
import cw.boardingschoolmanagement.gui.component.CWPanel;
import cw.customermanagementmodul.extention.point.CustomerOverviewEditCustomerExtentionPoint;
import cw.customermanagementmodul.gui.CustomerOverviewEditCustomerPresentationModel;
import cw.studentmanagementmodul.gui.StudentEditCustomerPresentationModel;
import cw.studentmanagementmodul.pojo.Student;

/**
 *
 * @author Manuel Geier
 */
public class StudentCustomerOverviewEditCustomerExtention
        implements CustomerOverviewEditCustomerExtentionPoint {

    private CustomerOverviewEditCustomerPresentationModel customerOverviewEditCustomerPresentationModel;
    private CWComponentFactory.CWComponentContainer componentContainer;
    private CWLabel lStudentClass;

    public void initPresentationModel(CustomerOverviewEditCustomerPresentationModel customerOverviewEditCustomerPresentationModel) {
        this.customerOverviewEditCustomerPresentationModel = customerOverviewEditCustomerPresentationModel;
        componentContainer = new CWComponentFactory.CWComponentContainer();
    }
    
    public CWPanel getView() {
        CWPanel panel = CWComponentFactory.createPanel();

        ValueModel newValueModel;

        lStudentClass = CWComponentFactory.createLabel( newValueModel =
                ((StudentEditCustomerPresentationModel)
                    ((StudentEditCustomerTabExtention)customerOverviewEditCustomerPresentationModel
                        .getEditCustomerPresentationModel()
                        .getExtention(StudentEditCustomerTabExtention.class))
                        .getModel()).getBufferedModel(Student.PROPERTYNAME_STUDENTCLASS)
                    );

        CWComponentFactory.createNullLabel(lStudentClass, "keiner Klasse zugeordnet");

        componentContainer.addComponent(lStudentClass);

        FormLayout layout = new FormLayout(
                "right:pref, 4dlu, pref:grow",
                "pref, 4dlu, pref"
        );

        PanelBuilder builder = new PanelBuilder(layout,panel);
        CellConstraints cc = new CellConstraints();

        builder.addSeparator("Schülerinformation",  cc.xyw(1, 1, 3));
        builder.addLabel("Klasse:",                  cc.xy(1, 3));
        builder.add(lStudentClass,                  cc.xy(3, 3));

        return panel;
    }

    public void dispose() {
        componentContainer.dispose();
    }

    public int priority() {
        return 0;
    }

}

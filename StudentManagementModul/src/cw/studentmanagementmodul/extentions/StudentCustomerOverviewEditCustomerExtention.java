package cw.studentmanagementmodul.extentions;

import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;
import cw.boardingschoolmanagement.app.CWComponentFactory;
import cw.boardingschoolmanagement.gui.component.CWJLabel;
import cw.boardingschoolmanagement.gui.component.CWJPanel;
import cw.customermanagementmodul.extentions.interfaces.CustomerOverviewEditCustomerExtention;
import cw.customermanagementmodul.gui.CustomerOverviewEditCustomerPresentationModel;
import cw.studentmanagementmodul.gui.StudentEditCustomerPresentationModel;
import javax.swing.JComponent;

/**
 *
 * @author Manuel Geier
 */
public class StudentCustomerOverviewEditCustomerExtention
        implements CustomerOverviewEditCustomerExtention {

    private CustomerOverviewEditCustomerPresentationModel customerOverviewEditCustomerPresentationModel;

    private CWJLabel lStudentClass;

    public void initPresentationModel(CustomerOverviewEditCustomerPresentationModel customerOverviewEditCustomerPresentationModel) {
        this.customerOverviewEditCustomerPresentationModel = customerOverviewEditCustomerPresentationModel;
    }
    
    public JComponent getView() {
        CWJPanel panel = CWComponentFactory.createPanel();

        lStudentClass = CWComponentFactory.createLabel(
                ((StudentEditCustomerPresentationModel)
                    ((StudentEditCustomerTabExtention)customerOverviewEditCustomerPresentationModel
                        .getEditCustomerPresentationModel()
                        .getExtention(StudentEditCustomerTabExtention.class))
                        .getModel()).getStudentClassNameModel()
                    );

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
        
    }

    public int priority() {
        return 0;
    }

}

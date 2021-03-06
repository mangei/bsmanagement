package cw.studentmanagementmodul.gui;

import cw.boardingschoolmanagement.gui.component.CWComponentFactory;
import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;
import cw.boardingschoolmanagement.gui.component.CWButton;
import cw.boardingschoolmanagement.gui.component.CWCheckBox;
import cw.boardingschoolmanagement.gui.component.CWLabel;
import cw.boardingschoolmanagement.gui.component.CWView;
import cw.studentmanagementmodul.pojo.Student;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 *
 * @author Manuel Geier
 */
public class StudentEditCustomerView extends CWView
{
    private StudentEditCustomerPresentationModel model;

    private CWComponentFactory.CWComponentContainer componentContainer;
    private CWCheckBox cIsStudent;
    private CWButton bStudentClassChooser;
    private CWLabel lStudentClass;

    private PropertyChangeListener activeChangeListener;
    
    public StudentEditCustomerView(StudentEditCustomerPresentationModel model) {
        this.model = model;

        initComponents();
        buildView();
        initEventHandling();
    }

    private void initComponents() {
        cIsStudent              = CWComponentFactory.createCheckBox(model.getBufferedModel(Student.PROPERTYNAME_ACTIVE), "Kunde ist ein Schueler?");
        bStudentClassChooser    = CWComponentFactory.createButton(model.getStudentClassChooserAction());
        lStudentClass           = CWComponentFactory.createLabel(model.getBufferedModel(Student.PROPERTYNAME_STUDENTCLASS));

        CWComponentFactory.createNullLabel(lStudentClass, "keiner Klasse zugeordnet");

        componentContainer = CWComponentFactory.createComponentContainer()
                .addComponent(cIsStudent)
                .addComponent(bStudentClassChooser)
                .addComponent(lStudentClass);
    }
    
    private void initEventHandling() {
        model.getBufferedModel(Student.PROPERTYNAME_ACTIVE).addValueChangeListener(activeChangeListener = new PropertyChangeListener() {
            public void propertyChange(PropertyChangeEvent evt) {
                updateComponentsEnabled();
            }
        });
        updateComponentsEnabled();
    }

    private void updateComponentsEnabled() {
        bStudentClassChooser.setEnabled((Boolean)model.getBufferedModel(Student.PROPERTYNAME_ACTIVE).getValue());
    }

    private void buildView() {
        
        this.setHeaderInfo(model.getHeaderInfo());
        this.setName("Schueler");
        
        FormLayout layout = new FormLayout(
                "pref, 4dlu, pref, 20dlu, left:pref:grow",
                "pref, 4dlu, pref"
        );
        PanelBuilder builder = new PanelBuilder(layout, this.getContentPanel());
        
        CellConstraints cc = new CellConstraints();
        
        builder.add(cIsStudent, cc.xyw(1, 1, 5));
        builder.addLabel("Klasse:", cc.xy(1, 3));
        builder.add(lStudentClass, cc.xy(3, 3));
        builder.add(bStudentClassChooser, cc.xy(5, 3));
    }

    @Override
    public void dispose() {
        model.getBufferedModel(Student.PROPERTYNAME_ACTIVE).removeValueChangeListener(activeChangeListener);

        componentContainer.dispose();

        model.dispose();
    }

}

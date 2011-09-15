package cw.studentmanagementmodul.student.gui;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

import cw.boardingschoolmanagement.gui.component.CWButton;
import cw.boardingschoolmanagement.gui.component.CWCheckBox;
import cw.boardingschoolmanagement.gui.component.CWComponentFactory;
import cw.boardingschoolmanagement.gui.component.CWLabel;
import cw.boardingschoolmanagement.gui.component.CWView;
import cw.studentmanagementmodul.student.persistence.Student;

/**
 *
 * @author Manuel Geier
 */
public class StudentEditCustomerView
	extends CWView<StudentEditCustomerPresentationModel>
{
	
    private CWCheckBox cIsStudent;
    private CWButton bStudentClassChooser;
    private CWLabel lStudentClass;

    private PropertyChangeListener activeChangeListener;
    
    public StudentEditCustomerView(StudentEditCustomerPresentationModel model) {
        super(model);
    }

    public void initComponents() {
    	super.initComponents();
    	
        cIsStudent              = CWComponentFactory.createCheckBox(getModel().getBufferedModel(Student.PROPERTYNAME_ACTIVE), "Kunde ist ein Schueler?");
        bStudentClassChooser    = CWComponentFactory.createButton(getModel().getStudentClassChooserAction());
        lStudentClass           = CWComponentFactory.createLabel(getModel().getBufferedModel(Student.PROPERTYNAME_STUDENTCLASS));

        CWComponentFactory.createNullLabel(lStudentClass, "keiner Klasse zugeordnet");

        getComponentContainer()
                .addComponent(cIsStudent)
                .addComponent(bStudentClassChooser)
                .addComponent(lStudentClass);
        
        initEventHandling();
    }
    
    private void initEventHandling() {
        getModel().getBufferedModel(Student.PROPERTYNAME_ACTIVE).addValueChangeListener(activeChangeListener = new PropertyChangeListener() {
            public void propertyChange(PropertyChangeEvent evt) {
                updateComponentsEnabled();
            }
        });
        updateComponentsEnabled();
    }

    private void updateComponentsEnabled() {
        bStudentClassChooser.setEnabled((Boolean)getModel().getBufferedModel(Student.PROPERTYNAME_ACTIVE).getValue());
    }

    public void buildView() {
    	super.buildView();
        
        this.setHeaderInfo(getModel().getHeaderInfo());
        this.setName("Schueler");
        
        FormLayout layout = new FormLayout(
                "pref, 4dlu, pref, 20dlu, left:pref:grow",
                "pref, 4dlu, pref"
        );
        PanelBuilder builder = new PanelBuilder(layout);
        
        CellConstraints cc = new CellConstraints();
        
        builder.add(cIsStudent, cc.xyw(1, 1, 5));
        builder.addLabel("Klasse:", cc.xy(1, 3));
        builder.add(lStudentClass, cc.xy(3, 3));
        builder.add(bStudentClassChooser, cc.xy(5, 3));
        
        addToContentPanel(builder.getPanel());
    }

    @Override
    public void dispose() {
        getModel().getBufferedModel(Student.PROPERTYNAME_ACTIVE).removeValueChangeListener(activeChangeListener);

        super.dispose();
    }

}

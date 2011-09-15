package cw.studentmanagementmodul.student.gui;

import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

import cw.boardingschoolmanagement.gui.component.CWComponentFactory;
import cw.boardingschoolmanagement.gui.component.CWLabel;
import cw.boardingschoolmanagement.gui.component.CWView;

/**
 *
 * @author Manuel Geier
 */
public class StudentHomeExtentionView
	extends CWView<StudentHomeExtentionPresentationModel>
{

    private CWLabel lSizeStudents;

    public StudentHomeExtentionView(StudentHomeExtentionPresentationModel model) {
        super(model);
    }
    
    public void initComponents() {
    	super.initComponents();
    	
        lSizeStudents = CWComponentFactory.createLabel(getModel().getSizeStudentsValueModel());

        getComponentContainer()
                .addComponent(lSizeStudents);
    }

    public void buildView() {
    	super.buildView();
    	
        this.setHeaderInfo(new CWHeaderInfo("Schuelerinformationen"));

        FormLayout layout = new FormLayout(
                "pref",
                "pref"
        );
        PanelBuilder builder = new PanelBuilder(layout);

        CellConstraints cc = new CellConstraints();
        builder.add(lSizeStudents, cc.xy(1, 1));
        
        addToContentPanel(builder.getPanel());
    }

    @Override
    public void dispose() {
        super.dispose();
    }

}
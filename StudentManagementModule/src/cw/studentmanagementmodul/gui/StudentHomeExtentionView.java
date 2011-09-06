package cw.studentmanagementmodul.gui;

import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;
import cw.boardingschoolmanagement.gui.component.CWComponentFactory;
import cw.boardingschoolmanagement.gui.component.CWLabel;
import cw.boardingschoolmanagement.gui.component.CWView;

/**
 *
 * @author ManuelG
 */
public class StudentHomeExtentionView extends CWView
{

    private StudentHomeExtentionPresentationModel model;

    private CWComponentFactory.CWComponentContainer componentContainer;
    private CWLabel lSizeStudents;

    public StudentHomeExtentionView(StudentHomeExtentionPresentationModel model) {
        this.model = model;

        initComponents();
        buildView();
        initEventHandling();
    }
    
    private void initComponents() {
        lSizeStudents = CWComponentFactory.createLabel(model.getSizeStudentsValueModel());

        componentContainer = CWComponentFactory.createComponentContainer()
                .addComponent(lSizeStudents);
    }

    private void initEventHandling() {
        // Nothing to do
    }

    private void buildView() {
        this.setHeaderInfo(new CWHeaderInfo("Schuelerinformationen"));

        FormLayout layout = new FormLayout(
                "pref",
                "pref"
        );
        PanelBuilder builder = new PanelBuilder(layout, this.getContentPanel());

        CellConstraints cc = new CellConstraints();
        builder.add(lSizeStudents, cc.xy(1, 1));
    }

    @Override
    public void dispose() {
        componentContainer.dispose();

        model.dispose();
    }

}
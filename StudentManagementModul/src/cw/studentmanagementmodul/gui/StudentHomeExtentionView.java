package cw.studentmanagementmodul.gui;

import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;
import cw.boardingschoolmanagement.app.CWComponentFactory;
import cw.boardingschoolmanagement.gui.component.JViewPanel;
import cw.boardingschoolmanagement.interfaces.Disposable;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author ManuelG
 */
public class StudentHomeExtentionView
    implements Disposable
{

    private StudentHomeExtentionPresentationModel model;

    private CWComponentFactory.CWComponentContainer componentContainer;
    private JViewPanel panel;
    private JLabel lSizeStudents;

    public StudentHomeExtentionView(StudentHomeExtentionPresentationModel model) {
        this.model = model;
    }
    
    private void initComponents() {
        lSizeStudents = CWComponentFactory.createLabel(model.getSizeStudentsValueModel());

        componentContainer = CWComponentFactory.createCWComponentContainer()
                .addComponent(lSizeStudents);
    }

    private void initEventHandling() {
        // Nothing to do
    }

    public JPanel buildPanel() {
        initComponents();

        panel  = new JViewPanel("Schülerinformationen");

        FormLayout layout = new FormLayout(
                "pref",
                "pref"
        );
        PanelBuilder builder = new PanelBuilder(layout, panel.getContentPanel());

        CellConstraints cc = new CellConstraints();
        builder.add(lSizeStudents, cc.xy(1, 1));

        initEventHandling();

        return panel;
    }

    public void dispose() {
        panel.removeDisposableListener(this);

        componentContainer.dispose();

        model.dispose();
    }

}
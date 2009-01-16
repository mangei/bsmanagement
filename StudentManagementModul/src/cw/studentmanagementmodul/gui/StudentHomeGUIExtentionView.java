package cw.studentmanagementmodul.gui;

import cw.boardingschoolmanagement.app.CWComponentFactory;
import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;
import cw.boardingschoolmanagement.gui.component.JViewPanel;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author ManuelG
 */
public class StudentHomeGUIExtentionView {

    private StudentHomeGUIExtentionPresentationModel model;

    private JLabel lSizeStudents;

    public StudentHomeGUIExtentionView(StudentHomeGUIExtentionPresentationModel model) {
        this.model = model;
    }
    
    private void initComponents() {
        lSizeStudents = CWComponentFactory.createLabel(model.getSizeStudentsValueModel());
    }

    private void initEventHandling() {

    }

    public JPanel buildPanel() {
        initComponents();

        JViewPanel panel  = new JViewPanel("Schülerinformationen");

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

}
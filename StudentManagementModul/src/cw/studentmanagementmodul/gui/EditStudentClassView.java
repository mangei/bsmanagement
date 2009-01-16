package cw.studentmanagementmodul.gui;

import cw.boardingschoolmanagement.app.CWComponentFactory;
import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;
import cw.boardingschoolmanagement.gui.component.JButtonPanel;
import cw.boardingschoolmanagement.gui.component.JViewPanel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JTextField;
import cw.studentmanagementmodul.pojo.StudentClass;

/**
 *
 * @author ManuelG
 */
public class EditStudentClassView {

    private EditStudentClassPresentationModel model;

    private JTextField tfName;
    private JComboBox cbOrganisationUnit;
    private JComboBox cbNextStudentClass;

    private JButton bSave;
    private JButton bReset;
    private JButton bCancel;
    private JButton bSaveCancel;

    public EditStudentClassView(EditStudentClassPresentationModel model) {
        this.model = model;
    }

    public void initModels() {
        bSave       = CWComponentFactory.createButton(model.getSaveButtonAction());
        bReset      = CWComponentFactory.createButton(model.getResetButtonAction());
        bCancel     = CWComponentFactory.createButton(model.getCancelButtonAction());
        bSaveCancel = CWComponentFactory.createButton(model.getSaveCancelButtonAction());
        
        tfName              = CWComponentFactory.createTextField(model.getBufferedModel(StudentClass.PROPERTYNAME_NAME), false);
        cbOrganisationUnit  = CWComponentFactory.createComboBox(model.getSelectionOrganisationUnit());
        cbNextStudentClass  = CWComponentFactory.createComboBox(model.getSelectionStudentClass());
    }

    public void initEventHandling() {
        // Nothing to do
    }

    public JPanel buildPanel() {
        initModels();
        
        JViewPanel panel = new JViewPanel(model.getHeaderText());

        JButtonPanel buttonPanel = panel.getButtonPanel();
        buttonPanel.add(bSave);
        buttonPanel.add(bSaveCancel);
        buttonPanel.add(bReset);
        buttonPanel.add(bCancel);

        FormLayout layout = new FormLayout("right:pref, 4dlu, pref:grow", "pref, 4dlu, pref, 4dlu, pref");
        PanelBuilder builder = new PanelBuilder(layout, panel.getContentPanel());
        CellConstraints cc = new CellConstraints();
        
        builder.addLabel("Name:", cc.xy(1, 1));
        builder.add(tfName, cc.xy(3, 1));
        builder.addLabel("Bereich:", cc.xy(1, 3));
        builder.add(cbOrganisationUnit, cc.xy(3, 3));
        builder.addLabel("Nächste Klasse:", cc.xy(1, 5));
        builder.add(cbNextStudentClass, cc.xy(3, 5));

        initEventHandling();
        
        return panel;
    }
}

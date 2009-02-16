package cw.customermanagementmodul.gui;

import cw.boardingschoolmanagement.app.CWComponentFactory;
import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;
import cw.boardingschoolmanagement.gui.component.JButtonPanel;
import cw.boardingschoolmanagement.gui.component.JViewPanel;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;
import cw.customermanagementmodul.pojo.Group;

/**
 *
 * @author ManuelG
 */
public class EditGroupView {

    private EditGroupPresentationModel model;

    private JTextField tfName;

    private JButton bSave;
    private JButton bReset;
    private JButton bCancel;
    private JButton bSaveCancel;

    public EditGroupView(EditGroupPresentationModel model) {
        this.model = model;
    }

    private void initComponents() {
        tfName = CWComponentFactory.createTextField(model.getBufferedModel(Group.PROPERTYNAME_NAME), false);

        bSave       = new JButton(model.getSaveButtonAction());
        bReset      = new JButton(model.getResetButtonAction());
        bCancel     = new JButton(model.getCancelButtonAction());
        bSaveCancel = new JButton(model.getSaveCancelButtonAction());
    }

    private void initEventHandling() {
        
    }

    public JPanel buildPanel() {
        initComponents();

        JViewPanel panel = new JViewPanel(model.getHeaderText());

        JButtonPanel buttonPanel = panel.getButtonPanel();
        buttonPanel.add(bSave);
        buttonPanel.add(bSaveCancel);
//        buttonPanel.add(bReset);
        buttonPanel.add(bCancel);

        FormLayout layout = new FormLayout(
                "pref, 4dlu, pref:grow",
                "pref");

        PanelBuilder builder = new PanelBuilder(layout, panel.getContentPanel());
        CellConstraints cc = new CellConstraints();

        builder.addLabel("Name:", cc.xy(1, 1));
        builder.add(tfName, cc.xy(3, 1));

        initEventHandling();

        return panel;
    }
}

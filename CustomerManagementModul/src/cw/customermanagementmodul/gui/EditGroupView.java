package cw.customermanagementmodul.gui;

import cw.boardingschoolmanagement.app.CWComponentFactory;
import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;
import cw.boardingschoolmanagement.gui.component.JButtonPanel;
import cw.boardingschoolmanagement.gui.component.JViewPanel;
import cw.boardingschoolmanagement.interfaces.Disposable;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;
import cw.customermanagementmodul.pojo.Group;

/**
 *
 * @author ManuelG
 */
public class EditGroupView
    implements Disposable
{

    private EditGroupPresentationModel model;

    private CWComponentFactory.CWComponentContainer componentContainer;
    private JViewPanel panel;
    private JTextField tfName;

    private JButton bSave;
    private JButton bCancel;
    private JButton bSaveCancel;

    public EditGroupView(EditGroupPresentationModel model) {
        this.model = model;
    }

    private void initComponents() {
        tfName = CWComponentFactory.createTextField(model.getBufferedModel(Group.PROPERTYNAME_NAME), false);

        bSave       = new JButton(model.getSaveButtonAction());
        bCancel     = new JButton(model.getCancelButtonAction());
        bSaveCancel = new JButton(model.getSaveCancelButtonAction());

        componentContainer = CWComponentFactory.createCWComponentContainer()
                .addComponent(tfName)
                .addComponent(bSave)
                .addComponent(bCancel)
                .addComponent(bSaveCancel);
    }

    private void initEventHandling() {
        
    }

    public JPanel buildPanel() {
        initComponents();

        panel = new JViewPanel(model.getHeaderText());

        JButtonPanel buttonPanel = panel.getButtonPanel();
        buttonPanel.add(bSave);
        buttonPanel.add(bSaveCancel);
        buttonPanel.add(bCancel);

        FormLayout layout = new FormLayout(
                "pref, 4dlu, pref:grow",
                "pref");

        PanelBuilder builder = new PanelBuilder(layout, panel.getContentPanel());
        CellConstraints cc = new CellConstraints();

        builder.addLabel("Name:", cc.xy(1, 1));
        builder.add(tfName, cc.xy(3, 1));

        initEventHandling();

        panel.addDisposableListener(this);

        return panel;
    }

    public void dispose() {
        panel.removeDisposableListener(this);

        componentContainer.dispose();

        model.dispose();
    }
}

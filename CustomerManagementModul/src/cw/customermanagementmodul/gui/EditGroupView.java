package cw.customermanagementmodul.gui;

import cw.boardingschoolmanagement.gui.component.CWComponentFactory;
import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;
import cw.boardingschoolmanagement.gui.component.CWView;
import cw.boardingschoolmanagement.gui.component.CWButtonPanel;
import javax.swing.JButton;
import javax.swing.JTextField;
import cw.customermanagementmodul.pojo.Group;

/**
 *
 * @author Manuel Geier (CreativeWorkers)
 */
public class EditGroupView extends CWView
{

    private EditGroupPresentationModel model;

    private CWComponentFactory.CWComponentContainer componentContainer;
    private JTextField tfName;

    private JButton bSave;
    private JButton bCancel;
    private JButton bSaveCancel;

    public EditGroupView(EditGroupPresentationModel model) {
        this.model = model;

        initComponents();
        buildView();
        initEventHandling();
    }

    private void initComponents() {
        tfName = CWComponentFactory.createTextField(model.getBufferedModel(Group.PROPERTYNAME_NAME), false);

        bSave       = CWComponentFactory.createButton(model.getSaveButtonAction());
        bCancel     = CWComponentFactory.createButton(model.getCancelButtonAction());
        bSaveCancel = CWComponentFactory.createButton(model.getSaveCancelButtonAction());

        componentContainer = CWComponentFactory.createCWComponentContainer()
                .addComponent(tfName)
                .addComponent(bSave)
                .addComponent(bCancel)
                .addComponent(bSaveCancel);
    }

    private void initEventHandling() {
        // Nothing to do
    }

    public void buildView() {
        this.setHeaderInfo(model.getHeaderInfo());

        CWButtonPanel buttonPanel = getButtonPanel();
        buttonPanel.add(bSave);
        buttonPanel.add(bSaveCancel);
        buttonPanel.add(bCancel);

        FormLayout layout = new FormLayout(
                "pref, 4dlu, pref:grow",
                "pref");

        PanelBuilder builder = new PanelBuilder(layout, getContentPanel());
        CellConstraints cc = new CellConstraints();

        builder.addLabel("Name:", cc.xy(1, 1));
        builder.add(tfName, cc.xy(3, 1));
    }

    public void dispose() {
        componentContainer.dispose();

        model.dispose();
    }
}

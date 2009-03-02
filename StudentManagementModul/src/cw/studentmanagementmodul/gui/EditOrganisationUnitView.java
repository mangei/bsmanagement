package cw.studentmanagementmodul.gui;

import cw.boardingschoolmanagement.app.CWComponentFactory;
import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;
import cw.boardingschoolmanagement.gui.component.JButtonPanel;
import cw.boardingschoolmanagement.gui.component.JViewPanel;
import cw.boardingschoolmanagement.interfaces.Disposable;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JTextField;
import cw.studentmanagementmodul.pojo.OrganisationUnit;

/**
 *
 * @author ManuelG
 */
public class EditOrganisationUnitView
    implements Disposable
{

    private EditOrganisationUnitPresentationModel model;

    private CWComponentFactory.CWComponentContainer componentContainer;
    private JViewPanel panel;
    private JTextField tfName;
    private JComboBox cbOrganisationUnitParent;

    private JButton bSave;
    private JButton bCancel;
    private JButton bSaveCancel;

    public EditOrganisationUnitView(EditOrganisationUnitPresentationModel model) {
        this.model = model;
    }

    public void initModels() {
        bSave       = CWComponentFactory.createButton(model.getSaveButtonAction());
        bCancel     = CWComponentFactory.createButton(model.getCancelButtonAction());
        bSaveCancel = CWComponentFactory.createButton(model.getSaveCancelButtonAction());

        tfName                      = CWComponentFactory.createTextField(model.getBufferedModel(OrganisationUnit.PROPERTYNAME_NAME), false);
        cbOrganisationUnitParent    = CWComponentFactory.createComboBox(model.getSelectionOrganisationUnit());

        componentContainer = CWComponentFactory.createCWComponentContainer()
                .addComponent(bCancel)
                .addComponent(bSave)
                .addComponent(bSaveCancel)
                .addComponent(tfName)
                .addComponent(cbOrganisationUnitParent);
    }

    public void initEventHandling() {
        // Nothing to do
    }

    public JPanel buildPanel() {
        initModels();
        
        panel = new JViewPanel(model.getHeaderText());

        JButtonPanel buttonPanel = panel.getButtonPanel();
        buttonPanel.add(bSave);
        buttonPanel.add(bSaveCancel);
        buttonPanel.add(bCancel);

        FormLayout layout = new FormLayout("right:pref, 4dlu, pref:grow", "pref, 4dlu, pref");
        PanelBuilder builder = new PanelBuilder(layout, panel.getContentPanel());
        CellConstraints cc = new CellConstraints();
        
        builder.addLabel("Name:", cc.xy(1, 1));
        builder.add(tfName, cc.xy(3, 1));
        builder.addLabel("Überbereich:", cc.xy(1, 3));
        builder.add(cbOrganisationUnitParent, cc.xy(3, 3));

        panel.addDisposableListener(this);

        initEventHandling();
        
        return panel;
    }

    public void dispose() {
        panel.removeDisposableListener(this);

        componentContainer.dispose();

        model.dispose();
    }
}

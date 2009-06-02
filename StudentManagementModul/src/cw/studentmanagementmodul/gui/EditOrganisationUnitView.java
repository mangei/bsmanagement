package cw.studentmanagementmodul.gui;

import cw.boardingschoolmanagement.gui.component.CWComponentFactory;
import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;
import cw.boardingschoolmanagement.gui.component.CWButton;
import cw.boardingschoolmanagement.gui.component.CWButtonPanel;
import cw.boardingschoolmanagement.gui.component.CWComboBox;
import cw.boardingschoolmanagement.gui.component.CWTextField;
import cw.boardingschoolmanagement.gui.component.CWView;
import cw.studentmanagementmodul.pojo.OrganisationUnit;

/**
 *
 * @author ManuelG
 */
public class EditOrganisationUnitView extends CWView
{

    private EditOrganisationUnitPresentationModel model;

    private CWComponentFactory.CWComponentContainer componentContainer;
    private CWTextField tfName;
    private CWComboBox cbOrganisationUnitParent;

    private CWButton bSave;
    private CWButton bCancel;
    private CWButton bSaveCancel;

    public EditOrganisationUnitView(EditOrganisationUnitPresentationModel model) {
        this.model = model;

        initComponents();
        buildView();
        initEventHandling();
    }

    private void initComponents() {
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

    private void buildView() {
        this.setHeaderInfo(model.getHeaderInfo());

        CWButtonPanel buttonPanel = this.getButtonPanel();
        buttonPanel.add(bSave);
        buttonPanel.add(bSaveCancel);
        buttonPanel.add(bCancel);

        FormLayout layout = new FormLayout("right:pref, 4dlu, pref:grow", "pref, 4dlu, pref");
        PanelBuilder builder = new PanelBuilder(layout, this.getContentPanel());
        CellConstraints cc = new CellConstraints();
        
        builder.addLabel("Name:", cc.xy(1, 1));
        builder.add(tfName, cc.xy(3, 1));
        builder.addLabel("Überbereich:", cc.xy(1, 3));
        builder.add(cbOrganisationUnitParent, cc.xy(3, 3));
    }

    @Override
    public void dispose() {
        componentContainer.dispose();

        model.dispose();
    }
}

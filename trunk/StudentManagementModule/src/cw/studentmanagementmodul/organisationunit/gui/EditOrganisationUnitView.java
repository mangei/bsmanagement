package cw.studentmanagementmodul.organisationunit.gui;

import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

import cw.boardingschoolmanagement.gui.component.CWButton;
import cw.boardingschoolmanagement.gui.component.CWButtonPanel;
import cw.boardingschoolmanagement.gui.component.CWComboBox;
import cw.boardingschoolmanagement.gui.component.CWComponentFactory;
import cw.boardingschoolmanagement.gui.component.CWTextField;
import cw.boardingschoolmanagement.gui.component.CWView;
import cw.studentmanagementmodul.organisationunit.persistence.OrganisationUnit;

/**
 *
 * @author ManuelG
 */
public class EditOrganisationUnitView
	extends CWView<EditOrganisationUnitPresentationModel>
{

    private CWTextField tfName;
    private CWComboBox cbOrganisationUnitParent;

    private CWButton bSave;
    private CWButton bCancel;
    private CWButton bSaveCancel;

    public EditOrganisationUnitView(EditOrganisationUnitPresentationModel model) {
        super(model);
    }

    public void initComponents() {
    	super.initComponents();
    	
        bSave       = CWComponentFactory.createButton(getModel().getSaveButtonAction());
        bCancel     = CWComponentFactory.createButton(getModel().getCancelButtonAction());
        bSaveCancel = CWComponentFactory.createButton(getModel().getSaveCancelButtonAction());

        tfName                      = CWComponentFactory.createTextField(getModel().getBufferedModel(OrganisationUnit.PROPERTYNAME_NAME), false);
        cbOrganisationUnitParent    = CWComponentFactory.createComboBox(getModel().getSelectionOrganisationUnit());

        getComponentContainer()
                .addComponent(bCancel)
                .addComponent(bSave)
                .addComponent(bSaveCancel)
                .addComponent(tfName)
                .addComponent(cbOrganisationUnitParent);
    }

    public void buildView() {
    	super.buildView();
    	
        this.setHeaderInfo(getModel().getHeaderInfo());

        CWButtonPanel buttonPanel = this.getButtonPanel();
        buttonPanel.add(bSave);
        buttonPanel.add(bSaveCancel);
        buttonPanel.add(bCancel);

        FormLayout layout = new FormLayout("right:pref, 4dlu, pref:grow", "pref, 4dlu, pref");
        PanelBuilder builder = new PanelBuilder(layout);
        CellConstraints cc = new CellConstraints();
        
        builder.addLabel("Name:", cc.xy(1, 1));
        builder.add(tfName, cc.xy(3, 1));
        builder.addLabel("Ueberbereich:", cc.xy(1, 3));
        builder.add(cbOrganisationUnitParent, cc.xy(3, 3));
        
        addToContentPanel(builder.getPanel());
    }

    @Override
    public void dispose() {
        super.dispose();
    }
}

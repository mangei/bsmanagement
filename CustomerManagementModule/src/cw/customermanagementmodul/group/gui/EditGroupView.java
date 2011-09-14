package cw.customermanagementmodul.group.gui;

import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

import cw.boardingschoolmanagement.gui.component.CWButton;
import cw.boardingschoolmanagement.gui.component.CWButtonPanel;
import cw.boardingschoolmanagement.gui.component.CWComponentFactory;
import cw.boardingschoolmanagement.gui.component.CWTextField;
import cw.boardingschoolmanagement.gui.component.CWView;
import cw.customermanagementmodul.group.persistence.Group;

/**
 *
 * @author Manuel Geier (CreativeWorkers)
 */
public class EditGroupView
	extends CWView<EditGroupPresentationModel>
{
	
    private CWTextField tfName;
    private CWButton bSave;
    private CWButton bCancel;

    public EditGroupView(EditGroupPresentationModel model) {
        super(model);
    }

    public void initComponents() {
    	super.initComponents();
    	
        tfName = CWComponentFactory.createTextField(getModel().getBufferedModel(Group.PROPERTYNAME_NAME), false);

        bSave       = CWComponentFactory.createButton(getModel().getSaveButtonAction());
        bCancel     = CWComponentFactory.createButton(getModel().getCancelButtonAction());

        getComponentContainer()
                .addComponent(tfName)
                .addComponent(bSave)
                .addComponent(bCancel);
    }

    public void buildView() {
    	super.buildView();
    	
        this.setHeaderInfo(getModel().getHeaderInfo());

        CWButtonPanel buttonPanel = getButtonPanel();
        buttonPanel.add(bSave);
        buttonPanel.add(bCancel);

        FormLayout layout = new FormLayout(
                "pref, 4dlu, pref:grow",
                "pref");

        PanelBuilder builder = new PanelBuilder(layout);
        CellConstraints cc = new CellConstraints();

        builder.addLabel("Name:", cc.xy(1, 1));
        builder.add(tfName, cc.xy(3, 1));
        
        addToContentPanel(builder.getPanel(), true);
    }

    @Override
    public void dispose() {
        super.dispose();
    }
}

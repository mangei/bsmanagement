package cw.customermanagementmodul.gui;

import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

import cw.boardingschoolmanagement.gui.component.CWButton;
import cw.boardingschoolmanagement.gui.component.CWButtonPanel;
import cw.boardingschoolmanagement.gui.component.CWComponentFactory;
import cw.boardingschoolmanagement.gui.component.CWTextField;
import cw.boardingschoolmanagement.gui.component.CWView;
import cw.customermanagementmodul.persistence.Group;

/**
 *
 * @author Manuel Geier (CreativeWorkers)
 */
public class EditGroupView extends CWView
{

    private EditGroupPresentationModel model;

    private CWComponentFactory.CWComponentContainer componentContainer;
    private CWTextField tfName;

    private CWButton bSave;
    private CWButton bCancel;

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

        componentContainer = CWComponentFactory.createComponentContainer()
                .addComponent(tfName)
                .addComponent(bSave)
                .addComponent(bCancel);
    }

    private void initEventHandling() {
        // Nothing to do
    }

    public void buildView() {
        this.setHeaderInfo(model.getHeaderInfo());

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
        componentContainer.dispose();

        model.dispose();
    }
}

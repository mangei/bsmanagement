package cw.customermanagementmodul.gui;

import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

import cw.boardingschoolmanagement.gui.component.CWButton;
import cw.boardingschoolmanagement.gui.component.CWComponentFactory;
import cw.boardingschoolmanagement.gui.component.CWLabel;
import cw.boardingschoolmanagement.gui.component.CWView;

/**
 *
 * @author CreativeWorkers.at
 */
public class EditGuardianEditCustomerView
	extends CWView<EditGuardianEditCustomerPresentationModel>
{
    private CWComponentFactory.CWComponentContainer componentContainer;
    private CWLabel lGuardian;
    private CWButton bChooseGuardian;

    public EditGuardianEditCustomerView(EditGuardianEditCustomerPresentationModel model) {
        super(model, true);

        initComponents();
        buildView();
        initEventHandling();
    }

    private void initComponents() {

        lGuardian           = CWComponentFactory.createLabel(getModel().getGuardianLabelModel());
        bChooseGuardian		= CWComponentFactory.createButton(getModel().getChooseGuardianAction());

        componentContainer = CWComponentFactory.createComponentContainer()
                .addComponent(lGuardian)
                .addComponent(bChooseGuardian);
    }

    private void initEventHandling() {
    	// nothing
    }
    
    private void buildView() {
        FormLayout layout = new FormLayout(
                "right:pref, 4dlu, 100dlu, 4dlu, pref",
                "pref");

        PanelBuilder builder = new PanelBuilder(layout);

        int row = 1;
        row+=2;

        CellConstraints cc = new CellConstraints();
        builder.addSeparator("<html><b>Erziehungsberechtigter</b></html>",  cc.xyw(1, row, 5));
        builder.addLabel("Erziehungsberechtigte Person:", 	cc.xy(1, row+=2));
        builder.add(lGuardian,                				cc.xy(3, row));
        builder.add(bChooseGuardian,                		cc.xy(5, row));
        
        addToContentPanel(builder.getPanel());
        
        loadViewExtentions();
    }

    @Override
    public void dispose() {
        componentContainer.dispose();

        getModel().dispose();
    }
}
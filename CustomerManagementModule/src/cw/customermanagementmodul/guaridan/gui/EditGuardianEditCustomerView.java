package cw.customermanagementmodul.guaridan.gui;

import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

import cw.boardingschoolmanagement.gui.component.CWButton;
import cw.boardingschoolmanagement.gui.component.CWComponentFactory;
import cw.boardingschoolmanagement.gui.component.CWLabel;
import cw.boardingschoolmanagement.gui.component.CWPanel;
import cw.boardingschoolmanagement.gui.component.CWView;
import cw.customermanagementmodul.customer.gui.EditCustomerEditCustomerView;

/**
 *
 * @author Manuel Geier
 */
public class EditGuardianEditCustomerView
	extends CWView<EditGuardianEditCustomerPresentationModel>
{

	private EditCustomerEditCustomerView baseView;
    public CWLabel lGuardian;
    public CWButton bChooseGuardian;

    public EditGuardianEditCustomerView(EditGuardianEditCustomerPresentationModel model, EditCustomerEditCustomerView baseView) {
        super(model, true);
        this.baseView = baseView;
    }

    public void initComponents() {
    	super.initComponents();

        lGuardian           = CWComponentFactory.createLabel(getModel().getGuardianLabelModel());
        bChooseGuardian		= CWComponentFactory.createButton(getModel().getChooseGuardianAction());

        getComponentContainer()
                .addComponent(lGuardian)
                .addComponent(bChooseGuardian);
    }
    
    public void buildView() {
    	super.buildView();
    	
        FormLayout layout = new FormLayout(
                "right:pref, 4dlu, 100dlu, 4dlu, pref",
                "pref, 4dlu, pref");
        
        PanelBuilder builder = new PanelBuilder(layout);

        int row = 1;
        CellConstraints cc = new CellConstraints();
        builder.addSeparator("<html><b>Erziehungsberechtigter</b></html>",  cc.xyw(1, row, 5));
        builder.addLabel("Erziehungsberechtigte Person:", 	cc.xy(1, row+=2));
        builder.add(lGuardian,                				cc.xy(3, row));
        builder.add(bChooseGuardian,                		cc.xy(5, row));

        this.addToContentPanel(builder.getPanel());
        
        baseView.addToContentPanel(builder.getPanel());
    }

    @Override
    public void dispose() {
        super.dispose();
    }
}
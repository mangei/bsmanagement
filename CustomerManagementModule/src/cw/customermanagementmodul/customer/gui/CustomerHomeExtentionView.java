package cw.customermanagementmodul.customer.gui;

import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

import cw.boardingschoolmanagement.gui.HomeView;
import cw.boardingschoolmanagement.gui.component.CWComponentFactory;
import cw.boardingschoolmanagement.gui.component.CWLabel;
import cw.boardingschoolmanagement.gui.component.CWView;

/**
 *
 * @author ManuelG
 */
public class CustomerHomeExtentionView
	extends CWView<CustomerHomeExtentionPresentationModel>
{

	private HomeView baseView;
    private CWLabel lSizeCustomers;

    public CustomerHomeExtentionView(CustomerHomeExtentionPresentationModel model, HomeView baseView) {
    	super(model);
    	this.baseView = baseView;
    }
    
    public void initComponents() {
    	super.initComponents();
    	
        lSizeCustomers = CWComponentFactory.createLabel(getModel().getSizeCustomersValueModel());

        getComponentContainer()
                .addComponent(lSizeCustomers);
    }

    public void buildView() {
    	super.buildView();
    	
        this.setHeaderInfo(new CWHeaderInfo("Kundeninformationen"));

        FormLayout layout = new FormLayout(
                "pref",
                "pref"
        );
        PanelBuilder builder = new PanelBuilder(layout);

        CellConstraints cc = new CellConstraints();
        builder.add(lSizeCustomers, cc.xy(1, 1));
        
        this.addToContentPanel(builder.getPanel());
        
        baseView.addToContentPanel(this, true);
    }

    @Override
    public void dispose() {
        super.dispose();
    }

}
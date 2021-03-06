package cw.customermanagementmodul.customer.gui;

import java.util.List;

import javax.swing.JComponent;

import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

import cw.boardingschoolmanagement.gui.component.CWView;

/**
 *
 * @author CreativeWorkers.at
 */
public class CustomerOverviewEditCustomerView
	extends CWView<CustomerOverviewEditCustomerPresentationModel>
{

	private EditCustomerView baseView;
	
    public CustomerOverviewEditCustomerView(CustomerOverviewEditCustomerPresentationModel model, EditCustomerView baseView) {
        super(model);
        this.baseView = baseView;
    }

    public void initComponents() {
    	super.initComponents();
    }

    public void buildView() {
    	super.buildView();
    	
        this.setHeaderInfo(getModel().getHeaderInfo());
        
        List<JComponent> extentionComponents = getModel().getExtentionComponents();

        StringBuilder rowLayoutString = new StringBuilder();
        for(int i=0, l=extentionComponents.size(); i<l; i++) {
            if(i != 0) {
                rowLayoutString.append(", ");
            }
            rowLayoutString.append("pref, 4dlu");
        }

        FormLayout layout = new FormLayout(
                "fill:pref:grow",
                rowLayoutString.toString());

        PanelBuilder builder = new PanelBuilder(layout);
        CellConstraints cc = new CellConstraints();

        for(int i=0, l=extentionComponents.size(); i<l; i++) {
            builder.add(extentionComponents.get(i), cc.xy(1, i*2+1));
        }

        this.addToContentPanel(builder.getPanel());
        
        baseView.addToContentPanel(this);
    }

    @Override
    public void dispose() {
    	super.dispose();
    }
}

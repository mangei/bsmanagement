package cw.customermanagementmodul.customer.gui;

import com.l2fprod.common.swing.JButtonBar;

import cw.boardingschoolmanagement.gui.component.CWButton;
import cw.boardingschoolmanagement.gui.component.CWComponentFactory;
import cw.boardingschoolmanagement.gui.component.CWView;

/**
 *
 * @author ManuelG
 */
public class CustomerChooserView
	extends CWView<CustomerChooserPresentationModel>
{

    private CWButton bOk;
    private CWButton bCancel;
    private CustomerSelectorView customerSelectorView;

    public CustomerChooserView(CustomerChooserPresentationModel model) {
        super(model);
    }

    public void initComponents() {
    	super.initComponents();
    	
        bOk                 = CWComponentFactory.createButton(getModel().getOkAction());
        bCancel             = CWComponentFactory.createButton(getModel().getCancelAction());
        
        customerSelectorView = new CustomerSelectorView(getModel().getCustomerSelectorPresentationModel());
        customerSelectorView.initComponents();
        
        getComponentContainer()
                .addComponent(bOk)
                .addComponent(bCancel)
                .addComponent(customerSelectorView);
        }

    public void buildView() {
    	super.buildView();
    	
    	customerSelectorView.buildView();
    	this.setName("Kunden auswählen");
        
        JButtonBar buttonBar = this.getButtonPanel();
        buttonBar.add(bOk);
        buttonBar.add(bCancel);

        addToContentPanel(customerSelectorView, true);
    }

    @Override
    public void dispose() {
        super.dispose();
    }

}

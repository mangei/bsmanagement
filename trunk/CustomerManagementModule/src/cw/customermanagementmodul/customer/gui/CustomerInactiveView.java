package cw.customermanagementmodul.customer.gui;

import cw.boardingschoolmanagement.gui.component.CWButton;
import cw.boardingschoolmanagement.gui.component.CWComponentFactory;
import cw.boardingschoolmanagement.gui.component.CWView;

/**
 * @author CreativeWorkers.at
 */
public class CustomerInactiveView
	extends CWView<CustomerInactivePresentationModel>
{

    private CWButton bActivate;
    private CWButton bDelete;
    private CustomerSelectorView customerSelectorView;

    public CustomerInactiveView(CustomerInactivePresentationModel model) {
    	super(model);
    }

    public void initComponents() {
    	super.initComponents();
    	
        bActivate   = CWComponentFactory.createButton(getModel().getActivateAction());
        bDelete     = CWComponentFactory.createButton(getModel().getDeleteAction());
        
        customerSelectorView = new CustomerSelectorView(getModel().getCustomerSelectorPresentationModel());
        customerSelectorView.initComponents();
        
        getComponentContainer()
                .addComponent(bActivate)
                .addComponent(bDelete)
                .addComponent(customerSelectorView);

        }

    public void buildView() {
    	super.buildView();
    	
    	customerSelectorView.buildView();
    	
        this.setHeaderInfo(getModel().getHeaderInfo());

        this.getButtonPanel().add(bActivate);
        this.getButtonPanel().add(bDelete);
        
        addToContentPanel(customerSelectorView, true);
    }

    @Override
    public void dispose() {
        super.dispose();
    }

}

package cw.customermanagementmodul.gui;

import cw.boardingschoolmanagement.gui.component.CWButton;
import cw.boardingschoolmanagement.gui.component.CWComponentFactory;
import cw.boardingschoolmanagement.gui.component.CWView;

/**
 * @author CreativeWorkers.at
 */
public class CustomerInactiveView
	extends CWView<CustomerInactivePresentationModel>
{

    private CWComponentFactory.CWComponentContainer componentContainer;
    private CWButton bActivate;
    private CWButton bDelete;
    private CustomerSelectorView customerSelectorView;

    public CustomerInactiveView(CustomerInactivePresentationModel model) {
    	super(model);

        initComponents();
        buildView();
        initEventHandling();
    }

    private void initComponents() {
        bActivate   = CWComponentFactory.createButton(getModel().getActivateAction());
        bDelete     = CWComponentFactory.createButton(getModel().getDeleteAction());
        
        componentContainer = CWComponentFactory.createComponentContainer()
                .addComponent(bActivate)
                .addComponent(bDelete);

        customerSelectorView = new CustomerSelectorView(getModel().getCustomerSelectorPresentationModel());
    }

    private void initEventHandling() {
        // Nothing to do
    }

    private void buildView() {
        this.setHeaderInfo(getModel().getHeaderInfo());

        this.getButtonPanel().add(bActivate);
        this.getButtonPanel().add(bDelete);
        
        addToContentPanel(customerSelectorView, true);
    }

    @Override
    public void dispose() {
        customerSelectorView.dispose();

        componentContainer.dispose();

        getModel().dispose();
    }

}

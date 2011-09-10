package cw.customermanagementmodul.gui;

import java.awt.BorderLayout;

import cw.boardingschoolmanagement.gui.component.CWButton;
import cw.boardingschoolmanagement.gui.component.CWComponentFactory;
import cw.boardingschoolmanagement.gui.component.CWView;

/**
 * @author CreativeWorkers.at
 */
public class CustomerInactiveView extends CWView
{

    private CustomerInactivePresentationModel model;
    private CWComponentFactory.CWComponentContainer componentContainer;
    private CWButton bActivate;
    private CWButton bDelete;
    private CustomerSelectorView customerSelectorView;

    public CustomerInactiveView(CustomerInactivePresentationModel model) {
        this.model = model;

        initComponents();
        buildView();
        initEventHandling();
    }

    private void initComponents() {
        bActivate   = CWComponentFactory.createButton(model.getActivateAction());
        bDelete     = CWComponentFactory.createButton(model.getDeleteAction());
        
        componentContainer = CWComponentFactory.createComponentContainer()
                .addComponent(bActivate)
                .addComponent(bDelete);

        customerSelectorView = new CustomerSelectorView(model.getCustomerSelectorPresentationModel());
    }

    private void initEventHandling() {
        // Nothing to do
    }

    private void buildView() {
        this.setHeaderInfo(model.getHeaderInfo());

        this.getButtonPanel().add(bActivate);
        this.getButtonPanel().add(bDelete);
        
        addToContentPanel(customerSelectorView, true);
    }

    @Override
    public void dispose() {
        customerSelectorView.dispose();

        componentContainer.dispose();

        model.dispose();
    }

}

package cw.customermanagementmodul.gui;

import cw.boardingschoolmanagement.app.CWComponentFactory;
import cw.boardingschoolmanagement.gui.component.CWView;
import java.awt.BorderLayout;
import javax.swing.JButton;
import javax.swing.JPanel;

/**
 * @author CreativeWorkers.at
 */
public class CustomerInactiveView extends CWView
{

    private CustomerInactivePresentationModel model;
    private CWComponentFactory.CWComponentContainer componentContainer;
    private JButton bActivate;
    private JButton bDelete;
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
        
        componentContainer = CWComponentFactory.createCWComponentContainer()
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

        this.getContentPanel().setLayout(new BorderLayout());
        this.getContentPanel().add(customerSelectorView, BorderLayout.CENTER);
    }

    public void dispose() {
        customerSelectorView.dispose();

        componentContainer.dispose();

        model.dispose();
    }

}

package cw.customermanagementmodul.gui;

import cw.boardingschoolmanagement.app.CWComponentFactory;
import cw.boardingschoolmanagement.gui.component.JViewPanel;
import cw.boardingschoolmanagement.interfaces.Disposable;
import java.awt.BorderLayout;
import javax.swing.JButton;
import javax.swing.JPanel;

/**
 * @author CreativeWorkers.at
 */
public class CustomerInactiveView
    implements Disposable
{

    private CustomerInactivePresentationModel model;
    private CWComponentFactory.CWComponentContainer componentContainer;
    private JViewPanel panel;
    private JButton bActivate;
    private JButton bDelete;
    private CustomerSelectorView customerSelectorView;

    public CustomerInactiveView(CustomerInactivePresentationModel model) {
        this.model = model;
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

    public JPanel buildPanel() {
        initComponents();

        panel = CWComponentFactory.createViewPanel(model.getHeaderInfo());

        panel.getButtonPanel().add(bActivate);
        panel.getButtonPanel().add(bDelete);

        panel.getContentPanel().setLayout(new BorderLayout());
        panel.getContentPanel().add(customerSelectorView.buildPanel(), BorderLayout.CENTER);

        panel.addDisposableListener(this);

        initEventHandling();
        
        return panel;
    }

    public void dispose() {
        panel.removeDisposableListener(this);

        customerSelectorView.dispose();

        componentContainer.dispose();

        model.dispose();
    }

}

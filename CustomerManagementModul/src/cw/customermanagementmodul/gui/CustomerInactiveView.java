package cw.customermanagementmodul.gui;

import cw.boardingschoolmanagement.app.CWComponentFactory;
import cw.boardingschoolmanagement.gui.component.JViewPanel;
import java.awt.BorderLayout;
import javax.swing.JButton;
import javax.swing.JPanel;

/**
 * @author CreativeWorkers.at
 */
public class CustomerInactiveView {

    private CustomerInactivePresentationModel model;
    private JButton bActivate;
    private JButton bDelete;
    private CustomerSelectorView customerSelectorView;

    public CustomerInactiveView(CustomerInactivePresentationModel m) {
        model = m;
    }

    private void initComponents() {
        bActivate   = CWComponentFactory.createButton(model.getActivateAction());
        bDelete     = CWComponentFactory.createButton(model.getDeleteAction());

        customerSelectorView = new CustomerSelectorView(model.getCustomerSelectorPresentationModel());
    }

    private void initEventHandling() {
        // Nothing to do
    }

    public JPanel buildPanel() {
        initComponents();
        initEventHandling();

        JViewPanel panel = CWComponentFactory.createViewPanel(model.getHeaderInfo());

        panel.getButtonPanel().add(bActivate);
        panel.getButtonPanel().add(bDelete);

        panel.getContentPanel().setLayout(new BorderLayout());
        panel.getContentPanel().add(customerSelectorView.buildPanel(), BorderLayout.CENTER);

        return panel;
    }

}

package cw.customermanagementmodul.gui;

import cw.boardingschoolmanagement.app.CWComponentFactory;
import cw.boardingschoolmanagement.gui.component.JViewPanel;
import java.awt.BorderLayout;
import javax.swing.JButton;
import javax.swing.JPanel;

/**
 * @author CreativeWorkers.at
 */
public class CustomerManagementView {

    private CustomerManagementPresentationModel model;
    private JButton bNew;
    private JButton bEdit;
    private JButton bDelete;
    private JButton bInactive;
    private JButton bViewInactives;
    private CustomerSelectorView customerSelectorView;

    public CustomerManagementView(CustomerManagementPresentationModel m) {
        model = m;
    }

    private void initComponents() {
        bNew        = CWComponentFactory.createButton(model.getNewAction());
        bEdit       = CWComponentFactory.createButton(model.getEditAction());
        bDelete     = CWComponentFactory.createButton(model.getDeleteAction());
        bInactive   = CWComponentFactory.createButton(model.getInactiveAction());
        bViewInactives   = CWComponentFactory.createButton(model.getViewInactivesAction());

        customerSelectorView = new CustomerSelectorView(model.getCustomerSelectorPresentationModel());
    }

    private void initEventHandling() {
        // Nothing to do
    }

    public JPanel buildPanel() {
        initComponents();
        initEventHandling();

        JViewPanel panel = CWComponentFactory.createViewPanel(model.getHeaderInfo());

        panel.getButtonPanel().add(bNew);
        panel.getButtonPanel().add(bEdit);
        panel.getButtonPanel().add(bDelete);
        panel.getButtonPanel().add(bInactive);
        panel.getButtonPanel().add(bViewInactives);

        panel.getContentPanel().setLayout(new BorderLayout());
        panel.getContentPanel().add(customerSelectorView.buildPanel(), BorderLayout.CENTER);

        return panel;
    }

}

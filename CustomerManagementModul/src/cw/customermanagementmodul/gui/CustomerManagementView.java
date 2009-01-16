package cw.customermanagementmodul.gui;

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
    private CustomerSelectorView customerSelectorView;

    public CustomerManagementView(CustomerManagementPresentationModel m) {
        model = m;
    }

    private void initComponents() {
        bNew = new JButton(model.getNewAction());
        bEdit = new JButton(model.getEditAction());
        bDelete = new JButton(model.getDeleteAction());

        customerSelectorView = new CustomerSelectorView(model.getCustomerSelectorPresentationModel());
    }

    private void initEventHandling() {
        // Nothing to do
    }

    public JPanel buildPanel() {
        initComponents();
        initEventHandling();

        JViewPanel panel = new JViewPanel();
        panel.setHeaderText(model.getHeaderText());

        panel.getButtonPanel().add(bNew);
        panel.getButtonPanel().add(bEdit);
        panel.getButtonPanel().add(bDelete);

        panel.getContentPanel().setLayout(new BorderLayout());
        panel.getContentPanel().add(customerSelectorView.buildPanel(), BorderLayout.CENTER);

        return panel;
    }

}

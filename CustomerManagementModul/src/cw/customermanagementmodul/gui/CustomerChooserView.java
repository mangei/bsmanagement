package cw.customermanagementmodul.gui;

import cw.boardingschoolmanagement.app.CWComponentFactory;
import com.l2fprod.common.swing.JButtonBar;
import cw.boardingschoolmanagement.gui.component.JViewPanel;
import java.awt.BorderLayout;
import javax.swing.JButton;
import javax.swing.JPanel;

/**
 *
 * @author ManuelG
 */
public class CustomerChooserView {

    private CustomerChooserPresentationModel model;

    private JButton bOk;
    private JButton bCancel;
    private CustomerSelectorView customerSelectorView;


    public CustomerChooserView(CustomerChooserPresentationModel model) {
        this.model = model;
    }

    private void initComponents() {
        bOk                 = CWComponentFactory.createButton(model.getOkAction());
        bCancel             = CWComponentFactory.createButton(model.getCancelAction());
        customerSelectorView = new CustomerSelectorView(model.getCustomerSelectorPresentationModel());
    }

    private void initEventHandling() {
        // Nothing to do
    }

    public JPanel buildPanel() {
        initComponents();
        
        JViewPanel panel = new JViewPanel(model.getHeaderText());
        
        JButtonBar buttonBar = panel.getButtonPanel();
        buttonBar.add(bOk);
        buttonBar.add(bCancel);

        panel.getContentPanel().add(customerSelectorView.buildPanel(), BorderLayout.CENTER);

        initEventHandling();

        return panel;
    }

}

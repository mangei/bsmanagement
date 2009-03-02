package cw.customermanagementmodul.gui;

import cw.boardingschoolmanagement.app.CWComponentFactory;
import com.l2fprod.common.swing.JButtonBar;
import cw.boardingschoolmanagement.gui.component.JViewPanel;
import cw.boardingschoolmanagement.interfaces.Disposable;
import java.awt.BorderLayout;
import javax.swing.JButton;
import javax.swing.JPanel;

/**
 *
 * @author ManuelG
 */
public class CustomerChooserView
    implements Disposable
{

    private CustomerChooserPresentationModel model;

    private CWComponentFactory.CWComponentContainer componentContainer;
    private JViewPanel panel;
    private JButton bOk;
    private JButton bCancel;
    private CustomerSelectorView customerSelectorView;

    public CustomerChooserView(CustomerChooserPresentationModel model) {
        this.model = model;
    }

    private void initComponents() {
        bOk                 = CWComponentFactory.createButton(model.getOkAction());
        bCancel             = CWComponentFactory.createButton(model.getCancelAction());

        componentContainer = CWComponentFactory.createCWComponentContainer()
                .addComponent(bOk)
                .addComponent(bCancel);

        customerSelectorView = new CustomerSelectorView(model.getCustomerSelectorPresentationModel());
    }

    private void initEventHandling() {
        // Nothing to do
    }

    public JPanel buildPanel() {
        initComponents();
        
        panel = new JViewPanel(model.getHeaderText());
        
        JButtonBar buttonBar = panel.getButtonPanel();
        buttonBar.add(bOk);
        buttonBar.add(bCancel);

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

package cw.customermanagementmodul.gui;

import cw.boardingschoolmanagement.gui.component.CWComponentFactory;
import com.l2fprod.common.swing.JButtonBar;
import cw.boardingschoolmanagement.gui.component.CWView;
import java.awt.BorderLayout;
import javax.swing.JButton;
import javax.swing.JPanel;

/**
 *
 * @author ManuelG
 */
public class CustomerChooserView extends CWView
{

    private CustomerChooserPresentationModel model;

    private CWComponentFactory.CWComponentContainer componentContainer;
    private JButton bOk;
    private JButton bCancel;
    private CustomerSelectorView customerSelectorView;

    public CustomerChooserView(CustomerChooserPresentationModel model) {
        this.model = model;

        initComponents();
        buildView();
        initEventHandling();
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

    private void buildView() {
        this.setHeaderInfo(model.getHeaderInfo());
        
        JButtonBar buttonBar = this.getButtonPanel();
        buttonBar.add(bOk);
        buttonBar.add(bCancel);

        this.getContentPanel().add(customerSelectorView, BorderLayout.CENTER);
    }

    public void dispose() {
        customerSelectorView.dispose();

        componentContainer.dispose();

        model.dispose();
    }

}

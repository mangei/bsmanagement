package cw.customermanagementmodul.gui;

import java.awt.BorderLayout;

import com.l2fprod.common.swing.JButtonBar;

import cw.boardingschoolmanagement.gui.component.CWButton;
import cw.boardingschoolmanagement.gui.component.CWComponentFactory;
import cw.boardingschoolmanagement.gui.component.CWView;

/**
 *
 * @author ManuelG
 */
public class CustomerChooserView extends CWView
{

    private CustomerChooserPresentationModel model;

    private CWComponentFactory.CWComponentContainer componentContainer;
    private CWButton bOk;
    private CWButton bCancel;
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

        componentContainer = CWComponentFactory.createComponentContainer()
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

        addToContentPanel(customerSelectorView, true);
    }

    @Override
    public void dispose() {
        customerSelectorView.dispose();

        componentContainer.dispose();

        model.dispose();
    }

}

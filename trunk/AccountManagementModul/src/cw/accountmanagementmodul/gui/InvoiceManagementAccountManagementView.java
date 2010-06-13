package cw.accountmanagementmodul.gui;

import cw.boardingschoolmanagement.gui.component.CWComponentFactory;
import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;
import cw.boardingschoolmanagement.gui.component.CWButton;
import cw.boardingschoolmanagement.gui.component.CWView;
import cw.boardingschoolmanagement.gui.component.CWButtonPanel;
import cw.boardingschoolmanagement.gui.component.CWTreeTable;

/**
 *
 * @author CreativeWorkers.at
 */
public class InvoiceManagementAccountManagementView extends CWView
{

    private InvoiceManagementAccountManagementPresentationModel model;

    private CWComponentFactory.CWComponentContainer componentContainer;
    
    private CWButton bCancel;
    private CWButton bSave;
    private CWTreeTable ttInvoices;

    public InvoiceManagementAccountManagementView(InvoiceManagementAccountManagementPresentationModel model) {
        this.model = model;

        initComponents();
        buildView();
        initEventHandling();
    }

    private void initComponents() {
        bCancel     = CWComponentFactory.createButton(model.getCancelAction());
        bSave = CWComponentFactory.createButton(model.getSaveAction());

        ttInvoices = CWComponentFactory.createTreeTable(model.getInvoiceTreeTableModel());
        ttInvoices.setRootVisible(false);

        componentContainer = CWComponentFactory.createComponentContainer()
                .addComponent(ttInvoices);
    }

    private void initEventHandling() {
    }

    private void buildView() {
        this.setHeaderInfo(model.getHeaderInfo());
        CWButtonPanel buttonPanel = this.getButtonPanel();

        buttonPanel.add(bSave);
        buttonPanel.add(bCancel);

        FormLayout layout = new FormLayout(
                "fill:pref:grow",
                "pref:grow");
        
        PanelBuilder builder = new PanelBuilder(layout, this.getContentPanel());
        builder.setDefaultDialogBorder();

        CellConstraints cc = new CellConstraints();
        builder.add(CWComponentFactory.createScrollPane(ttInvoices), cc.xy(1, 1));
    }

    @Override
    public void dispose() {
        componentContainer.dispose();

        model.dispose();
    }
}

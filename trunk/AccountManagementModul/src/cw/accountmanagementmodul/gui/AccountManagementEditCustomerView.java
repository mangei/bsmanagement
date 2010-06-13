package cw.accountmanagementmodul.gui;

import cw.boardingschoolmanagement.gui.component.CWComponentFactory;
import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;
import com.jidesoft.swing.JideTabbedPane;
import cw.boardingschoolmanagement.gui.component.CWView;
import javax.swing.JScrollPane;

/**
 *
 * @author Manuel Geier
 */
public class AccountManagementEditCustomerView extends CWView
{
    private AccountManagementEditCustomerPresentationModel model;

    private CWComponentFactory.CWComponentContainer componentContainer;

    private JideTabbedPane tabs;
    private AccountOverviewAccountManagementView accountOverviewAccountManagementView;
    private InvoiceManagementAccountManagementView invoiceManagementAccountManagementView;
    private PostingManagementAccountManagementView postingManagementAccountManagementView;
    private BailmentManagementAccountManagementView bailmentManagementAccountManagementView;

    public AccountManagementEditCustomerView(AccountManagementEditCustomerPresentationModel model) {
        this.model = model;

        initComponents();
        buildView();
        initEventHandling();
    }
    
    private void initComponents() {
        tabs = new JideTabbedPane();

        accountOverviewAccountManagementView = new AccountOverviewAccountManagementView(model.getAccountOverviewAccountManagementPresentationModel());
        invoiceManagementAccountManagementView = new InvoiceManagementAccountManagementView(model.getInvoiceManagementAccountManagementPresentationModel());
        postingManagementAccountManagementView = new PostingManagementAccountManagementView(model.getPostingManagementAccountManagementPresentationModel());
        bailmentManagementAccountManagementView = new BailmentManagementAccountManagementView(model.getBailmentManagementAccountManagementPresentationModel());

        componentContainer = CWComponentFactory.createComponentContainer()
                ;
    }
    
    private void initEventHandling() {
    }

    private void buildView() {
        this.setHeaderInfo(model.getHeaderInfo());
        this.setName("Konto");

        tabs.addTab(
                accountOverviewAccountManagementView.getHeaderInfo().getHeaderText(),
                accountOverviewAccountManagementView.getHeaderInfo().getSmallIcon(),
                accountOverviewAccountManagementView
        );
        tabs.addTab(
                invoiceManagementAccountManagementView.getHeaderInfo().getHeaderText(),
                invoiceManagementAccountManagementView.getHeaderInfo().getSmallIcon(),
                invoiceManagementAccountManagementView
        );
        tabs.addTab(
                postingManagementAccountManagementView.getHeaderInfo().getHeaderText(),
                postingManagementAccountManagementView.getHeaderInfo().getSmallIcon(),
                postingManagementAccountManagementView
        );
        tabs.addTab(
                bailmentManagementAccountManagementView.getHeaderInfo().getHeaderText(),
                bailmentManagementAccountManagementView.getHeaderInfo().getSmallIcon(),
                bailmentManagementAccountManagementView
        );

        // Main layout
        FormLayout layout = new FormLayout(
                "pref:grow",
                "pref, 4dlu, fill:pref:grow, 4dlu, pref"
        );
        PanelBuilder builder = new PanelBuilder(layout, this.getContentPanel());
        CellConstraints cc = new CellConstraints();
        
        builder.add(CWComponentFactory.createScrollPane(tabs), cc.xy(1, 3));
    }

    @Override
    public void dispose() {
        componentContainer.dispose();

        model.dispose();
    }

}

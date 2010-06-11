package cw.accountmanagementmodul.gui;

import cw.boardingschoolmanagement.app.CWUtils;
import cw.accountmanagementmodul.pojo.Account;
import cw.boardingschoolmanagement.gui.component.CWView.CWHeaderInfo;
import cw.customermanagementmodul.pojo.Customer;
import cw.accountmanagementmodul.pojo.manager.AccountManager;

/**
 *
 * @author Manuel Geier
 */
public class AccountManagementEditCustomerPresentationModel {

    private Customer customer;
    private Account account;
    private CWHeaderInfo headerInfo;

    public AccountOverviewAccountManagementPresentationModel accountOverviewAccountManagementPresentationModel;
    public PostingManagementAccountManagementPresentationModel postingManagementAccountManagementPresentationModel;
    public InvoiceManagementAccountManagementPresentationModel invoiceManagementAccountManagementPresentationModel;
    public BailmentManagementAccountManagementPresentationModel bailmentManagementAccountManagementPresentationModel;

    public AccountManagementEditCustomerPresentationModel() {
        this(null);
    }

    public AccountManagementEditCustomerPresentationModel(Customer customer) {
        this.customer = customer;
        this.account = AccountManager.getInstance().get(customer);

        initModels();
        initEventHandling();
    }

    public void initModels() {
        headerInfo = new CWHeaderInfo(
                "Kundenkonto",
                "Hier sehen sie eine Übersicht über alle Buchungen für Ihren Kunden.",
                CWUtils.loadIcon("cw/accountmanagementmodul/images/account.png"),
                CWUtils.loadIcon("cw/accountmanagementmodul/images/account.png")
        );

        accountOverviewAccountManagementPresentationModel = new AccountOverviewAccountManagementPresentationModel(account);
        postingManagementAccountManagementPresentationModel = new PostingManagementAccountManagementPresentationModel(account);
        invoiceManagementAccountManagementPresentationModel = new InvoiceManagementAccountManagementPresentationModel(account);
        bailmentManagementAccountManagementPresentationModel = new BailmentManagementAccountManagementPresentationModel(account);

    }

    private void initEventHandling() {
    }

    void dispose() {
    }

    public void save() {
    }

    public void reset() {
    }

    public CWHeaderInfo getHeaderInfo() {
        return headerInfo;
    }

    public AccountOverviewAccountManagementPresentationModel getAccountOverviewAccountManagementPresentationModel(){
        return accountOverviewAccountManagementPresentationModel;
    }

    public PostingManagementAccountManagementPresentationModel getPostingManagementAccountManagementPresentationModel() {
        return postingManagementAccountManagementPresentationModel;
    }

    public InvoiceManagementAccountManagementPresentationModel getInvoiceManagementAccountManagementPresentationModel() {
        return invoiceManagementAccountManagementPresentationModel;
    }
    
    public BailmentManagementAccountManagementPresentationModel getBailmentManagementAccountManagementPresentationModel() {
        return bailmentManagementAccountManagementPresentationModel;
    }
}

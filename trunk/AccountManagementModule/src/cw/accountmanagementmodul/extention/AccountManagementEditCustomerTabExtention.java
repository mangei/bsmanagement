package cw.accountmanagementmodul.extention;

import cw.accountmanagementmodul.gui.AccountManagementEditCustomerPresentationModel;
import cw.accountmanagementmodul.gui.AccountManagementEditCustomerView;
import cw.accountmanagementmodul.pojo.Account;
import cw.boardingschoolmanagement.gui.component.CWPanel;
import cw.customermanagementmodul.extention.point.EditCustomerTabExtentionPoint;
import cw.customermanagementmodul.gui.EditCustomerPresentationModel;
import cw.accountmanagementmodul.pojo.manager.AccountManager;
import cw.customermanagementmodul.persistence.model.CustomerModel;

import java.util.List;

/**
 *
 * @author Manuel Geier
 */
public class AccountManagementEditCustomerTabExtention
        implements EditCustomerTabExtentionPoint {

    private AccountManagementEditCustomerPresentationModel model;
    private AccountManagementEditCustomerView view;
    private EditCustomerPresentationModel editCustomerModel;
    private Account account;
    
    public void initPresentationModel(EditCustomerPresentationModel editCustomerModel) {
        this.editCustomerModel = editCustomerModel;

        CustomerModel customer = editCustomerModel.getBean();
        account = AccountManager.getInstance().get(customer);

        if(account == null) {
            account = new Account(customer);
        }

        model = new AccountManagementEditCustomerPresentationModel(account);
        view = new AccountManagementEditCustomerView(model);
    }
    
    public CWPanel getView() {
        return view;
    }

    public Object getModel() {
        return model;
    }

    public void save() {
        AccountManager.getInstance().save(account);
    }

    public List<String> validate() {
        return null;
    }

    public void dispose() {
        view.dispose();
    }

    public int priority() {
        return 0;
    }
}

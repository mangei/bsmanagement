package cw.accountmanagementmodul.extention;

import com.jgoodies.binding.value.ValueModel;
import cw.accountmanagementmodul.gui.AccountCustomerSelectorFilterExtentionPresentationModel;
import cw.accountmanagementmodul.gui.AccountCustomerSelectorFilterExtentionView;
import cw.accountmanagementmodul.pojo.Account;
import cw.boardingschoolmanagement.gui.component.CWPanel;
import cw.customermanagementmodul.extention.point.CustomerSelectorFilterExtentionPoint;
import cw.customermanagementmodul.persistence.model.CustomerModel;
import cw.accountmanagementmodul.pojo.AccountPosting;
import cw.accountmanagementmodul.pojo.manager.AccountManager;
import cw.accountmanagementmodul.pojo.manager.PostingManager;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;



/**
 *
 * @author ManuelG
 */
public class AccountCustomerSelectorFilterExtention
        implements CustomerSelectorFilterExtentionPoint {

    public final static int NO_MATTER     = 0;
    public final static int BALANCED      = 1;
    public final static int NOT_BALANCED  = 2;

    private AccountCustomerSelectorFilterExtentionPresentationModel model;
    private AccountCustomerSelectorFilterExtentionView view;
    private ValueModel change;

    private PropertyChangeListener changeListener;

    public void init(final ValueModel change) {
        this.change = change;

        model = new AccountCustomerSelectorFilterExtentionPresentationModel();
        view = new AccountCustomerSelectorFilterExtentionView(model);
    }

    public void initEventHandling() {
        model.getOption().addValueChangeListener(changeListener = new PropertyChangeListener() {
            public void propertyChange(PropertyChangeEvent evt) {
                change.setValue(true);
            }
        });
    }

    public List<CustomerModel> filter(List<CustomerModel> customers) {

        if((Integer)model.getOption().getValue() != NO_MATTER) {
            
            Object[] customersArray =  customers.toArray();

            for (int i = 0, l=customersArray.length; i < l; i++) {
                CustomerModel customer = (CustomerModel)customersArray[i];
                Account account = AccountManager.getInstance().get(customer);

                // Get the all postings
                List<AccountPosting> allPostings = PostingManager.getInstance().getAll(account);

                double sollSummary = 0, habenSummary = 0;
                for(int j=0, k=allPostings.size(); j<k; j++) {
                    AccountPosting p = allPostings.get(i);
//                    if(p.isAssets()) {
//                        habenSummary += p.getAmount();
//                    } else {
//                        sollSummary += p.getAmount();
//                    }
                }

                // Check the status
                if( (Integer)model.getOption().getValue() == BALANCED && habenSummary != sollSummary ||
                    (Integer)model.getOption().getValue() == NOT_BALANCED && habenSummary == sollSummary
                    ) {
                    customers.remove(customer);
                }
            }
        }

        return customers;
    }

    public CWPanel getView() {
        return view;
    }

    public void dispose() {
        model.getOption().removeValueChangeListener(changeListener);
        view.dispose();
    }

    public String getFilterName() {
        return "Buchungskonto";
    }
}

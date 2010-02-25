package cw.accountmanagementmodul.gui;

import com.jgoodies.binding.value.ValueHolder;
import com.jgoodies.binding.value.ValueModel;
import cw.accountmanagementmodul.extention.AccountCustomerSelectorFilterExtention;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.Action;

/**
 * @author CreativeWorkers.at
 */
public class AccountCustomerSelectorFilterExtentionPresentationModel
{

    private Action noMatterAction;
    private Action notBalancedAction;
    private Action balancedAction;
    private ValueModel option;

    public AccountCustomerSelectorFilterExtentionPresentationModel() {
        initModels();
        initEventHandling();
    }

    public void initModels() {
        option = new ValueHolder(AccountCustomerSelectorFilterExtention.NO_MATTER);
        noMatterAction = new AbstractAction("egal") {
            public void actionPerformed(ActionEvent e) {
                option.setValue(AccountCustomerSelectorFilterExtention.NO_MATTER);
            }
        };
        notBalancedAction = new AbstractAction("ausgeglichen") {
            public void actionPerformed(ActionEvent e) {
                option.setValue(AccountCustomerSelectorFilterExtention.BALANCED);
            }
        };
        balancedAction = new AbstractAction("nicht ausgeglichen") {
            public void actionPerformed(ActionEvent e) {
                option.setValue(AccountCustomerSelectorFilterExtention.NOT_BALANCED);
            }
        };
    }

    private void initEventHandling() {
    }

    public void dispose() {
        // Nothing to do
    }

    
    ////////////////////////////////////////////////////////////////////////////
    // Getter methods for the model
    ////////////////////////////////////////////////////////////////////////////

    public Action getBalancedAction() {
        return balancedAction;
    }

    public Action getNotBalancedAction() {
        return notBalancedAction;
    }

    public Action getNoMatterAction() {
        return noMatterAction;
    }

    public ValueModel getOption() {
        return option;
    }

}

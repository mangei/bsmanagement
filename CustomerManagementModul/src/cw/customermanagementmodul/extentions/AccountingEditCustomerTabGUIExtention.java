package cw.customermanagementmodul.extentions;

import cw.customermanagementmodul.extentions.interfaces.EditCustomerGUITabExtention;
import com.jgoodies.binding.value.ValueModel;
import cw.customermanagementmodul.gui.AccountingManagementCustomerGUIExtentionPresentationModel;
import cw.customermanagementmodul.gui.AccountingManagementCustomerGUIExtentionView;
import javax.swing.JComponent;
import cw.customermanagementmodul.pojo.Customer;

/**
 *
 * @author Manuel Geier
 */
public class AccountingEditCustomerTabGUIExtention
        implements EditCustomerGUITabExtention {

    private static AccountingManagementCustomerGUIExtentionPresentationModel model;
    
    public void initPresentationModel(final Customer c, ValueModel unsaved) {
        model = new AccountingManagementCustomerGUIExtentionPresentationModel(c);
    }
    
    public JComponent getView() {
        return new AccountingManagementCustomerGUIExtentionView(model).buildPanel();
    }

    public void save() {
        // Not necessary for this Extention
    }

    public void reset() {
        // Not necessary for this Extention
    }
}

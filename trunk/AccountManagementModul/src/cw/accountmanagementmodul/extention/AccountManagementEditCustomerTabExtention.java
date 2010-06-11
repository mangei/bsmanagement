package cw.accountmanagementmodul.extention;

import cw.accountmanagementmodul.gui.AccountManagementEditCustomerPresentationModel;
import cw.accountmanagementmodul.gui.AccountManagementEditCustomerView;
import cw.boardingschoolmanagement.gui.component.CWPanel;
import cw.customermanagementmodul.extention.point.EditCustomerTabExtentionPoint;
import cw.customermanagementmodul.gui.EditCustomerPresentationModel;
import cw.accountmanagementmodul.pojo.Posting;
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
    
    public void initPresentationModel(EditCustomerPresentationModel editCustomerModel) {
        this.editCustomerModel = editCustomerModel;
        model = new AccountManagementEditCustomerPresentationModel(editCustomerModel.getBean());
        view = new AccountManagementEditCustomerView(model);
    }
    
    public CWPanel getView() {
        return view;
    }

    public Object getModel() {
        return model;
    }

    public void save() {
        // Not necessary for this Extention
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

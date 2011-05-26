package cw.accountmanagementmodul.extention;

import cw.accountmanagementmodul.gui.PostingManagementAccountManagementPresentationModel;
import cw.accountmanagementmodul.gui.PostingManagementAccountManagementView;
import cw.accountmanagementmodul.pojo.manager.AccountManager;
import cw.boardingschoolmanagement.gui.component.CWPanel;
import cw.customermanagementmodul.extention.point.EditCustomerTabExtentionPoint;
import cw.customermanagementmodul.gui.EditCustomerPresentationModel;
import java.util.List;

/**
 *
 * @author Manuel Geier
 */
public class PostingEditCustomerTabExtention
        implements EditCustomerTabExtentionPoint {

    private PostingManagementAccountManagementPresentationModel model;
    private PostingManagementAccountManagementView view;
    private EditCustomerPresentationModel editCustomerModel;
    
    public void initPresentationModel(EditCustomerPresentationModel editCustomerModel) {
        this.editCustomerModel = editCustomerModel;
        model = new PostingManagementAccountManagementPresentationModel(AccountManager.getInstance().get(editCustomerModel.getBean()));
        view = new PostingManagementAccountManagementView(model);
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

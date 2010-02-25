package cw.accountmanagementmodul.extention;

import cw.accountmanagementmodul.gui.PostingManagementEditCustomerPresentationModel;
import cw.accountmanagementmodul.gui.PostingManagementEditCustomerView;
import cw.boardingschoolmanagement.gui.component.CWPanel;
import cw.customermanagementmodul.extention.point.EditCustomerTabExtentionPoint;
import cw.customermanagementmodul.gui.EditCustomerPresentationModel;
import cw.accountmanagementmodul.pojo.Posting;
import java.util.List;

/**
 *
 * @author Manuel Geier
 */
public class PostingEditCustomerTabExtention
        implements EditCustomerTabExtentionPoint {

    private PostingManagementEditCustomerPresentationModel model;
    private PostingManagementEditCustomerView view;
    private EditCustomerPresentationModel editCustomerModel;
    
    public void initPresentationModel(EditCustomerPresentationModel editCustomerModel) {
        this.editCustomerModel = editCustomerModel;
        model = new PostingManagementEditCustomerPresentationModel(editCustomerModel.getBean());
        view = new PostingManagementEditCustomerView(model);
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

    public void addPosting(Posting posting) {
        model.getPostingSelection().getList().add(posting);
    }

    public void dispose() {
        view.dispose();
    }

    public int priority() {
        return 0;
    }
}

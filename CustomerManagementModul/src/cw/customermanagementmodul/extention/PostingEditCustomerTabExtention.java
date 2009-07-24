package cw.customermanagementmodul.extention;

import cw.boardingschoolmanagement.gui.component.CWPanel;
import cw.customermanagementmodul.extention.point.EditCustomerTabExtention;
import cw.customermanagementmodul.gui.EditCustomerPresentationModel;
import cw.customermanagementmodul.gui.PostingManagementEditCustomerPresentationModel;
import cw.customermanagementmodul.gui.PostingManagementEditCustomerView;
import cw.customermanagementmodul.pojo.Posting;
import java.util.List;

/**
 *
 * @author Manuel Geier
 */
public class PostingEditCustomerTabExtention
        implements EditCustomerTabExtention {

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

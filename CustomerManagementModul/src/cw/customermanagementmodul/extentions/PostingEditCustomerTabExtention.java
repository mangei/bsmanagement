package cw.customermanagementmodul.extentions;

import cw.customermanagementmodul.extentions.interfaces.EditCustomerTabExtention;
import cw.customermanagementmodul.gui.EditCustomerPresentationModel;
import cw.customermanagementmodul.gui.PostingManagementPresentationModel;
import cw.customermanagementmodul.gui.PostingManagementView;
import javax.swing.JComponent;
import cw.customermanagementmodul.pojo.Customer;
import cw.customermanagementmodul.pojo.Posting;
import java.util.List;

/**
 *
 * @author Manuel Geier
 */
public class PostingEditCustomerTabExtention
        implements EditCustomerTabExtention {

    private PostingManagementPresentationModel model;
    private EditCustomerPresentationModel editCustomerModel;
    
    public void initPresentationModel(EditCustomerPresentationModel editCustomerModel) {
        this.editCustomerModel = editCustomerModel;
        model = new PostingManagementPresentationModel(editCustomerModel.getBean());
    }
    
    public JComponent getView() {
        return new PostingManagementView(model).buildPanel();
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
}

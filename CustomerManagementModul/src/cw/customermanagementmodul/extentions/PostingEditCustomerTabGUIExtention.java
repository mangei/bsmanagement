package cw.customermanagementmodul.extentions;

import cw.customermanagementmodul.extentions.interfaces.EditCustomerGUITabExtention;
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
public class PostingEditCustomerTabGUIExtention
        implements EditCustomerGUITabExtention {

    private PostingManagementPresentationModel model;
    private EditCustomerPresentationModel editCustomerModel;
    
    public void initPresentationModel(final Customer c, EditCustomerPresentationModel editCustomerModel) {
        this.editCustomerModel = editCustomerModel;
        model = new PostingManagementPresentationModel(c);
    }
    
    public JComponent getView() {
        return new PostingManagementView(model).buildPanel();
    }

    public void save() {
        // Not necessary for this Extention
    }

    public void reset() {
        // Not necessary for this Extention
    }

    public boolean validate() {
        return true;
    }

    public List<String> getErrorMessages() {
        return null;
    }

    public void addPosting(Posting posting) {
        model.getPostingSelection().getList().add(posting);
    }
}

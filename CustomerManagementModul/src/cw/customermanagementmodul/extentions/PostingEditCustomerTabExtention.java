package cw.customermanagementmodul.extentions;

import cw.boardingschoolmanagement.interfaces.Disposable;
import cw.customermanagementmodul.extentions.interfaces.EditCustomerTabExtention;
import cw.customermanagementmodul.gui.EditCustomerPresentationModel;
import cw.customermanagementmodul.gui.PostingManagementPresentationModel;
import cw.customermanagementmodul.gui.PostingManagementView;
import javax.swing.JComponent;
import cw.customermanagementmodul.pojo.Posting;
import java.util.List;

/**
 *
 * @author Manuel Geier
 */
public class PostingEditCustomerTabExtention
        implements EditCustomerTabExtention, Disposable {

    private PostingManagementPresentationModel model;
    private PostingManagementView view;
    private EditCustomerPresentationModel editCustomerModel;
    
    public void initPresentationModel(EditCustomerPresentationModel editCustomerModel) {
        this.editCustomerModel = editCustomerModel;
        model = new PostingManagementPresentationModel(editCustomerModel.getBean());
    }
    
    public JComponent getView() {
        view = new PostingManagementView(model);
        return view.buildPanel();
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

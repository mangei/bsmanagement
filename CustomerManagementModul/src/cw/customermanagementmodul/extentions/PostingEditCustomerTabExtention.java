package cw.customermanagementmodul.extentions;

import cw.boardingschoolmanagement.gui.component.JViewPanel;
import cw.boardingschoolmanagement.interfaces.Disposable;
import cw.customermanagementmodul.extentions.interfaces.EditCustomerTabExtention;
import cw.customermanagementmodul.gui.EditCustomerPresentationModel;
import cw.customermanagementmodul.gui.PostingManagementEditCustomerPresentationModel;
import cw.customermanagementmodul.gui.PostingManagementEditCustomerView;
import javax.swing.JComponent;
import cw.customermanagementmodul.pojo.Posting;
import java.util.List;

/**
 *
 * @author Manuel Geier
 */
public class PostingEditCustomerTabExtention
        implements EditCustomerTabExtention, Disposable {

    private PostingManagementEditCustomerPresentationModel model;
    private PostingManagementEditCustomerView view;
    private JViewPanel panel;
    private EditCustomerPresentationModel editCustomerModel;
    
    public void initPresentationModel(EditCustomerPresentationModel editCustomerModel) {
        this.editCustomerModel = editCustomerModel;
        model = new PostingManagementEditCustomerPresentationModel(editCustomerModel.getBean());
    }
    
    public JComponent getView() {
        view = new PostingManagementEditCustomerView(model);
        panel = view.buildPanel();
        return panel;
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
        panel.dispose();
    }

    public int priority() {
        return 0;
    }
}

package cw.customermanagementmodul.extentions;

import cw.customermanagementmodul.extentions.interfaces.EditCustomerTabExtention;
import cw.customermanagementmodul.gui.EditCustomerEditCustomerTabExtentionPresentationModel;
import cw.customermanagementmodul.gui.EditCustomerEditCustomerTabExtentionView;
import cw.customermanagementmodul.gui.EditCustomerPresentationModel;
import java.util.List;
import javax.swing.JComponent;

/**
 *
 * @author Manuel Geier
 */
public class EditCustomerEditCustomerTabExtention
        implements EditCustomerTabExtention {

    private EditCustomerEditCustomerTabExtentionPresentationModel model;
    private EditCustomerEditCustomerTabExtentionView view;
    private EditCustomerPresentationModel editCustomerModel;

    public void initPresentationModel(EditCustomerPresentationModel editCustomerModel) {
        this.editCustomerModel = editCustomerModel;
        model = new EditCustomerEditCustomerTabExtentionPresentationModel(editCustomerModel);
    }
    
    public JComponent getView() {
         view = new EditCustomerEditCustomerTabExtentionView(model);
         return view.buildPanel();
    }

    public void save() {
        model.save();
    }

    public List<String> validate() {
        return null;
    }

    public void dispose() {
        view.dispose();
    }

    public int priority() {
        return -90;
    }

}

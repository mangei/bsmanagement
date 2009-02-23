package cw.customermanagementmodul.extentions;

import cw.customermanagementmodul.extentions.interfaces.EditCustomerTabExtention;
import cw.customermanagementmodul.gui.EditCustomerPresentationModel;
import cw.customermanagementmodul.gui.GroupEditCustomerTabExtentionPresentationModel;
import cw.customermanagementmodul.gui.GroupEditCustomerTabExtentionView;
import java.util.List;
import javax.swing.JComponent;

/**
 *
 * @author Manuel Geier
 */
public class GroupEditCustomerTabExtention
        implements EditCustomerTabExtention {

    private GroupEditCustomerTabExtentionPresentationModel model;
    private EditCustomerPresentationModel editCustomerModel;

    public void initPresentationModel(EditCustomerPresentationModel editCustomerModel) {
        this.editCustomerModel = editCustomerModel;
        model = new GroupEditCustomerTabExtentionPresentationModel(editCustomerModel.getBean(), editCustomerModel.getUnsaved());
    }
    
    public JComponent getView() {
        return new GroupEditCustomerTabExtentionView(model).buildPanel();
    }

    public void save() {
        // Not necessary for this Extention
    }

    public List<String> validate() {
        return null;
    }

}

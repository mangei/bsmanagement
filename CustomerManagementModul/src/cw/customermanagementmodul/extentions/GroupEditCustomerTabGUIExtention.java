package cw.customermanagementmodul.extentions;

import cw.customermanagementmodul.extentions.interfaces.EditCustomerGUITabExtention;
import cw.customermanagementmodul.gui.EditCustomerPresentationModel;
import cw.customermanagementmodul.gui.GroupEditCustomerTabGUIExtentionPresentationModel;
import cw.customermanagementmodul.gui.GroupEditCustomerTabGUIExtentionView;
import java.util.List;
import javax.swing.JComponent;
import cw.customermanagementmodul.pojo.Customer;

/**
 *
 * @author Manuel Geier
 */
public class GroupEditCustomerTabGUIExtention
        implements EditCustomerGUITabExtention {

    private GroupEditCustomerTabGUIExtentionPresentationModel model;
    private EditCustomerPresentationModel editCustomerModel;

    public void initPresentationModel(final Customer c, EditCustomerPresentationModel editCustomerModel) {
        this.editCustomerModel = editCustomerModel;
        model = new GroupEditCustomerTabGUIExtentionPresentationModel(c, editCustomerModel.getUnsaved());
    }
    
    public JComponent getView() {
        return new GroupEditCustomerTabGUIExtentionView(model).buildPanel();
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
}

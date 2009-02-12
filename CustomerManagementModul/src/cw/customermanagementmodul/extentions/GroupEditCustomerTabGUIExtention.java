package cw.customermanagementmodul.extentions;

import cw.customermanagementmodul.extentions.interfaces.EditCustomerGUITabExtention;
import com.jgoodies.binding.value.ValueModel;
import cw.customermanagementmodul.gui.GroupEditCustomerTabGUIExtentionPresentationModel;
import cw.customermanagementmodul.gui.GroupEditCustomerTabGUIExtentionView;
import java.util.List;
import javax.swing.JComponent;
import cw.customermanagementmodul.pojo.Customer;
import java.util.ArrayList;

/**
 *
 * @author Manuel Geier
 */
public class GroupEditCustomerTabGUIExtention
        implements EditCustomerGUITabExtention {

    private static GroupEditCustomerTabGUIExtentionPresentationModel model;
    
    public void initPresentationModel(final Customer c, ValueModel unsaved) {
        model = new GroupEditCustomerTabGUIExtentionPresentationModel(c, unsaved);
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

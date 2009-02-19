package cw.customermanagementmodul.extentions;

import cw.customermanagementmodul.extentions.interfaces.EditCustomerGUITabExtention;
import com.jgoodies.binding.value.ValueModel;
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
    
    public void initPresentationModel(final Customer c, ValueModel unsaved) {
        System.out.println("MODEL");
        model = new GroupEditCustomerTabGUIExtentionPresentationModel(c, unsaved);
    }
    
    public JComponent getView() {
        System.out.println("VIEW");
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

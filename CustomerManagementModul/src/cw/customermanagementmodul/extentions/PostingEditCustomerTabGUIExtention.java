package cw.customermanagementmodul.extentions;

import cw.customermanagementmodul.extentions.interfaces.EditCustomerGUITabExtention;
import com.jgoodies.binding.value.ValueModel;
import cw.customermanagementmodul.gui.PostingManagementEditCustomerTabGUIExtentionPresentationModel;
import cw.customermanagementmodul.gui.PostingManagementEditCustomerTabGUIExtentionView;
import javax.swing.JComponent;
import cw.customermanagementmodul.pojo.Customer;

/**
 *
 * @author Manuel Geier
 */
public class PostingEditCustomerTabGUIExtention
        implements EditCustomerGUITabExtention {

    private static PostingManagementEditCustomerTabGUIExtentionPresentationModel model;
    
    public void initPresentationModel(final Customer c, ValueModel unsaved) {
        model = new PostingManagementEditCustomerTabGUIExtentionPresentationModel(c);
    }
    
    public JComponent getView() {
        return new PostingManagementEditCustomerTabGUIExtentionView(model).buildPanel();
    }

    public void save() {
        // Not necessary for this Extention
    }

    public void reset() {
        // Not necessary for this Extention
    }
}

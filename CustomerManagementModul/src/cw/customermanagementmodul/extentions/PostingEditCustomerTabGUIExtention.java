package cw.customermanagementmodul.extentions;

import cw.customermanagementmodul.extentions.interfaces.EditCustomerGUITabExtention;
import com.jgoodies.binding.value.ValueModel;
import cw.customermanagementmodul.gui.PostingManagementPresentationModel;
import cw.customermanagementmodul.gui.PostingManagementView;
import javax.swing.JComponent;
import cw.customermanagementmodul.pojo.Customer;
import java.util.List;

/**
 *
 * @author Manuel Geier
 */
public class PostingEditCustomerTabGUIExtention
        implements EditCustomerGUITabExtention {

    private static PostingManagementPresentationModel model;
    
    public void initPresentationModel(final Customer c, ValueModel unsaved) {
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
}

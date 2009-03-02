package cw.customermanagementmodul.extentions;

import cw.customermanagementmodul.extentions.interfaces.EditCustomerTabExtention;
import cw.customermanagementmodul.gui.EditCustomerPresentationModel;
import cw.customermanagementmodul.gui.GroupEditCustomerPresentationModel;
import cw.customermanagementmodul.gui.GroupEditCustomerView;
import java.util.List;
import javax.swing.JComponent;

/**
 *
 * @author Manuel Geier
 */
public class GroupEditCustomerTabExtention
        implements EditCustomerTabExtention {

    private GroupEditCustomerPresentationModel model;
    private GroupEditCustomerView view;
    private EditCustomerPresentationModel editCustomerModel;

    public void initPresentationModel(EditCustomerPresentationModel editCustomerModel) {
        this.editCustomerModel = editCustomerModel;
        model = new GroupEditCustomerPresentationModel(editCustomerModel.getBean(), editCustomerModel.getUnsaved());
    }
    
    public JComponent getView() {
         view = new GroupEditCustomerView(model);
         return view.buildPanel();
    }

    public void save() {
        // Not necessary for this Extention
    }

    public List<String> validate() {
        return null;
    }

    public void dispose() {
        view.dispose();
    }

    public int priority() {
        return 0;
    }

}

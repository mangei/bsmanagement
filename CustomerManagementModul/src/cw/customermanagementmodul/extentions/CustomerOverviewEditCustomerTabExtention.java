package cw.customermanagementmodul.extentions;

import cw.customermanagementmodul.extentions.interfaces.EditCustomerTabExtention;
import cw.customermanagementmodul.gui.CustomerOverviewEditCustomerTabExtentionPresentationModel;
import cw.customermanagementmodul.gui.CustomerOverviewEditCustomerTabExtentionView;
import cw.customermanagementmodul.gui.EditCustomerPresentationModel;
import java.util.List;
import javax.swing.JComponent;

/**
 *
 * @author Manuel Geier
 */
public class CustomerOverviewEditCustomerTabExtention
        implements EditCustomerTabExtention {

    private CustomerOverviewEditCustomerTabExtentionPresentationModel model;
    private CustomerOverviewEditCustomerTabExtentionView view;
    private EditCustomerPresentationModel editCustomerModel;

    public void initPresentationModel(EditCustomerPresentationModel editCustomerModel) {
        this.editCustomerModel = editCustomerModel;
        model = new CustomerOverviewEditCustomerTabExtentionPresentationModel(editCustomerModel);
    }
    
    public JComponent getView() {
         view = new CustomerOverviewEditCustomerTabExtentionView(model);
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
        return -100;
    }

}

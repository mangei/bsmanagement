package cw.customermanagementmodul.extention;

import cw.boardingschoolmanagement.gui.component.CWPanel;
import cw.customermanagementmodul.extention.point.EditCustomerTabExtention;
import cw.customermanagementmodul.gui.CustomerOverviewEditCustomerPresentationModel;
import cw.customermanagementmodul.gui.CustomerOverviewEditCustomerView;
import cw.customermanagementmodul.gui.EditCustomerPresentationModel;
import java.util.List;

/**
 *
 * @author Manuel Geier
 */
public class CustomerOverviewEditCustomerTabExtention
        implements EditCustomerTabExtention {

    private CustomerOverviewEditCustomerPresentationModel model;
    private CustomerOverviewEditCustomerView view;
    private EditCustomerPresentationModel editCustomerModel;

    public void initPresentationModel(EditCustomerPresentationModel editCustomerModel) {
        this.editCustomerModel = editCustomerModel;
        model = new CustomerOverviewEditCustomerPresentationModel(editCustomerModel);
    }
    
    public CWPanel getView() {
        if(view == null) {
            view = new CustomerOverviewEditCustomerView(model);
        }
        return view;
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

    public void dispose() {
        view.dispose();
        
        // Kill references
        view = null;
        model = null;
        editCustomerModel = null;
    }

    public int priority() {
        return 100;
    }

}

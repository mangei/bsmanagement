package cw.customermanagementmodul.extention;

import cw.boardingschoolmanagement.gui.component.CWPanel;
import cw.customermanagementmodul.extention.point.CustomerOverviewEditCustomerExtention;
import cw.customermanagementmodul.gui.CustomerOverviewEditCustomerPresentationModel;
import cw.customermanagementmodul.gui.GeneralCustomerOverviewEditCustomerPresentationModel;
import cw.customermanagementmodul.gui.GeneralCustomerOverviewEditCustomerView;

/**
 *
 * @author Manuel Geier
 */
public class GeneralCustomerOverviewEditCustomerExtention
        implements CustomerOverviewEditCustomerExtention {

    private GeneralCustomerOverviewEditCustomerPresentationModel model;
    private GeneralCustomerOverviewEditCustomerView view;
    private CustomerOverviewEditCustomerPresentationModel customerOverviewEditCustomerPresentationModel;

    public void initPresentationModel(CustomerOverviewEditCustomerPresentationModel customerOverviewEditCustomerPresentationModel) {
        this.customerOverviewEditCustomerPresentationModel = customerOverviewEditCustomerPresentationModel;
        model = new GeneralCustomerOverviewEditCustomerPresentationModel(customerOverviewEditCustomerPresentationModel);
        view = new GeneralCustomerOverviewEditCustomerView(model);
    }
    
    public CWPanel getView() {
         return view;
    }

    public void dispose() {
        view.dispose();

        // Kill references
        model = null;
        view = null;
        customerOverviewEditCustomerPresentationModel = null;
    }

    public int priority() {
        return 100;
    }

}

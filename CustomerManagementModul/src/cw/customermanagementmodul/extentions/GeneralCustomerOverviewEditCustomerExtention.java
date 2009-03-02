package cw.customermanagementmodul.extentions;

import cw.customermanagementmodul.extentions.interfaces.CustomerOverviewEditCustomerExtention;
import cw.customermanagementmodul.gui.CustomerOverviewEditCustomerPresentationModel;
import cw.customermanagementmodul.gui.CustomerOverviewEditCustomerView;
import cw.customermanagementmodul.gui.GeneralCustomerOverviewEditCustomerPresentationModel;
import cw.customermanagementmodul.gui.GeneralCustomerOverviewEditCustomerView;
import javax.swing.JComponent;

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
    }
    
    public JComponent getView() {
         view = new GeneralCustomerOverviewEditCustomerView(model);
         return view.buildPanel();
    }

    public void dispose() {
        view.dispose();
    }

    public int priority() {
        return 100;
    }

}

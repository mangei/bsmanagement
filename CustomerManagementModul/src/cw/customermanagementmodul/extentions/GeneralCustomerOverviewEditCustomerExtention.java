package cw.customermanagementmodul.extentions;

import cw.boardingschoolmanagement.gui.component.CWJPanel;
import cw.customermanagementmodul.extentions.interfaces.CustomerOverviewEditCustomerExtention;
import cw.customermanagementmodul.gui.CustomerOverviewEditCustomerPresentationModel;
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
    private CWJPanel panel;

    public void initPresentationModel(CustomerOverviewEditCustomerPresentationModel customerOverviewEditCustomerPresentationModel) {
        this.customerOverviewEditCustomerPresentationModel = customerOverviewEditCustomerPresentationModel;
        model = new GeneralCustomerOverviewEditCustomerPresentationModel(customerOverviewEditCustomerPresentationModel);
    }
    
    public JComponent getView() {
         view = new GeneralCustomerOverviewEditCustomerView(model);
         panel = view.buildPanel();
         return panel;
    }

    public void dispose() {
        panel.dispose();

        // Kill references
        model = null;
        view = null;
        customerOverviewEditCustomerPresentationModel = null;
        panel = null;
    }

    public int priority() {
        return 100;
    }

}

package cw.customermanagementmodul.gui;

import com.jgoodies.binding.value.ValueHolder;
import com.jgoodies.binding.value.ValueModel;
import cw.boardingschoolmanagement.gui.component.JViewPanel.HeaderInfo;
import cw.boardingschoolmanagement.interfaces.Disposable;
import java.util.List;
import cw.customermanagementmodul.extentions.interfaces.EditCustomerTabExtention;

/**
 *
 * @author CreativeWorkers.at
 */
public class GeneralCustomerOverviewEditCustomerPresentationModel
        implements Disposable {

    private CustomerOverviewEditCustomerPresentationModel customerOverviewEditCustomerPresentationModel;

    public GeneralCustomerOverviewEditCustomerPresentationModel(CustomerOverviewEditCustomerPresentationModel customerOverviewEditCustomerPresentationModel) {
        this.customerOverviewEditCustomerPresentationModel = customerOverviewEditCustomerPresentationModel;

        initModels();
        initEventHandling();
    }

    public void initModels() {
        // Nothing to do
    }

    public void initEventHandling() {
        // Nothing to do
    }

    public EditCustomerPresentationModel getEditCustomerPresentationModel() {
        return customerOverviewEditCustomerPresentationModel.getEditCustomerPresentationModel();
    }

    public void dispose() {
    }

}

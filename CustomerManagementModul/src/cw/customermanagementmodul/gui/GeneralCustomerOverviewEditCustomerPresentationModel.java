package cw.customermanagementmodul.gui;

import cw.boardingschoolmanagement.interfaces.Disposable;

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

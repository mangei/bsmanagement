package cw.customermanagementmodul.customer.gui;

import cw.boardingschoolmanagement.gui.CWPresentationModel;


/**
 *
 * @author CreativeWorkers.at
 */
public class GeneralCustomerOverviewEditCustomerPresentationModel
	extends CWPresentationModel
{

    private CustomerOverviewEditCustomerPresentationModel customerOverviewEditCustomerPresentationModel;

    public GeneralCustomerOverviewEditCustomerPresentationModel(CustomerOverviewEditCustomerPresentationModel customerOverviewEditCustomerPresentationModel) {
        super(null);
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

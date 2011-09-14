package cw.customermanagementmodul.customer.extention;

import cw.boardingschoolmanagement.extention.point.CWViewExtentionPoint;
import cw.customermanagementmodul.customer.gui.CustomerOverviewEditCustomerView;
import cw.customermanagementmodul.customer.gui.EditCustomerView;
import cw.customermanagementmodul.customer.gui.GeneralCustomerOverviewEditCustomerPresentationModel;
import cw.customermanagementmodul.customer.gui.GeneralCustomerOverviewEditCustomerView;

/**
 *
 * @author Manuel Geier
 */
public class GeneralCustomerOverviewEditCustomerExtention
        implements CWViewExtentionPoint<CustomerOverviewEditCustomerView> {

    private GeneralCustomerOverviewEditCustomerPresentationModel model;
    private GeneralCustomerOverviewEditCustomerView view;
    private CustomerOverviewEditCustomerView baseView;
    
    @Override
	public Class<?> getViewExtentionClass() {
		return CustomerOverviewEditCustomerView.class;
	}

	@Override
	public void initComponents(CustomerOverviewEditCustomerView baseView) {
		this.baseView = baseView;
		
        model = new GeneralCustomerOverviewEditCustomerPresentationModel(baseView.getModel());
        view = new GeneralCustomerOverviewEditCustomerView(model);
        view.initComponents();
	}

	@Override
	public void buildView() {
		view.buildView();
		baseView.addToContentPanel(view);
	}

	@Override
	public void dispose() {
		view.dispose();
	}

}

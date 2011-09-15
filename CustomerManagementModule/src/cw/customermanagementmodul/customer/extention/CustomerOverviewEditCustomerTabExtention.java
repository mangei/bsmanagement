package cw.customermanagementmodul.customer.extention;

import java.util.List;

import javax.persistence.EntityManager;

import cw.boardingschoolmanagement.extention.point.CWIViewExtentionPoint;
import cw.boardingschoolmanagement.gui.CWErrorMessage;
import cw.boardingschoolmanagement.gui.component.CWPanel;
import cw.customermanagementmodul.customer.gui.CustomerOverviewEditCustomerPresentationModel;
import cw.customermanagementmodul.customer.gui.CustomerOverviewEditCustomerView;
import cw.customermanagementmodul.customer.gui.EditCustomerPresentationModel;
import cw.customermanagementmodul.customer.gui.EditCustomerView;
import cw.customermanagementmodul.customer.gui.GeneralCustomerOverviewEditCustomerPresentationModel;
import cw.customermanagementmodul.customer.gui.GeneralCustomerOverviewEditCustomerView;

/**
 *
 * @author Manuel Geier
 */
public class CustomerOverviewEditCustomerTabExtention
        implements CWIViewExtentionPoint<EditCustomerView> {

    private CustomerOverviewEditCustomerPresentationModel model;
    private CustomerOverviewEditCustomerView view;
    private EditCustomerView baseView;
    
    @Override
	public Class<?> getExtentionClass() {
		return EditCustomerView.class;
	}

	@Override
	public void initComponents(EditCustomerView baseView) {
		this.baseView = baseView;
		
		model = new CustomerOverviewEditCustomerPresentationModel(baseView.getModel(), baseView.getModel().getEntityManager());
        view = new CustomerOverviewEditCustomerView(model);
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

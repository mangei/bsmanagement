package cw.customermanagementmodul.customer.extention;

import java.util.List;

import javax.persistence.EntityManager;

import cw.boardingschoolmanagement.extention.point.CWIViewExtentionPoint;
import cw.boardingschoolmanagement.gui.CWErrorMessage;
import cw.boardingschoolmanagement.gui.component.CWPanel;
import cw.customermanagementmodul.customer.gui.EditCustomerEditCustomerPresentationModel;
import cw.customermanagementmodul.customer.gui.EditCustomerEditCustomerView;
import cw.customermanagementmodul.customer.gui.EditCustomerPresentationModel;
import cw.customermanagementmodul.customer.gui.EditCustomerView;

/**
 *
 * @author Manuel Geier
 */
public class EditCustomerEditCustomerTabExtention
        implements CWIViewExtentionPoint<EditCustomerView> {

    private EditCustomerEditCustomerPresentationModel model;
    private EditCustomerEditCustomerView view;
    private EditCustomerView baseView;

	@Override
	public Class<?> getExtentionClass() {
		return EditCustomerView.class;
	}

	@Override
	public void initComponents(EditCustomerView baseView) {
		this.baseView = baseView;
		
        model = new EditCustomerEditCustomerPresentationModel(baseView.getModel(), baseView.getModel().getEntityManager());
        view = new EditCustomerEditCustomerView(model);
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

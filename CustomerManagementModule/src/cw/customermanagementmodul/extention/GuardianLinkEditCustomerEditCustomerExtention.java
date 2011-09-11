package cw.customermanagementmodul.extention;

import javax.persistence.EntityManager;

import cw.boardingschoolmanagement.extention.point.CWEditViewExtentionPoint;
import cw.boardingschoolmanagement.gui.CWEditPresentationModel;
import cw.customermanagementmodul.gui.EditCustomerEditCustomerView;
import cw.customermanagementmodul.gui.EditGuardianEditCustomerPresentationModel;
import cw.customermanagementmodul.gui.EditGuardianEditCustomerView;

public class GuardianLinkEditCustomerEditCustomerExtention
	implements CWEditViewExtentionPoint<EditCustomerEditCustomerView> {

	private EditGuardianEditCustomerPresentationModel model;
	private EditGuardianEditCustomerView view;
	
	@Override
	public Class getViewExtentionClass() {
		return EditCustomerEditCustomerView.class;
	}

	@Override
	public void init(EditCustomerEditCustomerView baseView, EntityManager entityManager) {

		model = new EditGuardianEditCustomerPresentationModel(baseView.getModel().getEditCustomerPresentationModel(), entityManager);
		view = new EditGuardianEditCustomerView(model);
		
		baseView.addToContentPanel(view.panel);
	}

	public CWEditPresentationModel getModel() {
		return model;
	}

}

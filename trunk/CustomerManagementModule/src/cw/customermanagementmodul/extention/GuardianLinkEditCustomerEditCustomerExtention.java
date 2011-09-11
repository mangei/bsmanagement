package cw.customermanagementmodul.extention;

import javax.persistence.EntityManager;
import javax.swing.JLabel;

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
	public void init(EditCustomerEditCustomerView view, EntityManager entityManager) {

		model = new EditGuardianEditCustomerPresentationModel(, entityManager);
		
		view.addToContentPanel(new JLabel("TEST"));
	}

	public CWEditPresentationModel getModel() {
		return model;
	}

}

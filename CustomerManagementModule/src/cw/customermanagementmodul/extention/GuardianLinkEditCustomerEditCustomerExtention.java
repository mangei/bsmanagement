package cw.customermanagementmodul.extention;

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
	public void execute(EditCustomerEditCustomerView view) {

		view.addToContentPanel(new JLabel("TEST"));
	}

	public CWEditPresentationModel getModel() {
		return model;
	}

}

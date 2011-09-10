package cw.customermanagementmodul.extention;

import javax.swing.JLabel;

import cw.boardingschoolmanagement.extention.point.CWViewExtentionPoint;
import cw.customermanagementmodul.gui.EditCustomerEditCustomerView;

public class GuardianLinkEditCustomerEditCustomerExtention
	implements CWViewExtentionPoint<EditCustomerEditCustomerView> {

	@Override
	public Class getViewExtention() {
		return EditCustomerEditCustomerView.class;
	}

	@Override
	public void execute(EditCustomerEditCustomerView view) {

		view.addToContentPanel(new JLabel("TEST"));
	}

	
}

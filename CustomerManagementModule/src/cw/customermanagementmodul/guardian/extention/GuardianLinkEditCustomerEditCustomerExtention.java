package cw.customermanagementmodul.guardian.extention;

import cw.boardingschoolmanagement.extention.point.CWEditViewExtentionPoint;
import cw.boardingschoolmanagement.gui.CWEditPresentationModel;
import cw.boardingschoolmanagement.gui.component.CWView;
import cw.customermanagementmodul.customer.gui.EditCustomerEditCustomerView;
import cw.customermanagementmodul.guaridan.gui.EditGuardianEditCustomerPresentationModel;
import cw.customermanagementmodul.guaridan.gui.EditGuardianEditCustomerView;

public class GuardianLinkEditCustomerEditCustomerExtention
	implements CWEditViewExtentionPoint<EditCustomerEditCustomerView> {

	private EditGuardianEditCustomerPresentationModel model;
	private EditGuardianEditCustomerView view;
	private EditCustomerEditCustomerView baseView;
	
	@Override
	public Class<EditCustomerEditCustomerView> getViewExtentionClass() {
		return EditCustomerEditCustomerView.class;
	}

	@Override
	public void initComponents(EditCustomerEditCustomerView baseView) {
		this.baseView = baseView;
		
		model = new EditGuardianEditCustomerPresentationModel(baseView.getModel().getEditCustomerPresentationModel(), baseView.getModel().getEntityManager());
		view = new EditGuardianEditCustomerView(model);
		view.initComponents();
	}

	@Override
	public void buildView() {
		view.buildView();
		baseView.addToContentPanel(view.panel);
	}

	@Override
	public void dispose() {
		view.dispose();
	}

	@Override
	public CWView<?> getView() {
		return view;
	}

	@Override
	public CWEditPresentationModel<?> getModel() {
		return model;
	}
}

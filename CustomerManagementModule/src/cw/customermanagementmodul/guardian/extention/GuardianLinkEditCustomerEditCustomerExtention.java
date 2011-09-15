package cw.customermanagementmodul.guardian.extention;

import java.util.ArrayList;
import java.util.List;

import cw.boardingschoolmanagement.extention.point.CWIEditViewExtentionPoint;
import cw.boardingschoolmanagement.gui.CWIPresentationModel;
import cw.boardingschoolmanagement.gui.component.CWView;
import cw.customermanagementmodul.customer.gui.EditCustomerEditCustomerPresentationModel;
import cw.customermanagementmodul.customer.gui.EditCustomerEditCustomerView;
import cw.customermanagementmodul.customer.logic.BoCustomer;
import cw.customermanagementmodul.guardian.logic.BoGuardian;
import cw.customermanagementmodul.guaridan.gui.EditGuardianEditCustomerPresentationModel;
import cw.customermanagementmodul.guaridan.gui.EditGuardianEditCustomerView;

public class GuardianLinkEditCustomerEditCustomerExtention
	implements CWIEditViewExtentionPoint<EditCustomerEditCustomerView, EditCustomerEditCustomerPresentationModel> {

	private EditGuardianEditCustomerPresentationModel model;
	private EditGuardianEditCustomerView view;
	
	@Override
	public void initView(EditCustomerEditCustomerView baseView) {
		view = new EditGuardianEditCustomerView(model, baseView);
	}

	@Override
	public void initPresentationModel(EditCustomerEditCustomerPresentationModel baseModel) {
		
		BoCustomer boCustomer = baseModel.getEditCustomerPresentationModel().getBean().getTypedAdapter(BoCustomer.class);
    	BoGuardian boGuardian = boCustomer.getTypedAdapter(BoGuardian.class);
		
		model = new EditGuardianEditCustomerPresentationModel(boGuardian.getPersistence(), baseModel.getEditCustomerPresentationModel(), baseModel.getEntityManager());
	}

	@Override
	public CWIPresentationModel getModel() {
		return model;
	}


	@Override
	public CWView<?> getView() {
		return view;
	}
	
	@Override
	public Class<?> getExtentionClass() {
		return null;
	}

	@Override
	public List<Class<?>> getExtentionClassList() {
		List<Class<?>> list = new ArrayList<Class<?>>();
		list.add(EditCustomerEditCustomerView.class);
		list.add(EditCustomerEditCustomerPresentationModel.class);
		return list;
	}

}

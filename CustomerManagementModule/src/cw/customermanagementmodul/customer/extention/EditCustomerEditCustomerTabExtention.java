package cw.customermanagementmodul.customer.extention;

import java.util.ArrayList;
import java.util.List;

import cw.boardingschoolmanagement.extention.point.CWIEditViewExtentionPoint;
import cw.boardingschoolmanagement.gui.CWIEditPresentationModel;
import cw.boardingschoolmanagement.gui.component.CWView;
import cw.customermanagementmodul.customer.gui.EditCustomerEditCustomerPresentationModel;
import cw.customermanagementmodul.customer.gui.EditCustomerEditCustomerView;
import cw.customermanagementmodul.customer.gui.EditCustomerPresentationModel;
import cw.customermanagementmodul.customer.gui.EditCustomerView;

/**
 *
 * @author Manuel Geier
 */
public class EditCustomerEditCustomerTabExtention
        implements CWIEditViewExtentionPoint<EditCustomerView, EditCustomerPresentationModel> {

    private EditCustomerEditCustomerPresentationModel model;
    private EditCustomerEditCustomerView view;

	@Override
	public CWIEditPresentationModel getModel() {
		return model;
	}


	@Override
	public void initPresentationModel(EditCustomerPresentationModel baseModel) {
		model = new EditCustomerEditCustomerPresentationModel(baseModel, baseModel.getEntityManager());
	}


	@Override
	public Class<?> getExtentionClass() {
		return null;
	}


	@Override
	public void initView(EditCustomerView baseView) {
		view = new EditCustomerEditCustomerView(model, baseView);
	}


	@Override
	public CWView<?> getView() {
		return view;
	}


	@Override
	public List<Class<?>> getExtentionClassList() {
		List<Class<?>> list = new ArrayList<Class<?>>();
		list.add(EditCustomerView.class);
		list.add(EditCustomerPresentationModel.class);
		return list;
	}

}

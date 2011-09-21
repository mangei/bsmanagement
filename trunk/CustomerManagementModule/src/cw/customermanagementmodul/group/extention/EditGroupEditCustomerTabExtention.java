package cw.customermanagementmodul.group.extention;

import java.util.ArrayList;
import java.util.List;

import cw.boardingschoolmanagement.extention.point.CWIEditViewExtentionPoint;
import cw.boardingschoolmanagement.gui.CWIEditPresentationModel;
import cw.boardingschoolmanagement.gui.component.CWView;
import cw.customermanagementmodul.customer.gui.EditCustomerPresentationModel;
import cw.customermanagementmodul.customer.gui.EditCustomerView;
import cw.customermanagementmodul.group.gui.EditGroupEditCustomerPresentationModel;
import cw.customermanagementmodul.group.gui.EditGroupEditCustomerView;

/**
 *
 * @author Manuel Geier
 */
public class EditGroupEditCustomerTabExtention
        implements CWIEditViewExtentionPoint<EditCustomerView, EditCustomerPresentationModel> {

    private EditGroupEditCustomerPresentationModel model;
    private EditGroupEditCustomerView view;
    
	@Override
	public CWIEditPresentationModel getModel() {
		return model;
	}

	@Override
	public void initPresentationModel(EditCustomerPresentationModel baseModel) {
		model = new EditGroupEditCustomerPresentationModel(baseModel.getBean(), baseModel.getUnsaved(), baseModel.getEntityManager());
	}

	@Override
	public Class<?> getExtentionClass() {
		return null;
	}

	@Override
	public void initView(EditCustomerView baseView) {
		view = new EditGroupEditCustomerView(model, baseView);
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

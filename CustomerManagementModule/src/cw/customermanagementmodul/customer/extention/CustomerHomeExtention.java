package cw.customermanagementmodul.customer.extention;

import java.util.ArrayList;
import java.util.List;

import cw.boardingschoolmanagement.extention.point.CWIViewExtentionPoint;
import cw.boardingschoolmanagement.gui.HomePresentationModel;
import cw.boardingschoolmanagement.gui.HomeView;
import cw.boardingschoolmanagement.gui.component.CWView;
import cw.customermanagementmodul.customer.gui.CustomerHomeExtentionPresentationModel;
import cw.customermanagementmodul.customer.gui.CustomerHomeExtentionView;
import cw.customermanagementmodul.customer.gui.EditCustomerPresentationModel;
import cw.customermanagementmodul.customer.gui.EditCustomerView;

/**
 *
 * @author Manuel Geier
 */
public class CustomerHomeExtention
	implements CWIViewExtentionPoint<HomeView, HomePresentationModel> {

    private CustomerHomeExtentionPresentationModel model;
    private CustomerHomeExtentionView view;

	@Override
	public Class<?> getExtentionClass() {
		return null;
	}

	@Override
	public void initView(HomeView baseView) {
		view = new CustomerHomeExtentionView(model, baseView);
	}

	@Override
	public CWView<?> getView() {
		return view;
	}

	@Override
	public void initPresentationModel(HomePresentationModel baseModel) {
		model = new CustomerHomeExtentionPresentationModel(baseModel.getEntityManager());
	}
	
	@Override
	public List<Class<?>> getExtentionClassList() {
		List<Class<?>> list = new ArrayList<Class<?>>();
		list.add(HomeView.class);
		list.add(HomePresentationModel.class);
		return list;
	}
	
}

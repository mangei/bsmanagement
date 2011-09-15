package cw.customermanagementmodul.customer.extention;

import cw.boardingschoolmanagement.extention.point.CWIViewExtentionPoint;
import cw.boardingschoolmanagement.gui.HomeView;
import cw.customermanagementmodul.customer.gui.CustomerHomeExtentionPresentationModel;
import cw.customermanagementmodul.customer.gui.CustomerHomeExtentionView;

/**
 *
 * @author ManuelG
 */
public class CustomerHomeExtention
	implements CWIViewExtentionPoint<HomeView> {

    private CustomerHomeExtentionPresentationModel model;
    private CustomerHomeExtentionView view;
    private HomeView baseView;

	@Override
	public Class<?> getExtentionClass() {
		return HomeView.class;
	}

	@Override
	public void initComponents(HomeView baseView) {
		this.baseView = baseView;
		
		model = new CustomerHomeExtentionPresentationModel(baseView.getModel().getEntityManager());
        view = new CustomerHomeExtentionView(model);
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

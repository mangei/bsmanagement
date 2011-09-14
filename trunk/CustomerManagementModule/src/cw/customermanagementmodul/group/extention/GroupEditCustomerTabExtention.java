package cw.customermanagementmodul.group.extention;

import cw.boardingschoolmanagement.extention.point.CWViewExtentionPoint;
import cw.customermanagementmodul.customer.gui.EditCustomerView;
import cw.customermanagementmodul.group.gui.GroupEditCustomerPresentationModel;
import cw.customermanagementmodul.group.gui.GroupEditCustomerView;

/**
 *
 * @author Manuel Geier
 */
public class GroupEditCustomerTabExtention
        implements CWViewExtentionPoint<EditCustomerView> {

    private GroupEditCustomerPresentationModel model;
    private GroupEditCustomerView view;
    private EditCustomerView baseView;
    
    @Override
	public Class<?> getViewExtentionClass() {
		return EditCustomerView.class;
	}

	@Override
	public void initComponents(EditCustomerView baseView) {
		this.baseView = baseView;
		
		model = new GroupEditCustomerPresentationModel(baseView.getModel().getBean(), baseView.getModel().getUnsaved(), baseView.getModel().getEntityManager());
        view = new GroupEditCustomerView(model);
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

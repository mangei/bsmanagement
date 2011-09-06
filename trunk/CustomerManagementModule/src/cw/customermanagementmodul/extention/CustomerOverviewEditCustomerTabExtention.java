package cw.customermanagementmodul.extention;

import java.util.List;

import javax.persistence.EntityManager;

import cw.boardingschoolmanagement.gui.CWErrorMessage;
import cw.boardingschoolmanagement.gui.component.CWPanel;
import cw.customermanagementmodul.extention.point.EditCustomerTabExtentionPoint;
import cw.customermanagementmodul.gui.CustomerOverviewEditCustomerPresentationModel;
import cw.customermanagementmodul.gui.CustomerOverviewEditCustomerView;
import cw.customermanagementmodul.gui.EditCustomerPresentationModel;

/**
 *
 * @author Manuel Geier
 */
public class CustomerOverviewEditCustomerTabExtention
        implements EditCustomerTabExtentionPoint {

    private CustomerOverviewEditCustomerPresentationModel model;
    private CustomerOverviewEditCustomerView view;
    private EditCustomerPresentationModel editCustomerModel;

    public void initPresentationModel(EditCustomerPresentationModel editCustomerModel, EntityManager entityManager) {
        this.editCustomerModel = editCustomerModel;
        model = new CustomerOverviewEditCustomerPresentationModel(editCustomerModel, entityManager);
    }
    
    public CWPanel getView() {
        if(view == null) {
            view = new CustomerOverviewEditCustomerView(model);
        }
        return view;
    }

    public Object getModel() {
        return model;
    }

    public void save() {
        // Not necessary for this Extention
    }

    public List<String> validate() {
        return null;
    }

    public void dispose() {
        view.dispose();
        
        // Kill references
        view = null;
        model = null;
        editCustomerModel = null;
    }

    public int priority() {
        return 100;
    }

	public boolean validate(List<CWErrorMessage> errorMessages) {
		return false;
	}

	public void cancel() {
	}

}

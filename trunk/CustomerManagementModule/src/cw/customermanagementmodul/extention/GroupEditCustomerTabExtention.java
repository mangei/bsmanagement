package cw.customermanagementmodul.extention;

import java.util.List;

import javax.persistence.EntityManager;

import cw.boardingschoolmanagement.gui.CWErrorMessage;
import cw.boardingschoolmanagement.gui.component.CWPanel;
import cw.customermanagementmodul.extention.point.EditCustomerTabExtentionPoint;
import cw.customermanagementmodul.gui.EditCustomerPresentationModel;
import cw.customermanagementmodul.gui.GroupEditCustomerPresentationModel;
import cw.customermanagementmodul.gui.GroupEditCustomerView;

/**
 *
 * @author Manuel Geier
 */
public class GroupEditCustomerTabExtention
        implements EditCustomerTabExtentionPoint {

    private GroupEditCustomerPresentationModel model;
    private GroupEditCustomerView view;
    private EditCustomerPresentationModel editCustomerModel;

    public void initPresentationModel(EditCustomerPresentationModel editCustomerModel, EntityManager entityManager) {
        this.editCustomerModel = editCustomerModel;
        model = new GroupEditCustomerPresentationModel(editCustomerModel.getBean(), editCustomerModel.getUnsaved(), entityManager);
        view = new GroupEditCustomerView(model);
    }
    
    public CWPanel getView() {
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
    }

    public int priority() {
        return 0;
    }

	public boolean validate(List<CWErrorMessage> errorMessages) {
		return false;
	}

	public void cancel() {
		
	}

}

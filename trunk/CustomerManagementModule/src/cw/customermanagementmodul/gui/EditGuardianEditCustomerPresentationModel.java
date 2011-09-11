package cw.customermanagementmodul.gui;

import java.awt.event.ActionEvent;
import java.util.List;

import javax.persistence.EntityManager;
import javax.swing.AbstractAction;
import javax.swing.Action;

import com.jgoodies.binding.value.ValueHolder;
import com.jgoodies.binding.value.ValueModel;

import cw.boardingschoolmanagement.gui.CWEditPresentationModel;
import cw.boardingschoolmanagement.gui.CWErrorMessage;
import cw.customermanagementmodul.logic.BoCustomer;
import cw.customermanagementmodul.logic.BoGuardian;
import cw.customermanagementmodul.persistence.Guardian;

/**
 *
 * @author CreativeWorkers.at
 */
public class EditGuardianEditCustomerPresentationModel
	extends CWEditPresentationModel<Guardian> {

    private EditCustomerPresentationModel editCustomerPresentationModel;
    private Action chooseGuardianAction;
    private ValueModel guardianLabelModel;
    
    public EditGuardianEditCustomerPresentationModel(EditCustomerPresentationModel editCustomerPresentationModel, EntityManager entityManager) {
        super(entityManager, EditGuardianEditCustomerView.class);
    	this.editCustomerPresentationModel = editCustomerPresentationModel;

    	BoCustomer boCustomer = editCustomerPresentationModel.getBean().getTypedAdapter(BoCustomer.class);
    	BoGuardian boGuardian = boCustomer.getTypedAdapter(BoGuardian.class);
    	
    	setBean(boGuardian.getPersistence());
    	
        initModels();
        initEventHandling();
    }

    public void initModels() {
    	
    	chooseGuardianAction = new ChooseGuardianAction("");
    	guardianLabelModel = new ValueHolder("");

    }

    public void initEventHandling() {
    }

    public void dispose() {
//        clearLocationDataAction = null;
//
//        titleList = null;
//        postOfficeNumberList = null;
//        cityList = null;
//        provinceList = null;
//        countryList = null;
//
//        editCustomerPresentationModel = null;
    }

    public Action getChooseGuardianAction() {
		return chooseGuardianAction;
	}
    
    public ValueModel getGuardianLabelModel() {
		return guardianLabelModel;
	}

    public EditCustomerPresentationModel getEditCustomerPresentationModel() {
        return editCustomerPresentationModel;
    }

    private class ChooseGuardianAction extends AbstractAction {

        public ChooseGuardianAction(String name) {
            super(name);
        }

        {
            putValue(Action.SHORT_DESCRIPTION, "Ortsdaten leeren");
        }

        public void actionPerformed(ActionEvent e) {
        	
        	
        	
        }

    }

    public void save() {
    	saveExtentions();
    }
    
	public boolean validate(List<CWErrorMessage> errorMessages) {
		validateExtentions(errorMessages);
		return !hasErrorMessages();
	}

	public void cancel() {
		cancelExtentions();
	}
}

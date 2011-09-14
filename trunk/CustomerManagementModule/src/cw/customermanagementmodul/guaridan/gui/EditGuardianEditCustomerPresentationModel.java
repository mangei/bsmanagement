package cw.customermanagementmodul.guaridan.gui;

import java.awt.event.ActionEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;

import javax.persistence.EntityManager;
import javax.swing.Action;

import com.jgoodies.binding.value.ValueHolder;
import com.jgoodies.binding.value.ValueModel;

import cw.boardingschoolmanagement.app.CWAction;
import cw.boardingschoolmanagement.gui.CWEditPresentationModel;
import cw.boardingschoolmanagement.gui.CWErrorMessage;
import cw.customermanagementmodul.customer.gui.EditCustomerPresentationModel;
import cw.customermanagementmodul.customer.logic.BoCustomer;
import cw.customermanagementmodul.customer.persistence.Customer;
import cw.customermanagementmodul.guardian.logic.BoGuardian;
import cw.customermanagementmodul.guardian.persistence.Guardian;

/**
 *
 * @author Manuel Geier
 */
public class EditGuardianEditCustomerPresentationModel
	extends CWEditPresentationModel<Guardian> {

    private EditCustomerPresentationModel editCustomerPresentationModel;
    private CWAction chooseGuardianAction;
    private ValueModel guardianLabelModel;
    private PropertyChangeListener guardianChangeListener;
    private PropertyChangeListener changeListener;
    
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
    	
    	chooseGuardianAction = new ChooseGuardianAction("Erziehungsberechtigten auswählen...");
    	guardianLabelModel = new ValueHolder("Keine gewählt");

    }

    public void initEventHandling() {
    	
    	// Change label if the customer changes
    	guardianChangeListener = new PropertyChangeListener() {
			@Override
			public void propertyChange(PropertyChangeEvent evt) {
				Customer customer = (Customer)evt.getNewValue();
				if(customer != null) {
					guardianLabelModel.setValue(customer.getForename() + " " + customer.getSurname());
				} else {
					guardianLabelModel.setValue("Keine gewählt");
				}
			}
		};
		getBufferedModel(Guardian.PROPERTYNAME_GUARDIAN).addValueChangeListener(guardianChangeListener);

    	changeListener = new PropertyChangeListener() {
			@Override
			public void propertyChange(PropertyChangeEvent evt) {
				editCustomerPresentationModel.getUnsaved().setValue(true);
			}
		};
        getBufferedModel(Guardian.PROPERTYNAME_LEGITIMATE).addValueChangeListener(changeListener);
    	getBufferedModel(Guardian.PROPERTYNAME_GUARDIAN).addPropertyChangeListener(changeListener);
    }

    public void dispose() {
    	getBufferedModel(Guardian.PROPERTYNAME_GUARDIAN).removePropertyChangeListener(guardianChangeListener);
    	
    	getBufferedModel(Guardian.PROPERTYNAME_LEGITIMATE).removeValueChangeListener(changeListener);
    	getBufferedModel(Guardian.PROPERTYNAME_GUARDIAN).removePropertyChangeListener(changeListener);
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

    private class ChooseGuardianAction extends CWAction {

        public ChooseGuardianAction(String name) {
            super(name);
        }

        {
            putValue(Action.SHORT_DESCRIPTION, "Erziehungsberechtigten auswählen");
        }

        public void action(ActionEvent e) {
        	Customer selectedCustomer = BoCustomer.selectCustomer(getEntityManager());
        	System.out.println(selectedCustomer);
        	getBufferedModel(Guardian.PROPERTYNAME_GUARDIAN).setValue(selectedCustomer);
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

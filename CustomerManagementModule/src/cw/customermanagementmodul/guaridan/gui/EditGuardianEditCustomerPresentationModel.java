package cw.customermanagementmodul.guaridan.gui;

import java.awt.event.ActionEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.persistence.EntityManager;
import javax.swing.Action;

import com.jgoodies.binding.value.ValueHolder;
import com.jgoodies.binding.value.ValueModel;
import com.jgoodies.validation.ValidationResult;

import cw.boardingschoolmanagement.app.CWAction;
import cw.boardingschoolmanagement.gui.CWEditPresentationModel;
import cw.boardingschoolmanagement.gui.component.CWView.CWHeaderInfo;
import cw.customermanagementmodul.customer.gui.EditCustomerPresentationModel;
import cw.customermanagementmodul.customer.logic.BoCustomer;
import cw.customermanagementmodul.customer.persistence.Customer;
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
    
    public EditGuardianEditCustomerPresentationModel(Guardian guardian, EditCustomerPresentationModel editCustomerPresentationModel, EntityManager entityManager) {
        super(guardian, entityManager, EditGuardianEditCustomerView.class);
    	this.editCustomerPresentationModel = editCustomerPresentationModel;
    	
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
				System.out.println("GSADFSADF");
				Customer customer = (Customer)evt.getNewValue();
				if(customer != null) {
					guardianLabelModel.setValue(customer.getForename() + " " + customer.getSurname());
				} else {
					guardianLabelModel.setValue("Keine gewähltwww");
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
    	getBufferedModel(Guardian.PROPERTYNAME_GUARDIAN).addValueChangeListener(changeListener);
    }

    public void dispose() {
    	getBufferedModel(Guardian.PROPERTYNAME_GUARDIAN).removeValueChangeListener(guardianChangeListener);
    	
    	getBufferedModel(Guardian.PROPERTYNAME_LEGITIMATE).removeValueChangeListener(changeListener);
    	getBufferedModel(Guardian.PROPERTYNAME_GUARDIAN).removeValueChangeListener(changeListener);
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
        	Customer selectedCustomer = BoCustomer.selectCustomer(getEntityManager(),
        			new CWHeaderInfo(
        					"Erziehungsberechtigen auswählen",
        					"Wählen Sie den Erziehungsberechtigten aus."));
        	getBufferedModel(Guardian.PROPERTYNAME_GUARDIAN).setValue(selectedCustomer);
        }

    }

    public void save() {
    	super.save();
    }
    
	public ValidationResult validate() {
		ValidationResult validationResult = super.validate();
		
		return validationResult;
	}

	public void cancel() {
		super.cancel();
	}
}

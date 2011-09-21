package cw.customermanagementmodul.customer.gui;

import javax.persistence.EntityManager;

import com.jgoodies.binding.value.ValueHolder;
import com.jgoodies.binding.value.ValueModel;

import cw.boardingschoolmanagement.gui.CWPresentationModel;
import cw.customermanagementmodul.customer.persistence.PMCustomer;

/**
 *
 * @author ManuelG
 */
public class CustomerHomeExtentionPresentationModel
	extends CWPresentationModel {

    private ValueModel sizeCustomersValueModel;

    public CustomerHomeExtentionPresentationModel(EntityManager entityManager) {
    	super(entityManager);
        initModels();
        initEventHandling();
    }

    private void initModels() {
        sizeCustomersValueModel = new ValueHolder("<html>Es befinden sich <b>" + PMCustomer.getInstance().countActive(getEntityManager()) + " Kunden</b> im System.</html>");
    }

    private void initEventHandling() {
        // Nothing to do
    }

    public void release() {
        // Nothing to do
    }

    public ValueModel getSizeCustomersValueModel() {
        return sizeCustomersValueModel;
    }

}

package cw.customermanagementmodul.gui;

import javax.persistence.EntityManager;

import com.jgoodies.binding.value.ValueHolder;
import com.jgoodies.binding.value.ValueModel;

import cw.boardingschoolmanagement.gui.CWPresentationModel;
import cw.customermanagementmodul.persistence.CustomerManager;

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
        sizeCustomersValueModel = new ValueHolder("Kunden: " + CustomerManager.getInstance().countActive(getEntityManager()));
    }

    private void initEventHandling() {
        // Nothing to do
    }

    public void dispose() {
        // Nothing to do
    }

    public ValueModel getSizeCustomersValueModel() {
        return sizeCustomersValueModel;
    }

}

package cw.customermanagementmodul.customer.gui;

import java.awt.event.ActionEvent;

import javax.persistence.EntityManager;
import javax.swing.AbstractAction;
import javax.swing.Action;

import com.jgoodies.binding.value.ValueHolder;
import com.jgoodies.binding.value.ValueModel;

import cw.boardingschoolmanagement.gui.CWPresentationModel;

/**
 * @author CreativeWorkers.at
 */
public class SearchCustomerSelectorFilterExtentionPresentationModel
	extends CWPresentationModel {

    private ValueModel searchModel;
    private Action clearAction;

    public SearchCustomerSelectorFilterExtentionPresentationModel(EntityManager entityManager) {
    	super(entityManager);
        initModels();
        initEventHandling();
    }

    private void initModels() {
        searchModel = new ValueHolder("");
        clearAction = new ClearAction("");
    }

    private void initEventHandling() {
        // Nothing do to
    }

    public void dispose() {
        // Nothing to do
    }


    ////////////////////////////////////////////////////////////////////////////
    // Action classes
    ////////////////////////////////////////////////////////////////////////////

    private class ClearAction extends AbstractAction {

        public ClearAction(String name) {
            super(name);
        }

        public void actionPerformed(ActionEvent e) {
            searchModel.setValue("");
        }
        
    }

    ////////////////////////////////////////////////////////////////////////////
    // Getter methods for the model
    ////////////////////////////////////////////////////////////////////////////

    public ValueModel getSearchModel() {
        return searchModel;
    }

    public Action getClearAction() {
        return clearAction;
    }

}

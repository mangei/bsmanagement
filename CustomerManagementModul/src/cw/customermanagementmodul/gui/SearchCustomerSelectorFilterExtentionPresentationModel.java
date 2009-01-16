package cw.customermanagementmodul.gui;

import com.jgoodies.binding.value.ValueHolder;
import com.jgoodies.binding.value.ValueModel;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.Action;

/**
 * @author CreativeWorkers.at
 */
public class SearchCustomerSelectorFilterExtentionPresentationModel {

    private ValueModel searchModel;
    private Action clearAction;

    public SearchCustomerSelectorFilterExtentionPresentationModel() {
        initModels();
        initEventHandling();
    }

    public void initModels() {
        searchModel = new ValueHolder("");
        clearAction = new ClearAction("");
    }

    private void initEventHandling() {
        // Nothing do to
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

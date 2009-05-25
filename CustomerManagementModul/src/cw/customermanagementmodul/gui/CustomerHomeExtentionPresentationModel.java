package cw.customermanagementmodul.gui;

import com.jgoodies.binding.value.ValueHolder;
import com.jgoodies.binding.value.ValueModel;
import cw.customermanagementmodul.pojo.manager.CustomerManager;

/**
 *
 * @author ManuelG
 */
public class CustomerHomeExtentionPresentationModel
{

    private ValueModel sizeCustomersValueModel;

    public CustomerHomeExtentionPresentationModel() {
        initModels();
        initEventHandling();
    }

    private void initModels() {
        sizeCustomersValueModel = new ValueHolder("Kunden: " + CustomerManager.getInstance().sizeActive());
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

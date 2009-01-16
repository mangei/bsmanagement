package cw.customermanagementmodul.gui;

import com.jgoodies.binding.value.ValueHolder;
import com.jgoodies.binding.value.ValueModel;
import cw.customermanagementmodul.pojo.manager.CustomerManager;

/**
 *
 * @author ManuelG
 */
public class CustomerHomeGUIExtentionPresentationModel {

    private ValueModel sizeCustomersValueModel;

    public CustomerHomeGUIExtentionPresentationModel() {
        initModels();
        initEventHandling();
    }

    private void initModels() {
        sizeCustomersValueModel = new ValueHolder("Kunden: " + CustomerManager.getSize());
    }

    private void initEventHandling() {

    }

    public ValueModel getSizeCustomersValueModel() {
        return sizeCustomersValueModel;
    }

}

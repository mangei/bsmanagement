package cw.customermanagementmodul.gui;

import com.jgoodies.binding.value.ValueHolder;
import com.jgoodies.binding.value.ValueModel;
import cw.boardingschoolmanagement.interfaces.Disposable;
import cw.customermanagementmodul.pojo.manager.CustomerManager;

/**
 *
 * @author ManuelG
 */
public class CustomerHomeExtentionPresentationModel
    implements Disposable
{

    private ValueModel sizeCustomersValueModel;

    public CustomerHomeExtentionPresentationModel() {
        initModels();
        initEventHandling();
    }

    private void initModels() {
        sizeCustomersValueModel = new ValueHolder("Kunden: " + CustomerManager.getInstance().size());
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

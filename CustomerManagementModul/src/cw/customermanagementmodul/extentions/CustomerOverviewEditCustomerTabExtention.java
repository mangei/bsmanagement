package cw.customermanagementmodul.extentions;

import cw.boardingschoolmanagement.gui.component.JViewPanel;
import cw.customermanagementmodul.extentions.interfaces.EditCustomerTabExtention;
import cw.customermanagementmodul.gui.CustomerOverviewEditCustomerPresentationModel;
import cw.customermanagementmodul.gui.CustomerOverviewEditCustomerView;
import cw.customermanagementmodul.gui.EditCustomerPresentationModel;
import java.util.List;
import javax.swing.JComponent;

/**
 *
 * @author Manuel Geier
 */
public class CustomerOverviewEditCustomerTabExtention
        implements EditCustomerTabExtention {

    private CustomerOverviewEditCustomerPresentationModel model;
    private CustomerOverviewEditCustomerView view;
    private EditCustomerPresentationModel editCustomerModel;
    private JViewPanel panel;

    public void initPresentationModel(EditCustomerPresentationModel editCustomerModel) {
        this.editCustomerModel = editCustomerModel;
        model = new CustomerOverviewEditCustomerPresentationModel(editCustomerModel);
    }
    
    public JComponent getView() {
         view = new CustomerOverviewEditCustomerView(model);
         panel = view.buildPanel();
         return panel;
    }

    public Object getModel() {
        return model;
    }

    public void save() {
        // Not necessary for this Extention
    }

    public List<String> validate() {
        return null;
    }

    public void dispose() {
        panel.dispose();
        
        // Kill references
        view = null;
        model = null;
        editCustomerModel = null;
        panel = null;
    }

    public int priority() {
        return 100;
    }

}

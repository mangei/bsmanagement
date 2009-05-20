package cw.customermanagementmodul.extentions;

import cw.boardingschoolmanagement.gui.component.JViewPanel;
import cw.customermanagementmodul.extentions.interfaces.EditCustomerTabExtention;
import cw.customermanagementmodul.gui.EditCustomerEditCustomerPresentationModel;
import cw.customermanagementmodul.gui.EditCustomerEditCustomerView;
import cw.customermanagementmodul.gui.EditCustomerPresentationModel;
import java.util.List;
import javax.swing.JComponent;

/**
 *
 * @author Manuel Geier
 */
public class EditCustomerEditCustomerTabExtention
        implements EditCustomerTabExtention {

    private EditCustomerEditCustomerPresentationModel model;
    private EditCustomerEditCustomerView view;
    private EditCustomerPresentationModel editCustomerModel;
    private JViewPanel panel;

    public void initPresentationModel(EditCustomerPresentationModel editCustomerModel) {
        this.editCustomerModel = editCustomerModel;
        model = new EditCustomerEditCustomerPresentationModel(editCustomerModel);
    }
    
    public JComponent getView() {
         view = new EditCustomerEditCustomerView(model);
         panel = view.buildPanel();
         return panel;
    }

    public Object getModel() {
        return model;
    }

    public void save() {
        model.save();
    }

    public List<String> validate() {
        return null;
    }

    public void dispose() {
        panel.dispose();
    }

    public int priority() {
        return 90;
    }

}

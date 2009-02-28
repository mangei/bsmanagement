package cw.customermanagementmodul.gui;

import cw.boardingschoolmanagement.app.CWUtils;
import com.jgoodies.binding.value.ValueHolder;
import com.jgoodies.binding.value.ValueModel;
import cw.boardingschoolmanagement.gui.component.JViewPanel.HeaderInfo;
import cw.boardingschoolmanagement.interfaces.Disposable;
import cw.boardingschoolmanagement.manager.ModulManager;
import cw.customermanagementmodul.extentions.interfaces.CustomerOverviewEditCustomerExtention;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JComponent;

/**
 *
 * @author CreativeWorkers.at
 */
public class CustomerOverviewEditCustomerPresentationModel
        implements Disposable {

    private EditCustomerPresentationModel editCustomerPresentationModel;
    private ValueModel unsaved;
    private HeaderInfo headerInfo;

    private List<CustomerOverviewEditCustomerExtention> customerOverviewEditCustomerExtentions;

    public CustomerOverviewEditCustomerPresentationModel(EditCustomerPresentationModel editCustomerPresentationModel) {
        this.editCustomerPresentationModel = editCustomerPresentationModel;

        initModels();
        initEventHandling();
    }

    public void initModels() {
        unsaved = new ValueHolder();

        // Addons initialisieren
        customerOverviewEditCustomerExtentions = getExtentions();
        for (CustomerOverviewEditCustomerExtention extention : customerOverviewEditCustomerExtentions) {
            extention.initPresentationModel(this);
        }

        headerInfo = new HeaderInfo(
                "Kundenübersicht",
                "Hier haben Sie alle Informationen für den Kunden im Überblick.",
                CWUtils.loadIcon("cw/customermanagementmodul/images/user.png"),
                CWUtils.loadIcon("cw/customermanagementmodul/images/user.png")
        );

    }

    public void initEventHandling() {
    }

    public void dispose() {
        customerOverviewEditCustomerExtentions = null;
        unsaved = null;
    }

    /**
     * Wenn sich ein Document ändert, wird saved auf false gesetzt
     */
    public class SaveListener implements PropertyChangeListener {

        public void propertyChange(PropertyChangeEvent evt) {
            updateState();
        }

        public void updateState() {
            unsaved.setValue(true);
        }
    }

    public List<JComponent> getExtentionComponents() {
        List<JComponent> comps = new ArrayList<JComponent>();
        for (CustomerOverviewEditCustomerExtention ex : customerOverviewEditCustomerExtentions) {
            comps.add((ex).getView());
        }
        return comps;
    }

    public List<CustomerOverviewEditCustomerExtention> getExtentions() {
        if(customerOverviewEditCustomerExtentions == null) {
            customerOverviewEditCustomerExtentions = (List<CustomerOverviewEditCustomerExtention>) ModulManager.getExtentions(CustomerOverviewEditCustomerExtention.class);
        }
        return customerOverviewEditCustomerExtentions;
    }

    public EditCustomerPresentationModel getEditCustomerPresentationModel() {
        return editCustomerPresentationModel;
    }

    public HeaderInfo getHeaderInfo() {
        return headerInfo;
    }

}

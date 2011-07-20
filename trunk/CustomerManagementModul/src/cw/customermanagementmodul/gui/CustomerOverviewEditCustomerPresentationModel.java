package cw.customermanagementmodul.gui;

import cw.boardingschoolmanagement.app.CWUtils;
import com.jgoodies.binding.value.ValueHolder;
import com.jgoodies.binding.value.ValueModel;
import cw.boardingschoolmanagement.gui.component.CWView.CWHeaderInfo;
import cw.boardingschoolmanagement.manager.ModulManager;
import cw.customermanagementmodul.extention.point.CustomerOverviewEditCustomerExtentionPoint;
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
{

    private EditCustomerPresentationModel editCustomerPresentationModel;
    private ValueModel unsaved;
    private CWHeaderInfo headerInfo;

    private List<CustomerOverviewEditCustomerExtentionPoint> customerOverviewEditCustomerExtentions;

    public CustomerOverviewEditCustomerPresentationModel(EditCustomerPresentationModel editCustomerPresentationModel) {
        this.editCustomerPresentationModel = editCustomerPresentationModel;

        initModels();
        initEventHandling();
    }

    public void initModels() {
        unsaved = new ValueHolder();

        // Addons initialisieren
        customerOverviewEditCustomerExtentions = getExtentions();
        for (CustomerOverviewEditCustomerExtentionPoint extention : customerOverviewEditCustomerExtentions) {
            extention.initPresentationModel(this);
        }

        headerInfo = new CWHeaderInfo(
                "Übersicht",
                "Hier haben Sie alle Informationen für den Kunden im Überblick.",
                CWUtils.loadIcon("cw/customermanagementmodul/images/user.png"),
                CWUtils.loadIcon("cw/customermanagementmodul/images/user.png")
        );

    }

    public void initEventHandling() {
    }

    public void dispose() {
        customerOverviewEditCustomerExtentions = getExtentions();
        for (CustomerOverviewEditCustomerExtentionPoint extention : customerOverviewEditCustomerExtentions) {
            extention.dispose();
        }
        customerOverviewEditCustomerExtentions.clear();

        // Kill references
        editCustomerPresentationModel = null;
        unsaved = null;
        headerInfo = null;
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
        for (CustomerOverviewEditCustomerExtentionPoint ex : customerOverviewEditCustomerExtentions) {
            comps.add((ex).getView());
        }
        return comps;
    }

    public List<CustomerOverviewEditCustomerExtentionPoint> getExtentions() {
        if(customerOverviewEditCustomerExtentions == null) {
            customerOverviewEditCustomerExtentions = (List<CustomerOverviewEditCustomerExtentionPoint>) ModulManager.getExtentions(CustomerOverviewEditCustomerExtentionPoint.class);
        }
        return customerOverviewEditCustomerExtentions;
    }

    public EditCustomerPresentationModel getEditCustomerPresentationModel() {
        return editCustomerPresentationModel;
    }

    public CWHeaderInfo getHeaderInfo() {
        return headerInfo;
    }

}

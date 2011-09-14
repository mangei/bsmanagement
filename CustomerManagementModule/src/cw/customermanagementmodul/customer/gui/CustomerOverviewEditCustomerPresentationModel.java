package cw.customermanagementmodul.customer.gui;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.swing.JComponent;

import com.jgoodies.binding.value.ValueHolder;
import com.jgoodies.binding.value.ValueModel;

import cw.boardingschoolmanagement.app.CWUtils;
import cw.boardingschoolmanagement.gui.CWPresentationModel;
import cw.boardingschoolmanagement.gui.component.CWView.CWHeaderInfo;
import cw.boardingschoolmanagement.manager.ModuleManager;
import cw.customermanagementmodul.customer.extention.point.CustomerOverviewEditCustomerExtentionPoint;

/**
 *
 * @author CreativeWorkers.at
 */
public class CustomerOverviewEditCustomerPresentationModel
	extends CWPresentationModel {

    private EditCustomerPresentationModel editCustomerPresentationModel;
    private ValueModel unsaved;
    private CWHeaderInfo headerInfo;

    private List<CustomerOverviewEditCustomerExtentionPoint> customerOverviewEditCustomerExtentions;

    public CustomerOverviewEditCustomerPresentationModel(EditCustomerPresentationModel editCustomerPresentationModel, EntityManager entityManager) {
        super(entityManager);
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
                "Kundenuebersicht",
                "Hier haben Sie alle Informationen fuer den Kunden im Ueberblick.",
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
     * Wenn sich ein Document aendert, wird saved auf false gesetzt
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
            customerOverviewEditCustomerExtentions = (List<CustomerOverviewEditCustomerExtentionPoint>) ModuleManager.getExtentions(CustomerOverviewEditCustomerExtentionPoint.class);
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

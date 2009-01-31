package cw.roommanagementmodul.gui;

import com.jgoodies.binding.value.ValueModel;
import cw.customermanagementmodul.extentions.interfaces.EditCustomerGUITabExtention;
import cw.customermanagementmodul.pojo.Customer;
import javax.swing.JComponent;
import cw.roommanagementmodul.pojo.manager.BewohnerManager;
import cw.roommanagementmodul.pojo.Bewohner;
import cw.roommanagementmodul.pojo.Zimmer;
import cw.roommanagementmodul.pojo.manager.BewohnerHistoryManager;

/**
 *
 * @author Dominik
 */
public class BewohnerCostumerGUIExtention implements EditCustomerGUITabExtention {

    private static BewohnerManager bewohnerManager;
    private static BewohnerHistoryManager historyManager;
    private static BewohnerGUIExtentionPresentationModel model;
    private static Bewohner b = null;
    private static Customer c;
    private static Zimmer tempZimmer;

    public void initPresentationModel(Customer customer, ValueModel unsaved) {
        bewohnerManager = BewohnerManager.getInstance();
        historyManager = BewohnerHistoryManager.getInstance();
        c = customer;
        b = bewohnerManager.getBewohner(customer);
        tempZimmer = b.getZimmer();

        model = new BewohnerGUIExtentionPresentationModel(bewohnerManager, b, unsaved);
    }

    public JComponent getView() {
        return new BewohnerGUIExtentionView(model).buildPanel();
    }

    public void save() {
        model.triggerCommit();


        b.setCustomer(c);
        b.setActive(true);
        bewohnerManager.save(b);

        if (tempZimmer == null) {
            historyManager.saveBewohnerHistory(b);
        } else {
            if (!tempZimmer.equals(b.getZimmer())) {
                historyManager.saveBewohnerHistory(b);
            }
        }
    }

    public void reset() {
        model.triggerFlush();
    }
}

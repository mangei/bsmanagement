package cw.roommanagementmodul.gui;

import com.jgoodies.binding.value.ValueModel;
import cw.customermanagementmodul.extentions.interfaces.EditCustomerGUITabExtention;
import cw.customermanagementmodul.pojo.Customer;
import java.util.List;
import javax.swing.JComponent;
import cw.roommanagementmodul.pojo.manager.BewohnerManager;
import cw.roommanagementmodul.pojo.Bewohner;
import cw.roommanagementmodul.pojo.Zimmer;
import cw.roommanagementmodul.pojo.manager.BewohnerHistoryManager;
import java.awt.event.ItemEvent;
import java.util.ArrayList;

/**
 *
 * @author Dominik
 */
public class BewohnerCostumerGUIExtention implements EditCustomerGUITabExtention {

    private static BewohnerManager bewohnerManager;
    private static BewohnerHistoryManager historyManager;
    private static BewohnerGUIExtentionPresentationModel model;
    private static Bewohner b;
    private static Customer c;
    private static Zimmer tempZimmer;

    public void initPresentationModel(Customer customer, ValueModel unsaved) {
        bewohnerManager = BewohnerManager.getInstance();
        historyManager = BewohnerHistoryManager.getInstance();
        c = customer;
        b = bewohnerManager.getBewohner(customer);
        if (b == null) {
            b = new Bewohner();
        } else {
            tempZimmer = b.getZimmer();
        }


        model = new BewohnerGUIExtentionPresentationModel(bewohnerManager, b, unsaved);
    }

    public JComponent getView() {
        return new BewohnerGUIExtentionView(model).buildPanel();
    }

    public void save() {
        model.triggerCommit();

        if (model.getCheckBoxState() == ItemEvent.SELECTED) {

            b.setCustomer(c);
            b.setActive(true);
            bewohnerManager.save(b);

//        if (tempZimmer == null) {
//            historyManager.saveBewohnerHistory(b);
//        } else {
//            if (!tempZimmer.equals(b.getZimmer())) {
//                historyManager.saveBewohnerHistory(b);
//            }
//        }

        }
        if (model.getCheckBoxState() == ItemEvent.DESELECTED) {
            b.setCustomer(c);
            b.setBis(null);
            b.setVon(null);
            b.setZimmer(null);
            b.setActive(false);
            bewohnerManager.save(b);
        }

    }

    public void reset() {
        model.triggerFlush();
    }

    public boolean validate() {
        model.triggerCommit();
        if (model.getCheckBoxState() == ItemEvent.SELECTED && (b.getZimmer() == null || b.getVon() == null)) {
            return false;
        }
        else{
            return true;
        }
    }

    public List<String> getErrorMessages() {
        ArrayList<String> l= new ArrayList<String>();
         if (!validate()) {
             l.add("In der Registerkarte Zimmer müssen die Attribute Einzugsdatum und Zimmer gesetzt sein!");
             return l;
        }
        return l;
    }
}

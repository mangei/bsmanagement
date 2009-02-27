package cw.customermanagementmodul.gui;

import cw.boardingschoolmanagement.app.CWUtils;
import com.jgoodies.binding.value.ValueHolder;
import com.jgoodies.binding.value.ValueModel;
import cw.boardingschoolmanagement.gui.component.JViewPanel.HeaderInfo;
import cw.boardingschoolmanagement.interfaces.Disposable;
import cw.boardingschoolmanagement.manager.ModulManager;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JComponent;
import cw.customermanagementmodul.extentions.interfaces.EditCustomerTabExtention;

/**
 *
 * @author CreativeWorkers.at
 */
public class CustomerOverviewEditCustomerTabExtentionPresentationModel
        implements Disposable {

    private EditCustomerPresentationModel editCustomerPresentationModel;
    private ValueModel unsaved;
    private HeaderInfo headerInfo;

    private List<EditCustomerTabExtention> customerOverviewEditCustomerGUITabExtentions;

    public CustomerOverviewEditCustomerTabExtentionPresentationModel(EditCustomerPresentationModel editCustomerPresentationModel) {
        this.editCustomerPresentationModel = editCustomerPresentationModel;

        initModels();
        initEventHandling();
    }

    public void initModels() {
        unsaved = new ValueHolder();

        // Addons initialisieren
        customerOverviewEditCustomerGUITabExtentions = getExtentions();
//        for (EditCustomerTabExtention extention : editCustomerGUITabExtentions) {
//            System.out.println("Extention: " + extention.toString());
//            extention.initPresentationModel(this);
//        }

        headerInfo = new HeaderInfo(
                "Allgemeine Kundeninformationen",
                "Hier können sie allgemeine Kundeninformationen eingeben.",
                CWUtils.loadIcon("cw/customermanagementmodul/images/user.png"),
                CWUtils.loadIcon("cw/customermanagementmodul/images/user.png")
        );

    }

    public void initEventHandling() {
    }

    public void dispose() {
        customerOverviewEditCustomerGUITabExtentions = null;
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
        for (EditCustomerTabExtention ex : customerOverviewEditCustomerGUITabExtentions) {
            comps.add((ex).getView());
        }
        return comps;
    }

    public List<EditCustomerTabExtention> getExtentions() {
        if(customerOverviewEditCustomerGUITabExtentions == null) {
            customerOverviewEditCustomerGUITabExtentions = (List<EditCustomerTabExtention>) ModulManager.getExtentions(EditCustomerTabExtention.class);
        }
        return customerOverviewEditCustomerGUITabExtentions;
    }

    public EditCustomerTabExtention getExtention(EditCustomerTabExtention extention) {

        for(EditCustomerTabExtention ex : customerOverviewEditCustomerGUITabExtentions) {
            if(extention.getClass().isInstance(ex)) {
                return ex;
            }
        }

        return null;
    }


    public EditCustomerPresentationModel getEditCustomerPresentationModel() {
        return editCustomerPresentationModel;
    }

    public HeaderInfo getHeaderInfo() {
        return headerInfo;
    }

}

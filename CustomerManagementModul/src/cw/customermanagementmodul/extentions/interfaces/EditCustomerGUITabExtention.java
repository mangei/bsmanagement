package cw.customermanagementmodul.extentions.interfaces;

import com.jgoodies.binding.value.ValueModel;
import cw.boardingschoolmanagement.extentions.interfaces.GUIExtention;
import javax.swing.JComponent;
import cw.customermanagementmodul.pojo.Customer;

/**
 *
 * @author Manuel Geier
 */
public interface EditCustomerGUITabExtention extends GUIExtention{

    /**
     * To initialize the PresentationModel
     * @param costumer The shown customer
     * @param unsaved If you want to know if the costumer and the other extentions are unsaved.<br>
     *                If you want to dis- or enable the top buttons, set this parameter
     */
    public void initPresentationModel(Customer costumer, ValueModel unsaved);

    /**
     * The component you want to add in an new tab. <br>
     * Tip: Set the name of the component, to set the name of the tab
     * @return JComponent
     */
    public JComponent getView();
    
    /**
     * If the user presses the save button
     */
    public void save();
    
    /**
     * If the user presses the reset button
     */
    public void reset();
}

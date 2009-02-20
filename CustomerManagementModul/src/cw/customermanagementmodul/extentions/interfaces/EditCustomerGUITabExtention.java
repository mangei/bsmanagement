package cw.customermanagementmodul.extentions.interfaces;

import cw.boardingschoolmanagement.extentions.interfaces.GUIExtention;
import cw.customermanagementmodul.gui.EditCustomerPresentationModel;
import javax.swing.JComponent;
import cw.customermanagementmodul.pojo.Customer;
import java.util.List;

/**
 *
 * @author Manuel Geier
 */
public interface EditCustomerGUITabExtention extends GUIExtention{

    /**
     * To initialize the PresentationModel
     * @param costumer The shown customer
     * @param editCustomerModel represents the main model with the extentions in it
     */
    public void initPresentationModel(Customer costumer, EditCustomerPresentationModel editCustomerModel);

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

    /**
     * checks before the save-method if the content the user entered is validate
     * @return validate
     */
    public boolean validate();

    /**
     * Returns the error messages, if the content is not validate
     * @return error messages
     */
    public List<String> getErrorMessages();
}

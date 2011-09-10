package cw.customermanagementmodul.extention.point;

import java.util.List;

import javax.persistence.EntityManager;

import cw.boardingschoolmanagement.extention.CWIExtention;
import cw.boardingschoolmanagement.gui.CWErrorMessage;
import cw.boardingschoolmanagement.gui.component.CWPanel;
import cw.boardingschoolmanagement.interfaces.Priority;
import cw.customermanagementmodul.gui.EditCustomerPresentationModel;

/**
 *
 * @author Manuel Geier
 */
public interface EditCustomerTabExtentionPoint
        extends CWIExtention, Priority{

    /**
     * To initialize the PresentationModel
     * @param editCustomerModel represents the main model with the extentions in it
     */
    public void initPresentationModel(EditCustomerPresentationModel editCustomerModel, EntityManager entityManager);

    /**
     * The panel you want to add in an new tab. <br>
     * Tip: Set the name of the component, to set the name of the tab
     * @return CWPanel
     */
    public CWPanel getView();

    public Object getModel();
    
    /**
     * If the user presses the save button
     */
    public void save();

    public boolean validate(List<CWErrorMessage> errorMessages);

    public void cancel();

    /**
     * checks before the save-method if the content the user entered is validate
     * if there are any errors, return a list of Strings with it otherwise return
     * null or an empty list.
     * @return validate
     */
    public List<String> validate();

    public void dispose();

    public int priority();
}

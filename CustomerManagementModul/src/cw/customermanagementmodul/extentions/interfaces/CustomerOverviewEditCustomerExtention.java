package cw.customermanagementmodul.extentions.interfaces;

import cw.boardingschoolmanagement.extentions.interfaces.Extention;
import cw.boardingschoolmanagement.interfaces.Priority;
import cw.customermanagementmodul.gui.CustomerOverviewEditCustomerPresentationModel;
import javax.swing.JComponent;

/**
 *
 * @author Manuel Geier
 */
public interface CustomerOverviewEditCustomerExtention
        extends Extention, Priority{

    public void initPresentationModel(CustomerOverviewEditCustomerPresentationModel customerOverviewEditCustomerPresentationModel);

    public JComponent getView();
    
    public void dispose();

    public int priority();
}

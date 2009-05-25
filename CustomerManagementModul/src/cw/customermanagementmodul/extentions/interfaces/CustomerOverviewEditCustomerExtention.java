package cw.customermanagementmodul.extentions.interfaces;

import cw.boardingschoolmanagement.extentions.interfaces.Extention;
import cw.boardingschoolmanagement.gui.component.CWPanel;
import cw.boardingschoolmanagement.interfaces.Priority;
import cw.customermanagementmodul.gui.CustomerOverviewEditCustomerPresentationModel;

/**
 *
 * @author Manuel Geier
 */
public interface CustomerOverviewEditCustomerExtention
        extends Extention, Priority{

    public void initPresentationModel(CustomerOverviewEditCustomerPresentationModel customerOverviewEditCustomerPresentationModel);

    public CWPanel getView();
    
    public void dispose();

    public int priority();
}

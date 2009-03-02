package cw.customermanagementmodul.extentions;

import cw.customermanagementmodul.gui.CustomerHomeExtentionPresentationModel;
import cw.customermanagementmodul.gui.CustomerHomeExtentionView;
import cw.boardingschoolmanagement.extentions.interfaces.HomeExtention;
import javax.swing.JPanel;

/**
 *
 * @author ManuelG
 */
public class CustomerHomeExtention implements HomeExtention {

    public JPanel getPanel() {
        return new CustomerHomeExtentionView(new CustomerHomeExtentionPresentationModel()).buildPanel();
    }

    public void dispose() {
        
    }

}

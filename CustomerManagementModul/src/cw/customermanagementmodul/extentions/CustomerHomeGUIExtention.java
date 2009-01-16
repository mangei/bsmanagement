package cw.customermanagementmodul.extentions;

import cw.customermanagementmodul.gui.CustomerHomeGUIExtentionPresentationModel;
import cw.customermanagementmodul.gui.CustomerHomeGUIExtentionView;
import cw.boardingschoolmanagement.extentions.interfaces.HomeGUIExtention;
import javax.swing.JPanel;

/**
 *
 * @author ManuelG
 */
public class CustomerHomeGUIExtention implements HomeGUIExtention {

    public JPanel getPanel() {
        return new CustomerHomeGUIExtentionView(new CustomerHomeGUIExtentionPresentationModel()).buildPanel();
    }

}

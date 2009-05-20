package cw.customermanagementmodul.extentions;

import cw.boardingschoolmanagement.gui.HomePresentationModel;
import cw.customermanagementmodul.gui.CustomerHomeExtentionPresentationModel;
import cw.customermanagementmodul.gui.CustomerHomeExtentionView;
import cw.boardingschoolmanagement.extentions.interfaces.HomeExtention;
import cw.boardingschoolmanagement.gui.component.JViewPanel;
import javax.swing.JPanel;

/**
 *
 * @author ManuelG
 */
public class CustomerHomeExtention implements HomeExtention {

    private CustomerHomeExtentionPresentationModel model;
    private CustomerHomeExtentionView view;
    private JViewPanel panel;

    public void initPresentationModel(HomePresentationModel homePresentationModel) {
        model = new CustomerHomeExtentionPresentationModel();
        view = new CustomerHomeExtentionView(model);
    }

    public JPanel getPanel() {
        panel = view.buildPanel();
        return panel;
    }

    public void dispose() {
        panel.dispose();
    }

    public Object getModel() {
        return model;
    }

}

package cw.customermanagementmodul.extentions;

import cw.boardingschoolmanagement.gui.HomePresentationModel;
import cw.customermanagementmodul.gui.CustomerHomeExtentionPresentationModel;
import cw.customermanagementmodul.gui.CustomerHomeExtentionView;
import cw.boardingschoolmanagement.extentions.interfaces.HomeExtention;
import javax.swing.JPanel;

/**
 *
 * @author ManuelG
 */
public class CustomerHomeExtention implements HomeExtention {

    private CustomerHomeExtentionPresentationModel model;
    private CustomerHomeExtentionView view;

    public void initPresentationModel(HomePresentationModel homePresentationModel) {
        model = new CustomerHomeExtentionPresentationModel();
        view = new CustomerHomeExtentionView(model);
    }

    public JPanel getPanel() {
        return view.buildPanel();
    }

    public void dispose() {
        view.dispose();
    }

    public Object getModel() {
        return model;
    }

}

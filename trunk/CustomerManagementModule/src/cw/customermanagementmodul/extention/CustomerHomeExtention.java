package cw.customermanagementmodul.extention;

import javax.persistence.EntityManager;

import cw.boardingschoolmanagement.extention.point.HomeExtentionPoint;
import cw.boardingschoolmanagement.gui.HomePresentationModel;
import cw.boardingschoolmanagement.gui.component.CWPanel;
import cw.customermanagementmodul.gui.CustomerHomeExtentionPresentationModel;
import cw.customermanagementmodul.gui.CustomerHomeExtentionView;

/**
 *
 * @author ManuelG
 */
public class CustomerHomeExtention implements HomeExtentionPoint {

    private CustomerHomeExtentionPresentationModel model;
    private CustomerHomeExtentionView view;

    public void initPresentationModel(HomePresentationModel homePresentationModel, EntityManager entityManager) {
        model = new CustomerHomeExtentionPresentationModel(entityManager);
        view = new CustomerHomeExtentionView(model);
    }

    public CWPanel getView() {
        return view;
    }

    public void dispose() {
        view.dispose();
    }

    public Object getModel() {
        return model;
    }

}

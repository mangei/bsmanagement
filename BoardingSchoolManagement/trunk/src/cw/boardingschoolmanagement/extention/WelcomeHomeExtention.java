package cw.boardingschoolmanagement.extention;

import cw.boardingschoolmanagement.gui.HomePresentationModel;
import cw.boardingschoolmanagement.gui.WelcomeHomeExtentionPresentationModel;
import cw.boardingschoolmanagement.gui.WelcomeHomeExtentionView;
import cw.boardingschoolmanagement.extention.point.HomeExtentionPoint;
import cw.boardingschoolmanagement.gui.component.CWComponentFactory;
import cw.boardingschoolmanagement.gui.component.CWPanel;

/**
 *
 * @author ManuelG
 */
public class WelcomeHomeExtention
    implements HomeExtentionPoint {

    private WelcomeHomeExtentionPresentationModel model;
    private WelcomeHomeExtentionView view;

    public void initPresentationModel(HomePresentationModel homePresentationModel) {
        model = new WelcomeHomeExtentionPresentationModel();
        view = new WelcomeHomeExtentionView(model);
    }

    public void dispose() {
        view.dispose();
    }

    public Object getModel() {
        return model;
    }

    public CWPanel getView() {
        return view;
    }

}

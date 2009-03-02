package cw.boardingschoolmanagement.extentions;

import cw.boardingschoolmanagement.gui.HomePresentationModel;
import cw.boardingschoolmanagement.gui.WelcomeHomeGUIExtentionPresentationModel;
import cw.boardingschoolmanagement.gui.WelcomeHomeGUIExtentionView;
import cw.boardingschoolmanagement.extentions.interfaces.HomeExtention;
import javax.swing.JPanel;

/**
 *
 * @author ManuelG
 */
public class WelcomeHomeGUIExtention implements HomeExtention {

    private WelcomeHomeGUIExtentionPresentationModel model;
    private WelcomeHomeGUIExtentionView view;

     public void initPresentationModel(HomePresentationModel homePresentationModel) {
        model = new WelcomeHomeGUIExtentionPresentationModel();
        view = new WelcomeHomeGUIExtentionView(model);
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

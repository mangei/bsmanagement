package cw.boardingschoolmanagement.extentions;

import cw.boardingschoolmanagement.gui.WelcomeHomeGUIExtentionPresentationModel;
import cw.boardingschoolmanagement.gui.WelcomeHomeGUIExtentionView;
import cw.boardingschoolmanagement.extentions.interfaces.HomeExtention;
import javax.swing.JPanel;

/**
 *
 * @author ManuelG
 */
public class WelcomeHomeGUIExtention implements HomeExtention {

    private WelcomeHomeGUIExtentionView view;

    public JPanel getPanel() {
        view = new WelcomeHomeGUIExtentionView(new WelcomeHomeGUIExtentionPresentationModel());
        return view.buildPanel();
    }

    public void dispose() {
        view.dispose();
    }

}

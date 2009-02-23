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

    public JPanel getPanel() {
        return new WelcomeHomeGUIExtentionView(new WelcomeHomeGUIExtentionPresentationModel()).buildPanel();
    }

}

package cw.boardingschoolmanagement.gui;

import cw.boardingschoolmanagement.extentions.interfaces.HomeGUIExtention;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JPanel;
import cw.boardingschoolmanagement.manager.ModulManager;

/**
 *
 * @author Manuel Geier (CreativeWorkers)
 */
public class HomePresentationModel {

    public HomePresentationModel() {
        initModels();
        initEventHandling();
    }

    public void initModels() {
    }

    public void initEventHandling() {
    }

    public List<JPanel> getExtentionPanels() {
        List<JPanel> panels = new ArrayList<JPanel>();

        List<HomeGUIExtention> aList = (List<HomeGUIExtention>) ModulManager.getExtentions(HomeGUIExtention.class);
        for (HomeGUIExtention ex : aList) {
            panels.add(ex.getPanel());
        }

        return panels;
    }
}

package cw.boardingschoolmanagement.gui;

import cw.boardingschoolmanagement.extentions.interfaces.HomeExtention;
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

        List<HomeExtention> aList = (List<HomeExtention>) ModulManager.getExtentions(HomeExtention.class);
        for (HomeExtention ex : aList) {
            panels.add(ex.getPanel());
        }

        return panels;
    }
}

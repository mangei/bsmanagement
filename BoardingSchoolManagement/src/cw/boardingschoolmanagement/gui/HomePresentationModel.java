package cw.boardingschoolmanagement.gui;

import cw.boardingschoolmanagement.extention.point.HomeExtention;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JPanel;
import cw.boardingschoolmanagement.manager.ModulManager;

/**
 *
 * @author Manuel Geier (CreativeWorkers)
 */
public class HomePresentationModel
{

    private List<HomeExtention> homeExtentions;

    public HomePresentationModel() {
        initModels();
        initEventHandling();
    }

    public void initModels() {
        List<HomeExtention> aList = getExtentions();
        for (HomeExtention ex : aList) {
            ex.initPresentationModel(this);
        }
    }

    public void initEventHandling() {
    }

    public void dispose() {
        List<HomeExtention> aList = getExtentions();
        for (HomeExtention ex : aList) {
            ex.dispose();
        }
    }

    public List<HomeExtention> getExtentions() {
        if(homeExtentions == null) {
            homeExtentions = (List<HomeExtention>) ModulManager.getExtentions(HomeExtention.class);
        }
        return homeExtentions;
    }

    public List<JPanel> getExtentionPanels() {
        List<JPanel> panels = new ArrayList<JPanel>();

        List<HomeExtention> aList = getExtentions();
        for (HomeExtention ex : aList) {
            panels.add(ex.getView());
        }

        return panels;
    }
}

package cw.boardingschoolmanagement.gui;

import cw.boardingschoolmanagement.extention.point.HomeExtentionPoint;
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

    private List<HomeExtentionPoint> homeExtentions;

    public HomePresentationModel() {
        initModels();
        initEventHandling();
    }

    public void initModels() {
        List<HomeExtentionPoint> aList = getExtentions();
        for (HomeExtentionPoint ex : aList) {
            ex.initPresentationModel(this);
        }
    }

    public void initEventHandling() {
    }

    public void dispose() {
        List<HomeExtentionPoint> aList = getExtentions();
        for (HomeExtentionPoint ex : aList) {
            ex.dispose();
        }
    }

    public List<HomeExtentionPoint> getExtentions() {
        if(homeExtentions == null) {
            homeExtentions = (List<HomeExtentionPoint>) ModulManager.getExtentions(HomeExtentionPoint.class);
        }
        return homeExtentions;
    }

    public List<JPanel> getExtentionPanels() {
        List<JPanel> panels = new ArrayList<JPanel>();

        List<HomeExtentionPoint> aList = getExtentions();
        for (HomeExtentionPoint ex : aList) {
            panels.add(ex.getView());
        }

        return panels;
    }
}

package cw.boardingschoolmanagement.gui;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

import cw.boardingschoolmanagement.app.CWEntityManager;
import cw.boardingschoolmanagement.extention.point.HomeExtentionPoint;
import cw.boardingschoolmanagement.manager.ModulManager;

/**
 *
 * @author Manuel Geier (CreativeWorkers)
 */
public class HomePresentationModel
	extends CWPresentationModel {

    private List<HomeExtentionPoint> homeExtentions;

    public HomePresentationModel() {
    	super(CWEntityManager.createEntityManager());
        initModels();
        initEventHandling();
    }

    public void initModels() {
        List<HomeExtentionPoint> aList = getExtentions();
        for (HomeExtentionPoint ex : aList) {
            ex.initPresentationModel(this, getEntityManager());
        }
    }

    public void initEventHandling() {
    }

    public void dispose() {
        List<HomeExtentionPoint> aList = getExtentions();
        for (HomeExtentionPoint ex : aList) {
            ex.dispose();
        }
        
        CWEntityManager.closeEntityManager(getEntityManager());
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

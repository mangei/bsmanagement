package cw.boardingschoolmanagement.gui;

import cw.boardingschoolmanagement.app.CWEntityManager;

/**
 *
 * @author Manuel Geier (CreativeWorkers)
 */
public class HomePresentationModel
	extends CWPresentationModel {

    public HomePresentationModel() {
    	super(CWEntityManager.createEntityManager());
        initModels();
        initEventHandling();
    }

    public void initModels() {
    }

    public void initEventHandling() {
    }

    public void release() {
        CWEntityManager.closeEntityManager(getEntityManager());
    }

}

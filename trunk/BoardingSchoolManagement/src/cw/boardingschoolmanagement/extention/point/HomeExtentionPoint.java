package cw.boardingschoolmanagement.extention.point;

import javax.persistence.EntityManager;

import cw.boardingschoolmanagement.extention.CWIExtention;
import cw.boardingschoolmanagement.gui.HomePresentationModel;
import cw.boardingschoolmanagement.gui.component.CWPanel;

/**
 *
 * @author Manuel Geier (CreativeWorkers)
 */
public interface HomeExtentionPoint extends CWIExtention {

    public void initPresentationModel(HomePresentationModel homePresentationModel, EntityManager entityManager);

    public Object getModel();

    /**
     * The panel you want to add on the home view <br>
     * @return JPanel
     */
    public CWPanel getView();

    public void dispose();
}

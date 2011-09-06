package cw.boardingschoolmanagement.extention.point;

import javax.persistence.EntityManager;

import cw.boardingschoolmanagement.gui.HomePresentationModel;
import cw.boardingschoolmanagement.gui.component.CWPanel;
import cw.boardingschoolmanagement.interfaces.Extention;

/**
 *
 * @author Manuel Geier (CreativeWorkers)
 */
public interface HomeExtentionPoint extends Extention {

    public void initPresentationModel(HomePresentationModel homePresentationModel, EntityManager entityManager);

    public Object getModel();

    /**
     * The panel you want to add on the home view <br>
     * @return JPanel
     */
    public CWPanel getView();

    public void dispose();
}

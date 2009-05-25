package cw.boardingschoolmanagement.extentions.interfaces;

import cw.boardingschoolmanagement.gui.HomePresentationModel;
import cw.boardingschoolmanagement.gui.component.CWPanel;

/**
 *
 * @author Manuel Geier (CreativeWorkers)
 */
public interface HomeExtention extends Extention {

    public void initPresentationModel(HomePresentationModel homePresentationModel);

    public Object getModel();

    /**
     * The panel you want to add on the home view <br>
     * @return JPanel
     */
    public CWPanel getView();

    public void dispose();
}

package cw.boardingschoolmanagement.extentions.interfaces;

import cw.boardingschoolmanagement.gui.HomePresentationModel;
import javax.swing.JPanel;

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
    public JPanel getPanel();

    public void dispose();
}

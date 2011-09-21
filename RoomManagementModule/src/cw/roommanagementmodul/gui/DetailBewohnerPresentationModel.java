package cw.roommanagementmodul.gui;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;

import cw.boardingschoolmanagement.app.ButtonListenerSupport;
import cw.boardingschoolmanagement.app.CWUtils;
import cw.boardingschoolmanagement.gui.CWEditPresentationModel;
import cw.boardingschoolmanagement.manager.GUIManager;
import cw.roommanagementmodul.persistence.Bewohner;

/**
 *
 * @author Dominik
 */
public class DetailBewohnerPresentationModel 
        extends CWEditPresentationModel<Bewohner>
{

    private Action backAction;
    private Bewohner bewohner;
    private String headerText;
    private ButtonListenerSupport support;

    public DetailBewohnerPresentationModel(Bewohner bewohner, String header) {
        super(bewohner);
        this.bewohner = bewohner;
        this.headerText = header;
        initModels();
        initEventHandling();
    }

    private void initModels() {
        support = new ButtonListenerSupport();
        backAction = new BackAction();
    }

    private void initEventHandling() {
    }

    public void dispose() {
        
    }

    /**
     * @return the bewohner
     */
    public Bewohner getBewohner() {
        return bewohner;
    }

    /**
     * @return the headerText
     */
    public String getHeaderText() {
        return headerText;
    }

    /**
     * @return the backAction
     */
    public Action getBackAction() {
        return backAction;
    }


    private class BackAction
            extends AbstractAction {

        {
            putValue(Action.SMALL_ICON, CWUtils.loadIcon("cw/roommanagementmodul/images/arrow_left.png"));
        }

        public void actionPerformed(ActionEvent e) {
            GUIManager.changeToPreviousView();  // Zur Uebersicht wechseln

        }
    }
}

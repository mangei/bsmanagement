/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cw.roommanagementmodul.gui;

import com.jgoodies.binding.PresentationModel;
import cw.boardingschoolmanagement.app.ButtonListenerSupport;
import cw.boardingschoolmanagement.app.CWUtils;
import cw.boardingschoolmanagement.manager.GUIManager;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.Action;
import cw.roommanagementmodul.pojo.Bewohner;

/**
 *
 * @author Dominik
 */
public class DetailBewohnerPresentationModel extends PresentationModel<Bewohner> {

    private Action backAction;
    private Bewohner bewohner;
    private String headerText;
    private ButtonListenerSupport support;

    public DetailBewohnerPresentationModel(Bewohner bewohner, String header) {
        super(bewohner);
        this.bewohner = bewohner;
        this.headerText=header;
        initModels();
        //this.initEventHandling();
    }

    private void initModels() {
        support = new ButtonListenerSupport();
        backAction = new BackAction();
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
            GUIManager.changeToLastView();  // Zur Übersicht wechseln

        }
    }
}

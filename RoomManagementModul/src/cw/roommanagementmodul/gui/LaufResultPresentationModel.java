/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cw.roommanagementmodul.gui;


import com.jgoodies.binding.PresentationModel;
import cw.boardingschoolmanagement.app.ButtonListener;
import cw.boardingschoolmanagement.app.ButtonListenerSupport;
import cw.boardingschoolmanagement.app.CWUtils;
import cw.boardingschoolmanagement.manager.GUIManager;
import cw.roommanagementmodul.geblauf.BewohnerTarifSelection;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.swing.AbstractAction;
import javax.swing.Action;
import cw.roommanagementmodul.pojo.Bewohner;

/**
 *
 * @author Dominik
 */
public class LaufResultPresentationModel extends PresentationModel<BewohnerTarifSelection> {

    private BewohnerTarifSelection tarifSelection;
    private String headerText;
    private Action backAction;
    private ButtonListenerSupport support;

    public LaufResultPresentationModel(BewohnerTarifSelection tarifSelection, String header) {
        super(tarifSelection);
        this.tarifSelection = tarifSelection;
        this.headerText = header;
        initModels();
    }

    private void initModels() {
        support = new ButtonListenerSupport();
        backAction = new BackAction();
        
    }

    public int getBewohnerAnzahl(){

        Map map=tarifSelection.getMap();
        Set keySet =map.keySet();
        return keySet.size();

    }

    public List<Bewohner> getBewohner(){

        List<Bewohner> bewohnerList = new ArrayList<Bewohner>();
        Map map=tarifSelection.getMap();
        Set keySet =map.keySet();

        Iterator iterator=keySet.iterator();

        while(iterator.hasNext()){
            bewohnerList.add((Bewohner)iterator.next());
        }

        return bewohnerList;

    }

    /**
     * @return the tarifSelection
     */
    public BewohnerTarifSelection getTarifSelection() {
        return tarifSelection;
    }

    /**
     * @param tarifSelection the tarifSelection to set
     */
    public void setTarifSelection(BewohnerTarifSelection tarifSelection) {
        this.tarifSelection = tarifSelection;
    }

    /**
     * @return the headerText
     */
    public String getHeaderText() {
        return headerText;
    }

    /**
     * @param headerText the headerText to set
     */
    public void setHeaderText(String headerText) {
        this.headerText = headerText;
    }

    /**
     * @return the backAction
     */
    public Action getBackAction() {
        return backAction;
    }

    /**
     * @param backAction the backAction to set
     */
    public void setBackAction(Action backAction) {
        this.backAction = backAction;
    }

    public void removeButtonListener(ButtonListener listener) {
        support.removeButtonListener(listener);
    }

    public void addButtonListener(ButtonListener listener) {
        support.addButtonListener(listener);
    }

    private class BackAction
            extends AbstractAction {

        {
            putValue(Action.SMALL_ICON, CWUtils.loadIcon("cw/roommanagementmodul/images/arrow_left.png"));
        }

        public void actionPerformed(ActionEvent e) {
            GUIManager.changeToLastView();  // Zur Übersicht wechseln
//                GUIManager.removeView(); // Diese View nicht merken
        //support.fireButtonPressed(new ButtonEvent(ButtonEvent.EXIT_BUTTON));

        }
    }
}

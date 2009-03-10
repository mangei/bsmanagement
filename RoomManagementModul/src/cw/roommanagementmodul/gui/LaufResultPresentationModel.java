/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cw.roommanagementmodul.gui;

import com.jgoodies.binding.PresentationModel;
import cw.boardingschoolmanagement.app.ButtonEvent;
import cw.boardingschoolmanagement.app.ButtonListener;
import cw.boardingschoolmanagement.app.ButtonListenerSupport;
import cw.boardingschoolmanagement.app.CWUtils;
import cw.boardingschoolmanagement.gui.component.JViewPanel.HeaderInfo;
import cw.boardingschoolmanagement.interfaces.Disposable;
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
public class LaufResultPresentationModel extends PresentationModel<BewohnerTarifSelection>
        implements Disposable {

    private BewohnerTarifSelection tarifSelection;
    private String headerText;
    private Action backAction;
    private Action printAction;
    private ButtonListenerSupport support;
    private HeaderInfo headerInfo;

    public LaufResultPresentationModel(BewohnerTarifSelection tarifSelection, HeaderInfo header) {
        super(tarifSelection);
        this.tarifSelection = tarifSelection;
        this.headerInfo = header;
        initModels();
    }

    private void initModels() {
        support = new ButtonListenerSupport();
        backAction = new BackAction();
        printAction = new PrintAction();

    }

    public int getBewohnerAnzahl() {

        Map map = tarifSelection.getMap();
        Set keySet = map.keySet();
        return keySet.size();

    }

    public List<Bewohner> getBewohner() {

        List<Bewohner> bewohnerList = new ArrayList<Bewohner>();
        Map map = tarifSelection.getMap();
        Set keySet = map.keySet();

        Iterator iterator = keySet.iterator();

        while (iterator.hasNext()) {
            bewohnerList.add((Bewohner) iterator.next());
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

    /**
     * @return the headerInfo
     */
    public HeaderInfo getHeaderInfo() {
        return headerInfo;
    }

    /**
     * @param headerInfo the headerInfo to set
     */
    public void setHeaderInfo(HeaderInfo headerInfo) {
        this.headerInfo = headerInfo;
    }

    /**
     * @return the printAction
     */
    public Action getPrintAction() {
        return printAction;
    }

    /**
     * @param printAction the printAction to set
     */
    public void setPrintAction(Action printAction) {
        this.printAction = printAction;
    }

    public void dispose() {
        release();
    }

    private class PrintAction extends AbstractAction {

        {
            putValue(Action.SMALL_ICON, CWUtils.loadIcon("cw/roommanagementmodul/images/printer.png"));
        }

        public void actionPerformed(ActionEvent e) {
            final PrintGebLaufPresentationModel model = new PrintGebLaufPresentationModel(tarifSelection, new HeaderInfo("Gebühren Lauf", "Gebühren Lauf zum Ausdrucken."));
            final PrintGebLaufView printView = new PrintGebLaufView(model);
            model.addButtonListener(new ButtonListener() {

                public void buttonPressed(ButtonEvent evt) {
                    if (evt.getType() == ButtonEvent.SAVE_BUTTON || evt.getType() == ButtonEvent.SAVE_EXIT_BUTTON) {
                    }
                    if (evt.getType() == ButtonEvent.EXIT_BUTTON || evt.getType() == ButtonEvent.SAVE_EXIT_BUTTON) {
                        model.removeButtonListener(this);
                        GUIManager.changeToLastView();
                    }
                }
            });
            GUIManager.changeView(printView.buildPanel(), true);

        }
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

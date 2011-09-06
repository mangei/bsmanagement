package cw.roommanagementmodul.gui;

import com.jgoodies.binding.PresentationModel;
import cw.boardingschoolmanagement.app.ButtonEvent;
import cw.boardingschoolmanagement.app.ButtonListener;
import cw.boardingschoolmanagement.app.ButtonListenerSupport;
import cw.boardingschoolmanagement.app.CWUtils;
import cw.boardingschoolmanagement.gui.component.CWView.CWHeaderInfo;
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
    private Action backAction;
    private Action printAction;
    private ButtonListenerSupport support;
    private CWHeaderInfo headerInfo;

    public LaufResultPresentationModel(BewohnerTarifSelection tarifSelection, CWHeaderInfo header) {
        super(tarifSelection);
        this.tarifSelection = tarifSelection;
        this.headerInfo = header;
        initModels();
        initEventHandling();
    }

    private void initModels() {
        support = new ButtonListenerSupport();
        backAction = new BackAction();
        printAction = new PrintAction();

    }

    private void initEventHandling() {

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
    public CWHeaderInfo getHeaderInfo() {
        return headerInfo;
    }

    /**
     * @return the printAction
     */
    public Action getPrintAction() {
        return printAction;
    }

    public void dispose() {
        release();
    }

    private class PrintAction extends AbstractAction {

        {
            putValue(Action.SMALL_ICON, CWUtils.loadIcon("cw/roommanagementmodul/images/printer.png"));
        }

        public void actionPerformed(ActionEvent e) {
            final PrintGebLaufPresentationModel model = new PrintGebLaufPresentationModel(tarifSelection, new CWHeaderInfo("Gebuehren Lauf", "Gebuehren Lauf zum Ausdrucken."), headerInfo.getHeaderText());
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
            GUIManager.changeView(printView, true);

        }
    }

    private class BackAction
            extends AbstractAction {

        {
            putValue(Action.SMALL_ICON, CWUtils.loadIcon("cw/roommanagementmodul/images/arrow_left.png"));
        }

        public void actionPerformed(ActionEvent e) {
            GUIManager.changeToLastView();  // Zur Uebersicht wechseln
//                GUIManager.removeView(); // Diese View nicht merken
        //support.fireButtonPressed(new ButtonEvent(ButtonEvent.EXIT_BUTTON));

        }
    }
}

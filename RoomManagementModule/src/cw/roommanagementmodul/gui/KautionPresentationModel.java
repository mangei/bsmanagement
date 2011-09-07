package cw.roommanagementmodul.gui;

import com.jgoodies.binding.list.SelectionInList;
import cw.boardingschoolmanagement.app.ButtonEvent;
import cw.boardingschoolmanagement.app.ButtonListener;
import cw.boardingschoolmanagement.app.ButtonListenerSupport;
import cw.boardingschoolmanagement.app.CWUtils;
import cw.boardingschoolmanagement.gui.component.CWView.CWHeaderInfo;
import cw.boardingschoolmanagement.manager.GUIManager;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.EventObject;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.SwingUtilities;
import cw.roommanagementmodul.pojo.Kaution;
import cw.roommanagementmodul.pojo.manager.BewohnerManager;
import cw.roommanagementmodul.pojo.manager.KautionManager;
import javax.swing.JOptionPane;

/**
 *
 * @author Dominik
 */
public class KautionPresentationModel {

    private KautionManager kautionManager;
    private BewohnerManager bewohnerManager;
    private Action newAction;
    private Action editAction;
    private Action deleteAction;
    private Action backAction;
    private ButtonListenerSupport support;
    private SelectionInList<Kaution> kautionSelection;
    private String headerText;
    private SelectionEmptyHandler selectionEmptyHandler;
    private DoubleClickHandler doubleClickHandler;
    private CWHeaderInfo headerInfo;

    KautionPresentationModel(KautionManager kautionManager, String header) {
        this.kautionManager = kautionManager;
        bewohnerManager = BewohnerManager.getInstance();
        selectionEmptyHandler = new SelectionEmptyHandler();
        this.headerText = header;
        doubleClickHandler = new DoubleClickHandler();
        initModels();
        this.initEventHandling();
    }

    KautionPresentationModel(KautionManager kautionManager, CWHeaderInfo header) {
        this.kautionManager = kautionManager;
        bewohnerManager = BewohnerManager.getInstance();
        selectionEmptyHandler = new SelectionEmptyHandler();
        this.headerInfo = header;
        doubleClickHandler = new DoubleClickHandler();
        initModels();
        this.initEventHandling();
    }

    private void initEventHandling() {
        getKautionSelection().addPropertyChangeListener(
                SelectionInList.PROPERTYNAME_SELECTION_EMPTY,
                selectionEmptyHandler);
    }

    private void initModels() {
        support = new ButtonListenerSupport();
        setNewAction(new NewAction());
        setEditAction(new EditAction());
        setDeleteAction(new DeleteAction());
        setBackAction(new BackAction());

        setKautionSelection(new SelectionInList<Kaution>(kautionManager.getAll()));
        updateActionEnablement();
    }

    public void removeButtonListener(ButtonListener listener) {
        support.removeButtonListener(listener);
    }

    public void addButtonListener(ButtonListener listener) {
        support.addButtonListener(listener);
    }

    /**
     * @return the kautionSelection
     */
    public SelectionInList<Kaution> getKautionSelection() {
        return kautionSelection;
    }

    /**
     * @param kautionSelection the kautionSelection to set
     */
    public void setKautionSelection(SelectionInList<Kaution> kautionSelection) {
        this.kautionSelection = kautionSelection;
    }

    /**
     * @return the headerText
     */
    public String getHeaderText() {
        return headerText;
    }

    /**
     * @return the newAction
     */
    public Action getNewAction() {
        return newAction;
    }

    /**
     * @param newAction the newAction to set
     */
    public void setNewAction(Action newAction) {
        this.newAction = newAction;
    }

    /**
     * @return the editAction
     */
    public Action getEditAction() {
        return editAction;
    }

    /**
     * @param editAction the editAction to set
     */
    public void setEditAction(Action editAction) {
        this.editAction = editAction;
    }

    /**
     * @return the deleteAction
     */
    public Action getDeleteAction() {
        return deleteAction;
    }

    /**
     * @param deleteAction the deleteAction to set
     */
    public void setDeleteAction(Action deleteAction) {
        this.deleteAction = deleteAction;
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

    public void dispose() {
        getKautionSelection().removeValueChangeListener(selectionEmptyHandler);
    }

    /**
     * @return the headerInfo
     */
    public CWHeaderInfo getHeaderInfo() {
        return headerInfo;
    }

    private class NewAction
            extends AbstractAction {

        {
            putValue(Action.SMALL_ICON, CWUtils.loadIcon("cw/roommanagementmodul/images/add.png"));
        }

        public void actionPerformed(ActionEvent e) {
            final Kaution k = new Kaution();
            final EditKautionPresentationModel model = new EditKautionPresentationModel(k, new CWHeaderInfo("Kaution erstellen", "Hier koennen Sie eine Kaution erstellen"));
            final EditKautionView editView = new EditKautionView(model);
            model.addButtonListener(new ButtonListener() {

                public void buttonPressed(ButtonEvent evt) {
                    if (evt.getType() == ButtonEvent.SAVE_BUTTON || evt.getType() == ButtonEvent.SAVE_EXIT_BUTTON) {
                        kautionManager.save(k);
                    }
                    if (evt.getType() == ButtonEvent.EXIT_BUTTON || evt.getType() == ButtonEvent.SAVE_EXIT_BUTTON) {
                        model.removeButtonListener(this);
                        kautionSelection.setList(kautionManager.getAll());
                        GUIManager.changeToLastView();
                        GUIManager.getStatusbar().setTextAndFadeOut("Kaution wurde erstellt.");
                    }
                }
            });
            GUIManager.changeView(editView, true);
        }
    }

    private class EditAction
            extends AbstractAction {

        {
            putValue(Action.SMALL_ICON, CWUtils.loadIcon("cw/roommanagementmodul/images/pencil.png"));
        }

        public void actionPerformed(ActionEvent e) {
            editSelectedItem(e);
        }
    }

    private class DeleteAction
            extends AbstractAction {

        {
            putValue(Action.SMALL_ICON, CWUtils.loadIcon("cw/roommanagementmodul/images/delete.png"));
        }

        public void actionPerformed(ActionEvent e) {

            Kaution k = kautionSelection.getSelection();

            int check = JOptionPane.showConfirmDialog(null, "Kaution wirklich loeschen?", "Loeschen", JOptionPane.YES_NO_OPTION);
            if (check == JOptionPane.YES_OPTION) {
                boolean existBewohner = bewohnerManager.existKaution(k);
                if (existBewohner == false) {
                    kautionManager.delete(k);
                    kautionSelection.setList(kautionManager.getAll());
                }else{
                    JOptionPane.showMessageDialog(null,"Kaution kann nicht geloescht werden da sie noch von " +
                            "Bewohnern benutzt wird!" , "Kaution", JOptionPane.OK_OPTION);
                }

            }
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

    private void updateActionEnablement() {
        boolean hasSelection = kautionSelection.hasSelection();
        getEditAction().setEnabled(hasSelection);
        getDeleteAction().setEnabled(hasSelection);
    }

    private final class SelectionEmptyHandler implements PropertyChangeListener {

        public void propertyChange(PropertyChangeEvent evt) {
            updateActionEnablement();
        }
    }

// Event Handling *********************************************************
    public MouseListener getDoubleClickHandler() {
        return doubleClickHandler;
    }

    private final class DoubleClickHandler extends MouseAdapter {

        @Override
        public void mouseClicked(MouseEvent e) {
            if (SwingUtilities.isLeftMouseButton(e) && e.getClickCount() == 2) {
                editSelectedItem(e);
            }
        }
    }

    private void editSelectedItem(EventObject e) {
        final Kaution k = this.getKautionSelection().getSelection();
        final EditKautionPresentationModel model = new EditKautionPresentationModel(k, new CWHeaderInfo("Kaution bearbeiten", "Hier koennen Sie ein Kaution bearbeiten"));
        final EditKautionView editView = new EditKautionView(model);
        model.addButtonListener(new ButtonListener() {

            public void buttonPressed(ButtonEvent evt) {
                if (evt.getType() == ButtonEvent.SAVE_BUTTON || evt.getType() == ButtonEvent.SAVE_EXIT_BUTTON) {
                    kautionManager.save(k);

                    GUIManager.getStatusbar().setTextAndFadeOut("Kaution wurde aktualisiert.");
                }

                if (evt.getType() == ButtonEvent.EXIT_BUTTON || evt.getType() == ButtonEvent.SAVE_EXIT_BUTTON) {
                    model.removeButtonListener(this);
                    GUIManager.changeToLastView();
                }

            }
        });
        GUIManager.changeView(editView, true);
    }
}

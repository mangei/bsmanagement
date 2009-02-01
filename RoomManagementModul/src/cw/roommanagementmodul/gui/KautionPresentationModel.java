/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package cw.roommanagementmodul.gui;


import com.jgoodies.binding.PresentationModel;
import com.jgoodies.binding.list.SelectionInList;
import cw.boardingschoolmanagement.app.ButtonEvent;
import cw.boardingschoolmanagement.app.ButtonListener;
import cw.boardingschoolmanagement.app.ButtonListenerSupport;
import cw.boardingschoolmanagement.app.CWUtils;
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
import cw.roommanagementmodul.pojo.manager.KautionManager;

/**
 *
 * @author Dominik
 */
public class KautionPresentationModel extends PresentationModel<KautionManager>{

     private KautionManager kautionManager;
    private Action newAction;
    private Action editAction;
    private Action deleteAction;
    private Action backAction;
    private ButtonListenerSupport support;
    private SelectionInList<Kaution> kautionSelection;
    private String headerText;



    KautionPresentationModel(KautionManager kautionManager, String header) {
        super(kautionManager);
        this.kautionManager = kautionManager;
        this.headerText = header;
        initModels();
        this.initEventHandling();
    }

     private void initEventHandling() {
        getKautionSelection().addPropertyChangeListener(
                SelectionInList.PROPERTYNAME_SELECTION_EMPTY,
                new SelectionEmptyHandler());
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

     private class NewAction
            extends AbstractAction {

        {
            putValue(Action.SMALL_ICON, CWUtils.loadIcon("cw/roommanagementmodul/images/category_add.png"));
        }

        public void actionPerformed(ActionEvent e) {
            final Kaution k = new Kaution();
            final EditKautionPresentationModel model = new EditKautionPresentationModel(k, "Kaution erstellen");
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
            GUIManager.changeView(editView.buildPanel(), true);
        }
    }

    private class EditAction
            extends AbstractAction {

        {
            putValue(Action.SMALL_ICON, CWUtils.loadIcon("cw/roommanagementmodul/images/category_edit.png"));
        }

        public void actionPerformed(ActionEvent e) {
            editSelectedItem(e);
        }
    }

    private class DeleteAction
            extends AbstractAction {

        {
            putValue(Action.SMALL_ICON, CWUtils.loadIcon("cw/roommanagementmodul/images/category_remove.png"));
        }

        public void actionPerformed(ActionEvent e) {
            Kaution k = kautionSelection.getSelection();
            kautionManager.delete(k);
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
        return new DoubleClickHandler();
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
        final EditKautionPresentationModel model = new EditKautionPresentationModel(k, "Kaution bearbeiten");
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
        GUIManager.changeView(editView.buildPanel(), true);
    }
}
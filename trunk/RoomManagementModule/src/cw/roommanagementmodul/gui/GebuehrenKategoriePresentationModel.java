package cw.roommanagementmodul.gui;

import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.EventObject;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import com.jgoodies.binding.list.SelectionInList;

import cw.boardingschoolmanagement.app.ButtonEvent;
import cw.boardingschoolmanagement.app.ButtonListener;
import cw.boardingschoolmanagement.app.ButtonListenerSupport;
import cw.boardingschoolmanagement.app.CWUtils;
import cw.boardingschoolmanagement.gui.CWPresentationModel;
import cw.boardingschoolmanagement.gui.component.CWView.CWHeaderInfo;
import cw.boardingschoolmanagement.manager.GUIManager;
import cw.roommanagementmodul.persistence.GebuehrenKategorie;
import cw.roommanagementmodul.persistence.PMGebuehrenKat;

/**
 *
 * @author Dominik
 */
public class GebuehrenKategoriePresentationModel
	extends CWPresentationModel
{

    private PMGebuehrenKat gebKatManager;
    private Action newAction;
    private Action editAction;
    private Action deleteAction;
    private Action backAction;
    private ButtonListenerSupport support;
    private SelectionInList<GebuehrenKategorie> gebuehrenKatSelection;
    private CWHeaderInfo headerInfo;
    private SelectionEmptyHandler selectionEmptyHandler;
    private DoubleClickHandler doubleClickHandler;

    public GebuehrenKategoriePresentationModel(PMGebuehrenKat gebKatManager) {
        selectionEmptyHandler = new SelectionEmptyHandler();
        this.gebKatManager = gebKatManager;
        doubleClickHandler = new DoubleClickHandler();
        initModels();
        this.initEventHandling();
    }

    public GebuehrenKategoriePresentationModel(PMGebuehrenKat gebKatManager, CWHeaderInfo header) {
        this.gebKatManager = gebKatManager;
        selectionEmptyHandler = new SelectionEmptyHandler();
        doubleClickHandler = new DoubleClickHandler();
        this.headerInfo = header;
        initModels();
        this.initEventHandling();
    }

    private void initEventHandling() {
        getGebuehrenKatSelection().addPropertyChangeListener(
                SelectionInList.PROPERTYNAME_SELECTION_EMPTY,
                selectionEmptyHandler);
    }

    private void initModels() {
        support = new ButtonListenerSupport();
        setNewAction(new NewAction());
        setEditAction(new EditAction());
        setDeleteAction(new DeleteAction());
        backAction = new BackAction();

        setGebuehrenKatSelection(new SelectionInList<GebuehrenKategorie>(getGebKatManager().getAll()));
        updateActionEnablement();
    }

    public void dispose() {
        getGebuehrenKatSelection().removeValueChangeListener(selectionEmptyHandler);
    }

    public void removeButtonListener(ButtonListener listener) {
        support.removeButtonListener(listener);
    }

    public void addButtonListener(ButtonListener listener) {
        support.addButtonListener(listener);
    }

    public Action getNewAction() {
        return newAction;
    }

    public void setNewAction(Action newAction) {
        this.newAction = newAction;
    }

    public Action getEditAction() {
        return editAction;
    }

    public Action getBackAction() {
        return backAction;
    }

    public void setEditAction(Action editAction) {
        this.editAction = editAction;
    }

    public Action getDeleteAction() {
        return deleteAction;
    }

    public void setDeleteAction(Action deleteAction) {
        this.deleteAction = deleteAction;
    }

    public PMGebuehrenKat getGebKatManager() {
        return gebKatManager;
    }

    public void setGebKatManager(PMGebuehrenKat gebKatManager) {
        this.gebKatManager = gebKatManager;
    }

    public SelectionInList<GebuehrenKategorie> getGebuehrenKatSelection() {
        return gebuehrenKatSelection;
    }

    public void setGebuehrenKatSelection(SelectionInList<GebuehrenKategorie> gebuehrenKatSelection) {
        this.gebuehrenKatSelection = gebuehrenKatSelection;
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
            putValue(Action.SMALL_ICON, CWUtils.loadIcon("cw/roommanagementmodul/images/category_add.png"));
        }

        public void actionPerformed(ActionEvent e) {
            final GebuehrenKategorie gk = new GebuehrenKategorie();
            final EditGebuehrenKategoriePresentationModel model = new EditGebuehrenKategoriePresentationModel(gk, new CWHeaderInfo("Kategorie erstellen", "Hier koennen Sie eine neue Gebuehren Kategorie erstellen"));
            final EditGebuehrenKategorieView editView = new EditGebuehrenKategorieView(model);
            model.addButtonListener(new ButtonListener() {

                public void buttonPressed(ButtonEvent evt) {
                    if (evt.getType() == ButtonEvent.SAVE_BUTTON || evt.getType() == ButtonEvent.SAVE_EXIT_BUTTON) {
                        gebKatManager.save(gk);
                    }
                    if (evt.getType() == ButtonEvent.EXIT_BUTTON || evt.getType() == ButtonEvent.SAVE_EXIT_BUTTON) {
                        model.removeButtonListener(this);
                        gebuehrenKatSelection.setList(gebKatManager.getAll());
                        GUIManager.changeToPreviousView();
                        GUIManager.getStatusbar().setTextAndFadeOut("Zimmer wurde erstellt.");
                    }
                }
            });
            GUIManager.changeViewTo(editView, true);
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
            GebuehrenKategorie gk = gebuehrenKatSelection.getSelection();

            int check = JOptionPane.showConfirmDialog(null, "Kategorie wirklich loeschen?", "Loeschen", JOptionPane.YES_NO_OPTION);
            if (check == JOptionPane.YES_OPTION) {
                gebKatManager.delete(gk);
                gebuehrenKatSelection.setList(gebKatManager.getAll());
            }

        }
    }

    private class BackAction
            extends AbstractAction {

        {
            putValue(Action.SMALL_ICON, CWUtils.loadIcon("cw/roommanagementmodul/images/arrow_left.png"));
        }

        public void actionPerformed(ActionEvent e) {
            GUIManager.changeToPreviousView();  // Zur Uebersicht wechseln
//                GUIManager.removeView(); // Diese View nicht merken
        //support.fireButtonPressed(new ButtonEvent(ButtonEvent.EXIT_BUTTON));

        }
    }

    private void updateActionEnablement() {
        boolean hasSelection = gebuehrenKatSelection.hasSelection();
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
        final GebuehrenKategorie gk = this.getGebuehrenKatSelection().getSelection();
        final EditGebuehrenKategoriePresentationModel model = new EditGebuehrenKategoriePresentationModel(gk, new CWHeaderInfo("Kategorie bearbeiten", "Hier koennen Sie eine bestehende Gebuehren Kategorie bearbeiten"));
        final EditGebuehrenKategorieView editView = new EditGebuehrenKategorieView(model);
        model.addButtonListener(new ButtonListener() {

            public void buttonPressed(ButtonEvent evt) {
                if (evt.getType() == ButtonEvent.SAVE_BUTTON || evt.getType() == ButtonEvent.SAVE_EXIT_BUTTON) {
                    gebKatManager.save(gk);

                    GUIManager.getStatusbar().setTextAndFadeOut("Kategorie wurde aktualisiert.");
                }

                if (evt.getType() == ButtonEvent.EXIT_BUTTON || evt.getType() == ButtonEvent.SAVE_EXIT_BUTTON) {
                    model.removeButtonListener(this);
                    GUIManager.changeToPreviousView();
                }

            }
        });
        GUIManager.changeViewTo(editView, true);
    }
}

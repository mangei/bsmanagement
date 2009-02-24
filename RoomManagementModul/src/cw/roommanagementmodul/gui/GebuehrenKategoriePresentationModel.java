/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cw.roommanagementmodul.gui;

import com.jgoodies.binding.list.SelectionInList;
import cw.boardingschoolmanagement.app.ButtonEvent;
import cw.boardingschoolmanagement.app.ButtonListener;
import cw.boardingschoolmanagement.app.ButtonListenerSupport;
import cw.boardingschoolmanagement.app.CWUtils;
import cw.boardingschoolmanagement.gui.component.JViewPanel.HeaderInfo;
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
import cw.roommanagementmodul.pojo.manager.GebuehrenKatManager;
import cw.roommanagementmodul.pojo.GebuehrenKategorie;
import javax.swing.JOptionPane;

/**
 *
 * @author Dominik
 */
public class GebuehrenKategoriePresentationModel {

    private GebuehrenKatManager gebKatManager;
    private Action newAction;
    private Action editAction;
    private Action deleteAction;
    private Action backAction;
    private ButtonListenerSupport support;
    private SelectionInList<GebuehrenKategorie> gebuehrenKatSelection;
    private String headerText;
    private HeaderInfo headerInfo;

    public GebuehrenKategoriePresentationModel(GebuehrenKatManager gebKatManager) {
        this.gebKatManager = gebKatManager;
        initModels();
        this.initEventHandling();
    }

    GebuehrenKategoriePresentationModel(GebuehrenKatManager gebKatManager, HeaderInfo header) {
        this.gebKatManager = gebKatManager;
        this.headerText = header.getHeaderText();
        this.headerInfo=header;
        initModels();
        this.initEventHandling();
    }

    private void initEventHandling() {
        getGebuehrenKatSelection().addPropertyChangeListener(
                SelectionInList.PROPERTYNAME_SELECTION_EMPTY,
                new SelectionEmptyHandler());
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

    public GebuehrenKatManager getGebKatManager() {
        return gebKatManager;
    }

    public void setGebKatManager(GebuehrenKatManager gebKatManager) {
        this.gebKatManager = gebKatManager;
    }

    public SelectionInList<GebuehrenKategorie> getGebuehrenKatSelection() {
        return gebuehrenKatSelection;
    }

    public void setGebuehrenKatSelection(SelectionInList<GebuehrenKategorie> gebuehrenKatSelection) {
        this.gebuehrenKatSelection = gebuehrenKatSelection;
    }

    public String getHeaderText() {
        return headerText;
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

    private class NewAction
            extends AbstractAction {

        {
            putValue(Action.SMALL_ICON, CWUtils.loadIcon("cw/roommanagementmodul/images/category_add.png"));
        }

        public void actionPerformed(ActionEvent e) {
            final GebuehrenKategorie gk = new GebuehrenKategorie();
            final EditGebuehrenKategoriePresentationModel model = new EditGebuehrenKategoriePresentationModel(gk, new HeaderInfo("Kategorie erstellen","Hier können Sie eine neue Gebühren Kategorie erstellen"));
            final EditGebuehrenKategorieView editView = new EditGebuehrenKategorieView(model);
            model.addButtonListener(new ButtonListener() {

                public void buttonPressed(ButtonEvent evt) {
                    if (evt.getType() == ButtonEvent.SAVE_BUTTON || evt.getType() == ButtonEvent.SAVE_EXIT_BUTTON) {
                        gebKatManager.save(gk);
                    }
                    if (evt.getType() == ButtonEvent.EXIT_BUTTON || evt.getType() == ButtonEvent.SAVE_EXIT_BUTTON) {
                        model.removeButtonListener(this);
                        gebuehrenKatSelection.setList(gebKatManager.getAll());
                        GUIManager.changeToLastView();
                        GUIManager.getStatusbar().setTextAndFadeOut("Zimmer wurde erstellt.");
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
            GebuehrenKategorie gk = gebuehrenKatSelection.getSelection();

            int check = JOptionPane.showConfirmDialog(null, "Kategorie wirklich löschen?", "Löschen", JOptionPane.YES_NO_OPTION);
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
            GUIManager.changeToLastView();  // Zur Übersicht wechseln
//                GUIManager.removeView(); // Diese View nicht merken
        //support.fireButtonPressed(new ButtonEvent(ButtonEvent.EXIT_BUTTON));

        }
    }

    private void updateActionEnablement() {
        boolean hasSelection = gebuehrenKatSelection.hasSelection();
        System.out.println(hasSelection);
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
        final GebuehrenKategorie gk = this.getGebuehrenKatSelection().getSelection();
        final EditGebuehrenKategoriePresentationModel model = new EditGebuehrenKategoriePresentationModel(gk, new HeaderInfo("Kategorie bearbeiten","Hier können Sie eine bestehende Gebühren Kategorie bearbeiten"));
        final EditGebuehrenKategorieView editView = new EditGebuehrenKategorieView(model);
        model.addButtonListener(new ButtonListener() {

            public void buttonPressed(ButtonEvent evt) {
                if (evt.getType() == ButtonEvent.SAVE_BUTTON || evt.getType() == ButtonEvent.SAVE_EXIT_BUTTON) {
                    gebKatManager.save(gk);

                    GUIManager.getStatusbar().setTextAndFadeOut("Kategorie wurde aktualisiert.");
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

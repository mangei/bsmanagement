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
import javax.swing.ListModel;
import javax.swing.SwingUtilities;
import javax.swing.table.TableModel;
import javax.swing.tree.DefaultMutableTreeNode;

import com.jgoodies.binding.adapter.AbstractTableAdapter;
import com.jgoodies.binding.list.SelectionInList;

import cw.boardingschoolmanagement.app.ButtonEvent;
import cw.boardingschoolmanagement.app.ButtonListener;
import cw.boardingschoolmanagement.app.ButtonListenerSupport;
import cw.boardingschoolmanagement.app.CWUtils;
import cw.boardingschoolmanagement.gui.CWPresentationModel;
import cw.boardingschoolmanagement.gui.component.CWView.CWHeaderInfo;
import cw.boardingschoolmanagement.manager.GUIManager;
import cw.roommanagementmodul.persistence.Bereich;
import cw.roommanagementmodul.persistence.PMBereich;
import cw.roommanagementmodul.persistence.PMZimmer;
import cw.roommanagementmodul.persistence.Zimmer;

/**
 *
 * @author Dominik
 */
public class ZimmerPresentationModel
	extends CWPresentationModel
{

    private PMZimmer zimmerManager;
    private Action newAction;
    private Action editAction;
    private Action deleteAction;
    private Action backAction;
    private Action printAction;
    private ButtonListenerSupport support;
    private SelectionInList<Zimmer> zimmerSelection;
    private BereichPresentationModel bereichModel;
    private CWHeaderInfo headerInfo;
    private SelectionEmptyHandler selectionEmptyHandler;
    private DoubleClickHandler doubleClickHandler;

    public ZimmerPresentationModel(PMZimmer zimmerManager, CWHeaderInfo header, BereichPresentationModel bereichModel) {
        this.bereichModel = bereichModel;
        this.zimmerManager = zimmerManager;
        this.headerInfo = header;
        initModels();
        initEventHandling();
    }

    private void initModels() {
        doubleClickHandler = new DoubleClickHandler();
        selectionEmptyHandler = new SelectionEmptyHandler();
        support = new ButtonListenerSupport();
        newAction = new NewAction();
        editAction = new EditAction();
        deleteAction = new DeleteAction();
        printAction = new PrintAction();
        backAction = new BackAction();

        zimmerSelection = new SelectionInList<Zimmer>(zimmerManager.getAll());

    }

    private void initEventHandling() {
        getZimmerSelection().addPropertyChangeListener(
                SelectionInList.PROPERTYNAME_SELECTION_EMPTY,
                selectionEmptyHandler);
        updateActionEnablement();
    }


    public void dispose() {
        getZimmerSelection().removeValueChangeListener(selectionEmptyHandler);
        zimmerSelection.release();
    }

    public TableModel createZimmerTableModel(ListModel listModel) {
        return new ZimmerTableModel(listModel);
    }

    private void updateActionEnablement() {
        boolean hasSelection = getZimmerSelection().hasSelection();
        getEditAction().setEnabled(hasSelection);
        getDeleteAction().setEnabled(hasSelection);
    }

    public Action getNewAction() {
        return newAction;
    }

    public Action getEditAction() {
        return editAction;
    }

    public Action getDeleteAction() {
        return deleteAction;
    }

    public SelectionInList<Zimmer> getZimmerSelection() {
        return zimmerSelection;
    }

    public void removeButtonListener(ButtonListener listener) {
        support.removeButtonListener(listener);
    }

    public void addButtonListener(ButtonListener listener) {
        support.addButtonListener(listener);
    }

    /**
     * @return the backAction
     */
    public Action getBackAction() {
        return backAction;
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


    private class NewAction
            extends AbstractAction {

        {
            putValue(Action.SMALL_ICON, CWUtils.loadIcon("cw/roommanagementmodul/images/door_add.png"));
        }

        public void actionPerformed(ActionEvent e) {
            final Zimmer z = new Zimmer();
            final EditZimmerPresentationModel model = new EditZimmerPresentationModel(z, new CWHeaderInfo("Zimmer erstellen", "Hier koennen Sie ein neus Zimmer erstellen"));
            final EditZimmerView editView = new EditZimmerView(model);
            model.addButtonListener(new ButtonListener() {

                public void buttonPressed(ButtonEvent evt) {
                    if (evt.getType() == ButtonEvent.SAVE_BUTTON || evt.getType() == ButtonEvent.SAVE_EXIT_BUTTON) {
                        zimmerManager.save(z);
                    }
                    if (evt.getType() == ButtonEvent.EXIT_BUTTON || evt.getType() == ButtonEvent.SAVE_EXIT_BUTTON) {
                        model.removeButtonListener(this);
                        zimmerSelection.setList(zimmerManager.getAll());
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
            putValue(Action.SMALL_ICON, CWUtils.loadIcon("cw/roommanagementmodul/images/door_edit.png"));
        }

        public void actionPerformed(ActionEvent e) {
            editSelectedItem(e);
        }
    }

    private class DeleteAction
            extends AbstractAction {

        {
            putValue(Action.SMALL_ICON, CWUtils.loadIcon("cw/roommanagementmodul/images/door_remove.png"));
        }

        public void actionPerformed(ActionEvent e) {
            Zimmer z = zimmerSelection.getSelection();
            zimmerManager.refresh(z);
            System.out.println(z.getBewohnerList().size());
            if (z.getBewohnerList() == null || z.getBewohnerList().size() == 0) {
                zimmerManager.delete(z);
                zimmerSelection.setList(zimmerManager.getAll());
            } else {
                Object[] options = {"Ok"};
                JOptionPane.showOptionDialog(null, "Zimmer " + z.getName() + " enthaelt Bewohner!", "Warnung", JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null, options, options[0]);
            }

        }
    }

    private class BackAction
            extends AbstractAction {

        {
            putValue(Action.SMALL_ICON, CWUtils.loadIcon("cw/roommanagementmodul/images/arrow_left.png"));
        }

        public void actionPerformed(ActionEvent e) {
            Bereich internatBereich;
            PMBereich bereichManager = PMBereich.getInstance();
            if (bereichManager.existRoot() == false) {
                internatBereich = new Bereich("Internat", null);
                bereichManager.save(internatBereich);
            } else {
                internatBereich = bereichManager.getRoot();
            }

            bereichModel.setRootTree(new DefaultMutableTreeNode(internatBereich, true));
            bereichModel.getTreeModel().setRoot(bereichModel.getRootTree());
            bereichModel.initTree(bereichModel.getRootTree());
            bereichModel.getTreeModel().reload();
            GUIManager.changeToPreviousView();  // Zur Uebersicht wechseln


        }
    }

    private class PrintAction
            extends AbstractAction {

        {
            putValue(Action.SMALL_ICON, CWUtils.loadIcon("cw/roommanagementmodul/images/printer.png"));
        }

        public void actionPerformed(ActionEvent e) {

            final PrintZimmerPresentationModel model = new PrintZimmerPresentationModel(zimmerSelection.getList(), new CWHeaderInfo("Zimmer Liste drucken", "Hier koennen Sie die aktuelle Zimmer Liste ausdrucken oder speichern."));
            final PrintZimmerView printView = new PrintZimmerView(model);
            model.addButtonListener(new ButtonListener() {

                public void buttonPressed(ButtonEvent evt) {
                    if (evt.getType() == ButtonEvent.SAVE_BUTTON || evt.getType() == ButtonEvent.SAVE_EXIT_BUTTON) {
                    }
                    if (evt.getType() == ButtonEvent.EXIT_BUTTON || evt.getType() == ButtonEvent.SAVE_EXIT_BUTTON) {
                        model.removeButtonListener(this);
                        GUIManager.changeToPreviousView();
                    }
                }
            });
            GUIManager.changeViewTo(printView, true);


        }
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

    private class ZimmerTableModel extends AbstractTableAdapter<Zimmer> {

        private ListModel listModel;

        public ZimmerTableModel(ListModel listModel) {
            super(listModel);
            this.listModel = listModel;
        }

        @Override
        public int getColumnCount() {
            return 3;
        }

        @Override
        public String getColumnName(int column) {
            switch (column) {
                case 0:
                    return "Zimmer";
                case 1:
                    return "Bereich";
                case 2:
                    return "Anzahl Betten";
                default:
                    return "";
            }
        }

        @Override
        public boolean isCellEditable(int rowIndex, int columnIndex) {
            return false;
        }

        public Object getValueAt(int rowIndex, int columnIndex) {
            Zimmer z = (Zimmer) listModel.getElementAt(rowIndex);
            switch (columnIndex) {
                case 0:
                    return z.getName();
                case 1:
                    if (z.getBereich() != null) {
                        return z.getBereich();
                    } else {
                        return "-";
                    }
                case 2:
                    return z.getAnzbetten();
                default:
                    return "";
            }

        }
    }

    private void editSelectedItem(EventObject e) {
        final Zimmer z = zimmerSelection.getSelection();
        final EditZimmerPresentationModel model = new EditZimmerPresentationModel(z, new CWHeaderInfo("Zimmer bearbeiten", "Hier koennen Sie ein bestehendes Zimmer bearbeiten"));
        final EditZimmerView editView = new EditZimmerView(model);
        model.addButtonListener(new ButtonListener() {

            public void buttonPressed(ButtonEvent evt) {
                if (evt.getType() == ButtonEvent.SAVE_BUTTON || evt.getType() == ButtonEvent.SAVE_EXIT_BUTTON) {
                    zimmerManager.save(z);

                    GUIManager.getStatusbar().setTextAndFadeOut("Zimmer wurde aktualisiert.");
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

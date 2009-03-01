/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cw.roommanagementmodul.gui;

import com.jgoodies.binding.adapter.AbstractTableAdapter;
import com.jgoodies.binding.list.SelectionInList;
import cw.boardingschoolmanagement.app.ButtonEvent;
import cw.boardingschoolmanagement.app.ButtonListener;
import cw.boardingschoolmanagement.app.ButtonListenerSupport;
import cw.boardingschoolmanagement.app.CWUtils;
import cw.boardingschoolmanagement.gui.component.JViewPanel.HeaderInfo;
import cw.boardingschoolmanagement.manager.GUIManager;
import cw.roommanagementmodul.pojo.Bereich;
import cw.roommanagementmodul.pojo.manager.ZimmerManager;
import cw.roommanagementmodul.pojo.Zimmer;
import cw.roommanagementmodul.pojo.manager.BereichManager;
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

/**
 *
 * @author Dominik
 */
public class ZimmerPresentationModel {

    private ZimmerManager zimmerManager;
    private Action newAction;
    private Action editAction;
    private Action deleteAction;
    private Action backAction;
    private Action printAction;
    private String headerText;
    private ButtonListenerSupport support;
    private SelectionInList<Zimmer> zimmerSelection;
    private BereichPresentationModel bereichModel;
    private HeaderInfo headerInfo;

    public ZimmerPresentationModel(ZimmerManager zimmerManager) {

        this.zimmerManager = zimmerManager;
        initModels();
        this.initEventHandling();

    }

    public ZimmerPresentationModel(ZimmerManager zimmerManager, HeaderInfo header, BereichPresentationModel bereichModel) {
        this.bereichModel = bereichModel;
        this.zimmerManager = zimmerManager;
        this.headerText = header.getHeaderText();
        this.headerInfo=header;
        initModels();
        this.initEventHandling();
    }

    private void initModels() {
        support = new ButtonListenerSupport();
        newAction = new NewAction();
        editAction = new EditAction();
        deleteAction = new DeleteAction();
        setPrintAction(new PrintAction());
        setBackAction(new BackAction());

        zimmerSelection = new SelectionInList<Zimmer>(zimmerManager.getAll());
        updateActionEnablement();
    }

    public TableModel createZimmerTableModel(ListModel listModel) {
        return new ZimmerTableModel(listModel);
    }

    private void updateActionEnablement() {
        boolean hasSelection = getZimmerSelection().hasSelection();
        getEditAction().setEnabled(hasSelection);
        getDeleteAction().setEnabled(hasSelection);
    }

    private void initEventHandling() {
        getZimmerSelection().addPropertyChangeListener(
                SelectionInList.PROPERTYNAME_SELECTION_EMPTY,
                new SelectionEmptyHandler());
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

    public String getHeaderText() {
        return headerText;
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

    private class NewAction
            extends AbstractAction {

        {
            putValue(Action.SMALL_ICON, CWUtils.loadIcon("cw/roommanagementmodul/images/door_add.png"));
        }

        public void actionPerformed(ActionEvent e) {
            final Zimmer z = new Zimmer();
            final EditZimmerPresentationModel model = new EditZimmerPresentationModel(z, new HeaderInfo("Zimmer erstellen","Hier können Sie ein neus Zimmer erstellen"));
            final EditZimmerView editView = new EditZimmerView(model);
            model.addButtonListener(new ButtonListener() {

                public void buttonPressed(ButtonEvent evt) {
                    if (evt.getType() == ButtonEvent.SAVE_BUTTON || evt.getType() == ButtonEvent.SAVE_EXIT_BUTTON) {
                        zimmerManager.save(z);
                    }
                    if (evt.getType() == ButtonEvent.EXIT_BUTTON || evt.getType() == ButtonEvent.SAVE_EXIT_BUTTON) {
                        model.removeButtonListener(this);
                        zimmerSelection.setList(zimmerManager.getAll());
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
            if (z.getBewohnerList() == null || z.getBewohnerList().size() == 0) {
                zimmerManager.delete(z);
                zimmerSelection.setList(zimmerManager.getAll());
            } else {
                Object[] options = {"Ok"};
                JOptionPane.showOptionDialog(null, "Zimmer " + z.getName() + " enthält Bewohner!", "Warnung", JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null, options, options[0]);
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
            BereichManager bereichManager = BereichManager.getInstance();
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
            GUIManager.changeToLastView();  // Zur Übersicht wechseln


        }
    }

        private class PrintAction
            extends AbstractAction {

        {
            putValue(Action.SMALL_ICON, CWUtils.loadIcon("cw/roommanagementmodul/images/printer.png"));
        }

        public void actionPerformed(ActionEvent e) {

            final PrintZimmerPresentationModel model = new PrintZimmerPresentationModel(zimmerSelection.getList(), new HeaderInfo("Zimmer Liste drucken","Hier können Sie die aktuelle Zimmer Liste ausdrucken oder speichern."));
            final PrintZimmerView printView = new PrintZimmerView(model);
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
        final EditZimmerPresentationModel model = new EditZimmerPresentationModel(z, new HeaderInfo("Zimmer bearbeiten","Hier können Sie ein bestehendes Zimmer bearbeiten"));
        final EditZimmerView editView = new EditZimmerView(model);
        model.addButtonListener(new ButtonListener() {

            public void buttonPressed(ButtonEvent evt) {
                if (evt.getType() == ButtonEvent.SAVE_BUTTON || evt.getType() == ButtonEvent.SAVE_EXIT_BUTTON) {
                    zimmerManager.save(z);

                    GUIManager.getStatusbar().setTextAndFadeOut("Zimmer wurde aktualisiert.");
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

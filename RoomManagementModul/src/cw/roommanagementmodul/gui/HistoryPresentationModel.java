/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cw.roommanagementmodul.gui;


import com.jgoodies.binding.PresentationModel;
import com.jgoodies.binding.adapter.AbstractTableAdapter;
import com.jgoodies.binding.list.SelectionInList;
import cw.boardingschoolmanagement.app.ButtonListener;
import cw.boardingschoolmanagement.app.ButtonListenerSupport;
import cw.boardingschoolmanagement.app.CWUtils;
import cw.boardingschoolmanagement.manager.GUIManager;
import java.awt.event.ActionEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ListModel;
import javax.swing.table.TableModel;
import cw.roommanagementmodul.pojo.Bewohner;
import cw.roommanagementmodul.pojo.BewohnerHistory;
import cw.roommanagementmodul.pojo.manager.BewohnerHistoryManager;

/**
 *
 * @author Dominik
 */
public class HistoryPresentationModel extends PresentationModel<Bewohner> {

    private Action deleteAction;
    private Action backAction;
    private Bewohner bewohner;
    private String headerText;
    private ButtonListenerSupport support;
    private SelectionInList<BewohnerHistory> historySelection;
    private BewohnerHistoryManager historyManager;

    public HistoryPresentationModel(Bewohner bewohner, String header) {
        super(bewohner);
        this.bewohner = bewohner;
        deleteAction = new DeleteAction();
        backAction = new BackAction();
        headerText = header;
        historyManager = BewohnerHistoryManager.getInstance();
        initModels();
        this.initEventHandling();


    }

    private void initEventHandling() {
        getHistorySelection().addPropertyChangeListener(
                SelectionInList.PROPERTYNAME_SELECTION_EMPTY,
                new SelectionEmptyHandler());
    }

    private void initModels() {
        support = new ButtonListenerSupport();
        deleteAction = new DeleteAction();
        backAction = new BackAction();

        //nur vom bestimmten Bewohner!!!
        historySelection = new SelectionInList<BewohnerHistory>(historyManager.getBewohnerHistory(this.bewohner));
        updateActionEnablement();
    }

    /**
     * @return the headerText
     */
    public String getHeaderText() {
        return headerText;
    }

    /**
     * @return the deleteAction
     */
    public Action getDeleteAction() {
        return deleteAction;
    }

    /**
     * @return the backAction
     */
    public Action getBackAction() {
        return backAction;
    }

    /**
     * @return the historySelection
     */
    public SelectionInList<BewohnerHistory> getHistorySelection() {
        return historySelection;
    }

    private final class SelectionEmptyHandler implements PropertyChangeListener {

        public void propertyChange(PropertyChangeEvent evt) {
            updateActionEnablement();
        }
    }

    public void removeButtonListener(ButtonListener listener) {
        support.removeButtonListener(listener);
    }

    public void addButtonListener(ButtonListener listener) {
        support.addButtonListener(listener);
    }

    private void updateActionEnablement() {
        boolean hasSelection = getHistorySelection().hasSelection();
        getDeleteAction().setEnabled(hasSelection);
    }

    private class DeleteAction
            extends AbstractAction {

        {
            putValue(Action.SMALL_ICON, CWUtils.loadIcon("cw/roommanagementmodul/images/delete.png"));
        }

        public void actionPerformed(ActionEvent e) {
            final BewohnerHistory bh = getHistorySelection().getSelection();
            historyManager.delete(bh);
            historySelection.setList(historyManager.getBewohnerHistory(bewohner));

        }
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

    public TableModel createHistoryTableModel(ListModel listModel) {
        return new HistoryTableModel(listModel);
    }

    private class HistoryTableModel extends AbstractTableAdapter<BewohnerHistory> {

        private ListModel listModel;

        public HistoryTableModel(ListModel listModel) {
            super(listModel);
            this.listModel = listModel;
        }

        @Override
        public int getColumnCount() {
            return 4;
        }

        @Override
        public String getColumnName(int column) {
            switch (column) {
                case 0:
                    return "Zimmer Name";
                case 1:
                    return "Bereich";
                case 2:
                    return "Von";
                case 3:
                    return "Bis";
                default:
                    return "";
            }
        }

        @Override
        public boolean isCellEditable(int rowIndex, int columnIndex) {
            return false;
        }

        public Object getValueAt(int rowIndex, int columnIndex) {
            BewohnerHistory bh = (BewohnerHistory) listModel.getElementAt(rowIndex);
            switch (columnIndex) {
                case 0:
                    return bh.getZimmerName();
                case 1:
                        return bh.getBereichName();
                case 2:
                    return bh.getVon();
                case 3:
                    return bh.getBis();
                default:
                    return "";
            }


        }
    }
}

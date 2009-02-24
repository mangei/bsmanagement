/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cw.roommanagementmodul.gui;


import com.jgoodies.binding.PresentationModel;
import com.jgoodies.binding.adapter.AbstractTableAdapter;
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
import javax.swing.JOptionPane;
import javax.swing.ListModel;
import javax.swing.SwingUtilities;
import javax.swing.table.TableModel;
import cw.roommanagementmodul.pojo.Gebuehr;
import cw.roommanagementmodul.pojo.Tarif;
import cw.roommanagementmodul.pojo.manager.TarifManager;

/**
 *
 * @author Dominik
 */
public class TarifPresentationModel extends PresentationModel<Gebuehr> {

    private TarifManager tarifManager;
    private Action newAction;
    private Action editAction;
    private Action deleteAction;
    private Action backAction;
    private Gebuehr gebuehr;
    private ButtonListenerSupport support;
    private SelectionInList<Tarif> tarifSelection;
    private String headerText;
    private HeaderInfo headerInfo;

    public TarifPresentationModel(Gebuehr gebuehr) {
        super(gebuehr);
        this.gebuehr = gebuehr;
        this.tarifManager = TarifManager.getInstance();
        initModels();
        this.initEventHandling();

    }

    public TarifPresentationModel(Gebuehr gebuehr, HeaderInfo header) {
        super(gebuehr);
        this.gebuehr = gebuehr;
        this.tarifManager = TarifManager.getInstance();
        this.headerText=header.getHeaderText();
        this.headerInfo=header;
        initModels();
        this.initEventHandling();
    }

    private void initEventHandling() {
        getTarifSelection().addPropertyChangeListener(
                SelectionInList.PROPERTYNAME_SELECTION_EMPTY,
                new SelectionEmptyHandler());
    }

    private void initModels() {
        support = new ButtonListenerSupport();
        newAction = new NewAction();
        editAction = new EditAction();
        deleteAction = new DeleteAction();
        backAction = new BackAction();


        setTarifSelection(new SelectionInList<Tarif>(tarifManager.getAllOrderd(gebuehr)));
        updateActionEnablement();
    }

    private void updateActionEnablement() {
        boolean hasSelection = getTarifSelection().hasSelection();
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

    public Action getBackAction() {
        return backAction;
    }

    public SelectionInList<Tarif> getTarifSelection() {
        return tarifSelection;
    }

    public void removeButtonListener(ButtonListener listener) {
        support.removeButtonListener(listener);
    }

    public void addButtonListener(ButtonListener listener) {
        support.addButtonListener(listener);
    }

    public void setTarifSelection(SelectionInList<Tarif> tarifSelection) {
        this.tarifSelection = tarifSelection;
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
            putValue(Action.SMALL_ICON, CWUtils.loadIcon("cw/roommanagementmodul/images/coins_add.png"));
        }

        public void actionPerformed(ActionEvent e) {
            newSelectedItem(e);
        }
    }

    private class EditAction
            extends AbstractAction {

        {
            putValue(Action.SMALL_ICON, CWUtils.loadIcon("cw/roommanagementmodul/images/coins_edit.png"));
        }

        public void actionPerformed(ActionEvent e) {
            editSelectedItem(e);
        }
    }

    private class DeleteAction
            extends AbstractAction {

        {
            putValue(Action.SMALL_ICON, CWUtils.loadIcon("cw/roommanagementmodul/images/coins_remove.png"));
        }

        public void actionPerformed(ActionEvent e) {
             int i = JOptionPane.showConfirmDialog(null, "Tarif wirklich löschen?","Löschen",JOptionPane.OK_CANCEL_OPTION);
            if (i == JOptionPane.OK_OPTION) {
                Gebuehr g=tarifSelection.getSelection().getGebuehr();
                tarifManager.delete(tarifSelection.getSelection());
                tarifSelection.setList(tarifManager.getTarif(g));
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

    private void newSelectedItem(EventObject e) {
        final Tarif t = new Tarif();
        t.setGebuehr(gebuehr);
        final EditTarifPresentationModel model = new EditTarifPresentationModel(t,new HeaderInfo("Tarif erstellen","Hier können Sie einen neuen Tarif erstellen"));
        final EditTarifView gebView = new EditTarifView(model);
        model.addButtonListener(new ButtonListener() {

            public void buttonPressed(ButtonEvent evt) {
                if (evt.getType() == ButtonEvent.SAVE_BUTTON || evt.getType() == ButtonEvent.SAVE_EXIT_BUTTON) {
                    tarifManager.save(t);
                    tarifSelection.setList(tarifManager.getAllOrderd(gebuehr));
                    GUIManager.getStatusbar().setTextAndFadeOut("Zuordnung wurde aktualisiert.");
                }
                if (evt.getType() == ButtonEvent.EXIT_BUTTON || evt.getType() == ButtonEvent.SAVE_EXIT_BUTTON) {
                    model.removeButtonListener(this);
                    GUIManager.changeToLastView();
                }
            }
        });
        GUIManager.changeView(gebView.buildPanel(), true);
    }

    private void editSelectedItem(EventObject e) {
        final Tarif t= tarifSelection.getSelection();
        final EditTarifPresentationModel model = new EditTarifPresentationModel(t,new HeaderInfo("Tarif bearbeiten","Hier können Sie einen bestehenden Tarif bearbeiten"));
        final EditTarifView editView = new EditTarifView(model);
        model.addButtonListener(new ButtonListener() {
            public void buttonPressed(ButtonEvent evt) {
                if(evt.getType() == ButtonEvent.SAVE_BUTTON || evt.getType() == ButtonEvent.SAVE_EXIT_BUTTON) {
                    tarifManager.save(t);
                    
                    GUIManager.getStatusbar().setTextAndFadeOut("Tarif wurde aktualisiert.");
                }
                if(evt.getType() == ButtonEvent.EXIT_BUTTON || evt.getType() == ButtonEvent.SAVE_EXIT_BUTTON) {
                    model.removeButtonListener(this);
                    GUIManager.changeToLastView();
                }
            }
        });
        GUIManager.changeView(editView.buildPanel(),true);
    }

    public TableModel createZuordnungTableModel(ListModel listModel) {
        return new TarifTableModel(listModel);
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

    private class TarifTableModel extends AbstractTableAdapter<Tarif> {

        private ListModel listModel;

        public TarifTableModel(ListModel listModel) {
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
                    return "Von";
                case 1:
                    return "Bis";
                case 2:
                    return "Tarif";
                default:
                    return "";
            }
        }

        @Override
        public boolean isCellEditable(int rowIndex, int columnIndex) {
            return false;
        }

        public Object getValueAt(int rowIndex, int columnIndex) {
            Tarif t = (Tarif) listModel.getElementAt(rowIndex);
            switch (columnIndex) {
                case 0:
                    return t.getAb();
                case 1:
                    return t.getBis();
                case 2:
                    return t.getTarif();
                default:
                    return "";
            }

        }
    }
}

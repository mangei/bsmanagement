/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cw.roommanagementmodul.gui;

import com.jgoodies.binding.adapter.AbstractTableAdapter;
import com.jgoodies.binding.adapter.ComboBoxAdapter;
import com.jgoodies.binding.list.SelectionInList;
import cw.boardingschoolmanagement.app.ButtonEvent;
import cw.boardingschoolmanagement.app.ButtonListener;
import cw.boardingschoolmanagement.app.CWUtils;
import cw.boardingschoolmanagement.gui.component.JViewPanel.HeaderInfo;
import cw.boardingschoolmanagement.manager.GUIManager;
import cw.customermanagementmodul.gui.CustomerManagementPresentationModel;
import cw.customermanagementmodul.gui.EditCustomerPresentationModel;
import cw.customermanagementmodul.pojo.Customer;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.EventObject;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.ListModel;
import javax.swing.SwingUtilities;
import javax.swing.table.TableModel;

import cw.roommanagementmodul.pojo.Bewohner;
import cw.roommanagementmodul.pojo.GebuehrZuordnung;
import cw.roommanagementmodul.pojo.manager.BewohnerHistoryManager;
import cw.roommanagementmodul.pojo.manager.GebuehrZuordnungManager;
import cw.roommanagementmodul.pojo.manager.BewohnerManager;
import cw.roommanagementmodul.pojo.manager.KautionManager;
import java.util.List;

/**
 *
 * @author Dominik
 */
public class BewohnerPresentationModel {

    private BewohnerManager bewohnerManager;
    private String headerText;
    private GebuehrZuordnungManager gebZuordnungManager;
    private BewohnerHistoryManager historyManager;
    private Action gebuehrZuordnungAction;
    private Action gebAction;
    private Action deleteAction;
    private Action historyAction;
    private Action inaktiveAction;
    private Action detailAction;
    private Action kautionAction;
    private SelectionInList<Bewohner> bewohnerSelection;
    private HeaderInfo headerInfo;

    public BewohnerPresentationModel(BewohnerManager bewohnerManager) {
        //super(bewohnerManager);
        this.bewohnerManager = bewohnerManager;
        historyManager = BewohnerHistoryManager.getInstance();
        gebZuordnungManager = GebuehrZuordnungManager.getInstance();
        initModels();
        this.initEventHandling();

    }

    public BewohnerPresentationModel(BewohnerManager bewohnerManager, HeaderInfo header) {
        //super(bewohnerManager);
        this.bewohnerManager = bewohnerManager;

        this.headerInfo = header;
        this.headerText = header.getHeaderText();
        historyManager = BewohnerHistoryManager.getInstance();
        gebZuordnungManager = GebuehrZuordnungManager.getInstance();
        initModels();
        this.initEventHandling();
    }

    private void initModels() {

        gebAction = new GebAction();
        deleteAction = new DeleteAction();
        setGebuehrZuordnungAction(new GebuehrZuordnungAction());
        historyAction = new HistoryAction();
        inaktiveAction = new InaktiveAction();
        kautionAction = new KautionAction();
        detailAction = new AbstractAction("Bearbeiten", CWUtils.loadIcon("cw/customermanagementmodul/images/user_edit.png")) {

            public void actionPerformed(ActionEvent e) {
                EditCustomerPresentationModel model;
                model = CustomerManagementPresentationModel.editCustomer(bewohnerSelection.getSelection().getCustomer());
                model.addButtonListener(new ButtonListener() {

                    public void buttonPressed(ButtonEvent evt) {

                        if (evt.getType() == ButtonEvent.SAVE_BUTTON || evt.getType() == ButtonEvent.SAVE_EXIT_BUTTON) {

                            bewohnerSelection.setList(bewohnerManager.getBewohner(true));
                        }
                    }
                });
            }
        };
        bewohnerSelection = new SelectionInList<Bewohner>(getBewohnerManager().getBewohner(true));
        updateActionEnablement();
    }

    public TableModel createBewohnerTableModel(ListModel listModel) {
        return new BewohnerTableModel(listModel);
    }

    public ComboBoxModel createComboModel(SelectionInList list) {
        return new ComboBoxAdapter(list);
    }

    private void updateActionEnablement() {
        boolean hasSelection = getBewohnerSelection().hasSelection();
        getDeleteAction().setEnabled(hasSelection);
        gebAction.setEnabled(hasSelection);
        getGebuehrZuordnungAction().setEnabled(hasSelection);
        historyAction.setEnabled(hasSelection);
        detailAction.setEnabled(hasSelection);
    }

    private void initEventHandling() {
        getBewohnerSelection().addPropertyChangeListener(
                SelectionInList.PROPERTYNAME_SELECTION_EMPTY,
                new SelectionEmptyHandler());
    }

    public SelectionInList<Bewohner> getBewohnerSelection() {
        return bewohnerSelection;
    }

    public Action getGebAction() {
        return gebAction;
    }

    public Action getDeleteAction() {
        return deleteAction;
    }

    public Action getHistoryAction() {
        return historyAction;
    }

    public String getHeaderText() {
        return headerText;
    }

    /**
     * @return the bewohnerManager
     */
    public BewohnerManager getBewohnerManager() {
        return bewohnerManager;
    }

    /**
     * @param bewohnerManager the bewohnerManager to set
     */
    public void setBewohnerManager(BewohnerManager bewohnerManager) {
        this.bewohnerManager = bewohnerManager;
    }

    /**
     * @return the inaktiveAction
     */
    public Action getInaktiveAction() {
        return inaktiveAction;
    }

    /**
     * @return the gebuehrZuordnungAction
     */
    public Action getGebuehrZuordnungAction() {
        return gebuehrZuordnungAction;
    }

    /**
     * @param gebuehrZuordnungAction the gebuehrZuordnungAction to set
     */
    public void setGebuehrZuordnungAction(Action gebuehrZuordnungAction) {
        this.gebuehrZuordnungAction = gebuehrZuordnungAction;
    }

    /**
     * @return the detailAction
     */
    public Action getDetailAction() {
        return detailAction;
    }

    /**
     * @param detailAction the detailAction to set
     */
    public void setDetailAction(Action detailAction) {
        this.detailAction = detailAction;
    }

    /**
     * @return the kautionAction
     */
    public Action getKautionAction() {
        return kautionAction;
    }

    public void deleteBewohner() {
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

    private class DeleteAction
            extends AbstractAction {

        {
            putValue(Action.SMALL_ICON, CWUtils.loadIcon("cw/roommanagementmodul/images/delete.png"));
        }

        public void actionPerformed(ActionEvent e) {
            Bewohner b = getBewohnerSelection().getSelection();

            int k = JOptionPane.showConfirmDialog(null, "Bewohner: " + b.getCustomer().getSurname() + " " + b.getCustomer().getForename() + " wirklich löschen?", "LÖSCHEN", JOptionPane.OK_CANCEL_OPTION);
            if (k == JOptionPane.OK_OPTION) {

                System.out.println("Bewohner Delete------------------------------------------");
                b.setCustomer(null);
                bewohnerManager.delete(b);
                bewohnerSelection.setList(bewohnerManager.getBewohner(true));

            }
        }
    }

    private class GebAction
            extends AbstractAction {

        {
            putValue(Action.SMALL_ICON, CWUtils.loadIcon("cw/roommanagementmodul/images/money_add.png"));
        }

        public void actionPerformed(ActionEvent e) {
            gebSelectedItem(e);
        }
    }

    private class GebuehrZuordnungAction
            extends AbstractAction {

        {
            putValue(Action.SMALL_ICON, CWUtils.loadIcon("cw/roommanagementmodul/images/money.png"));
        }

        public void actionPerformed(ActionEvent e) {
            gebZuordnungSelectedItem(e);
        }
    }

    private class InaktiveAction
            extends AbstractAction {

        {
            putValue(Action.SMALL_ICON, CWUtils.loadIcon("cw/roommanagementmodul/images/user_orange_remove.png"));
        }

        public void actionPerformed(ActionEvent e) {

            final InaktiveBewohnerPresentationModel model = new InaktiveBewohnerPresentationModel(getBewohnerManager(), "Inaktive Bewohner");
            final InaktiveBewohnerView inaktiveBewohnerView = new InaktiveBewohnerView(model);

            GUIManager.changeView(inaktiveBewohnerView.buildPanel(), true);

        }
    }

    private class KautionAction
            extends AbstractAction {

        {
            putValue(Action.SMALL_ICON, CWUtils.loadIcon("cw/roommanagementmodul/images/money_dollar.png"));
        }

        public void actionPerformed(ActionEvent e) {

            final KautionPresentationModel model = new KautionPresentationModel(KautionManager.getInstance(), "Kautionen Verwalten");
            final KautionView kautionView = new KautionView(model);

            GUIManager.changeView(kautionView.buildPanel(), true);

        }
    }

    private class HistoryAction
            extends AbstractAction {

        {
            putValue(Action.SMALL_ICON, CWUtils.loadIcon("cw/roommanagementmodul/images/date.png"));
        }

        public void actionPerformed(ActionEvent e) {
            final Bewohner b = bewohnerSelection.getSelection();
            Customer c = b.getCustomer();
            final HistoryPresentationModel model = new HistoryPresentationModel(b, c.getSurname() + " " + c.getForename());
            final HistoryView editBewohnerView = new HistoryView(model);
            model.addButtonListener(new ButtonListener() {

                public void buttonPressed(ButtonEvent evt) {

                    if (evt.getType() == ButtonEvent.EXIT_BUTTON || evt.getType() == ButtonEvent.SAVE_EXIT_BUTTON) {

                        model.removeButtonListener(this);
                        GUIManager.changeToLastView();
                    }
                }
            });
            GUIManager.changeView(editBewohnerView.buildPanel(), true);

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
                CustomerManagementPresentationModel.editCustomer(bewohnerSelection.getSelection().getCustomer());
            //detailSelectedItem(e);
            }
        }
    }

    private void gebSelectedItem(EventObject e) {
        //final Bewohner b = getBewohnerSelection().getSelection();
        final GebuehrZuordnung gb = new GebuehrZuordnung();
        Customer c = bewohnerSelection.getSelection().getCustomer();
        gb.setBewohner(bewohnerSelection.getSelection());
        final GebBewohnerPresentationModel model = new GebBewohnerPresentationModel(gb, new HeaderInfo("Bewohner: " + c.getSurname() + " " + c.getForename(), "Hier können Sie alle Gebühren verwalten, die zu einem Bewohner zugeordnet sind."));
        final GebBewohnerView gebView = new GebBewohnerView(model);
        model.addButtonListener(new ButtonListener() {

            public void buttonPressed(ButtonEvent evt) {
                if (evt.getType() == ButtonEvent.SAVE_BUTTON || evt.getType() == ButtonEvent.SAVE_EXIT_BUTTON) {

                    gebZuordnungManager.save(gb);
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

    private void gebZuordnungSelectedItem(EventObject e) {
        Customer c = bewohnerSelection.getSelection().getCustomer();
        final GebZuordnungBewohnerPresentationModel model = new GebZuordnungBewohnerPresentationModel(this.getBewohnerSelection().getSelection(), new HeaderInfo("Gebühren Übersicht: " + c.getSurname() + " " + c.getForename(), "Übersicht aller Gebühren die diesem Bewohner zugeordnet sind."));
        final GebZuordnunglBewohnerView detailView = new GebZuordnunglBewohnerView(model);
        model.addButtonListener(new ButtonListener() {

            public void buttonPressed(ButtonEvent evt) {
                if (evt.getType() == ButtonEvent.SAVE_BUTTON || evt.getType() == ButtonEvent.SAVE_EXIT_BUTTON) {
                    //gb.setBewohner(bewohnerSelection.getSelection());
                    //gebZuordnungManager.saveGebuehrZuordnung(gb);
                    GUIManager.getStatusbar().setTextAndFadeOut("Zuordnung wurde aktualisiert.");
                }

                if (evt.getType() == ButtonEvent.EXIT_BUTTON || evt.getType() == ButtonEvent.SAVE_EXIT_BUTTON) {
                    model.removeButtonListener(this);
                    GUIManager.changeToLastView();
                }

            }
        });
        GUIManager.changeView(detailView.buildPanel(), true);
    }

    private class BewohnerTableModel
            extends AbstractTableAdapter<Bewohner> {

        private ListModel listModel;

        public BewohnerTableModel(ListModel listModel) {
            super(listModel);
            this.listModel = listModel;
        }

        @Override
        public int getColumnCount() {
            return 7;
        }

        @Override
        public String getColumnName(int column) {
            switch (column) {
                case 0:
                    return "Nachname";
                case 1:
                    return "Vorname";
                case 2:
                    return "Zimmer";
                case 3:
                    return "Bereich";
                case 4:
                    return "Einzugsdatum";
                case 5:
                    return "Auszugsdatum";
                case 6:
                    return "Gebühr Zuordnungen";
                default:
                    return "";
            }
        }

        @Override
        public boolean isCellEditable(int rowIndex, int columnIndex) {

            return false;
        }

        public Object getValueAt(int rowIndex, int columnIndex) {
            Bewohner b = (Bewohner) listModel.getElementAt(rowIndex);
            switch (columnIndex) {
                case 0:
                    return b.getCustomer().getSurname();
                case 1:
                    return b.getCustomer().getForename();

                case 2:
                    return b.getZimmer();

                case 3:
                    if (b.getZimmer() != null) {
                        return b.getZimmer().getBereich();
                    } else {
                        return "-";
                    }

                case 4:
                    return b.getVon();
                case 5:
                    return b.getBis();

                case 6:
                    GebuehrZuordnungManager gebZuoManager = GebuehrZuordnungManager.getInstance();
                    List<GebuehrZuordnung> l = gebZuoManager.getGebuehrZuordnung(b);
                    return l.size();

                default:
                    return "";
            }

        }
    }
}

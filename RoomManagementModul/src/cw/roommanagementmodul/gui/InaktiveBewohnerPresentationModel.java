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
import cw.boardingschoolmanagement.manager.GUIManager;
import cw.customermanagementmodul.pojo.Customer;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.EventObject;
import javax.swing.Action;
import javax.swing.JOptionPane;
import javax.swing.ListModel;
import javax.swing.SwingUtilities;
import javax.swing.table.TableModel;
import cw.roommanagementmodul.pojo.Bewohner;
import cw.roommanagementmodul.pojo.manager.BewohnerHistoryManager;
import cw.roommanagementmodul.pojo.manager.BewohnerManager;
import cw.roommanagementmodul.pojo.manager.GebuehrZuordnungManager;
import javax.swing.AbstractAction;

/**
 *
 * @author Dominik
 */
public class InaktiveBewohnerPresentationModel extends PresentationModel<BewohnerManager> {

    private BewohnerManager bewohnerManager;
    private String headerText;
    private GebuehrZuordnungManager gebZuordnungManager;
    private BewohnerHistoryManager historyManager;
    private Action gebuehrZuordnungAction;
    private Action deleteAction;
    private Action historyAction;
    private Action eintretenAction;
    private Action backAction;
    private Action detailAction;
    private SelectionInList<Bewohner> bewohnerSelection;
    private ButtonListenerSupport support;

    public InaktiveBewohnerPresentationModel(BewohnerManager bewohnerManager, String header) {
        super(bewohnerManager);
        this.bewohnerManager = bewohnerManager;
        this.headerText = header;
        historyManager = BewohnerHistoryManager.getInstance();
        gebZuordnungManager = GebuehrZuordnungManager.getInstance();
        initModels();
        this.initEventHandling();
    }

    private void initEventHandling() {
        getBewohnerSelection().addPropertyChangeListener(
                SelectionInList.PROPERTYNAME_SELECTION_EMPTY,
                new SelectionEmptyHandler());
    }

    private void initModels() {
        support = new ButtonListenerSupport();
        setGebuehrZuordnungAction(new GebuehrZuordnungAction());
        deleteAction = new DeleteAction();
        historyAction = new HistoryAction();
        backAction = new BackAction();
        eintretenAction = new EintretenAction();
        detailAction = new DetailAction();
        bewohnerSelection = new SelectionInList<Bewohner>(bewohnerManager.getBewohner(false));
        updateActionEnablement();
    }

    private void updateActionEnablement() {
        boolean hasSelection = getBewohnerSelection().hasSelection();
        getDeleteAction().setEnabled(hasSelection);
        getGebuehrZuordnungAction().setEnabled(hasSelection);
        getHistoryAction().setEnabled(hasSelection);
        getEintretenAction().setEnabled(hasSelection);
        detailAction.setEnabled(hasSelection);
    }

    public TableModel createBewohnerTableModel(ListModel listModel) {
        return new BewohnerTableModel(listModel);
    }

    public void removeButtonListener(ButtonListener listener) {
        support.removeButtonListener(listener);
    }

    public void addButtonListener(ButtonListener listener) {
        support.addButtonListener(listener);
    }

    /**
     * @return the headerText
     */
    public String getHeaderText() {
        return headerText;
    }

    /**
     * @return the detailAction
     */
    /**
     * @return the deleteAction
     */
    public Action getDeleteAction() {
        return deleteAction;
    }

    /**
     * @return the historyAction
     */
    public Action getHistoryAction() {
        return historyAction;
    }

    /**
     * @return the eintretenAction
     */
    public Action getEintretenAction() {
        return eintretenAction;
    }

    /**
     * @return the bewohnerSelection
     */
    public SelectionInList<Bewohner> getBewohnerSelection() {
        return bewohnerSelection;
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
     * @return the backAction
     */
    public Action getBackAction() {
        return backAction;
    }

    /**
     * @return the detailAction
     */
    public Action getDetailAction() {
        return detailAction;
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
                detailSelectedItem(e);
            }
        }
    }

    private class DeleteAction
            extends AbstractAction {

        {
            putValue(Action.SMALL_ICON, CWUtils.loadIcon("cw/roommanagementmodul/images/delete.png"));
        }

        public void actionPerformed(ActionEvent e) {
            final Bewohner b = getBewohnerSelection().getSelection();

            int i = JOptionPane.showConfirmDialog(null, "Bewohner: " + b.getCustomer().getSurname() + " " + b.getCustomer().getForename() + " endgültig löschen?","Löschen",JOptionPane.OK_CANCEL_OPTION);
            if (i == JOptionPane.OK_OPTION) {
                if (b.getKautionStatus() == Bewohner.EINGEZAHLT) {
                    int j = JOptionPane.showConfirmDialog(null, "Der Status der Kaution befindet sich auf EINGEZAHLT!  \n \n Trotzdem löschen?","Kaution",JOptionPane.OK_CANCEL_OPTION);
                    if (j == JOptionPane.OK_OPTION) {
                        historyManager.removeBewohnerHistory(b);
                        gebZuordnungManager.removeGebuehrZuordnung(b);
                        bewohnerManager.delete(b);
                    }
                } else {
                    historyManager.removeBewohnerHistory(b);
                    gebZuordnungManager.removeGebuehrZuordnung(b);
                    bewohnerManager.delete(b);
                }

            }
        }
    }

    private class DetailAction
            extends AbstractAction {

        {
            putValue(Action.SMALL_ICON, CWUtils.loadIcon("cw/roommanagementmodul/images/zoom.png"));
        }

        public void actionPerformed(ActionEvent e) {
            detailSelectedItem(e);
        }
    }

    private void detailSelectedItem(EventObject e) {
        if (bewohnerSelection.getSelection() != null) {
            Customer c = bewohnerSelection.getSelection().getCustomer();
            final DetailBewohnerPresentationModel model = new DetailBewohnerPresentationModel(this.getBewohnerSelection().getSelection(), c.getSurname() + " " + c.getForename());
            final DetailBewohnerView detailView = new DetailBewohnerView(model);

            GUIManager.changeView(detailView.buildPanel(), true);
        }

    }

    private class HistoryAction
            extends AbstractAction {

        {
            putValue(Action.SMALL_ICON, CWUtils.loadIcon("cw/roommanagementmodul/images/date.png"));
        }

        public void actionPerformed(ActionEvent e) {
            final Bewohner b = getBewohnerSelection().getSelection();
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

    private class EintretenAction
            extends AbstractAction {

        {
            putValue(Action.SMALL_ICON, CWUtils.loadIcon("cw/roommanagementmodul/images/add.png"));
        }

        public void actionPerformed(ActionEvent e) {
            final Bewohner b = getBewohnerSelection().getSelection();
            Customer c = b.getCustomer();
            final EditBewohnerZimmerPresentationModel model = new EditBewohnerZimmerPresentationModel(b, c.getSurname() + " " + c.getForename());
            final EditBewohnerZimmerView editBewohnerZimmerView = new EditBewohnerZimmerView(model);
            model.addButtonListener(new ButtonListener() {

                public void buttonPressed(ButtonEvent evt) {

                    if (evt.getType() == ButtonEvent.SAVE_BUTTON || evt.getType() == ButtonEvent.SAVE_EXIT_BUTTON) {
                        b.setActive(true);
                        bewohnerManager.save(b);
                        historyManager.saveBewohnerHistory(b);
                        bewohnerSelection.setList(bewohnerManager.getBewohner(false));
                        GUIManager.getStatusbar().setTextAndFadeOut("Bewohner wurde aktualisiert.");
                    }

                    if (evt.getType() == ButtonEvent.EXIT_BUTTON || evt.getType() == ButtonEvent.SAVE_EXIT_BUTTON) {

                        model.removeButtonListener(this);
                        GUIManager.changeToLastView();
                    }
                }
            });
            GUIManager.changeView(editBewohnerZimmerView.buildPanel(), true);

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

    private class GebuehrZuordnungAction
            extends AbstractAction {

        {
            putValue(Action.SMALL_ICON, CWUtils.loadIcon("cw/roommanagementmodul/images/money.png"));
        }

        public void actionPerformed(ActionEvent e) {
            gebZuordnungSelectedItem(e);
        }
    }

    private void gebZuordnungSelectedItem(EventObject e) {
        Customer c = getBewohnerSelection().getSelection().getCustomer();
        final GebZuordnungBewohnerPresentationModel model = new GebZuordnungBewohnerPresentationModel(this.getBewohnerSelection().getSelection(), c.getSurname() + " " + c.getForename());
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

    private class BewohnerTableModel extends AbstractTableAdapter<Bewohner> {

        private ListModel listModel;

        public BewohnerTableModel(ListModel listModel) {
            super(listModel);
            this.listModel = listModel;
        }

        @Override
        public int getColumnCount() {
            return 5;
        }

        @Override
        public String getColumnName(int column) {
            switch (column) {
                case 0:
                    return "Nachname";
                case 1:
                    return "Vorname";
                case 2:
                    return "Einzahler";
                case 3:
                    return "Kaution";
                case 4:
                    return "Kaution Status";
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
                    if (b.getEinzahler() != null) {
                        return b.getEinzahler().getSurname() + " " + b.getEinzahler().getForename();
                    } else {
                        return b.getCustomer().getSurname() + " " + b.getCustomer().getForename();
                    }
                case 3:
                    return b.getKaution();
                case 4:
                    switch (b.getKautionStatus()) {
                        case Bewohner.EINGEZAHLT:
                            return "Eingezahlt";
                        case Bewohner.KEINE_KAUTION:
                            return "Keine Kaution";
                        case Bewohner.NICHT_EINGEZAHLT:
                            return "Nicht Eingezahlt";
                        case Bewohner.ZURUECK_GEZAHLT:
                            return "Zurück gezahlt";
                    }

                default:
                    return "";
            }

        }
    }
}

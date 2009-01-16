package cw.customermanagementmodul.gui;

import cw.boardingschoolmanagement.app.ButtonEvent;
import cw.boardingschoolmanagement.app.ButtonListener;
import cw.boardingschoolmanagement.app.CWUtils;
import cw.customermanagementmodul.pojo.manager.AccountingManager;
import com.jgoodies.binding.adapter.AbstractTableAdapter;
import com.jgoodies.binding.list.SelectionInList;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.EventObject;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ListModel;
import javax.swing.SwingUtilities;
import javax.swing.table.TableModel;
import cw.boardingschoolmanagement.manager.GUIManager;
import cw.customermanagementmodul.pojo.Accounting;
import cw.customermanagementmodul.pojo.Customer;
import javax.swing.Icon;

/**
 *
 * @author Manuel Geier
 */
public class AccountingManagementCustomerGUIExtentionPresentationModel {

    private Customer customer;

    private Action newAction;
    private Action editAction;
    private Action cancelAction;
    private Action deleteAction;
    private SelectionInList<Accounting> accountingSelection;

    /**
     * Constructor if you want to load all accountings
     */
    public AccountingManagementCustomerGUIExtentionPresentationModel() {
        this(null);
    }

    /**
     * Constructor if you want to load all accountings from one customer
     * @param customer Customer
     */
    public AccountingManagementCustomerGUIExtentionPresentationModel(Customer customer) {
        this.customer = customer;

        initModels();
        initEventHandling();
    }

    public void initModels() {
        newAction = new NewAction("Neu", CWUtils.loadIcon("cw/customermanagementmodul/images/money_add.png"));
        editAction = new EditAction("Bearbeiten", CWUtils.loadIcon("cw/customermanagementmodul/images/money_edit.png"));
        cancelAction = new CancelAction("Stornieren", CWUtils.loadIcon("cw/customermanagementmodul/images/money_delete.png"));
        deleteAction = new DeleteAction("Löschen", CWUtils.loadIcon("cw/customermanagementmodul/images/money_delete.png"));

        accountingSelection = new SelectionInList<Accounting>(AccountingManager.getAccountings());

        updateActionEnablement();
    }

    private void initEventHandling() {
        accountingSelection.addPropertyChangeListener(
                SelectionInList.PROPERTYNAME_SELECTION_EMPTY,
                new SelectionEmptyHandler());
    }

    public void save() {
    }

    public void reset() {
    }

    private class NewAction
            extends AbstractAction {

        public NewAction(String name, Icon icon) {
            super(name, icon);
        }

        public void actionPerformed(ActionEvent e) {
            GUIManager.setLoadingScreenText("Formular wird geladen...");
            GUIManager.setLoadingScreenVisible(true);

            new Thread(new Runnable() {

                public void run() {

                    final Accounting a = new Accounting(customer);
                    final EditAccountingPresentationModel model = new EditAccountingPresentationModel(a);
                    final EditAccountingView editView = new EditAccountingView(model);
                    model.addButtonListener(new ButtonListener() {

                        boolean accountingAlreadyCreated = false;

                        public void buttonPressed(ButtonEvent evt) {

                            if (evt.getType() == ButtonEvent.SAVE_BUTTON || evt.getType() == ButtonEvent.SAVE_EXIT_BUTTON) {
                                AccountingManager.saveAccounting(a);
                                if (accountingAlreadyCreated) {
                                    GUIManager.getStatusbar().setTextAndFadeOut("Buchung wurde aktualisiert.");
                                } else {
                                    GUIManager.getStatusbar().setTextAndFadeOut("Buchung wurde erstellt.");
                                    accountingAlreadyCreated = true;
                                    accountingSelection.getList().add(a);
                                }
                            }
                            if (evt.getType() == ButtonEvent.EXIT_BUTTON || evt.getType() == ButtonEvent.SAVE_EXIT_BUTTON) {
                                model.removeButtonListener(this);
                                GUIManager.changeToLastView();
                            }
                        }
                    });
                    GUIManager.changeView(editView.buildPanel(), true);
                    GUIManager.setLoadingScreenVisible(false);

                }
            }).start();
        }
    }

    private class EditAction
            extends AbstractAction {

        public EditAction(String name, Icon icon) {
            super(name, icon);
        }

        public void actionPerformed(ActionEvent e) {
            editSelectedItem(e);
        }
    }

    private class CancelAction
            extends AbstractAction {

        public CancelAction(String name, Icon icon) {
            super(name, icon);
        }

        public void actionPerformed(ActionEvent e) {
            GUIManager.setLoadingScreenText("Buchung wird storniert...");
            GUIManager.setLoadingScreenVisible(true);

            new Thread(new Runnable() {

                public void run() {
                    Accounting a = accountingSelection.getSelection();

                    a = AccountingManager.cancelAnAccounting(a);
                    accountingSelection.getList().add(a);

                    GUIManager.getStatusbar().setTextAndFadeOut("Buchung wurde storniert...");

                    GUIManager.setLoadingScreenVisible(false);
                }
            }).start();
        }
    }

    private class DeleteAction
            extends AbstractAction {

        public DeleteAction(String name, Icon icon) {
            super(name, icon);
        }

        public void actionPerformed(ActionEvent e) {
            GUIManager.setLoadingScreenText("Buchung wird gelöscht...");
            GUIManager.setLoadingScreenVisible(true);

            new Thread(new Runnable() {

                public void run() {
                    Accounting a = accountingSelection.getSelection();

                    accountingSelection.getList().remove(a);
                    AccountingManager.removeAccounting(a);

                    GUIManager.setLoadingScreenVisible(false);
                }
            }).start();
        }
    }

    public TableModel createBuchungTableModel(ListModel listModel) {
        return new BuchungTableModel(listModel);
    }

    public Action getDeleteButtonAction() {
        return deleteAction;
    }

    public Action getEditButtonAction() {
        return editAction;
    }

    public Action getCancelButtonAction() {
        return cancelAction;
    }

    public SelectionInList<Accounting> getAccountingSelection() {
        return accountingSelection;
    }

    public Action getNewButtonAction() {
        return newAction;
    }

    private void editSelectedItem(EventObject e) {
        GUIManager.setLoadingScreenText("Buchung wird geladen...");
        GUIManager.setLoadingScreenVisible(true);

        new Thread(new Runnable() {

            public void run() {
                final Accounting a = accountingSelection.getSelection();
                final EditAccountingPresentationModel model = new EditAccountingPresentationModel(a);
                final EditAccountingView editView = new EditAccountingView(model);
                model.addButtonListener(new ButtonListener() {

                    public void buttonPressed(ButtonEvent evt) {
                        if (evt.getType() == ButtonEvent.SAVE_BUTTON || evt.getType() == ButtonEvent.SAVE_EXIT_BUTTON) {
                            AccountingManager.saveAccounting(a);
                            GUIManager.getStatusbar().setTextAndFadeOut("Buchung wurde aktualisiert.");
                        }
                        if (evt.getType() == ButtonEvent.EXIT_BUTTON || evt.getType() == ButtonEvent.SAVE_EXIT_BUTTON) {
                            model.removeButtonListener(this);
                            GUIManager.changeToLastView();
                        }
                    }
                });
                GUIManager.changeView(editView.buildPanel(), true);
                GUIManager.setLoadingScreenVisible(false);
            }
        }).start();
    }

    private class BuchungTableModel extends AbstractTableAdapter<Accounting> {

        private ListModel listModel;

        public BuchungTableModel(ListModel listModel) {
            super(listModel);
            this.listModel = listModel;
        }

        @Override
        public int getColumnCount() {
            return 6;
        }

        @Override
        public String getColumnName(int column) {
            switch (column) {
                case 0:
                    return "Bezeichnung";
                case 1:
                    return "Betrag";
                case 2:
                    return "Soll";
                case 3:
                    return "Haben";
                case 4:
                    return "Eingangsdatum";
                case 5:
                    return "Buchungsdatum";
                default:
                    return "";
            }
        }

        @Override
        public boolean isCellEditable(int rowIndex, int columnIndex) {
            return false;
        }

        public Object getValueAt(int rowIndex, int columnIndex) {
            Accounting a = (Accounting) listModel.getElementAt(rowIndex);
            switch (columnIndex) {
                case 0:
                    return a.getDescription();
                case 1:
                    return a.getAmount();
                case 2:
                    return a.isLiabilities() ? a.getAmount() : "";
                case 3:
                    return a.isAssets() ? a.getAmount() : "";
                case 4:
                    return a.getAccountingEntryDate();
                case 5:
                    return a.getAccountingDate();
                default:
                    return "";
            }
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

    private void updateActionEnablement() {
        boolean hasSelection = accountingSelection.hasSelection();
        editAction.setEnabled(hasSelection);
        cancelAction.setEnabled(hasSelection);
        deleteAction.setEnabled(hasSelection);
    }

    private final class SelectionEmptyHandler implements PropertyChangeListener {

        public void propertyChange(PropertyChangeEvent evt) {
            updateActionEnablement();
        }
    }
}

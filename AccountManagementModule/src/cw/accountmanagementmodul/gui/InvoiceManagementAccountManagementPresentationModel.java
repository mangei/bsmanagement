package cw.accountmanagementmodul.gui;

import com.jgoodies.binding.list.SelectionInList;
import cw.boardingschoolmanagement.app.ButtonEvent;
import cw.boardingschoolmanagement.app.ButtonListener;
import cw.boardingschoolmanagement.app.ButtonListenerSupport;
import cw.boardingschoolmanagement.app.CWUtils;
import com.jgoodies.binding.value.ValueHolder;
import com.jgoodies.binding.value.ValueModel;
import cw.accountmanagementmodul.gui.model.InvoiceTreeTableModel;
import cw.accountmanagementmodul.pojo.Account;
import cw.accountmanagementmodul.pojo.Invoice;
import cw.boardingschoolmanagement.gui.component.CWView.CWHeaderInfo;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.Icon;
import javax.swing.JOptionPane;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javax.swing.table.TableModel;
import org.jdesktop.swingx.treetable.TreeTableModel;

/**
 *
 * @author CreativeWorkers.at
 */
public class InvoiceManagementAccountManagementPresentationModel
{

    private Account account;
    private ValueModel unsaved;
    private CWHeaderInfo headerInfo;
    private SelectionInList<Invoice> invoiceSelection;
    
    private Action cancelAction;
    private Action saveAction;

    private TreeTableModel invoiceTreeTableModel;

    private ButtonListenerSupport buttonListenerSupport;

    private SaveListener saveListener;
    private PropertyChangeListener unsavedListener;
    
    public InvoiceManagementAccountManagementPresentationModel(Account account) {
        this.account = account;

        initModels();
        initEventHandling();
    }
    
    public void initModels() {
        headerInfo = new CWHeaderInfo(
                "Rechnungen",
                "Rechnungsuebersicht",
                CWUtils.loadIcon("cw/accountmanagementmodul/images/invoice.png"),
                CWUtils.loadIcon("cw/accountmanagementmodul/images/invoice.png")
        );
        
        buttonListenerSupport = new ButtonListenerSupport();
        
        cancelAction = new CancelAction("Abbrechen", CWUtils.loadIcon("cw/accountmanagementmodul/images/cancel.png"));
        saveAction = new SaveAction("Hinzufuegen", CWUtils.loadIcon("cw/accountmanagementmodul/images/posting_lightning.png"));

        invoiceSelection = new SelectionInList<Invoice>(account.getInvoices());
    }
    
    public void initEventHandling() {
        unsaved = new ValueHolder();

        saveListener = new SaveListener();
//        getBufferedModel(Posting.PROPERTYNAME_POSTINGENTRYDATE).addValueChangeListener(saveListener);
        
        unsaved.addValueChangeListener(unsavedListener = new PropertyChangeListener() {
            public void propertyChange(PropertyChangeEvent evt) {
                if((Boolean)evt.getNewValue() == true) {
                    saveAction.setEnabled(true);
                } else {
                    saveAction.setEnabled(false);
                }
            }
        });
        unsaved.setValue(false);
    }

    public void dispose() {
        unsaved.removeValueChangeListener(unsavedListener);
    }

    /**
     * Wenn sich ein Document ändert, wird saved auf false gesetzt
     */
    public class SaveListener implements PropertyChangeListener {

        public void propertyChange(PropertyChangeEvent evt) {
            updateState();
        }

        public void updateState() {
            unsaved.setValue(true);
        }
    }

    public void removeButtonListener(ButtonListener listener) {
        buttonListenerSupport.removeButtonListener(listener);
    }

    public void addButtonListener(ButtonListener listener) {
        buttonListenerSupport.addButtonListener(listener);
    }

    public Action getCancelAction() {
        return cancelAction;
    }

    public Action getSaveAction() {
        return saveAction;
    }

    public ValueModel getUnsaved() {
        return unsaved;
    }

    public CWHeaderInfo getHeaderInfo() {
        return headerInfo;
    }

    private class CancelAction
            extends AbstractAction {

        public CancelAction(String name, Icon icon) {
            super(name, icon);
        }

        public void actionPerformed(ActionEvent e) {
            int i = JOptionPane.OK_OPTION;
            if((Boolean)unsaved.getValue() == true) {
               i = JOptionPane.showConfirmDialog(null, "Wollen Sie wirlich abbrechen?", "Abbrechen", JOptionPane.OK_CANCEL_OPTION);
            }
            if(i == JOptionPane.OK_OPTION) {
                buttonListenerSupport.fireButtonPressed(new ButtonEvent(ButtonEvent.EXIT_BUTTON));
            }
        }
    }
    
    private class SaveAction
            extends AbstractAction {
        
        public SaveAction(String name, Icon icon) {
            super(name, icon);
        }

        public void actionPerformed(ActionEvent e) {
            if(!save()) {
                return;
            }
            buttonListenerSupport.fireButtonPressed(new ButtonEvent(ButtonEvent.SAVE_EXIT_BUTTON));
        }
    }

    public boolean save() {
        return true;
    }
    
    public TreeTableModel getInvoiceTreeTableModel() {
        if(invoiceTreeTableModel == null) {
            invoiceTreeTableModel = new InvoiceTreeTableModel(invoiceSelection);
        }
        return invoiceTreeTableModel;
    }

}

package cw.accountmanagementmodul.gui;

import cw.boardingschoolmanagement.app.ButtonEvent;
import cw.boardingschoolmanagement.app.ButtonListener;
import cw.boardingschoolmanagement.app.ButtonListenerSupport;
import cw.boardingschoolmanagement.app.CWUtils;
import com.jgoodies.binding.PresentationModel;
import com.jgoodies.binding.value.ValueHolder;
import com.jgoodies.binding.value.ValueModel;
import cw.accountmanagementmodul.pojo.InvoiceItem;
import cw.boardingschoolmanagement.gui.component.CWView.CWHeaderInfo;
import java.awt.event.ActionEvent;
import java.util.List;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.Icon;
import javax.swing.JOptionPane;
import cw.accountmanagementmodul.pojo.AccountPosting;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;

/**
 *
 * @author CreativeWorkers.at
 */
public class EditInvoiceItemPresentationModel
        extends PresentationModel<InvoiceItem>
{

    private InvoiceItem invoiceItem;
    private ValueModel unsaved;
    private CWHeaderInfo headerInfo;
    
    private Action cancelAction;
    private Action saveAction;

    private ButtonListenerSupport buttonListenerSupport;

    private SaveListener saveListener;
    private PropertyChangeListener unsavedListener;
    
    public EditInvoiceItemPresentationModel(InvoiceItem invoiceItem, CWHeaderInfo headerInfo) {
        super(invoiceItem);
        this.invoiceItem = invoiceItem;
        this.headerInfo = headerInfo;

        initModels();
        initEventHandling();
    }
    
    public void initModels() {
        buttonListenerSupport = new ButtonListenerSupport();
        
        cancelAction = new CancelAction("Abbrechen", CWUtils.loadIcon("cw/accountmanagementmodul/images/cancel.png"));
        saveAction = new SaveAction("Hinzufügen", CWUtils.loadIcon("cw/accountmanagementmodul/images/posting_lightning.png"));

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
        getBufferedModel(AccountPosting.PROPERTYNAME_POSTINGENTRYDATE).removeValueChangeListener(saveListener);
        getBufferedModel(AccountPosting.PROPERTYNAME_AMOUNT).removeValueChangeListener(saveListener);

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

        boolean valid = true;
        List<String> errorMessages = new ArrayList<String>();

        // Check if everything is valid
//        valid = false;
//        errorMessages.addAll(validate);

        if(!valid) {

            StringBuffer buffer = new StringBuffer("<html>");

            for(String message : errorMessages) {
                buffer.append(message);
                buffer.append("<br>");
            }

            buffer.append("</html>");

            JOptionPane.showMessageDialog(null, buffer.toString(), "Fehler und Warnungen", JOptionPane.ERROR_MESSAGE);

            return false;
        }

        triggerCommit();
        unsaved.setValue(false);

        return true;
    }
    
}

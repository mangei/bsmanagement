package cw.accountmanagementmodul.gui;

import cw.boardingschoolmanagement.app.ButtonListener;
import cw.boardingschoolmanagement.app.ButtonListenerSupport;
import cw.boardingschoolmanagement.app.CWUtils;
import com.jgoodies.binding.value.ValueModel;
import cw.accountmanagementmodul.pojo.Account;
import cw.boardingschoolmanagement.gui.component.CWView.CWHeaderInfo;
import javax.swing.Action;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 *
 * @author CreativeWorkers.at
 */
public class AccountOverviewAccountManagementPresentationModel
{

    private Account account;
    private ValueModel unsaved;
    private CWHeaderInfo headerInfo;
    
    private ButtonListenerSupport buttonListenerSupport;

    private SaveListener saveListener;
    private PropertyChangeListener unsavedListener;
    
    public AccountOverviewAccountManagementPresentationModel(Account account) {
        this.account = account;

        initModels();
        initEventHandling();
    }
    
    public void initModels() {
        headerInfo = new CWHeaderInfo(
                "Uebersicht",
                "Uebersicht",
                CWUtils.loadIcon("cw/accountmanagementmodul/images/account.png"),
                CWUtils.loadIcon("cw/accountmanagementmodul/images/account.png")
        );

        buttonListenerSupport = new ButtonListenerSupport();
        
    }
    
    public void initEventHandling() {
    }

    public void dispose() {
        unsaved.removeValueChangeListener(unsavedListener);
    }

    /**
     * Wenn sich ein Document aendert, wird saved auf false gesetzt
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

    public ValueModel getUnsaved() {
        return unsaved;
    }

    public CWHeaderInfo getHeaderInfo() {
        return headerInfo;
    }

    public boolean save() {
        return true;
    }
    
}

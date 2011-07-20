package cw.roommanagementmodul.gui;


import com.jgoodies.binding.PresentationModel;
import com.jgoodies.binding.adapter.ComboBoxAdapter;
import com.jgoodies.binding.list.SelectionInList;
import com.jgoodies.binding.value.ValueHolder;
import com.jgoodies.binding.value.ValueModel;
import cw.boardingschoolmanagement.app.ButtonEvent;
import cw.boardingschoolmanagement.app.ButtonListener;
import cw.boardingschoolmanagement.app.ButtonListenerSupport;
import cw.boardingschoolmanagement.app.CWUtils;
import cw.boardingschoolmanagement.gui.component.CWView.CWHeaderInfo;
import java.awt.event.ActionEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ComboBoxModel;
import javax.swing.JOptionPane;
import cw.roommanagementmodul.pojo.manager.GebuehrenKatManager;
import cw.roommanagementmodul.pojo.Gebuehr;
import cw.roommanagementmodul.pojo.GebuehrenKategorie;

/**
 *
 * @author Dominik
 */
public class EditGebuehrenPresentationModel
        extends PresentationModel<Gebuehr>
{

    private Gebuehr gebuehr;
    private ButtonListenerSupport support;
    private Action saveButtonAction;
    private Action cancelButtonAction;
    private Action saveCancelButtonAction;
    private ValueModel unsaved;
    private SelectionInList<GebuehrenKategorie> gebKatList;
    private CWHeaderInfo headerInfo;
    private SaveListener saveListener;
    private ButtonEnable buttonEnable;

    public EditGebuehrenPresentationModel(Gebuehr gebuehr, CWHeaderInfo header) {
        super(gebuehr);
        this.gebuehr = gebuehr;
        this.headerInfo=header;
        
        initModels();
        initEventHandling();
    }

    private void initModels() {
        saveButtonAction = new SaveAction();
        cancelButtonAction = new CancelAction();
        saveCancelButtonAction = new SaveCancelAction();

        support = new ButtonListenerSupport();
        gebKatList = new SelectionInList(GebuehrenKatManager.getInstance().getAll(), getModel(Gebuehr.PROPERTYNAME_GEBKAT));
    }

    private void initEventHandling() {

        saveListener=new SaveListener();

        unsaved = new ValueHolder();
        unsaved.addValueChangeListener(buttonEnable = new ButtonEnable());
        unsaved.setValue(false);

        getGebKatList().addValueChangeListener(saveListener);
        getBufferedModel(Gebuehr.PROPERTYNAME_NAME).addValueChangeListener(saveListener);
    }

    public void dispose() {
        getGebKatList().removeValueChangeListener(saveListener);
        getBufferedModel(Gebuehr.PROPERTYNAME_NAME).removeValueChangeListener(saveListener);
        unsaved.removeValueChangeListener(buttonEnable);
        gebKatList.release();
        release();
    }

    public Action getSaveButtonAction() {
        return saveButtonAction;
    }

    public Action getCancelButtonAction() {
        return cancelButtonAction;
    }

    public void removeButtonListener(ButtonListener listener) {
        support.removeButtonListener(listener);
    }

    public void addButtonListener(ButtonListener listener) {
        support.addButtonListener(listener);
    }

    public Action getSaveCancelButtonAction() {
        return saveCancelButtonAction;
    }

    public SelectionInList<GebuehrenKategorie> getGebKatList() {
        return gebKatList;
    }

    public ComboBoxModel createKategorieComboModel(SelectionInList bereichList) {
        return new ComboBoxAdapter(this.gebKatList);
    }

    /**
     * @return the headerInfo
     */
    public CWHeaderInfo getHeaderInfo() {
        return headerInfo;
    }

    private class SaveAction
            extends AbstractAction {

        {
            putValue(Action.SMALL_ICON, CWUtils.loadIcon("cw/boardingschoolmanagement/images/save.png"));
        }

        public void actionPerformed(ActionEvent e) {
            saveGebuehr();
            unsaved.setValue(false);
            support.fireButtonPressed(new ButtonEvent(ButtonEvent.SAVE_BUTTON));
        }
    }

    private void saveGebuehr() {
        triggerCommit();
    }


    private class CancelAction
            extends AbstractAction {

        {
            putValue(Action.SMALL_ICON, CWUtils.loadIcon("cw/boardingschoolmanagement/images/cancel.png"));
        }

        public void actionPerformed(ActionEvent e) {
            int i = 1;
            if ((Boolean) unsaved.getValue() == true) {
                Object[] options = {"Speichern", "Nicht Speichern", "Abbrechen"};
                i = JOptionPane.showOptionDialog(null, "Daten wurden geändert. Wollen Sie die Änderungen speichern?", "Speichern", JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null, options, options[0]);
            }
            if (i == 0) {
                saveGebuehr();
            }
            if (i == 0 || i == 1) {
                //         GUIManager.lastView();  // Zur Übersicht wechseln
//                GUIManager.removeView(); // Diese View nicht merken
                support.fireButtonPressed(new ButtonEvent(ButtonEvent.EXIT_BUTTON));
            }
        }
    }

    private class SaveCancelAction
            extends AbstractAction {

        {
            putValue(Action.SMALL_ICON, CWUtils.loadIcon("cw/boardingschoolmanagement/images/save_cancel.png"));
        }

        public void actionPerformed(ActionEvent e) {
            saveGebuehr();
//            GUIManager.lastView();  // Zur Übersicht wechseln
//            GUIManager.removeView(); // Diese View nicht merken
            support.fireButtonPressed(new ButtonEvent(ButtonEvent.SAVE_EXIT_BUTTON));
        }
    }

    public class SaveListener implements PropertyChangeListener {

        public void propertyChange(PropertyChangeEvent evt) {
            updateState();
        }

        public void updateState() {
            unsaved.setValue(true);
        }
    }

   private class ButtonEnable implements PropertyChangeListener{

            public void propertyChange(PropertyChangeEvent evt) {
                if ((Boolean) evt.getNewValue() == true) {
                    getSaveButtonAction().setEnabled(true);
                    getSaveCancelButtonAction().setEnabled(true);
                } else {
                    getSaveButtonAction().setEnabled(false);
                    getSaveCancelButtonAction().setEnabled(false);
                }
            }
        }
}

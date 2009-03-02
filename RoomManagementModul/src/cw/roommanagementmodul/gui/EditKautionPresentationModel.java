/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package cw.roommanagementmodul.gui;


import com.jgoodies.binding.PresentationModel;
import com.jgoodies.binding.value.ValueHolder;
import com.jgoodies.binding.value.ValueModel;
import cw.boardingschoolmanagement.app.ButtonEvent;
import cw.boardingschoolmanagement.app.ButtonListener;
import cw.boardingschoolmanagement.app.ButtonListenerSupport;
import cw.boardingschoolmanagement.app.CWUtils;
import cw.boardingschoolmanagement.interfaces.Disposable;
import java.awt.event.ActionEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JOptionPane;
import cw.roommanagementmodul.pojo.Kaution;

/**
 *
 * @author Dominik
 */
public class EditKautionPresentationModel  extends PresentationModel<Kaution>
            implements Disposable{


     private Kaution kaution;
    private ButtonListenerSupport support;
    private Action saveButtonAction;
    private Action cancelButtonAction;
    private Action saveCancelButtonAction;
    private ValueModel unsaved;
    private String headerText;


    EditKautionPresentationModel(Kaution kaution, String header) {
        super(kaution);
        this.kaution = kaution;
        this.headerText=header;
        initModels();
        initEventHandling();
    }

    private void initEventHandling() {
        unsaved = new ValueHolder();
        unsaved.addValueChangeListener(new PropertyChangeListener() {

            public void propertyChange(PropertyChangeEvent evt) {
                if ((Boolean) evt.getNewValue() == true) {
                    getSaveButtonAction().setEnabled(true);
                    getSaveCancelButtonAction().setEnabled(true);
                } else {
                    getSaveButtonAction().setEnabled(false);
                    getSaveCancelButtonAction().setEnabled(false);
                }
            }
        });
        unsaved.setValue(false);
    }

    private void initModels() {
        saveButtonAction = new SaveAction();
        cancelButtonAction = new CancelAction();
        saveCancelButtonAction = new SaveCancelAction();

        support = new ButtonListenerSupport();
        getBufferedModel(Kaution.PROPERTYNAME_NAME).addValueChangeListener(new SaveListener());
        getBufferedModel(Kaution.PROPERTYNAME_BETRAG).addValueChangeListener(new SaveListener());
    }

    public void removeButtonListener(ButtonListener listener) {
        support.removeButtonListener(listener);
    }

    public void addButtonListener(ButtonListener listener) {
        support.addButtonListener(listener);
    }

   
    /**
     * @return the saveButtonAction
     */
    public Action getSaveButtonAction() {
        return saveButtonAction;
    }

    /**
     * @return the cancelButtonAction
     */
    public Action getCancelButtonAction() {
        return cancelButtonAction;
    }

    /**
     * @return the saveCancelButtonAction
     */
    public Action getSaveCancelButtonAction() {
        return saveCancelButtonAction;
    }

    public void dispose() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public class SaveListener implements PropertyChangeListener {

        public void propertyChange(PropertyChangeEvent evt) {
            updateState();
        }

        public void updateState() {
            unsaved.setValue(true);
        }
    }

    public String getHeaderText() {
        return headerText;
    }

    private class SaveAction
            extends AbstractAction {

        {
            putValue(Action.SMALL_ICON, CWUtils.loadIcon("cw/boardingschoolmanagement/images/disk_16.png"));
        }

        public void actionPerformed(ActionEvent e) {
            saveGebuehrenKat();
            unsaved.setValue(false);
            support.fireButtonPressed(new ButtonEvent(ButtonEvent.SAVE_BUTTON));
        }
    }

    private void saveGebuehrenKat() {
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
                saveGebuehrenKat();
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
            saveGebuehrenKat();
//            GUIManager.lastView();  // Zur Übersicht wechseln
//            GUIManager.removeView(); // Diese View nicht merken
            support.fireButtonPressed(new ButtonEvent(ButtonEvent.SAVE_EXIT_BUTTON));
        }
    }

}

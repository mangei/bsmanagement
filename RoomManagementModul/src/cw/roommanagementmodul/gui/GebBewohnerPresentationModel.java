/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
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
import java.awt.event.ActionEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ComboBoxModel;
import javax.swing.JOptionPane;
import cw.roommanagementmodul.pojo.manager.GebuehrZuordnungManager;
import cw.roommanagementmodul.pojo.manager.GebuehrenManager;
import cw.roommanagementmodul.pojo.Bereich;
import cw.roommanagementmodul.pojo.GebuehrZuordnung;

/**
 *
 * @author Dominik
 */
public class GebBewohnerPresentationModel extends PresentationModel<GebuehrZuordnung> {

    private GebuehrZuordnung gebuehrZuordnung;
    private ButtonListenerSupport support;
    private GebuehrZuordnungManager gebZuordnungManager;
    private GebuehrenManager gebuehrManager;
    private String headerText;
    private Action resetButtonAction;
    private Action saveButtonAction;
    private Action cancelButtonAction;
    private Action saveCancelButtonAction;
    private SelectionInList<Bereich> gebuehrList;
    private ValueModel unsaved;

    public GebBewohnerPresentationModel(GebuehrZuordnung gebuehrZuordnung) {
        super(gebuehrZuordnung);
        this.gebuehrZuordnung = gebuehrZuordnung;
        headerText="-";
        gebZuordnungManager = GebuehrZuordnungManager.getInstance();
        gebuehrManager = GebuehrenManager.getInstance();
        initModels();
        initEventHandling();
    }

    GebBewohnerPresentationModel(GebuehrZuordnung gb, String header) {
        super(gb);
        this.headerText=header;
        this.gebuehrZuordnung = gb;
        gebZuordnungManager = GebuehrZuordnungManager.getInstance();
        gebuehrManager = GebuehrenManager.getInstance();
        initModels();
        initEventHandling();
    }

    private void initEventHandling() {
        unsaved = new ValueHolder();
        unsaved.addValueChangeListener(new PropertyChangeListener() {

            public void propertyChange(PropertyChangeEvent evt) {
                if ((Boolean) evt.getNewValue() == true) {
                    saveButtonAction.setEnabled(true);
                    resetButtonAction.setEnabled(true);
                    saveCancelButtonAction.setEnabled(true);
                } else {
                    saveButtonAction.setEnabled(false);
                    resetButtonAction.setEnabled(false);
                    saveCancelButtonAction.setEnabled(false);
                }
            }
        });
        unsaved.setValue(false);
    }
    

    private void initModels() {
        saveButtonAction = new SaveAction();
        resetButtonAction = new ResetAction();
        cancelButtonAction = new CancelAction();
        saveCancelButtonAction = new SaveCancelAction();

        setGebuehrList((SelectionInList<Bereich>) new SelectionInList(gebuehrManager.getAll(), getModel(GebuehrZuordnung.PROPERTYNAME_GEBUEHR)));
        support = new ButtonListenerSupport();
        getGebuehrList().addValueChangeListener(new SaveListener());
        getBufferedModel(GebuehrZuordnung.PROPERTYNAME_VON).addValueChangeListener(new SaveListener());
        getBufferedModel(GebuehrZuordnung.PROPERTYNAME_BIS).addValueChangeListener(new SaveListener());
        getBufferedModel(GebuehrZuordnung.PROPERTYNAME_ANMERKUNG).addValueChangeListener(new SaveListener());
    }

    public ComboBoxModel createGebuehrComboModel(SelectionInList gebuehrList) {
        return new ComboBoxAdapter(gebuehrList);
    }

    public Action getResetButtonAction() {
        return resetButtonAction;
    }

    public Action getSaveButtonAction() {
        return saveButtonAction;
    }

    public Action getCancelButtonAction() {
        return cancelButtonAction;
    }

    public Action getSaveCancelButtonAction() {
        return saveCancelButtonAction;
    }

    public void removeButtonListener(ButtonListener listener) {
        support.removeButtonListener(listener);
    }

    public void addButtonListener(ButtonListener listener) {
        support.addButtonListener(listener);
    }

    public SelectionInList<Bereich> getGebuehrList() {
        return gebuehrList;
    }

    public void setGebuehrList(SelectionInList<Bereich> gebuehrList) {
        this.gebuehrList = gebuehrList;
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
            saveGebuehrZuordnung();
            unsaved.setValue(false);
            support.fireButtonPressed(new ButtonEvent(ButtonEvent.SAVE_BUTTON));
        }
    }

    private void saveGebuehrZuordnung() {
        triggerCommit();
    }

    private class ResetAction
            extends AbstractAction {

        {
            putValue(Action.SMALL_ICON, CWUtils.loadIcon("cw/boardingschoolmanagement/images/arrow_rotate_anticlockwise.png"));
        }

        public void actionPerformed(ActionEvent e) {
            int i = JOptionPane.showConfirmDialog(null, "Wollen Sie alle Änderungen verwerfen?");
            if (i == JOptionPane.OK_OPTION) {
                // TODO Wartedialog
                resetGebuehrZuordnung();
                unsaved.setValue(false);
                support.fireButtonPressed(new ButtonEvent(ButtonEvent.RESET_BUTTON));
            }
        }
    }

    private void resetGebuehrZuordnung() {
        this.triggerFlush();
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
                saveGebuehrZuordnung();
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
            saveGebuehrZuordnung();
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
}

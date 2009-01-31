/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cw.roommanagementmodul.gui;

import com.jgoodies.binding.PresentationModel;
import com.jgoodies.binding.value.ValueHolder;
import com.jgoodies.binding.value.ValueModel;
import com.toedter.calendar.JDateChooser;
import cw.boardingschoolmanagement.app.ButtonEvent;
import cw.boardingschoolmanagement.app.ButtonListener;
import cw.boardingschoolmanagement.app.ButtonListenerSupport;
import cw.boardingschoolmanagement.app.CWUtils;
import java.awt.event.ActionEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JOptionPane;
import cw.roommanagementmodul.pojo.Tarif;
import cw.roommanagementmodul.pojo.manager.TarifManager;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Dominik
 */
public class EditTarifPresentationModel extends PresentationModel<Tarif> {

    private Tarif tarif;
    private ButtonListenerSupport support;
    private Action resetButtonAction;
    private Action saveButtonAction;
    private Action cancelButtonAction;
    private Action saveCancelButtonAction;
    private ValueModel unsaved;
    private TarifManager tarifManager;
    private String headerText;
    private JDateChooser dcVon;
    private JDateChooser dcBis;
    private boolean cancel;
    private Tarif bisNullTarif = null;
    private boolean bisNull = false;
    private Date vonDateBefore;

    public EditTarifPresentationModel(Tarif tarif) {
        super(tarif);
        this.tarif = tarif;
        tarifManager = TarifManager.getInstance();
        initModels();
        initEventHandling();
    }

    EditTarifPresentationModel(Tarif tarif, String header) {
        super(tarif);
        cancel = true;
        this.tarif = tarif;
        tarifManager = TarifManager.getInstance();
        this.headerText = header;
        initModels();
        initEventHandling();
    }

    private void initEventHandling() {
        unsaved = new ValueHolder();
        unsaved.addValueChangeListener(new PropertyChangeListener() {

            public void propertyChange(PropertyChangeEvent evt) {
                if ((Boolean) evt.getNewValue() == true) {
                    getSaveButtonAction().setEnabled(true);
                    getResetButtonAction().setEnabled(true);
                    getSaveCancelButtonAction().setEnabled(true);
                } else {
                    getSaveButtonAction().setEnabled(false);
                    getResetButtonAction().setEnabled(false);
                    getSaveCancelButtonAction().setEnabled(false);
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


        dcVon = new JDateChooser();
        dcBis = new JDateChooser();
        support = new ButtonListenerSupport();
        getBufferedModel(Tarif.PROPERTYNAME_AB).addValueChangeListener(new SaveListener());
        getBufferedModel(Tarif.PROPERTYNAME_BIS).addValueChangeListener(new SaveListener());
        getBufferedModel(Tarif.PROPERTYNAME_TARIF).addValueChangeListener(new SaveListener());
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

    public void removeButtonListener(ButtonListener listener) {
        support.removeButtonListener(listener);
    }

    public void addButtonListener(ButtonListener listener) {
        support.addButtonListener(listener);
    }

    public Action getSaveCancelButtonAction() {
        return saveCancelButtonAction;
    }

    public String getHeaderText() {
        return headerText;
    }

    /**
     * @return the dcVon
     */
    public JDateChooser getDcVon() {
        return dcVon;
    }

    /**
     * @param dcVon the dcVon to set
     */
    public void setDcVon(JDateChooser dcVon) {
        this.dcVon = dcVon;
    }

    /**
     * @return the dcBis
     */
    public JDateChooser getDcBis() {
        return dcBis;
    }

    /**
     * @param dcBis the dcBis to set
     */
    public void setDcBis(JDateChooser dcBis) {
        this.dcBis = dcBis;
    }

    private class SaveAction
            extends AbstractAction {

        {
            putValue(Action.SMALL_ICON, CWUtils.loadIcon("cw/boardingschoolmanagement/images/disk_16.png"));
        }

        public void actionPerformed(ActionEvent e) {

            saveTarif();
            unsaved.setValue(false);
            support.fireButtonPressed(new ButtonEvent(ButtonEvent.SAVE_BUTTON));

        }
    }

    public int checkChronoDate() {
        Calendar von = dcVon.getCalendar();
        Calendar bis = dcBis.getCalendar();
        int check = 0;
        if (bisNull == true) {
            Calendar vonDateB = Calendar.getInstance();
            vonDateB.setTime(vonDateBefore);
            vonDateB.add(Calendar.DATE, 2);
            if (vonDateB.getTimeInMillis() > von.getTimeInMillis()) {
                return 2;
            }
        }

        if (bis != null) {

            if (dcBis.getDate().getTime() < dcVon.getDate().getTime()) {
                check = 1;
            }
        }

        return check;
    }

    public Date getVonDate() {

        List<Tarif> tarifList = tarifManager.getTarif(getBean().getGebuehr());

        long latestDate = 0;
        Date bisDate = null;
        vonDateBefore = null;
        for (int i = 0; i < tarifList.size(); i++) {
            if (tarifList.get(i).getAb().getTime() > latestDate) {
                latestDate = tarifList.get(i).getAb().getTime();
                bisDate = tarifList.get(i).getBis();
                vonDateBefore = tarifList.get(i).getAb();
                bisNullTarif = tarifList.get(i);
            }
        }

        if (bisDate != null) {
            Calendar cd = Calendar.getInstance();
            cd.setTime(bisDate);
            cd.add(Calendar.DATE, 1);
            bisDate = cd.getTime();
        } else {
            bisNull = true;
        }

        return bisDate;

    }

    private void saveTarif() {
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
                resetTarif();
                unsaved.setValue(false);
                support.fireButtonPressed(new ButtonEvent(ButtonEvent.RESET_BUTTON));
            }
        }
    }

    private void resetTarif() {
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
                saveTarif();
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

            saveTarif();
            support.fireButtonPressed(new ButtonEvent(ButtonEvent.SAVE_EXIT_BUTTON));
            cancel = true;


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

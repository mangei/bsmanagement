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
import cw.boardingschoolmanagement.gui.component.CWComponentFactory;
import cw.boardingschoolmanagement.app.CWUtils;
import cw.boardingschoolmanagement.gui.component.JViewPanel.HeaderInfo;
import cw.boardingschoolmanagement.interfaces.Disposable;
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
import java.util.GregorianCalendar;
import java.util.List;

/**
 *
 * @author Dominik
 */
public class EditTarifPresentationModel extends PresentationModel<Tarif>
                                implements Disposable{

    private Tarif tarif;
    private ButtonListenerSupport support;
    private Action saveButtonAction;
    private Action cancelButtonAction;
    private Action saveCancelButtonAction;
    private ValueModel unsaved;
    private TarifManager tarifManager;
    private String headerText;
    private JDateChooser dcVon;
    private JDateChooser dcBis;
    private Date recommendedDate;
    private Date oldVon,  oldBis,  newVon,  newBis;
    private HeaderInfo headerInfo;
    private SaveListener saveListener;
    private ButtonEnable buttonEnable;

    public EditTarifPresentationModel(Tarif tarif) {
        super(tarif);
        buttonEnable=new ButtonEnable();
        saveListener= new SaveListener();
        this.tarif = tarif;
        tarifManager = TarifManager.getInstance();
        initModels();
        initEventHandling();
    }

    EditTarifPresentationModel(Tarif tarif, HeaderInfo header) {
        super(tarif);
        this.tarif = tarif;
        buttonEnable=new ButtonEnable();
        saveListener= new SaveListener();
        tarifManager = TarifManager.getInstance();
        this.headerText = header.getHeaderText();
        this.headerInfo = header;
        initModels();
        initEventHandling();
    }

    private void initEventHandling() {
        setUnsaved(new ValueHolder());
        getUnsaved().addValueChangeListener(buttonEnable);
        getUnsaved().setValue(false);
    }

    private void initModels() {
        saveButtonAction = new SaveAction();
        cancelButtonAction = new CancelAction();
        saveCancelButtonAction = new SaveCancelAction();



        dcVon = CWComponentFactory.createDateChooser(getBufferedModel(Tarif.PROPERTYNAME_AB));
        dcBis = CWComponentFactory.createDateChooser(getBufferedModel(Tarif.PROPERTYNAME_BIS));


        support = new ButtonListenerSupport();
        getBufferedModel(Tarif.PROPERTYNAME_AB).addValueChangeListener(saveListener);
        getBufferedModel(Tarif.PROPERTYNAME_BIS).addValueChangeListener(saveListener);
        getBufferedModel(Tarif.PROPERTYNAME_TARIF).addValueChangeListener(saveListener);
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

    /**
     * @return the unsaved
     */
    public ValueModel getUnsaved() {
        return unsaved;
    }

    /**
     * @param unsaved the unsaved to set
     */
    public void setUnsaved(ValueModel unsaved) {
        this.unsaved = unsaved;
    }

    /**
     * @return the oldVon
     */
    public Date getOldVon() {
        return oldVon;
    }

    /**
     * @param oldVon the oldVon to set
     */
    public void setOldVon(Date oldVon) {
        this.oldVon = oldVon;
    }

    /**
     * @return the oldBis
     */
    public Date getOldBis() {
        return oldBis;
    }

    /**
     * @param oldBis the oldBis to set
     */
    public void setOldBis(Date oldBis) {
        this.oldBis = oldBis;
    }

    /**
     * @return the newVon
     */
    public Date getNewVon() {
        return newVon;
    }

    /**
     * @param newVon the newVon to set
     */
    public void setNewVon(Date newVon) {
        this.newVon = newVon;
    }

    /**
     * @return the newBis
     */
    public Date getNewBis() {
        return newBis;
    }

    /**
     * @param newBis the newBis to set
     */
    public void setNewBis(Date newBis) {
        this.newBis = newBis;
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

    public void dispose() {
        getUnsaved().removeValueChangeListener(buttonEnable);
        getBufferedModel(Tarif.PROPERTYNAME_AB).removeValueChangeListener(saveListener);
        getBufferedModel(Tarif.PROPERTYNAME_BIS).removeValueChangeListener(saveListener);
        getBufferedModel(Tarif.PROPERTYNAME_TARIF).removeValueChangeListener(saveListener);
        release();
    }

    private class SaveAction
            extends AbstractAction {

        {
            putValue(Action.SMALL_ICON, CWUtils.loadIcon("cw/boardingschoolmanagement/images/save.png"));
        }

        public void actionPerformed(ActionEvent e) {

            //Stunde - Minute - Sekunde auf 0 setzten damit auf Gleichheit geprüft werden kann
            Calendar v = dcVon.getCalendar();
            Calendar b = dcBis.getCalendar();
            if (b != null) {
                Calendar bisC = new GregorianCalendar(b.get(Calendar.YEAR), b.get(Calendar.MONTH), b.get(Calendar.DATE));
                dcBis.setCalendar(bisC);
            }

            if (v != null) {
                Calendar vonC = new GregorianCalendar(v.get(Calendar.YEAR), v.get(Calendar.MONTH), v.get(Calendar.DATE));
                dcVon.setCalendar(vonC);
            }

            if (validateData() == true) {
                saveTarif();
                getUnsaved().setValue(false);
                support.fireButtonPressed(new ButtonEvent(ButtonEvent.SAVE_BUTTON));
            }

        }
    }

    private boolean validateData() {

        if (checkFilledOut() == false) {
            JOptionPane.showMessageDialog(null, "Es müssen alle Felder ausgefüllt werden.");
            return false;
        }

        if (checkChrono() == false) {
            JOptionPane.showMessageDialog(null, "Bis-Datum muss sich chronologisch nach dem Von-Datum befinden.");
            return false;
        }

        if (checkMoreTarifError() == false) {
            int answer = JOptionPane.showConfirmDialog(null, "Diese Daten führen zu einer Überschneidung mehrere Tarife! \nTrotzdem fortfahren?", "Tarif Warnung", JOptionPane.YES_NO_OPTION);
            if (answer == 1) {
                return false;
            }
        }

        if (checkNoTarifError() == false) {
            int answer = JOptionPane.showConfirmDialog(null, "Diese Daten führen zu einem lückenhaften Tarif Bestand! \nTrotzdem fortfahren?", "Tarif Warnung", JOptionPane.YES_NO_OPTION);
            if (answer == 1) {
                return false;
            }
        }
        return true;


    }

    private boolean checkNoTarifError() {

        List<Tarif> sortedTarif = tarifManager.getAllOrderd(tarif.getGebuehr());
        if (sortedTarif.size() > 1) {
            long von = dcVon.getDate().getTime();
            long bis = dcBis.getDate().getTime();

            //Von um einen Tag zurück rechnen
            Calendar cd = Calendar.getInstance();
            cd.setTimeInMillis(von);
            cd.add(Calendar.DATE, -1);
            von = cd.getTime().getTime();

            //Bis um einen Tab nach vorn rechnen
            cd.setTimeInMillis(bis);
            cd.add(Calendar.DATE, 1);
            bis = cd.getTime().getTime();

            boolean check = false;
            for (int i = 0; i < sortedTarif.size(); i++) {
                if (tarif == null || tarif.getId() != sortedTarif.get(i).getId()) {
                    if (von >= sortedTarif.get(i).getAb().getTime() && von <= sortedTarif.get(i).getBis().getTime()) {
                        check = true;
                    }
                }
            }

            for (int i = 0; i < sortedTarif.size(); i++) {
                if (tarif == null || tarif.getId() != sortedTarif.get(i).getId()) {
                    if (bis >= sortedTarif.get(i).getAb().getTime() && bis <= sortedTarif.get(i).getBis().getTime()) {
                        check = true;
                    }
                }
            }
            return check;
        }
        return true;

    }

    private boolean checkMoreTarifError() {

        List<Tarif> sortedTarif = tarifManager.getAllOrderd(tarif.getGebuehr());
        long von = dcVon.getDate().getTime();
        long bis = dcBis.getDate().getTime();

        boolean check = true;
        for (int i = 0; i < sortedTarif.size(); i++) {
            if (tarif == null || tarif.getId() != sortedTarif.get(i).getId()) {
                if (von >= sortedTarif.get(i).getAb().getTime() && von <= sortedTarif.get(i).getBis().getTime()) {
                    check = false;
                    break;
                }
            }

        }

        for (int i = 0; i < sortedTarif.size(); i++) {
            if (tarif == null || tarif.getId() != sortedTarif.get(i).getId()) {
                if (bis >= sortedTarif.get(i).getAb().getTime() && bis <= sortedTarif.get(i).getBis().getTime()) {
                    check = false;
                    break;
                }
            }

        }
        return check;
    }

    //Kontrolliert ob Datumsfelder ausgefüllt sind
    private boolean checkFilledOut() {
        if (dcVon.getDate() == null || dcBis.getDate() == null) {
            return false;
        }
        return true;

    }

    //Kontroliert ob das Bis-Datum chronologisch nach dem Von-Datum ist
    private boolean checkChrono() {
        if (dcBis.getDate().getTime() <= dcVon.getDate().getTime()) {
            return false;
        }
        return true;
    }

    public Date getVonDate() {

        List<Tarif> tarifList = tarifManager.getTarif(getBean().getGebuehr());

        long latestDate = 0;
        recommendedDate = null;
        for (int i = 0; i < tarifList.size(); i++) {
            if (tarifList.get(i).getBis().getTime() > latestDate) {
                latestDate = tarifList.get(i).getBis().getTime();
            }
        }

        if (latestDate != 0) {
            Calendar cd = Calendar.getInstance();
            cd.setTimeInMillis(latestDate);
            cd.add(Calendar.DATE, 1);
            recommendedDate = cd.getTime();
        }
        return recommendedDate;
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
                getUnsaved().setValue(false);
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
            if ((Boolean) getUnsaved().getValue() == true) {
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

            //Stunde - Minute - Sekunde auf 0 setzten damit auf Gleichheit geprüft werden kann
            Calendar v = dcVon.getCalendar();
            Calendar b = dcBis.getCalendar();

            if (b != null) {
                Calendar bisC = new GregorianCalendar(b.get(Calendar.YEAR), b.get(Calendar.MONTH), b.get(Calendar.DATE));
                dcBis.setCalendar(bisC);
            }

            if (v != null) {
                Calendar vonC = new GregorianCalendar(v.get(Calendar.YEAR), v.get(Calendar.MONTH), v.get(Calendar.DATE));
                dcVon.setCalendar(vonC);
            }

            if (validateData() == true) {
                saveTarif();
                support.fireButtonPressed(new ButtonEvent(ButtonEvent.SAVE_EXIT_BUTTON));
            }
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


    public class SaveListener implements PropertyChangeListener {

        public void propertyChange(PropertyChangeEvent evt) {
            updateState();
        }

        public void updateState() {
            getUnsaved().setValue(true);
        }
    }
}

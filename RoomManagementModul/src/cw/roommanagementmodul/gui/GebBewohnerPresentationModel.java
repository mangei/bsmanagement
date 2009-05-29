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
import javax.swing.ComboBoxModel;
import javax.swing.JOptionPane;
import cw.roommanagementmodul.pojo.manager.GebuehrZuordnungManager;
import cw.roommanagementmodul.pojo.manager.GebuehrenManager;
import cw.roommanagementmodul.pojo.Bereich;
import cw.roommanagementmodul.pojo.GebuehrZuordnung;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 *
 * @author Dominik
 */
public class GebBewohnerPresentationModel extends PresentationModel<GebuehrZuordnung>
                implements Disposable{

    private GebuehrZuordnung gebuehrZuordnung;
    private ButtonListenerSupport support;
    private GebuehrZuordnungManager gebZuordnungManager;
    private GebuehrenManager gebuehrManager;
    private String headerText;
    private Action saveButtonAction;
    private Action cancelButtonAction;
    private Action saveCancelButtonAction;
    private SelectionInList<Bereich> gebuehrList;
    private ValueModel unsaved;
    private JDateChooser dcVon;
    private JDateChooser dcBis;
    private HeaderInfo headerInfo;
    private SaveListener saveListener;
    private ButtonEnable buttonEnable;

    public GebBewohnerPresentationModel(GebuehrZuordnung gebuehrZuordnung) {
        super(gebuehrZuordnung);
        saveListener= new  SaveListener();
        this.gebuehrZuordnung = gebuehrZuordnung;
        headerText = "-";
        gebZuordnungManager = GebuehrZuordnungManager.getInstance();
        gebuehrManager = GebuehrenManager.getInstance();
        initModels();
        initEventHandling();
    }

    GebBewohnerPresentationModel(GebuehrZuordnung gb, HeaderInfo header) {
        super(gb);
        saveListener= new  SaveListener();
        this.headerText = header.getHeaderText();
        this.headerInfo = header;
        this.gebuehrZuordnung = gb;
        gebZuordnungManager = GebuehrZuordnungManager.getInstance();
        gebuehrManager = GebuehrenManager.getInstance();
        initModels();
        initEventHandling();
    }

    private void initEventHandling() {
        buttonEnable= new ButtonEnable();
        unsaved = new ValueHolder();
        unsaved.addValueChangeListener(buttonEnable);
        unsaved.setValue(false);
    }

    private void initModels() {
        saveButtonAction = new SaveAction();
        cancelButtonAction = new CancelAction();
        saveCancelButtonAction = new SaveCancelAction();

        dcVon = CWComponentFactory.createDateChooser(getBufferedModel(GebuehrZuordnung.PROPERTYNAME_VON));

        dcBis = CWComponentFactory.createDateChooser(getBufferedModel(GebuehrZuordnung.PROPERTYNAME_BIS));
        setGebuehrList((SelectionInList<Bereich>) new SelectionInList(gebuehrManager.getAll(), getModel(GebuehrZuordnung.PROPERTYNAME_GEBUEHR)));
        support = new ButtonListenerSupport();
        getGebuehrList().addValueChangeListener(saveListener);
        getBufferedModel(GebuehrZuordnung.PROPERTYNAME_VON).addValueChangeListener(saveListener);
        getBufferedModel(GebuehrZuordnung.PROPERTYNAME_BIS).addValueChangeListener(saveListener);
        getBufferedModel(GebuehrZuordnung.PROPERTYNAME_ANMERKUNG).addValueChangeListener(saveListener);
    }

    public ComboBoxModel createGebuehrComboModel(SelectionInList gebuehrList) {
        return new ComboBoxAdapter(gebuehrList);
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
        getGebuehrList().removeValueChangeListener(saveListener);
        getBufferedModel(GebuehrZuordnung.PROPERTYNAME_VON).removeValueChangeListener(saveListener);
        getBufferedModel(GebuehrZuordnung.PROPERTYNAME_BIS).removeValueChangeListener(saveListener);
        getBufferedModel(GebuehrZuordnung.PROPERTYNAME_ANMERKUNG).removeValueChangeListener(saveListener);
        unsaved.removeValueChangeListener(buttonEnable);
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



            if (checkFilledOut() == false) {
                JOptionPane.showMessageDialog(null, "Die Attribute Gebühr und Von müssen mindestens ausgefüllt sein!");
            } else {
                if (checkChrono() == false) {
                    JOptionPane.showMessageDialog(null, "Bis-Datum muss ich chronologisch nach dem Von-Datum befinden.");
                } else {
                    if (checkEinzugAuszug() == false) {
                        JOptionPane.showMessageDialog(null, "Von- oder Bis Datum stimmen nicht mit den Einzugs- oder Auszugs Daten des Bewohners überein.");
                    } else {
                        saveGebuehrZuordnung();
                        unsaved.setValue(false);
                        support.fireButtonPressed(new ButtonEvent(ButtonEvent.SAVE_BUTTON));
                    }
                }

            }



        }
    }

    //Kontrolliert ob Datumsfelder ausgefüllt sind
    private boolean checkFilledOut() {
        if (this.gebuehrList.getSelection() == null || dcVon.getDate() == null) {
            return false;
        }
        return true;

    }

    //Kontroliert ob das Bis-Datum chronologisch nach dem Von-Datum ist
    private boolean checkChrono() {
        if (dcBis.getDate() != null) {
            if (dcBis.getDate().getTime() <= dcVon.getDate().getTime()) {
                return false;
            }
        }

        return true;
    }

    private void saveGebuehrZuordnung() {
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


            if (checkFilledOut() == false) {
                JOptionPane.showMessageDialog(null, "Die Attribute Gebühr und Von müssen mindestens ausgefüllt sein!");
            } else {
                if (checkChrono() == false) {
                    JOptionPane.showMessageDialog(null, "Bis-Datum muss ich chronologisch nach dem Von-Datum befinden.");
                } else {
                    if (checkEinzugAuszug() == false) {
                        JOptionPane.showMessageDialog(null, "Von- oder Bis Datum stimmen nicht mit dem Einzugs- oder Auszugs Datum des Bewohners überein.");
                    } else {
                        saveGebuehrZuordnung();
                        support.fireButtonPressed(new ButtonEvent(ButtonEvent.SAVE_EXIT_BUTTON));
                    }
                }

            }

        }
    }

    public boolean checkEinzugAuszug() {

        this.gebuehrZuordnung.getGebuehr().getName();
        gebuehrZuordnung.getBewohner();
        gebuehrZuordnung.getBewohner().getVon();
        gebuehrZuordnung.getBewohner().getVon().getTime();

        long einzug = this.gebuehrZuordnung.getBewohner().getVon().getTime();
        long von = dcVon.getDate().getTime();

        if (von < einzug) {
            return false;
        }

        Date auszugD = this.gebuehrZuordnung.getBewohner().getBis();
        Date bis = dcBis.getDate();

        if (auszugD == null) {
            return true;
        } else {
            if (bis == null) {
                return true;
            } else {
                if (bis.getTime() > auszugD.getTime()) {
                    return false;
                }
            }
        }
        return true;

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
                    saveButtonAction.setEnabled(true);
                    saveCancelButtonAction.setEnabled(true);
                } else {
                    saveButtonAction.setEnabled(false);
                    saveCancelButtonAction.setEnabled(false);
                }
            }
        }
}

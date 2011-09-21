package cw.roommanagementmodul.gui;

import java.awt.event.ActionEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ComboBoxModel;
import javax.swing.JOptionPane;

import com.jgoodies.binding.adapter.ComboBoxAdapter;
import com.jgoodies.binding.list.SelectionInList;
import com.jgoodies.binding.value.ValueHolder;
import com.jgoodies.binding.value.ValueModel;
import com.toedter.calendar.JDateChooser;

import cw.boardingschoolmanagement.app.ButtonEvent;
import cw.boardingschoolmanagement.app.ButtonListener;
import cw.boardingschoolmanagement.app.ButtonListenerSupport;
import cw.boardingschoolmanagement.app.CWUtils;
import cw.boardingschoolmanagement.gui.CWEditPresentationModel;
import cw.boardingschoolmanagement.gui.component.CWComponentFactory;
import cw.boardingschoolmanagement.gui.component.CWDateChooser;
import cw.boardingschoolmanagement.gui.component.CWView.CWHeaderInfo;
import cw.roommanagementmodul.persistence.Bereich;
import cw.roommanagementmodul.persistence.GebuehrZuordnung;
import cw.roommanagementmodul.persistence.PMGebuehr;
import cw.roommanagementmodul.persistence.PMGebuehrZuordnung;

/**
 *
 * @author Dominik
 */
public class GebBewohnerPresentationModel
	extends CWEditPresentationModel<GebuehrZuordnung>
{

    private GebuehrZuordnung gebuehrZuordnung;
    private ButtonListenerSupport support;
    private PMGebuehrZuordnung gebZuordnungManager;
    private PMGebuehr gebuehrManager;
    private String headerText;
    private Action saveButtonAction;
    private Action cancelButtonAction;
    private Action saveCancelButtonAction;
    private SelectionInList<Bereich> gebuehrList;
    private ValueModel unsaved;
    private CWDateChooser dcVon;
    private CWDateChooser dcBis;
    private CWHeaderInfo headerInfo;
    private SaveListener saveListener;
    private ButtonEnable buttonEnable;

    public GebBewohnerPresentationModel(GebuehrZuordnung gebuehrZuordnung) {
        super(gebuehrZuordnung);
        saveListener= new  SaveListener();
        this.gebuehrZuordnung = gebuehrZuordnung;
        headerText = "-";
        gebZuordnungManager = PMGebuehrZuordnung.getInstance();
        gebuehrManager = PMGebuehr.getInstance();
        initModels();
        initEventHandling();
    }

    GebBewohnerPresentationModel(GebuehrZuordnung gb, CWHeaderInfo header) {
        super(gb);
        saveListener= new  SaveListener();
        this.headerInfo = header;
        this.gebuehrZuordnung = gb;
        gebZuordnungManager = PMGebuehrZuordnung.getInstance();
        gebuehrManager = PMGebuehr.getInstance();
        initModels();
        initEventHandling();
    }

    private void initModels() {
        saveButtonAction = new SaveAction();
        cancelButtonAction = new CancelAction();
        saveCancelButtonAction = new SaveCancelAction();

        dcVon = CWComponentFactory.createDateChooser(getBufferedModel(GebuehrZuordnung.PROPERTYNAME_VON));

        dcBis = CWComponentFactory.createDateChooser(getBufferedModel(GebuehrZuordnung.PROPERTYNAME_BIS));
        gebuehrList = (SelectionInList<Bereich>) new SelectionInList(gebuehrManager.getAll(), getModel(GebuehrZuordnung.PROPERTYNAME_GEBUEHR));
        support = new ButtonListenerSupport();

    }

    private void initEventHandling() {
        saveListener= new  SaveListener();
        getGebuehrList().addValueChangeListener(saveListener);
        getBufferedModel(GebuehrZuordnung.PROPERTYNAME_VON).addValueChangeListener(saveListener);
        getBufferedModel(GebuehrZuordnung.PROPERTYNAME_BIS).addValueChangeListener(saveListener);
        getBufferedModel(GebuehrZuordnung.PROPERTYNAME_ANMERKUNG).addValueChangeListener(saveListener);
        buttonEnable= new ButtonEnable();
        unsaved = new ValueHolder();
        unsaved.addValueChangeListener(buttonEnable);
        unsaved.setValue(false);
    }

    public void dispose() {
        getGebuehrList().removeValueChangeListener(saveListener);
        getBufferedModel(GebuehrZuordnung.PROPERTYNAME_VON).removeValueChangeListener(saveListener);
        getBufferedModel(GebuehrZuordnung.PROPERTYNAME_BIS).removeValueChangeListener(saveListener);
        getBufferedModel(GebuehrZuordnung.PROPERTYNAME_ANMERKUNG).removeValueChangeListener(saveListener);
        unsaved.removeValueChangeListener(buttonEnable);
        release();
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
     * @return the dcBis
     */
    public JDateChooser getDcBis() {
        return dcBis;
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


            //Stunde - Minute - Sekunde auf 0 setzten damit auf Gleichheit geprueft werden kann
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
                JOptionPane.showMessageDialog(null, "Die Attribute Gebuehr und Von muessen mindestens ausgefuellt sein!");
            } else {
                if (checkChrono() == false) {
                    JOptionPane.showMessageDialog(null, "Bis-Datum muss ich chronologisch nach dem Von-Datum befinden.");
                } else {
                    if (checkEinzugAuszug() == false) {
                        JOptionPane.showMessageDialog(null, "Von- oder Bis Datum stimmen nicht mit den Einzugs- oder Auszugs Daten des Bewohners ueberein.");
                    } else {
                        saveGebuehrZuordnung();
                        unsaved.setValue(false);
                        support.fireButtonPressed(new ButtonEvent(ButtonEvent.SAVE_BUTTON));
                    }
                }

            }



        }
    }

    //Kontrolliert ob Datumsfelder ausgefuellt sind
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
                i = JOptionPane.showOptionDialog(null, "Daten wurden geaendert. Wollen Sie die Aenderungen speichern?", "Speichern", JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null, options, options[0]);
            }
            if (i == 0) {
                saveGebuehrZuordnung();
            }
            if (i == 0 || i == 1) {
                //         GUIManager.lastView();  // Zur Uebersicht wechseln
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
            //Stunde - Minute - Sekunde auf 0 setzten damit auf Gleichheit geprueft werden kann
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
                JOptionPane.showMessageDialog(null, "Die Attribute Gebuehr und Von muessen mindestens ausgefuellt sein!");
            } else {
                if (checkChrono() == false) {
                    JOptionPane.showMessageDialog(null, "Bis-Datum muss ich chronologisch nach dem Von-Datum befinden.");
                } else {
                    if (checkEinzugAuszug() == false) {
                        JOptionPane.showMessageDialog(null, "Von- oder Bis Datum stimmen nicht mit dem Einzugs- oder Auszugs Datum des Bewohners ueberein.");
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

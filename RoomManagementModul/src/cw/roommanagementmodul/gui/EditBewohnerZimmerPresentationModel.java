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
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ComboBoxModel;
import javax.swing.JOptionPane;
import cw.roommanagementmodul.pojo.Bereich;
import cw.roommanagementmodul.pojo.Bewohner;
import cw.roommanagementmodul.pojo.Kaution;
import cw.roommanagementmodul.pojo.Zimmer;
import cw.roommanagementmodul.pojo.manager.BereichManager;
import cw.roommanagementmodul.pojo.manager.KautionManager;
import cw.roommanagementmodul.pojo.manager.ZimmerManager;

/**
 *
 * @author Dominik
 */
public class EditBewohnerZimmerPresentationModel extends PresentationModel<Bewohner> {

    private Bewohner bewohner;
    private ButtonListenerSupport support;
    private ValueModel unsaved;
    private Action resetButtonAction;
    private Action saveButtonAction;
    private Action cancelButtonAction;
    private Action saveCancelButtonAction;
    private ZimmerManager zimmerManager;
    private BereichManager bereichManager;
    private KautionManager kautionManager;
    private SelectionInList<Zimmer> zimmerList;
    private SelectionInList<Bereich> bereichList;
    private String headerText;
    private SelectionInList<Kaution> kautionList;
    private KautionStatusItemListener kautionListener;

    EditBewohnerZimmerPresentationModel(Bewohner b) {
        super(b);
        this.bewohner = b;
        zimmerManager = ZimmerManager.getInstance();
        bereichManager = BereichManager.getInstance();
        kautionManager = KautionManager.getInstance();
        initModels();
        initEventHandling();

    }

    EditBewohnerZimmerPresentationModel(Bewohner b, String header) {
        super(b);
        this.bewohner = b;
        zimmerManager = ZimmerManager.getInstance();
        bereichManager = BereichManager.getInstance();
        kautionManager = KautionManager.getInstance();
        this.headerText = header;
        initModels();
        initEventHandling();
    }

    private void initModels() {
        kautionListener= new KautionStatusItemListener();
        saveButtonAction = new SaveAction();
        resetButtonAction = new ResetAction();
        cancelButtonAction = new CancelAction();
        saveCancelButtonAction = new SaveCancelAction();
        support = new ButtonListenerSupport();
        kautionList= new SelectionInList(kautionManager.getAll(),getModel(Bewohner.PROPERTYNAME_KAUTION));
        zimmerList = new SelectionInList(zimmerManager.getAll(), getModel(Bewohner.PROPERTYNAME_ZIMMER));
        getZimmerList().addValueChangeListener(new ZimmerPropertyListener());
        bereichList = new SelectionInList(extractLeafBereich(bereichManager.getBereich()));
        getBereichList().addValueChangeListener(new BereichPropertyListener());
        if (getBewohner().getZimmer() != null) {
            getBereichList().setSelection(getBewohner().getZimmer().getBereich());
            Bereich b = getBewohner().getZimmer().getBereich();
            if (b != null) {
                getZimmerList().setList(b.getZimmerList());
            } else {
                getZimmerList().setList(zimmerManager.getAll());
            }
        }

        getBufferedModel(Bewohner.PROPERTYNAME_ZIMMER).addValueChangeListener(new SaveListener());
        getBufferedModel(Bewohner.PROPERTYNAME_VON).addValueChangeListener(new SaveListener());
        getBufferedModel(Bewohner.PROPERTYNAME_BIS).addValueChangeListener(new SaveListener());
        getBufferedModel(Bewohner.PROPERTYNAME_KAUTION).addValueChangeListener(new SaveListener());
        getBufferedModel(Bewohner.PROPERTYNAME_KAUTIONSTATUS).addValueChangeListener(new SaveListener());
        getZimmerList().addValueChangeListener(new SaveListener());
        getKautionList().addValueChangeListener(new SaveListener());

        updateActionEnablement();
    }

    public void removeButtonListener(ButtonListener listener) {
        support.removeButtonListener(listener);
    }

    public void addButtonListener(ButtonListener listener) {
        support.addButtonListener(listener);
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

    public ComboBoxModel createBereichComboModel(SelectionInList bereichList) {
        return new ComboBoxAdapter(bereichList);
    }

    public ComboBoxModel createZimmerComboModel(SelectionInList zimmerList) {
        return new ComboBoxAdapter(zimmerList);
    }

     public ComboBoxModel createKautionComboModel(SelectionInList kautionList) {
        return new ComboBoxAdapter(kautionList);
    }
    
    private List<Bereich> extractLeafBereich(List<Bereich> l) {
        List<Bereich> extractList = new ArrayList<Bereich>();
        for (int i = 0; i < l.size(); i++) {
            if (l.get(i).getChildBereichList().size() == 0) {
                extractList.add(l.get(i));
            }
        }
        return extractList;
    }

    private void updateActionEnablement() {
    }

    public SelectionInList<Zimmer> getZimmerList() {
        return zimmerList;
    }

    public SelectionInList<Bereich> getBereichList() {
        return bereichList;
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

    public String getHeaderText() {
        return headerText;
    }

    /**
     * @return the bewohner
     */
    public Bewohner getBewohner() {
        return bewohner;
    }

    /**
     * @return the kautionListener
     */
    public KautionStatusItemListener getKautionListener() {
        return kautionListener;
    }

    /**
     * @return the kautionList
     */
    public SelectionInList<Kaution> getKautionList() {
        return kautionList;
    }

    public class SaveListener implements PropertyChangeListener {

        public void propertyChange(PropertyChangeEvent evt) {
            updateState();
        }

        public void updateState() {
            unsaved.setValue(true);
        }
    }

    private class SaveAction
            extends AbstractAction {

        {
            putValue(Action.SMALL_ICON, CWUtils.loadIcon("cw/boardingschoolmanagement/images/disk_16.png"));
        }

        public void actionPerformed(ActionEvent e) {
            saveBewohner();
            unsaved.setValue(false);
            support.fireButtonPressed(new ButtonEvent(ButtonEvent.SAVE_BUTTON));
        }
    }

    private void saveBewohner() {
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
                resetBewohner();
                unsaved.setValue(false);
                support.fireButtonPressed(new ButtonEvent(ButtonEvent.RESET_BUTTON));
            }
        }
    }

    private void resetBewohner() {
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
                saveBewohner();
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
            saveBewohner();
//            GUIManager.lastView();  // Zur Übersicht wechseln
//            GUIManager.removeView(); // Diese View nicht merken
            support.fireButtonPressed(new ButtonEvent(ButtonEvent.SAVE_EXIT_BUTTON));
        }
    }

    public class ZimmerPropertyListener implements PropertyChangeListener {

        public void propertyChange(PropertyChangeEvent evt) {
            Zimmer z = (Zimmer) evt.getNewValue();

            List<Bereich> l = getBereichList().getList();
            List<Zimmer> zList;
            for (int i = 0; i < l.size(); i++) {
                zList = l.get(i).getZimmerList();
                for (int j = 0; j < zList.size(); j++) {
                    if (zList.get(j).equals(z)) {
                        getBereichList().setSelection(l.get(i));
                    }
                }
            }

        }
    }

    public class BereichPropertyListener implements PropertyChangeListener {

        public void propertyChange(PropertyChangeEvent evt) {
            Bereich b = (Bereich) evt.getNewValue();

            if (b != null) {
                getZimmerList().setList(b.getZimmerList());
            } else {
                getZimmerList().setList(zimmerManager.getAll());
            }
        }
    }
 public class KautionStatusItemListener implements ItemListener{

        public void itemStateChanged(ItemEvent e) {

            if(e.getItem().equals("Keine Kaution")){
                getBewohner().setKautionStatus(Bewohner.KEINE_KAUTION);
               unsaved.setValue(true);

            }
            if(e.getItem().equals("Nicht eingezahlt")){
                getBewohner().setKautionStatus(Bewohner.NICHT_EINGEZAHLT);
                unsaved.setValue(true);
            }
            if(e.getItem().equals("Eingezahlt")){
                getBewohner().setKautionStatus(Bewohner.EINGEZAHLT);
                unsaved.setValue(true);

            }
            if(e.getItem().equals("Zurück gezahlt")){
                getBewohner().setKautionStatus(Bewohner.ZURUECK_GEZAHLT);
                unsaved.setValue(true);
            }
        }
    }

}

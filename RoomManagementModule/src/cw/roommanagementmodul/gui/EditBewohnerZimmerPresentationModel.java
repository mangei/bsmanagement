package cw.roommanagementmodul.gui;


import java.awt.event.ActionEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JOptionPane;

import com.jgoodies.binding.list.SelectionInList;
import com.jgoodies.binding.value.ValueHolder;
import com.jgoodies.binding.value.ValueModel;

import cw.boardingschoolmanagement.app.ButtonEvent;
import cw.boardingschoolmanagement.app.ButtonListener;
import cw.boardingschoolmanagement.app.ButtonListenerSupport;
import cw.boardingschoolmanagement.app.CWUtils;
import cw.boardingschoolmanagement.gui.CWEditPresentationModel;
import cw.roommanagementmodul.persistence.Bereich;
import cw.roommanagementmodul.persistence.Bewohner;
import cw.roommanagementmodul.persistence.Kaution;
import cw.roommanagementmodul.persistence.PMBereich;
import cw.roommanagementmodul.persistence.PMKaution;
import cw.roommanagementmodul.persistence.PMZimmer;
import cw.roommanagementmodul.persistence.Zimmer;

/**
 *
 * @author Dominik
 */
public class EditBewohnerZimmerPresentationModel
        extends CWEditPresentationModel<Bewohner>
{

    private Bewohner bewohner;
    private ButtonListenerSupport support;
    private ValueModel unsaved;
    private Action saveButtonAction;
    private Action cancelButtonAction;
    private Action saveCancelButtonAction;
    private PMZimmer zimmerManager;
    private PMBereich bereichManager;
    private PMKaution kautionManager;
    private SelectionInList<Zimmer> zimmerList;
    private SelectionInList<Bereich> bereichList;
    private String headerText;
    private SelectionInList<Kaution> kautionList;
    private SelectionInList kautionStatusSelection;
    private KautionStatusItemListener kautionListener;
    private SaveListener saveListener;
    private PropertyChangeListener unsavedChangeListener;

    public EditBewohnerZimmerPresentationModel(Bewohner b, String header) {
        super(b);
        this.bewohner = b;
        zimmerManager = PMZimmer.getInstance();
        bereichManager = PMBereich.getInstance();
        kautionManager = PMKaution.getInstance();
        this.headerText = header;
        
        initModels();
        initEventHandling();
    }

    private void initModels() {
        kautionListener= new KautionStatusItemListener();
        saveButtonAction = new SaveAction();
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


        kautionStatusSelection = new SelectionInList();
        kautionStatusSelection.getList().add("Keine Kaution");
        kautionStatusSelection.getList().add("Nicht eingezahlt");
        kautionStatusSelection.getList().add("Eingezahlt");
        kautionStatusSelection.getList().add("Zurueck gezahlt");

        if(getBewohner().getKaution()==null){
            
        }else{
            switch(getBewohner().getKautionStatus()){
                case Bewohner.EINGEZAHLT: kautionStatusSelection.setSelectionIndex(2);
                break;
                case Bewohner.EINGEZOGEN: kautionStatusSelection.setSelectionIndex(0);
                break;
                case Bewohner.NICHT_EINGEZAHLT: kautionStatusSelection.setSelectionIndex(1);
                break;
                case Bewohner.ZURUECK_GEZAHLT: kautionStatusSelection.setSelectionIndex(3);
                break;
            }
        }
    }

    private void initEventHandling() {

        kautionStatusSelection.addValueChangeListener(getKautionListener());

        saveListener = new SaveListener();
        getBufferedModel(Bewohner.PROPERTYNAME_ZIMMER).addValueChangeListener(saveListener);
        getBufferedModel(Bewohner.PROPERTYNAME_VON).addValueChangeListener(saveListener);
        getBufferedModel(Bewohner.PROPERTYNAME_BIS).addValueChangeListener(saveListener);
        getBufferedModel(Bewohner.PROPERTYNAME_KAUTION).addValueChangeListener(saveListener);
        getBufferedModel(Bewohner.PROPERTYNAME_KAUTIONSTATUS).addValueChangeListener(saveListener);
        getZimmerList().addValueChangeListener(saveListener);
        getKautionList().addValueChangeListener(saveListener);

        updateActionEnablement();

        unsaved = new ValueHolder();
        unsaved.addValueChangeListener(unsavedChangeListener = new PropertyChangeListener() {

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

    public void dispose() {
        getBufferedModel(Bewohner.PROPERTYNAME_ZIMMER).removeValueChangeListener(saveListener);
        getBufferedModel(Bewohner.PROPERTYNAME_VON).removeValueChangeListener(saveListener);
        getBufferedModel(Bewohner.PROPERTYNAME_BIS).removeValueChangeListener(saveListener);
        getBufferedModel(Bewohner.PROPERTYNAME_KAUTION).removeValueChangeListener(saveListener);
        getBufferedModel(Bewohner.PROPERTYNAME_KAUTIONSTATUS).removeValueChangeListener(saveListener);
        getZimmerList().removeValueChangeListener(saveListener);
        getKautionList().removeValueChangeListener(saveListener);

        kautionStatusSelection.release();

        unsaved.removeValueChangeListener(unsavedChangeListener);

        release();
    }

    public void removeButtonListener(ButtonListener listener) {
        support.removeButtonListener(listener);
    }

    public void addButtonListener(ButtonListener listener) {
        support.addButtonListener(listener);
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

    public SelectionInList getKautionStatusSelection() {
        return kautionStatusSelection;
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
                saveBewohner();
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
            saveBewohner();
//            GUIManager.lastView();  // Zur Uebersicht wechseln
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
 public class KautionStatusItemListener implements PropertyChangeListener{

        public void propertyChange(PropertyChangeEvent e) {

            if(e.getNewValue().equals("Eingezogen")){
                getBewohner().setKautionStatus(Bewohner.EINGEZOGEN);
               unsaved.setValue(true);

            }
            if(e.getNewValue().equals("Nicht eingezahlt")){
                getBewohner().setKautionStatus(Bewohner.NICHT_EINGEZAHLT);
                unsaved.setValue(true);
            }
            if(e.getNewValue().equals("Eingezahlt")){
                getBewohner().setKautionStatus(Bewohner.EINGEZAHLT);
                unsaved.setValue(true);

            }
            if(e.getNewValue().equals("Zurueck gezahlt")){
                getBewohner().setKautionStatus(Bewohner.ZURUECK_GEZAHLT);
                unsaved.setValue(true);
            }
        }

    }

}

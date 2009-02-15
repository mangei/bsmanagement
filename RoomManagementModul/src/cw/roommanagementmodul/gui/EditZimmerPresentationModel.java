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
import java.util.ArrayList;
import java.util.List;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ComboBoxModel;
import javax.swing.JOptionPane;
import cw.roommanagementmodul.pojo.manager.BereichManager;
import cw.roommanagementmodul.pojo.Bereich;
import cw.roommanagementmodul.pojo.Zimmer;

/**
 *
 * @author Dominik
 */
public class EditZimmerPresentationModel extends PresentationModel<Zimmer> {

    private Zimmer zimmer;
    private ButtonListenerSupport support;
    private BereichManager bereichManager;
    private Action resetButtonAction;
    private Action saveButtonAction;
    private Action cancelButtonAction;
    private Action saveCancelButtonAction;
    private SelectionInList<Bereich> bereichList;
    private ValueModel unsaved;
    private String headerText;
    private Bereich selectedBereich;

    public EditZimmerPresentationModel(Zimmer zimmer) {
        super(zimmer);
        this.zimmer = zimmer;
        bereichManager = BereichManager.getInstance();
        initModels();
        initEventHandling();
    }

    EditZimmerPresentationModel(Zimmer zimmer, String header) {
        super(zimmer);
        this.zimmer = zimmer;
        bereichManager =BereichManager.getInstance();
        this.headerText=header;
        initModels();
        initEventHandling();
    }

        EditZimmerPresentationModel(Zimmer zimmer, String header,Bereich selectedBereich) {
        super(zimmer);
        this.selectedBereich=selectedBereich;
        this.zimmer = zimmer;
        bereichManager =BereichManager.getInstance();
        this.headerText=header;
        initModels();
        initEventHandling();
    }


    private void initEventHandling() {
        setUnsaved(new ValueHolder());
        getUnsaved().addValueChangeListener(new PropertyChangeListener() {

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
        getUnsaved().setValue(false);
    }

    private void initModels() {
        saveButtonAction = new SaveAction();
        resetButtonAction = new ResetAction();
        cancelButtonAction = new CancelAction();
        saveCancelButtonAction = new SaveCancelAction();
        List<Bereich> l = getBereichManager().getBereichLeaf();

        bereichList = new SelectionInList(l, getModel(Zimmer.PROPERTYNAME_BEREICH));
        if(selectedBereich!=null){
           bereichList.setSelection(selectedBereich);
        }
        
        support = new ButtonListenerSupport();
        bereichList.addValueChangeListener(new SaveListener());
        getBufferedModel(Zimmer.PROPERTYNAME_NAME).addValueChangeListener(new SaveListener());
        getBufferedModel(Zimmer.PROPERTYNAME_NAME).addValueChangeListener(new PropertyChangeListener() {

            public void propertyChange(PropertyChangeEvent evt) {
                System.out.println("NAMECHANGE");
            }
        });
        getBufferedModel(Zimmer.PROPERTYNAME_ANZBETTEN).addValueChangeListener(new SaveListener());

    }

    private List<Bereich> extractLeafBereich(List<Bereich> l) {
        List<Bereich> extractList = new ArrayList<Bereich>();
        for (int i = 0; i < l.size(); i++) {
            if (l.get(i).getChildBereichList() == null || l.get(i).getChildBereichList().size() == 0) {
                extractList.add(l.get(i));
            }
        }
        return extractList;
    }

    public SelectionInList<Bereich> getBereichList() {
        List<Bereich> l=bereichManager.getBereichLeaf();
        for(int i=0;i<l.size();i++){
            System.out.println(l.get(i).getName());
        }

        SelectionInList selList= new SelectionInList(l,getModel(Zimmer.PROPERTYNAME_BEREICH));
        selList.addValueChangeListener(new SaveListener());
        return selList;
    }

    public ComboBoxModel createParentBereichComboModel(SelectionInList bereichList) {
        return new ComboBoxAdapter(bereichList);
    }

    public void setBereichList(SelectionInList<Bereich> bereichList) {
        this.bereichList = bereichList;
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

    public String getHeaderText() {
        return headerText;
    }

    /**
     * @return the zimmer
     */
    public Zimmer getZimmer() {
        return zimmer;
    }

    /**
     * @param zimmer the zimmer to set
     */
    public void setZimmer(Zimmer zimmer) {
        this.zimmer = zimmer;
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
     * @return the bereichManager
     */
    public BereichManager getBereichManager() {
        return bereichManager;
    }

    /**
     * @param bereichManager the bereichManager to set
     */
    public void setBereichManager(BereichManager bereichManager) {
        this.bereichManager = bereichManager;
    }

    private class SaveAction
            extends AbstractAction {

        {
            putValue(Action.SMALL_ICON, CWUtils.loadIcon("cw/boardingschoolmanagement/images/disk_16.png"));
        }

        public void actionPerformed(ActionEvent e) {
            saveZimmer();
            getUnsaved().setValue(false);
            support.fireButtonPressed(new ButtonEvent(ButtonEvent.SAVE_BUTTON));
        }
    }

    private void saveZimmer() {
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
                resetZimmer();
                getUnsaved().setValue(false);
                support.fireButtonPressed(new ButtonEvent(ButtonEvent.RESET_BUTTON));
            }
        }
    }

    private void resetZimmer() {
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
                saveZimmer();
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
            saveZimmer();
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
            getUnsaved().setValue(true);
        }
    }
}

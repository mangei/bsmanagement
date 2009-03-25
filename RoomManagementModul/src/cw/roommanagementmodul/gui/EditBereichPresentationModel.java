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
import cw.boardingschoolmanagement.gui.component.JViewPanel.HeaderInfo;
import cw.boardingschoolmanagement.interfaces.Disposable;
import java.awt.event.ActionEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ComboBoxModel;
import javax.swing.JOptionPane;
import cw.roommanagementmodul.pojo.manager.BereichManager;
import cw.roommanagementmodul.pojo.Bereich;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Dominik
 */
public class EditBereichPresentationModel extends PresentationModel<Bereich> implements Disposable{

    private BereichManager bereichManager;
    private Action saveButtonAction;
    private Action cancelButtonAction;
    private Action saveCancelButtonAction;
    private ComboBoxModel parentBereichComboModel;
    private SelectionInList<Bereich> bereichList;
    private ValueModel unsaved;
    private Bereich bereich;
    private ButtonListenerSupport support;
    private String headerText;
    private List<Bereich> editBereichList;
    private Bereich vaterBereich;
    private HeaderInfo headerInfo;
    private ButtonEnable buttonEnable;

    public EditBereichPresentationModel(Bereich bereich) {
        super(bereich);
        this.bereich = bereich;
        bereichManager = BereichManager.getInstance();
        initModels();
        initEventHandling();

    }

    EditBereichPresentationModel(Bereich bereich, HeaderInfo header, Bereich vaterBereich) {
        super(bereich);
        this.bereich = bereich;
        this.vaterBereich = vaterBereich;
        bereichManager = BereichManager.getInstance();
        this.headerText = header.getHeaderText();
        this.headerInfo=header;

        initModels();
        initEventHandling();
    }

    private void initEventHandling() {
        setUnsaved(new ValueHolder());
        buttonEnable=new ButtonEnable();
        getUnsaved().addValueChangeListener(buttonEnable);
        getUnsaved().setValue(false);
    }

    private void initModels() {

        saveButtonAction = new SaveAction();
        cancelButtonAction = new CancelAction();
        saveCancelButtonAction = new SaveCancelAction();
        editBereichList = getBereichManager().getBereich();
        checkZimmerBereich(getBereich());
        if (this.headerText.equals("Bereich bearbeiten")) {
            getBereichManager().refreshBereich(getBereich());
            checkBereichList(getBereich().getChildBereichList());
            editBereichList.remove(getBereich());
        }
        setBereichList((SelectionInList<Bereich>) new SelectionInList(editBereichList, getModel(Bereich.PROPERTYNAME_PARENTBEREICH)));


        support = new ButtonListenerSupport();

        getBufferedModel(Bereich.PROPERTYNAME_NAME).addValueChangeListener(new SaveListener());
        getBufferedModel(Bereich.PROPERTYNAME_PARENTBEREICH).addValueChangeListener(new SaveListener());
    //getBufferedModel(Bereich.PROPERTYNAME_CHILDBEREICHLIST).addValueChangeListener(new SaveListener());
    //getBufferedModel(Bereich.PROPERTYNAME_ZIMMERLIST).addValueChangeListener(new SaveListener());
    }

    private void checkZimmerBereich(Bereich bereich) {

        List<Bereich> removeList = new ArrayList<Bereich>();
        for (int i = 0; i < editBereichList.size(); i++) {
            if (editBereichList.get(i).getZimmerList() != null && editBereichList.get(i).getZimmerList().size() > 0) {
                removeList.add(editBereichList.get(i));
            }
        }

        for (int i = 0; i < removeList.size(); i++) {
            editBereichList.remove(removeList.get(i));
        }

    }

    private void checkBereichList(List<Bereich> childList) {

        if (childList != null) {
            for (int i = 0; i < childList.size(); i++) {
                checkBereichList(childList.get(i).getChildBereichList());
                editBereichList.remove(childList.get(i));
            }
        }
    }

    public ComboBoxModel createParentBereichComboModel(SelectionInList bereichList) {
        return new ComboBoxAdapter(bereichList);
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

    public ComboBoxModel getParentBereichComboModel() {
        return parentBereichComboModel;
    }

    public void setParentBereichComboModel(ComboBoxModel parentBereichComboModel) {
        this.parentBereichComboModel = parentBereichComboModel;
    }

    public SelectionInList<Bereich> getBereichList() {
        return bereichList;
    }

    public String getHeaderText() {
        return headerText;
    }

    /**
     * @param bereichList the bereichList to set
     */
    public void setBereichList(SelectionInList<Bereich> bereichList) {
        this.bereichList = bereichList;
    }

    /**
     * @return the bereich
     */
    public Bereich getBereich() {
        return bereich;
    }

    /**
     * @param bereich the bereich to set
     */
    public void setBereich(Bereich bereich) {
        this.bereich = bereich;
    }

    /**
     * @return the vaterBereich
     */
    public Bereich getVaterBereich() {
        return vaterBereich;
    }

    /**
     * @param vaterBereich the vaterBereich to set
     */
    public void setVaterBereich(Bereich vaterBereich) {
        this.vaterBereich = vaterBereich;
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
        release();
    }

    public class SaveListener implements PropertyChangeListener {

        public void propertyChange(PropertyChangeEvent evt) {
            updateState();
        }

        public void updateState() {
            getUnsaved().setValue(true);
        }
    }

    private class SaveAction
            extends AbstractAction {

        {
            putValue(Action.SMALL_ICON, CWUtils.loadIcon("cw/boardingschoolmanagement/images/save.png"));
        }

        public void actionPerformed(ActionEvent e) {

            if (bereichList.getSelection() != null) {
                saveBereich();
                getUnsaved().setValue(false);
                support.fireButtonPressed(new ButtonEvent(ButtonEvent.SAVE_BUTTON));
            } else {
                JOptionPane.showMessageDialog(null, "Es muss ein Übergeordneter Bereich ausgewählt sein!");
            }

        }
    }

    private void saveBereich() {
        triggerCommit();
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
                saveBereich();
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

            if (bereichList.getSelection() != null) {
                saveBereich();
                support.fireButtonPressed(new ButtonEvent(ButtonEvent.SAVE_EXIT_BUTTON));
            } else {
                JOptionPane.showMessageDialog(null, "Es muss ein Übergeordneter Bereich ausgewählt sein!");
            }

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

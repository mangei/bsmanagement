/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package cw.coursemanagementmodul.gui;

import com.jgoodies.binding.PresentationModel;
import com.jgoodies.binding.list.SelectionInList;
import com.jgoodies.binding.value.ValueHolder;
import com.jgoodies.binding.value.ValueModel;
import cw.boardingschoolmanagement.app.ButtonEvent;
import cw.boardingschoolmanagement.app.ButtonListener;
import cw.boardingschoolmanagement.app.ButtonListenerSupport;
import cw.boardingschoolmanagement.app.CWUtils;
import cw.boardingschoolmanagement.gui.component.JViewPanel.HeaderInfo;
import java.awt.event.ActionEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Date;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JOptionPane;
import cw.coursemanagementmodul.pojo.Course;

/**
 *
 * @author André Salmhofer
 */
public class EditCoursePresentationModel extends PresentationModel<Course>{
    //Definieren der Objekte in der oberen Leiste
    private Action saveButtonAction;
    private Action cancelButtonAction;
    private Action resetButtonAction;
    private Action saveAndCloseButtonAction;
    //*******************************************
    
    //Instanz eines Kurses
    private Course course;
    
    //Variable, die feststellt ob die Daten gespeichert sind oder nicht
    private ValueModel unsaved;
    
    private SelectionInList<Course> courseSelection;
    private ButtonListenerSupport support;

    private HeaderInfo headerInfo;
    
    //Konstruktor
    public EditCoursePresentationModel(Course course) {
        super(course);
        this.course = course;
        initModels();
        initEventHandling();

        headerInfo = new HeaderInfo(
                "Kurs bearbeiten",
                "Sie befinden sich im Kursbereich. Hier können Sie Kursdaten eigeben!",
                CWUtils.loadIcon("cw/coursemanagementmodul/images/course.png"),
                CWUtils.loadIcon("cw/coursemanagementmodul/images/course.png"));
    }
    //**************************************************************************
    
    public void initEventHandling(){
        unsaved = new ValueHolder();
        unsaved.addValueChangeListener(new PropertyChangeListener() {
            public void propertyChange(PropertyChangeEvent evt) {
                if((Boolean)evt.getNewValue() == true) {
                    saveButtonAction.setEnabled(true);
                    resetButtonAction.setEnabled(true);
                    saveAndCloseButtonAction.setEnabled(true);
                } else {
                    saveButtonAction.setEnabled(false);
                    resetButtonAction.setEnabled(false);
                    saveAndCloseButtonAction.setEnabled(false);
                }
                System.out.println("evt: " + evt.getNewValue());
            }
        });
        unsaved.setValue(false);
    }
    
    //**************************************************************************
    //Initialisieren der Objekte
    //**************************************************************************
    public void initModels(){
        
        resetButtonAction = new ResetButtonAction("Zurücksetzen");
        saveButtonAction = new SaveButtonAction("Speichern");
        cancelButtonAction = new CancelButtonAction("Schließen");
        saveAndCloseButtonAction = new SaveAndCloseButtonAction("Speichern & Schließen");
        
        support = new ButtonListenerSupport();
        
        getBufferedModel(Course.PROPERTYNAME_BEGINDATE).addValueChangeListener(new SaveListener());
        getBufferedModel(Course.PROPERTYNAME_ENDDATE).addValueChangeListener(new SaveListener());
        getBufferedModel(Course.PROPERTYNAME_NAME).addValueChangeListener(new SaveListener());
    }
    //**************************************************************************
    
    //**************************************************************************
    //Getter- und Setter-Methoden der Aktionen
    //**************************************************************************
    Action getCancelButtonAction() {
        return cancelButtonAction;
    }

    Action getResetButtonAction() {
        return resetButtonAction;
    }

    Action getSaveButtonAction() {
        return saveButtonAction;
    }

    Action getSaveCancelButtonAction() {
        return saveAndCloseButtonAction;
    }
    //**************************************************************************
    
    public void removeButtonListener(ButtonListener listener) {
        support.removeButtonListener(listener);
    }

    public void addButtonListener(ButtonListener listener) {
        support.addButtonListener(listener);
    }
    
    public class SaveListener implements PropertyChangeListener {
        
        public void propertyChange(PropertyChangeEvent evt) {
            updateState();
        }
        
        public void updateState() {
            unsaved.setValue(true);
        }
    }
    
    //**************************************************************************
    //Klasse zum Zurücksetzen eines Kurses
    //**************************************************************************
    private class ResetButtonAction extends AbstractAction{
        {
            putValue( Action.SMALL_ICON, CWUtils.loadIcon("cw/coursemanagementmodul/images/back.png") );
        }
        private ResetButtonAction(String string){
            super(string);
        }
        
        public void actionPerformed(ActionEvent e) {
            System.out.println("RESET");
            
            int i = JOptionPane.showConfirmDialog(null, "Wollen Sie alle Änderungen verwerfen?");
            if(i == JOptionPane.OK_OPTION) {
                // TODO Wartedialog
                resetCourse();
                unsaved.setValue(false);
                support.fireButtonPressed(new ButtonEvent(ButtonEvent.RESET_BUTTON));
            }
        }
    }
    //**************************************************************************
    
    //**************************************************************************
    //Klasse zum Beenden des Eingabeformulars. Wechselt anschließend
    //in die letzte View
    //**************************************************************************
    private class CancelButtonAction extends AbstractAction{
        {
            putValue( Action.SMALL_ICON, CWUtils.loadIcon("cw/coursemanagementmodul/images/cancel.png") );
        }
        
        public void actionPerformed(ActionEvent e){
            int i = 1;
            System.out.println(" --> " + unsaved.getValue());
            if((Boolean)unsaved.getValue() == true) {
                Object[] options = { "Speichern", "Nicht Speichern", "Abbrechen" };
                i = JOptionPane.showOptionDialog(null, "Daten wurden geändert. Wollen Sie die Änderungen speichern?", "Speichern", JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE,null,  options, options[0] );
            }
            if(i == 0) {
                saveCourse();
            }
            if(i == 0 || i == 1) {
                support.fireButtonPressed(new ButtonEvent(ButtonEvent.EXIT_BUTTON));
            }
            System.out.println("CANCEL");
        }
        
        private CancelButtonAction(String string){
            super(string);
        }
    }
    //**************************************************************************
    
    //**************************************************************************
    //Klasse zum Speichern und Schließen des Kursformulars
    //**************************************************************************
    private class SaveAndCloseButtonAction extends AbstractAction{
        {
            putValue( Action.SMALL_ICON, CWUtils.loadIcon("cw/coursemanagementmodul/images/save_cancel.png") );
        }
        private SaveAndCloseButtonAction(String string){
            super(string);
        }
        
        public void actionPerformed(ActionEvent e) {
            System.out.println("SAVE AND CLOSE");
            saveAndCloseCourse();
            support.fireButtonPressed(new ButtonEvent(ButtonEvent.SAVE_EXIT_BUTTON));
        }
        
    }
    //**************************************************************************
    
    //**************************************************************************
    //Klasse zum Speicher eines Kurses
    //**************************************************************************
    private class SaveButtonAction extends AbstractAction{
        {
            putValue( Action.SMALL_ICON, CWUtils.loadIcon("cw/coursemanagementmodul/images/save.png"));
        }
        private SaveButtonAction(String string){
            super(string);
        }
        
        public void actionPerformed(ActionEvent e) {
            if(((Date)getBufferedValue(Course.PROPERTYNAME_BEGINDATE)).compareTo(((Date)getBufferedValue(Course.PROPERTYNAME_ENDDATE))) < 0) {
                saveCourse();
                unsaved.setValue(false);
                support.fireButtonPressed(new ButtonEvent(ButtonEvent.SAVE_BUTTON));
            }
            else{
                JOptionPane.showMessageDialog(null, "Das Beginndatum liegt über dem Enddatum!");
            }
        }
    }
    //**************************************************************************
    
    //**************************************************************************
    //Methoden die in den oben angelegten Klassen zum
    // + Speichern
    // + Zurücksetzen
    // + Speichern & Schließen
    //dienen
    //**************************************************************************
    public void saveCourse(){
        triggerCommit();
    }
    
    public void resetCourse(){
        triggerFlush();
    }
    
    public void saveAndCloseCourse(){
        saveCourse();
    }

    public HeaderInfo getHeaderInfo() {
        return headerInfo;
    }
    //**************************************************************************
    
}

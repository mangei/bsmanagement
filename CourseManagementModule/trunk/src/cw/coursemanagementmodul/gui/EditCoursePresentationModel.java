package cw.coursemanagementmodul.gui;

import com.jgoodies.binding.PresentationModel;
import com.jgoodies.binding.value.ValueHolder;
import com.jgoodies.binding.value.ValueModel;
import cw.boardingschoolmanagement.app.ButtonEvent;
import cw.boardingschoolmanagement.app.ButtonListener;
import cw.boardingschoolmanagement.app.ButtonListenerSupport;
import cw.boardingschoolmanagement.app.CWUtils;
import cw.boardingschoolmanagement.gui.component.CWView.CWHeaderInfo;
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
public class EditCoursePresentationModel
        extends PresentationModel<Course>
{
    //Definieren der Objekte in der oberen Leiste
    private Action saveButtonAction;
    private Action cancelButtonAction;
    private Action saveAndCloseButtonAction;
    //*******************************************
    
    //Instanz eines Kurses
    private Course course;
    
    //Variable, die feststellt ob die Daten gespeichert sind oder nicht
    private ValueModel unsaved;
    
    private ButtonListenerSupport support;

    private CWHeaderInfo headerInfo;

    private SelectionHandler selectionHandler;

    private SaveListener saveListener;
    
    //Konstruktor
    public EditCoursePresentationModel(Course course) {
        super(course);
        this.course = course;

        initModels();
        initEventHandling();
    }
    //**************************************************************************


    //**************************************************************************
    //Initialisieren der Objekte
    //**************************************************************************
    public void initModels(){

        headerInfo = new CWHeaderInfo(
                "Kurs bearbeiten",
                "Sie befinden sich im Kursbereich. Hier können Sie Kursdaten eigeben!",
                CWUtils.loadIcon("cw/coursemanagementmodul/images/course.png"),
                CWUtils.loadIcon("cw/coursemanagementmodul/images/course.png"));

        saveButtonAction = new SaveButtonAction("Speichern");
        cancelButtonAction = new CancelButtonAction("Schließen");
        saveAndCloseButtonAction = new SaveAndCloseButtonAction("Speichern u. Schließen");

        support = new ButtonListenerSupport();
        unsaved = new ValueHolder();
    }
    //**************************************************************************

    public void initEventHandling(){

        saveListener = new SaveListener();
        getBufferedModel(Course.PROPERTYNAME_BEGINDATE).addValueChangeListener(saveListener);
        getBufferedModel(Course.PROPERTYNAME_ENDDATE).addValueChangeListener(saveListener);
        getBufferedModel(Course.PROPERTYNAME_NAME).addValueChangeListener(saveListener);
        
        unsaved.addValueChangeListener(selectionHandler = new SelectionHandler());
        unsaved.setValue(false);
    }

    public void dispose() {
        unsaved.removeValueChangeListener(selectionHandler);
        getBufferedModel(Course.PROPERTYNAME_BEGINDATE).removePropertyChangeListener(saveListener);
        getBufferedModel(Course.PROPERTYNAME_ENDDATE).removePropertyChangeListener(saveListener);
        getBufferedModel(Course.PROPERTYNAME_NAME).removePropertyChangeListener(saveListener);
        release();
    }

    private class SelectionHandler implements PropertyChangeListener{
        public void propertyChange(PropertyChangeEvent evt) {
                if((Boolean)evt.getNewValue() == true) {
                    saveButtonAction.setEnabled(true);
                    saveAndCloseButtonAction.setEnabled(true);
                } else {
                    saveButtonAction.setEnabled(false);
                    saveAndCloseButtonAction.setEnabled(false);
                }
                System.out.println("evt: " + evt.getNewValue());
            }
    }
    
    //**************************************************************************
    //Getter- und Setter-Methoden der Aktionen
    //**************************************************************************
    Action getCancelButtonAction() {
        return cancelButtonAction;
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

    public CWHeaderInfo getHeaderInfo() {
        return headerInfo;
    }
    //**************************************************************************
    
}

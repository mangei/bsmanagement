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
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JOptionPane;
import cw.coursemanagementmodul.pojo.Activity;

/**
 *
 * @author André Salmhofer
 */
public class EditActivityPresentationModel
        extends PresentationModel<Activity>
{
    //Definieren der Objekte in der oberen Leiste
    private Action saveButtonAction;
    private Action cancelButtonAction;
    private Action saveAndCloseButtonAction;
    //*******************************************
    
    //Instanz eines Kurses
    private Activity activity;
    
    //Variable, die feststellt ob die Daten gespeichert sind oder nicht
    private ValueModel unsaved;
    
    private ButtonListenerSupport support;

    private CWHeaderInfo headerInfo;

    private SelectionHandler selectionHandler;

    private SaveListener saveListener;
    
    //Konstruktor
    public EditActivityPresentationModel(Activity activity) {
        super(activity);
        this.activity = activity;
        initModels();
        initEventHandling();
    }
    //**************************************************************************

    //**************************************************************************
    //Initialisieren der Objekte
    //**************************************************************************
    public void initModels(){

        headerInfo = new CWHeaderInfo(
                "Aktivität bearbeiten",
                "Sie befinden sich im Aktivitätsbereich. Hier können Sie Aktivitätsdaten eigeben!",
                CWUtils.loadIcon("cw/coursemanagementmodul/images/activity.png"),
                CWUtils.loadIcon("cw/coursemanagementmodul/images/activity.png"));

        saveButtonAction = new SaveButtonAction("Speichern");
        cancelButtonAction = new CancelButtonAction("Schließen");
        saveAndCloseButtonAction = new SaveAndCloseButtonAction("Speichern u. Schließen");

        support = new ButtonListenerSupport();
        saveListener = new SaveListener();
        getBufferedModel(Activity.PROPERTYNAME_NAME).addValueChangeListener(saveListener);
    }
    //**************************************************************************

    public void initEventHandling(){
        unsaved = new ValueHolder();
        unsaved.addValueChangeListener(selectionHandler = new SelectionHandler());
        unsaved.setValue(false);
    }

    public void dispose() {
        unsaved.removeValueChangeListener(selectionHandler);
        getBufferedModel(Activity.PROPERTYNAME_NAME).removeValueChangeListener(saveListener);
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
    //Klasse zum Zurücksetzen eines Kurses
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
                saveActivity();
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
    //Klasse zum Speichern und Schließen des Aktivität-Formulars
    //**************************************************************************
    private class SaveAndCloseButtonAction extends AbstractAction{
        {
            putValue( Action.SMALL_ICON, CWUtils.loadIcon("cw/coursemanagementmodul/images/save.png") );
        }
        private SaveAndCloseButtonAction(String string){
            super(string);
        }
        
        public void actionPerformed(ActionEvent e) {
            System.out.println("SAVE AND CLOSE");
            saveAndCloseActivity();
            support.fireButtonPressed(new ButtonEvent(ButtonEvent.SAVE_EXIT_BUTTON));
        }
        
    }
    //**************************************************************************
    
    //**************************************************************************
    //Klasse zum Speicher eines Kurses
    //**************************************************************************
    private class SaveButtonAction extends AbstractAction{
        {
            putValue( Action.SMALL_ICON, CWUtils.loadIcon("cw/coursemanagementmodul/images/save_cancel.png"));
        }
        private SaveButtonAction(String string){
            super(string);
        }
        
        public void actionPerformed(ActionEvent e) {
            saveActivity();
            unsaved.setValue(false);
            support.fireButtonPressed(new ButtonEvent(ButtonEvent.SAVE_BUTTON));
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
    public void saveActivity(){
        triggerCommit();
    }
    
    public void resetActivity(){
        triggerFlush();
    }
    
    public void saveAndCloseActivity(){
        saveActivity();
    }

    //**************************************************************************

    public CWHeaderInfo getHeaderInfo() {
        return headerInfo;
    }
}

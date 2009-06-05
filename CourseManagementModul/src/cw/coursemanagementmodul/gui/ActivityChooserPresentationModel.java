package cw.coursemanagementmodul.gui;

import com.jgoodies.binding.PresentationModel;
import com.jgoodies.binding.adapter.AbstractTableAdapter;
import com.jgoodies.binding.list.SelectionInList;
import cw.boardingschoolmanagement.app.ButtonEvent;
import cw.boardingschoolmanagement.app.ButtonListener;
import cw.boardingschoolmanagement.app.ButtonListenerSupport;
import cw.boardingschoolmanagement.app.CWUtils;
import cw.boardingschoolmanagement.gui.component.CWView.CWHeaderInfo;
import cw.boardingschoolmanagement.manager.GUIManager;
import java.awt.event.ActionEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ListModel;
import javax.swing.table.TableModel;
import cw.coursemanagementmodul.pojo.Activity;
import cw.coursemanagementmodul.pojo.manager.ActivityManager;
import java.text.DecimalFormat;

/**
 *
 * @author André Salmhofer (CreativeWorkers)
 */
public class ActivityChooserPresentationModel
        extends PresentationModel<Activity>
{

    //Definieren der Objekte in der oberen Leiste
    private Action addButtonAction;
    private Action cancelButtonAction;
    //********************************************

    //Liste der Kurse
    private SelectionInList<Activity> activitySelection;
    //**********************************************

    private Activity activityItem;

    private ButtonListenerSupport support;
    private ButtonEvent buttonEvent;

    private CWHeaderInfo headerInfo;

    private SelectionHandler selectionHandler;

    public ActivityChooserPresentationModel(Activity activity) {
        super(activity);
        initModels();
        initEventHandling();
    }

    //**************************************************************************
    //Initialisieren der Objekte
    //**************************************************************************
    private void initModels() {
        buttonEvent = new ButtonEvent(ButtonEvent.OK_BUTTON);

        headerInfo = new CWHeaderInfo("Aktivität hinzufügen", "Hier können Sie Aktivitäten " +
                "zum markierten Kurs hinzufügen!",
                CWUtils.loadIcon("cw/coursemanagementmodul/images/activity.png"),
                CWUtils.loadIcon("cw/coursemanagementmodul/images/activity.png"));
        //Anlegen der Aktionen, für die Buttons
        addButtonAction = new AddAction("Hinzufügen");
        cancelButtonAction = new CancelButtonAction("Schließen");
        activitySelection = new SelectionInList<Activity>(ActivityManager.getInstance().getAll());
        support = new ButtonListenerSupport();
    }
    //**************************************************************************

    private void initEventHandling() {
        activitySelection.addValueChangeListener(selectionHandler = new SelectionHandler());
        updateActionEnablement();
    }

    public void dispose() {
//        activitySelection.removeValueChangeListener(selectionHandler);
//        activitySelection.release();
//        release();
    }

    //**************************************************************************
    //Methode, die ein CourseTableModel erzeugt.
    //Die private Klasse befindet sich in der gleichen Datei
    //**************************************************************************
    TableModel createActivityTableModel(SelectionInList<Activity> activitySelection) {
        return new ActivityTableModel(activitySelection);
    }
    //**************************************************************************

    private class SelectionHandler implements PropertyChangeListener{

        public void propertyChange(PropertyChangeEvent evt) {
            updateActionEnablement();
        }
    }

    private void updateActionEnablement() {
        boolean hasSelection = activitySelection.hasSelection();
        addButtonAction.setEnabled(hasSelection);
    }

    //**************************************************************************
    //Private Klasse, die Events behandelt, die das Neuanlegen
    //von Kursen beinhaltet
    //**************************************************************************
    private class AddAction extends AbstractAction{
          {
            putValue( Action.SMALL_ICON, CWUtils.loadIcon("cw/coursemanagementmodul/images/activity_add.png") );
          }

         private AddAction(String name) {
            super(name);
         }

        public void actionPerformed(ActionEvent e) {
            activityItem = activitySelection.getSelection();
            support.fireButtonPressed(buttonEvent);
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
            GUIManager.changeToLastView();
        }

        private CancelButtonAction(String string){
            super(string);
        }
    }
    //**************************************************************************

    //**************************************************************************
    //Getter.- und Setter-Methoden
    //**************************************************************************
    public Action getAddButtonAction() {
        return addButtonAction;
    }

    public void setAddButtonAction(Action newButtonAction) {
        this.addButtonAction = newButtonAction;
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
    //**************************************************************************
    
    public SelectionInList<Activity> getActivitySelection(){
        return activitySelection;
    }

    //**************************************************************************
    //CourseTableModel, das die Art der Anzeige von Kursen regelt.
    //**************************************************************************
    private class ActivityTableModel extends AbstractTableAdapter<Activity> {

        private ListModel listModel;
        private DecimalFormat numberFormat;

        public ActivityTableModel(ListModel listModel) {
            super(listModel);
            this.listModel = listModel;
            numberFormat = new DecimalFormat("#0.00");
        }

        @Override
        public int getColumnCount() {
            return 3;
        }

        @Override
        public String getColumnName(int column) {
            switch (column) {
                case 0:
                    return "Aktivitätsname";
                case 1:
                    return "Aktivitätsbeschreibung";
                case 2:
                    return "Preis";
                default:
                    return "";
            }
        }

        @Override
        public boolean isCellEditable(int rowIndex, int columnIndex) {
            return false;
        }

        public Object getValueAt(int rowIndex, int columnIndex) {
            Activity activity = (Activity) listModel.getElementAt(rowIndex);
            switch (columnIndex) {
                case 0:
                    return activity.getName();
                case 1:
                    return activity.getDescription();
                case 2:
                    return "€ " + numberFormat.format(activity.getPrice());
                default:
                    return "";
            }
        }
    }

    public Activity getActivityItem() {
        return activityItem;
    }

    public void setActivityItem(Activity activityItem) {
        this.activityItem = activityItem;
    }

    public CWHeaderInfo getHeaderInfo() {
        return headerInfo;
    }
    //**************************************************************************

}

package cw.coursemanagementmodul.gui;

import com.jgoodies.binding.adapter.AbstractTableAdapter;
import com.jgoodies.binding.list.SelectionInList;
import cw.boardingschoolmanagement.app.ButtonEvent;
import cw.boardingschoolmanagement.app.ButtonListener;
import cw.boardingschoolmanagement.app.CWUtils;
import cw.boardingschoolmanagement.gui.component.CWView.CWHeaderInfo;
import cw.boardingschoolmanagement.manager.GUIManager;
import java.awt.event.ActionEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.EventObject;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JOptionPane;
import javax.swing.ListModel;
import javax.swing.table.TableModel;
import cw.coursemanagementmodul.pojo.Activity;
import cw.coursemanagementmodul.pojo.CourseParticipant;
import cw.coursemanagementmodul.pojo.manager.ActivityManager;
import cw.coursemanagementmodul.pojo.manager.CourseParticipantManager;
import java.text.DecimalFormat;
import java.util.List;

/**
 *
 * @author André Salmhofer (CreativeWorkers)
 */
public class ActivityPresentationModel
{
    //Manager, der verschieden Methoden, wie beispielsweise
    //New, Edit, Delete enthalten
    //*********************************************************************************
    
    //Definieren der Objekte in der oberen Leiste
    private Action newButtonAction;
    private Action editButtonAction;
    private Action deleteButtonAction;
    //********************************************
    
    //Liste der Kurse
    private SelectionInList<Activity> activitySelection;
    //**********************************************

    private CWHeaderInfo headerInfo;

    private SelectionHandler selectionHandler;
    
    public ActivityPresentationModel() {
        initModels();
        initEventHandling();
    }
    
    //**************************************************************************
    //Initialisieren der Objekte
    //**************************************************************************
    private void initModels() {
        headerInfo = new CWHeaderInfo(
                "Aktivität",
                "Sie befinden sich im Aktivitätsbereich. Hier können Sie Aktivitäten anlegen",
                CWUtils.loadIcon("cw/coursemanagementmodul/images/activity.png"),
                CWUtils.loadIcon("cw/coursemanagementmodul/images/activity.png"));
        //Anlegen der Aktionen, für die Buttons
        newButtonAction = new NewAction("Neu");
        editButtonAction = new EditAction("Bearbeiten");
        deleteButtonAction = new DeleteAction("Löschen");
        
        activitySelection = new SelectionInList<Activity>(ActivityManager.getInstance().getAll());
    }
    //**************************************************************************

    private void initEventHandling() {
        activitySelection.addValueChangeListener(selectionHandler = new SelectionHandler());
        updateActionEnablement();
    }

    public void dispose() {
        activitySelection.removeValueChangeListener(selectionHandler);
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

    
    //**************************************************************************
    //Private Klasse, die Events behandelt, die das Neuanlegen
    //von Kursen beinhaltet
    //**************************************************************************
    private class NewAction extends AbstractAction{
          {
            putValue( Action.SMALL_ICON, CWUtils.loadIcon("cw/coursemanagementmodul/images/activity_add.png") );
          }
         
         private NewAction(String name) {
            super(name);
         }

        public void actionPerformed(ActionEvent e) {
            GUIManager.getInstance().lockMenu();

            final Activity activity = new Activity();
            final EditActivityPresentationModel model = new EditActivityPresentationModel(activity);
            final EditActivityView editView = new EditActivityView(model);

            model.addButtonListener(new ButtonListener() {
            
            boolean activityAlreadyCreated = false;

            public void buttonPressed(ButtonEvent evt) {
                if(evt.getType() == ButtonEvent.SAVE_BUTTON || evt.getType() == ButtonEvent.SAVE_EXIT_BUTTON) {
                    ActivityManager.getInstance().save(activity);
                    if(activityAlreadyCreated) {
                        GUIManager.getStatusbar().setTextAndFadeOut("Aktivität wurde aktualisiert.");
                    } else {
                        GUIManager.getStatusbar().setTextAndFadeOut("Aktivität wurde erstellt.");
                        activitySelection.getList().add(activity);
                        activitySelection.fireSelectedContentsChanged();
                        activityAlreadyCreated = true;
                    }
                }
                
                if(evt.getType() == ButtonEvent.EXIT_BUTTON || evt.getType() == ButtonEvent.SAVE_EXIT_BUTTON) {
                    GUIManager.getInstance().unlockMenu();
                    model.removeButtonListener(this);
                    GUIManager.changeToLastView();
                }
              }
            });
            GUIManager.changeView(editView, true);
        }
    }
    //**************************************************************************
    
    //**************************************************************************
    //Private Klasse, die Events behandelt, die das Editieren
    //von Kursen beinhaltet
    //**************************************************************************
    private class EditAction
            extends AbstractAction {

        {
            putValue(Action.SMALL_ICON, CWUtils.loadIcon("cw/coursemanagementmodul/images/activity_edit.png"));
        }

        private EditAction(String name) {
            super(name);
        }

        public void actionPerformed(ActionEvent e) {
            editSelectedItem(e);
        }
    }
    //**************************************************************************
    
    //**************************************************************************
    //Private Klasse, die Events behandelt, die das Löschen
    //von Kursen beinhaltet
    //**************************************************************************
    private class DeleteAction
            extends AbstractAction {

        {
            putValue(Action.SMALL_ICON, CWUtils.loadIcon("cw/coursemanagementmodul/images/activity_delete.png"));
        }

        private DeleteAction(String name) {
            super(name);
        }

        public void actionPerformed(ActionEvent e) {
            Activity activity = activitySelection.getSelection();
            List<CourseParticipant> courseParts = CourseParticipantManager.getInstance().getAll(activity);
            
            if(courseParts.size() > 0){
                    JOptionPane.showMessageDialog(null, "<html>Löschen der Aktivität "
                            + activity.getName() + " nicht möglich!<br/>"
                            + "Diese Aktivität wird noch von ein oder mehreren Kunden verwendet!</html>");
            }
            else{
                int ret = JOptionPane.showConfirmDialog(null, "Wollen Sie die Aktivität wirklich löschen?");
                if(ret == JOptionPane.OK_OPTION){
                    activitySelection.getList().remove(activity);
                    ActivityManager.getInstance().delete(activity);

                    GUIManager.setLoadingScreenText("Aktivität wird gelöscht...");
                    GUIManager.setLoadingScreenVisible(true);

                    GUIManager.setLoadingScreenVisible(false);
                    GUIManager.getStatusbar().setTextAndFadeOut("Die Aktivität wurde gelöscht!");
                }
            }
        }
    }
    //**************************************************************************
    
    //**************************************************************************
    //Getter.- und Setter-Methoden
    //**************************************************************************
    public Action getDeleteButtonAction() {
        return deleteButtonAction;
    }

    public void setDeleteButtonAction(Action deleteAction) {
        this.deleteButtonAction = deleteAction;
    }

    public Action getEditButtonAction() {
        return editButtonAction;
    }

    public Action getNewButtonAction() {
        return newButtonAction;
    }

    public void setNewButtonAction(Action newButtonAction) {
        this.newButtonAction = newButtonAction;
    }
    //**************************************************************************
    
    public SelectionInList<Activity> getActivitySelection(){
        return activitySelection;
    }
    
    //**************************************************************************
    //CourseTableModel, das die Art der Anzeige von Kursen regelt.
    //**************************************************************************
    private class ActivityTableModel extends AbstractTableAdapter<Activity> {
        private DecimalFormat numberFormat;
        private ListModel listModel;

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
                    return "Aktivität-Name";
                case 1:
                    return "Beschreibung";
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
    //**************************************************************************
    
    private void updateActionEnablement() {
        boolean hasSelection = activitySelection.hasSelection();
        editButtonAction.setEnabled(hasSelection);
        deleteButtonAction.setEnabled(hasSelection);
    }
    
    //**************************************************************************
    //Regelt das Editieren (Verändern) von Kursen
    //(Doppelclick oder Ändern-Button)
    //**************************************************************************
    private void editSelectedItem(EventObject e) {
        GUIManager.setLoadingScreenText("Kunde wird geladen...");
        GUIManager.setLoadingScreenVisible(true);
        final Activity activity = activitySelection.getSelection();
        final EditActivityPresentationModel model = new EditActivityPresentationModel(activity);
        final EditActivityView editView = new EditActivityView(model);
        model.addButtonListener(new ButtonListener() {

            public void buttonPressed(ButtonEvent evt) {
                if (evt.getType() == ButtonEvent.SAVE_BUTTON || evt.getType() == ButtonEvent.SAVE_EXIT_BUTTON) {
                    ActivityManager.getInstance().save(activity);
                    GUIManager.getStatusbar().setTextAndFadeOut("Aktivität wurde aktualisiert.");
                }
                if (evt.getType() == ButtonEvent.EXIT_BUTTON || evt.getType() == ButtonEvent.SAVE_EXIT_BUTTON) {
                    model.removeButtonListener(this);
                    GUIManager.changeToLastView();
                }
            }
        });
        GUIManager.changeView(editView, true);
        GUIManager.setLoadingScreenVisible(false);
    }

    public CWHeaderInfo getHeaderInfo() {
        return headerInfo;
    }
}

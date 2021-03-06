package cw.coursemanagementmodul.gui;

import java.awt.event.ActionEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.text.DecimalFormat;
import java.util.EventObject;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JOptionPane;
import javax.swing.ListModel;
import javax.swing.table.TableModel;

import com.jgoodies.binding.adapter.AbstractTableAdapter;
import com.jgoodies.binding.list.SelectionInList;

import cw.boardingschoolmanagement.app.ButtonEvent;
import cw.boardingschoolmanagement.app.ButtonListener;
import cw.boardingschoolmanagement.app.CWUtils;
import cw.boardingschoolmanagement.gui.component.CWView.CWHeaderInfo;
import cw.boardingschoolmanagement.manager.GUIManager;
import cw.coursemanagementmodul.pojo.Course;
import cw.coursemanagementmodul.pojo.CourseParticipant;
import cw.coursemanagementmodul.pojo.manager.CourseManager;
import cw.coursemanagementmodul.pojo.manager.CourseParticipantManager;

/**
 *
 * @author André Salmhofer (CreativeWorkers)
 */
public class CoursePresentationModel
{
    //Definieren der Objekte in der oberen Leiste
    private Action newButtonAction;
    private Action editButtonAction;
    private Action deleteButtonAction;
    private Action detailButtonAction;
    //********************************************
    
    //Liste der Kurse
    private SelectionInList<Course> courseSelection;
    //**********************************************

    private SelectionHandler selectionHandler;

    private CWHeaderInfo headerInfo;
    
    public CoursePresentationModel() {
        initModels();
        initEventHandling();
    }
    
    //**************************************************************************
    //Initialisieren der Objekte
    //**************************************************************************
    public void initModels() {
        headerInfo = new CWHeaderInfo(
                "Kurs",
                "Sie befinden sich im Kursverwaltungsbereich. Hier koennen Sie Kurse anlegen",
                CWUtils.loadIcon("cw/coursemanagementmodul/images/course.png"),
                CWUtils.loadIcon("cw/coursemanagementmodul/images/course.png"));
        
        //Anlegen der Aktionen, fuer die Buttons
        newButtonAction = new NewAction("Neu");
        editButtonAction = new EditAction("Bearbeiten");
        deleteButtonAction = new DeleteAction("Loeschen");
        detailButtonAction = new DetailAction("Detailansicht");
        
        courseSelection = new SelectionInList<Course>(CourseManager.getInstance().getAll());
    }
    //**************************************************************************

    private void initEventHandling() {
        courseSelection.addValueChangeListener(selectionHandler = new SelectionHandler());
        updateActionEnablement();
    }

    public void dispose() {
        courseSelection.removeValueChangeListener(selectionHandler);
        courseSelection.release();
    }

    //**************************************************************************
    //Methode, die ein CourseTableModel erzeugt.
    //Die private Klasse befindet sich in der gleichen Datei
    //**************************************************************************
    TableModel createCourseTableModel(SelectionInList<Course> courseSelection) {
        return new CourseTableModel(courseSelection);
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
            putValue( Action.SMALL_ICON, CWUtils.loadIcon("cw/coursemanagementmodul/images/course_add.png") );
          }
         
         private NewAction(String name) {
            super(name);
         }

        public void actionPerformed(ActionEvent e) {    
            GUIManager.getInstance().lockMenu();
            final Course c = new Course();
            final EditCoursePresentationModel model = new EditCoursePresentationModel(c);
            final EditCourseView editView = new EditCourseView(model);
            model.addButtonListener(new ButtonListener() {
                
            boolean courseAlreadyCreated = false;

            public void buttonPressed(ButtonEvent evt) {
                if(evt.getType() == ButtonEvent.SAVE_BUTTON || evt.getType() == ButtonEvent.SAVE_EXIT_BUTTON) {
                    CourseManager.getInstance().save(c);
                    if(courseAlreadyCreated) {
                        GUIManager.getStatusbar().setTextAndFadeOut("Kurs wurde aktualisiert.");
                    } else {
                        GUIManager.getStatusbar().setTextAndFadeOut("Kurs wurde erstellt.");
                        courseSelection.getList().add(c);
                        courseSelection.fireSelectedContentsChanged();
                        courseAlreadyCreated = true;
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
            putValue(Action.SMALL_ICON, CWUtils.loadIcon("cw/coursemanagementmodul/images/course_edit.png"));
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
    //Private Klasse, die Events behandelt, die das Loeschen
    //von Kursen beinhaltet
    //**************************************************************************
    private class DeleteAction
            extends AbstractAction {

        {
            putValue(Action.SMALL_ICON, CWUtils.loadIcon("cw/coursemanagementmodul/images/course_delete.png"));
        }

        private DeleteAction(String name) {
            super(name);
        }

        public void actionPerformed(ActionEvent e) {
                Course c = courseSelection.getSelection();
                
                List<CourseParticipant> courseParts = CourseParticipantManager.getInstance().getAll(c);
                if(courseParts.size() > 0){
                    JOptionPane.showMessageDialog(null, "<html>Loeschen des Kurses "
                            + c.getName() + " nicht moeglich!<br/>"
                            + "Der Kurs wird noch von ein oder mehreren Kunden verwendet!</html>");
                }
                else{
                    int ret = JOptionPane.showConfirmDialog(null, "Wollen Sie den Kurs wirklich loeschen?");
                    if(ret == JOptionPane.OK_OPTION){
                        courseSelection.getList().remove(c);
                        CourseManager.getInstance().delete(c);

                        GUIManager.setLoadingScreenText("Kurs wird geloescht...");
                        GUIManager.setLoadingScreenVisible(true);
                        
                        GUIManager.setLoadingScreenVisible(false);
                        GUIManager.getStatusbar().setTextAndFadeOut("Der Kurs wurde geloescht!");
                    }
                }
            }
    }
    //**************************************************************************
    
    //**************************************************************************
    //Private Klasse, die Events behandelt, die das Loeschen
    //von Kursen beinhaltet
    //**************************************************************************
    private class DetailAction
            extends AbstractAction {

        {
            putValue(Action.SMALL_ICON, CWUtils.loadIcon("cw/coursemanagementmodul/images/detail.png"));
        }

        private DetailAction(String name) {
            super(name);
        }

        public void actionPerformed(ActionEvent e) {
            GUIManager.getInstance().lockMenu();
            GUIManager.changeView(new CourseDetailView(new CourseDetailPresentationModel(courseSelection.getSelection())), true);
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

    public Action getDetailButtonAction() {
        return detailButtonAction;
    }
    
    public void setNewButtonAction(Action newButtonAction) {
        this.newButtonAction = newButtonAction;
    }
    //**************************************************************************
    
    public SelectionInList<Course> getCourseSelection(){
        return courseSelection;
    }
    
    //**************************************************************************
    //CourseTableModel, das die Art der Anzeige von Kursen regelt.
    //**************************************************************************
    private class CourseTableModel extends AbstractTableAdapter<Course> {
        private DecimalFormat numberFormat;
        private ListModel listModel;

        public CourseTableModel(ListModel listModel) {
            super(listModel);
            this.listModel = listModel;
            numberFormat = new DecimalFormat("#0.00");
        }

        @Override
        public int getColumnCount() {
            return 4;
        }

        @Override
        public String getColumnName(int column) {
            switch (column) {
                case 0:
                    return "Kursname";
                case 1:
                    return "Von";
                case 2:
                    return "Bis";
                case 3:
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
            Course course = (Course) listModel.getElementAt(rowIndex);
            switch (columnIndex) {
                case 0:
                    return course.getName();
                case 1:
                    return course.getBeginDate();
                case 2:
                    return course.getEndDate();
                case 3:
                    return "€ " + numberFormat.format(course.getPrice());
                default:
                    return "";
            }
        }
    }
    //**************************************************************************
    
    private void updateActionEnablement() {
        boolean hasSelection = courseSelection.hasSelection();
        editButtonAction.setEnabled(hasSelection);
        deleteButtonAction.setEnabled(hasSelection);
        detailButtonAction.setEnabled(hasSelection);
    }
    
    //**************************************************************************
    //Regelt das Editieren (Veraendern) von Kursen
    //(Doppelclick oder Aendern-Button)
    //**************************************************************************
    private void editSelectedItem(EventObject e) {
        GUIManager.getInstance().lockMenu();
        GUIManager.setLoadingScreenText("Kunde wird geladen...");
        GUIManager.setLoadingScreenVisible(true);

        final Course c = courseSelection.getSelection();
        final EditCoursePresentationModel model = new EditCoursePresentationModel(c);
        final EditCourseView editView = new EditCourseView(model);
        model.addButtonListener(new ButtonListener() {

            public void buttonPressed(ButtonEvent evt) {
                if (evt.getType() == ButtonEvent.SAVE_BUTTON || evt.getType() == ButtonEvent.SAVE_EXIT_BUTTON) {
                    CourseManager.getInstance().save(c);
                    GUIManager.getStatusbar().setTextAndFadeOut("Kurs wurde aktualisiert.");
                }
                if (evt.getType() == ButtonEvent.EXIT_BUTTON || evt.getType() == ButtonEvent.SAVE_EXIT_BUTTON) {
                    GUIManager.getInstance().unlockMenu();
                    model.removeButtonListener(this);
                    GUIManager.changeToLastView();
                }
            }
        });
        GUIManager.changeView(editView, true);
        GUIManager.setLoadingScreenVisible(false);

    }
    //**************************************************************************

    public CWHeaderInfo getHeaderInfo(){
        return headerInfo;
    }
}

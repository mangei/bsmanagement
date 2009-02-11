/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package cw.coursemanagementmodul.gui;

import com.jgoodies.binding.adapter.AbstractTableAdapter;
import com.jgoodies.binding.list.SelectionInList;
import cw.boardingschoolmanagement.app.ButtonEvent;
import cw.boardingschoolmanagement.app.ButtonListener;
import cw.boardingschoolmanagement.app.CWUtils;
import cw.boardingschoolmanagement.manager.GUIManager;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.EventObject;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JOptionPane;
import javax.swing.ListModel;
import javax.swing.SwingUtilities;
import javax.swing.table.TableModel;
import cw.coursemanagementmodul.pojo.CourseParticipant;
import cw.coursemanagementmodul.pojo.manager.CourseParticipantManager;

/**
 *
 * @author André Salmhofer
 */
public class CoursePartPresentationModel{
    //Manager, der verschieden Methoden, wie beispielsweise
    //New, Edit, Delete enthalten
    //*********************************************************************************
    
    //Definieren der Objekte in der oberen Leiste
    private Action newButtonAction;
    private Action editButtonAction;
    private Action deleteButtonAction;
    private Action detailAction;
    //********************************************
    
    //Liste der Kurse
    private SelectionInList<CourseParticipant> coursePartSelection;
    //**********************************************
    
    public CoursePartPresentationModel() {
        initModels();
        initEventHandling();
    }
    
    //**************************************************************************
    //Initialisieren der Objekte
    //**************************************************************************
    public void initModels() {
        //Anlegen der Aktionen, für die Buttons
        newButtonAction = new NewAction("Neu");
        editButtonAction = new EditAction("Bearbeiten");
        deleteButtonAction = new DeleteAction("Löschen");
        detailAction = new DetailAction("Detailansicht");
        
        coursePartSelection = new SelectionInList<CourseParticipant>(CourseParticipantManager.getInstance().getAll());
        updateActionEnablement();
    }
    //**************************************************************************
    
    //**************************************************************************
    //Methode, die ein CourseTableModel erzeugt.
    //Die private Klasse befindet sich in der gleichen Datei
    //**************************************************************************
    TableModel createCoursePartTableModel(SelectionInList<CourseParticipant> coursePartSelection) {
        return new CoursePartTableModel(coursePartSelection);
    }
    //**************************************************************************
     
    
    private void initEventHandling() {
        coursePartSelection.addPropertyChangeListener(
                SelectionInList.PROPERTYNAME_SELECTION_EMPTY,
                new SelectionEmptyHandler());
    }
    
    //**************************************************************************
    //Private Klasse, die Events behandelt, die das Neuanlegen
    //von Kursen beinhaltet
    //**************************************************************************
    private class NewAction extends AbstractAction{
          {
            putValue( Action.SMALL_ICON, CWUtils.loadIcon("cw/coursemanagementmodul/images/add.png") );
          }
         
         private NewAction(String name) {
            super(name);
         }

        public void actionPerformed(ActionEvent e) {
            final CourseParticipant c = new CourseParticipant();
            final EditCoursePartPresentationModel model = new EditCoursePartPresentationModel(c,null);
            final EditCoursePartView editView = new EditCoursePartView(model);
            model.addButtonListener(new ButtonListener() {
                
            boolean courseAlreadyCreated = false;

            public void buttonPressed(ButtonEvent evt) {
                if(evt.getType() == ButtonEvent.SAVE_BUTTON || evt.getType() == ButtonEvent.SAVE_EXIT_BUTTON) {
                    
                    CourseParticipantManager.getInstance().save(c);
                    if(courseAlreadyCreated) {
                        GUIManager.getStatusbar().setTextAndFadeOut("Kursteilnehmer wurde aktualisiert.");
                    } else {
                        GUIManager.getStatusbar().setTextAndFadeOut("Kursteilnehmer wurde erstellt.");
                        coursePartSelection.getList().add(c);
                        courseAlreadyCreated = true;
                    }
                }
                
                if(evt.getType() == ButtonEvent.EXIT_BUTTON || evt.getType() == ButtonEvent.SAVE_EXIT_BUTTON) {
                    model.removeButtonListener(this);
                    GUIManager.changeToLastView();
                    System.out.println("LAST VIEW   ################");
                }
              }
            });
            GUIManager.changeView(editView.buildPanel(), true);
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
            putValue(Action.SMALL_ICON, CWUtils.loadIcon("cw/coursemanagementmodul/images/edit.png"));
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
            putValue(Action.SMALL_ICON, CWUtils.loadIcon("cw/coursemanagementmodul/images/delete.png"));
        }

        private DeleteAction(String name) {
            super(name);
        }

        public void actionPerformed(ActionEvent e) {
            int ret = JOptionPane.showConfirmDialog(null, "Wollen Sie den Kursteilnehmer wirklich löschen?");
            if(ret == JOptionPane.OK_OPTION){
            GUIManager.setLoadingScreenText("Kursteilnehmer wird gelöscht...");
            GUIManager.setLoadingScreenVisible(true);

            new Thread(new Runnable() {

                public void run() {
                    CourseParticipant c = coursePartSelection.getSelection();
                    
                    coursePartSelection.getList().remove(c);
                    CourseParticipantManager.getInstance().delete(c);

                    GUIManager.setLoadingScreenVisible(false);
                    GUIManager.getStatusbar().setTextAndFadeOut("Der Kursteilnehmer wurde gelöscht!");
                }
            }).start();
        }
      }
    }
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
            GUIManager.changeView(new CoursePartDetailView(new CoursePartDetailPresentationModel(coursePartSelection.getSelection())).buildPanel(), true);
      }
    }
    
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

    public Action getDetailAction() {
        return detailAction;
    }
    
    public Action getNewButtonAction() {
        return newButtonAction;
    }

    public void setNewButtonAction(Action newButtonAction) {
        this.newButtonAction = newButtonAction;
    }
    //**************************************************************************
    
    
    private final class SelectionEmptyHandler implements PropertyChangeListener {

        public void propertyChange(PropertyChangeEvent evt) {
            updateActionEnablement();
        }
    }
    
    public SelectionInList<CourseParticipant> getCoursePartSelection(){
        return coursePartSelection;
    }
    
    //**************************************************************************
    //CourseTableModel, das die Art der Anzeige von Kursen regelt.
    //**************************************************************************
    private class CoursePartTableModel extends AbstractTableAdapter<CourseParticipant> {

        private ListModel listModel;

        public CoursePartTableModel(ListModel listModel) {
            super(listModel);
            this.listModel = listModel;
        }

        @Override
        public int getColumnCount() {
            return 3;
        }

        @Override
        public String getColumnName(int column) {
            switch (column) {
                case 0:
                    return "Vorname";
                case 1:
                    return "Nachname";
                case 2:
                    return "Kurse";
                default:
                    return "";
            }
        }

        @Override
        public boolean isCellEditable(int rowIndex, int columnIndex) {
            return false;
        }

        public Object getValueAt(int rowIndex, int columnIndex) {
            CourseParticipant coursePart = (CourseParticipant) listModel.getElementAt(rowIndex);
            int i = 0;
            String string = "";
            switch (columnIndex) {
                case 0:
                   return coursePart.getCostumer().getForename();
                case 1:
                    return coursePart.getCostumer().getSurname();
                case 2:

                    return coursePart.getCourseList().size();
                default:
                    return "";
            }
        }
    }
    //**************************************************************************
    
    //**************************************************************************
    //Event Handling
    //**************************************************************************
    public MouseListener getDoubleClickHandler() {
        return new DoubleClickHandler();
    }

    private final class DoubleClickHandler extends MouseAdapter {

        @Override
        public void mouseClicked(MouseEvent e) {
            if (SwingUtilities.isLeftMouseButton(e) && e.getClickCount() == 2) {
                editSelectedItem(e);
            }
        }
    }

    private void updateActionEnablement() {
        boolean hasSelection = coursePartSelection.hasSelection();
        editButtonAction.setEnabled(hasSelection);
        deleteButtonAction.setEnabled(hasSelection);
        detailAction.setEnabled(hasSelection);
    }
    
    //**************************************************************************
    //Regelt das Editieren (Verändern) von Kursen
    //(Doppelclick oder Ändern-Button)
    //**************************************************************************
    private void editSelectedItem(EventObject e) {
        GUIManager.setLoadingScreenText("Kursteilnehmer wird geladen...");
        GUIManager.setLoadingScreenVisible(true);

        new Thread(new Runnable() {

            public void run() {

                final CourseParticipant c = coursePartSelection.getSelection();
                final EditCoursePartPresentationModel model = new EditCoursePartPresentationModel(c,null);
                final EditCoursePartView editView = new EditCoursePartView(model);
                model.addButtonListener(new ButtonListener() {

                    public void buttonPressed(ButtonEvent evt) {
                        if (evt.getType() == ButtonEvent.SAVE_BUTTON || evt.getType() == ButtonEvent.SAVE_EXIT_BUTTON) {
                            CourseParticipantManager.getInstance().save(c);
                            GUIManager.getStatusbar().setTextAndFadeOut("Kursteilnehmer wurde aktualisiert.");
                        }
                        if (evt.getType() == ButtonEvent.EXIT_BUTTON || evt.getType() == ButtonEvent.SAVE_EXIT_BUTTON) {
                            model.removeButtonListener(this);
                            GUIManager.changeToLastView();
                        }
                    }
                });
                GUIManager.changeView(editView.buildPanel(), true);
                GUIManager.setLoadingScreenVisible(false);

            }
        }).start();
    }
    //**************************************************************************
}

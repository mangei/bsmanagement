package cw.coursemanagementmodul.gui;

import java.awt.event.ActionEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
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
import cw.coursemanagementmodul.pojo.CourseParticipant;
import cw.coursemanagementmodul.pojo.Subject;
import cw.coursemanagementmodul.pojo.manager.CourseParticipantManager;
import cw.coursemanagementmodul.pojo.manager.SubjectManager;

/**
 *
 * @author André Salmhofer
 */
public class SubjectPresentationModel
{
    //Definieren der Objekte in der oberen Leiste
    private Action newButtonAction;
    private Action editButtonAction;
    private Action deleteButtonAction;
    //********************************************
    
    //Liste der Kurse
    private SelectionInList<Subject> subjectSelection;
    //**********************************************

    private CWHeaderInfo headerInfo;

    private SelectionHandler selectionHandler;
    
    public SubjectPresentationModel() {
        initModels();
        initEventHandling();
    }
    
    public void initModels() {

        headerInfo = new CWHeaderInfo(
                "Kursgegenstand",
                "Sie befinden sich im Kursgegenstandsbereich. Hier koennen Sie Kursgegenstaende anlegen",
                CWUtils.loadIcon("cw/coursemanagementmodul/images/subject.png"),
                CWUtils.loadIcon("cw/coursemanagementmodul/images/subject.png"));
        
        //Anlegen der Aktionen, fuer die Buttons
        newButtonAction = new NewAction("Neu");
        editButtonAction = new EditAction("Bearbeiten");
        deleteButtonAction = new DeleteAction("Loeschen");
        
        subjectSelection = new SelectionInList<Subject>(SubjectManager.getInstance().getAll());
    }

    private void initEventHandling() {
        subjectSelection.addValueChangeListener(selectionHandler = new SelectionHandler());
        updateActionEnablement();
    }

    public void dispose() {
        subjectSelection.removeValueChangeListener(selectionHandler);
        subjectSelection.release();
    }
    
    //**************************************************************************
    //Methode, die ein CourseTableModel erzeugt.
    //Die private Klasse befindet sich in der gleichen Datei
    //**************************************************************************
    TableModel createSubjectTableModel(SelectionInList<Subject> subjectSelection) {
        return new SubjectTableModel(subjectSelection);
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
            putValue( Action.SMALL_ICON, CWUtils.loadIcon("cw/coursemanagementmodul/images/subject_add.png") );
          }
         
         private NewAction(String name) {
            super(name);
         }

        public void actionPerformed(ActionEvent e) {
            GUIManager.getInstance().lockMenu();
            final Subject s = new Subject();
            final EditSubjectPresentationModel model = new EditSubjectPresentationModel(s);
            final EditSubjectView editView = new EditSubjectView(model);
            model.addButtonListener(new ButtonListener() {
                
            boolean courseAlreadyCreated = false;

            public void buttonPressed(ButtonEvent evt) {
                if(evt.getType() == ButtonEvent.SAVE_BUTTON || evt.getType() == ButtonEvent.SAVE_EXIT_BUTTON) {
                    SubjectManager.getInstance().save(s);
                    if(courseAlreadyCreated) {
                        GUIManager.getStatusbar().setTextAndFadeOut("Kurs wurde aktualisiert.");
                    } else {
                        GUIManager.getStatusbar().setTextAndFadeOut("Kurs wurde erstellt.");
                        subjectSelection.getList().add(s);
                        subjectSelection.fireSelectedContentsChanged();
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
            putValue(Action.SMALL_ICON, CWUtils.loadIcon("cw/coursemanagementmodul/images/subject_edit.png"));
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
            putValue(Action.SMALL_ICON, CWUtils.loadIcon("cw/coursemanagementmodul/images/subject_delete.png"));
        }

        private DeleteAction(String name) {
            super(name);
        }

        public void actionPerformed(ActionEvent e) {
            Subject s = subjectSelection.getSelection();
            List<CourseParticipant> courseParts = CourseParticipantManager.getInstance().getAll(s);
            if(courseParts.size() > 0){
                JOptionPane.showMessageDialog(null, "<html>Loeschen des Kursgegenstands "
                        + s.getName() + " nicht moeglich!<br/>"
                        + "Der Kursgegenstand wird noch von ein oder mehreren Kunden verwendet!</html>");
            }
            else{
                int ret = JOptionPane.showConfirmDialog(null, "Wollen Sie den Kursgegenstand wirklich loeschen?");
                
                if(ret == JOptionPane.OK_OPTION){
                    subjectSelection.getList().remove(s);
                    SubjectManager.getInstance().delete(s);

                    GUIManager.setLoadingScreenText("Kursgegenstand wird geloescht...");
                    GUIManager.setLoadingScreenVisible(true);
                    
                    GUIManager.setLoadingScreenVisible(false);
                    GUIManager.getStatusbar().setTextAndFadeOut("Der Kursgegenstand wurde geloescht!");
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
    
    public SelectionInList<Subject> getSubjectSelection(){
        return subjectSelection;
    }
    
    //**************************************************************************
    //CourseTableModel, das die Art der Anzeige von Kursen regelt.
    //**************************************************************************
    private class SubjectTableModel extends AbstractTableAdapter<Subject> {

        private ListModel listModel;

        public SubjectTableModel(ListModel listModel) {
            super(listModel);
            this.listModel = listModel;
        }

        @Override
        public int getColumnCount() {
            return 1;
        }

        @Override
        public String getColumnName(int column) {
            switch (column) {
                case 0:
                    return "Gegenstand";
                    
                default:
                    return "";
            }
        }

        @Override
        public boolean isCellEditable(int rowIndex, int columnIndex) {
            return false;
        }

        public Object getValueAt(int rowIndex, int columnIndex) {
            Subject subject = (Subject) listModel.getElementAt(rowIndex);
            switch (columnIndex) {
                case 0:
                    return subject.getName();
                default:
                    return "";
            }
        }
    }
    //**************************************************************************
    
    private void updateActionEnablement() {
        boolean hasSelection = subjectSelection.hasSelection();
        editButtonAction.setEnabled(hasSelection);
        deleteButtonAction.setEnabled(hasSelection);
    }
    
    //**************************************************************************
    //Regelt das Editieren (Veraendern) von Kursen
    //(Doppelclick oder Ändern-Button)
    //**************************************************************************
    private void editSelectedItem(EventObject e) {
        GUIManager.getInstance().lockMenu();
        GUIManager.setLoadingScreenText("Kunde wird geladen...");
        GUIManager.setLoadingScreenVisible(true);

                final Subject subject = subjectSelection.getSelection();
                final EditSubjectPresentationModel model = new EditSubjectPresentationModel(subject);
                final EditSubjectView editView = new EditSubjectView(model);
                model.addButtonListener(new ButtonListener() {

                    public void buttonPressed(ButtonEvent evt) {
                        if (evt.getType() == ButtonEvent.SAVE_BUTTON || evt.getType() == ButtonEvent.SAVE_EXIT_BUTTON) {
                            SubjectManager.getInstance().save(subject);
                            GUIManager.getStatusbar().setTextAndFadeOut("Gegenstand wurde aktualisiert.");
                        }
                        if (evt.getType() == ButtonEvent.EXIT_BUTTON || evt.getType() == ButtonEvent.SAVE_EXIT_BUTTON) {
                            GUIManager.getInstance().unlockMenu();
                            model.removeButtonListener(this);
                        }
                    }
                });
                GUIManager.changeView(editView, true);
                GUIManager.setLoadingScreenVisible(false);
    }

    public CWHeaderInfo getHeaderInfo() {
        return headerInfo;
    }
    //**************************************************************************
}

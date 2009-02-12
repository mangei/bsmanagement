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
import cw.boardingschoolmanagement.gui.component.JViewPanel.HeaderInfo;
import cw.boardingschoolmanagement.manager.GUIManager;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.EventObject;
import cw.coursemanagementmodul.pojo.Subject;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JOptionPane;
import javax.swing.ListModel;
import javax.swing.SwingUtilities;
import javax.swing.table.TableModel;
import cw.coursemanagementmodul.pojo.manager.SubjectManager;

/**
 *
 * @author André Salmhofer
 */
public class SubjectPresentationModel{
    //Definieren der Objekte in der oberen Leiste
    private Action newButtonAction;
    private Action editButtonAction;
    private Action deleteButtonAction;
    //********************************************
    
    //Liste der Kurse
    private SelectionInList<Subject> subjectSelection;
    //**********************************************

    private HeaderInfo headerInfo;
    
    public SubjectPresentationModel() {
        initModels();
        initEventHandling();

        headerInfo = new HeaderInfo(
                "Kursgegenstand",
                "Sie befinden sich im Kursgegenstandsbereich. Hier können Sie Kursgegenstände anlegen",
                CWUtils.loadIcon("cw/coursemanagementmodul/images/subject.png"),
                CWUtils.loadIcon("cw/coursemanagementmodul/images/subject.png"));
    }
    
    //**************************************************************************
    //Initialisieren der Objekte
    //**************************************************************************
    public void initModels() {
        //Anlegen der Aktionen, für die Buttons
        newButtonAction = new NewAction("Neu");
        editButtonAction = new EditAction("Bearbeiten");
        deleteButtonAction = new DeleteAction("Löschen");
        
        subjectSelection = new SelectionInList<Subject>(SubjectManager.getInstance().getAll());
        updateActionEnablement();
    }
    //**************************************************************************
    
    //**************************************************************************
    //Methode, die ein CourseTableModel erzeugt.
    //Die private Klasse befindet sich in der gleichen Datei
    //**************************************************************************
    TableModel createSubjectTableModel(SelectionInList<Subject> subjectSelection) {
        return new SubjectTableModel(subjectSelection);
    }
    //**************************************************************************
     
    
    private void initEventHandling() {
        subjectSelection.addPropertyChangeListener(
                SelectionInList.PROPERTYNAME_SELECTION_EMPTY,
                new SelectionEmptyHandler());
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
                    model.removeButtonListener(this);
                    GUIManager.changeToLastView();
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
    //Private Klasse, die Events behandelt, die das Löschen
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
            int ret = JOptionPane.showConfirmDialog(null, "Wollen Sie den Gegenstand wirklich löschen?");
            if(ret == JOptionPane.OK_OPTION){
            GUIManager.setLoadingScreenText("Kurs wird gelöscht...");
            GUIManager.setLoadingScreenVisible(true);

            new Thread(new Runnable() {

                public void run() {
                    Subject s = subjectSelection.getSelection();
                    
                    subjectSelection.getList().remove(s);
                    SubjectManager.getInstance().delete(s);

                    GUIManager.setLoadingScreenVisible(false);
                    GUIManager.getStatusbar().setTextAndFadeOut("Der Gegenstand wurde gelöscht!");
                }
            }).start();
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
    
    
    private final class SelectionEmptyHandler implements PropertyChangeListener {

        public void propertyChange(PropertyChangeEvent evt) {
            updateActionEnablement();
        }
    }
    
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
        boolean hasSelection = subjectSelection.hasSelection();
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

        new Thread(new Runnable() {

            public void run() {

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

    public HeaderInfo getHeaderInfo() {
        return headerInfo;
    }
    //**************************************************************************
}

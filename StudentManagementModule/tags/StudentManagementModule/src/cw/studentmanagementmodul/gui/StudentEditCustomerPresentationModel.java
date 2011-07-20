package cw.studentmanagementmodul.gui;

import cw.boardingschoolmanagement.app.ButtonEvent;
import cw.boardingschoolmanagement.app.ButtonListener;
import cw.boardingschoolmanagement.app.CWUtils;
import com.jgoodies.binding.PresentationModel;
import com.jgoodies.binding.value.ValueHolder;
import com.jgoodies.binding.value.ValueModel;
import cw.boardingschoolmanagement.gui.component.CWView.CWHeaderInfo;
import java.awt.event.ActionEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javax.swing.AbstractAction;
import javax.swing.Action;
import cw.boardingschoolmanagement.manager.GUIManager;
import cw.studentmanagementmodul.pojo.Student;
import cw.studentmanagementmodul.pojo.StudentClass;
import javax.swing.Icon;

/**
 *
 * @author Manuel Geier
 */
public class StudentEditCustomerPresentationModel
        extends PresentationModel<Student>
{
    
    private Action studentClassChooserAction;

    private CWHeaderInfo headerInfo;
    private ValueModel unsaved;

    private PropertyChangeListener changeListener;
    
    public StudentEditCustomerPresentationModel(Student student, final ValueModel unsaved) {
        super(student);
        this.unsaved = unsaved;

        initModels();
        initEventHandling();
    }

    private void initModels() {
        headerInfo = new CWHeaderInfo(
                "Schüler",
                "Verwalten Sie Schulinformationen für einen Kunden.",
                CWUtils.loadIcon("cw/studentmanagementmodul/images/studentClass.png"),
                CWUtils.loadIcon("cw/studentmanagementmodul/images/studentClass.png")
        );

        studentClassChooserAction = new StudentClassChooserAction("Klasse auswählen", CWUtils.loadIcon("cw/studentmanagementmodul/images/studentClass_search.png"));
    }

    private void initEventHandling() {
        changeListener = new PropertyChangeListener() {
            public void propertyChange(PropertyChangeEvent evt) {
                unsaved.setValue(true);
            }
        };
        getBufferedModel(Student.PROPERTYNAME_ACTIVE).addValueChangeListener(changeListener);
        getBufferedModel(Student.PROPERTYNAME_STUDENTCLASS).addValueChangeListener(changeListener);
    }

    public void dispose() {
        getBufferedModel(Student.PROPERTYNAME_ACTIVE).removeValueChangeListener(changeListener);
        getBufferedModel(Student.PROPERTYNAME_STUDENTCLASS).removePropertyChangeListener(changeListener);
        release();
    }


    ////////////////////////////////////////////////////////////////////////////
    // Action classes
    ////////////////////////////////////////////////////////////////////////////

    private class StudentClassChooserAction extends AbstractAction {

        public StudentClassChooserAction(String name, Icon icon) {
            super(name, icon);
        }

        public void actionPerformed(ActionEvent e) {
            final StudentClassChooserPresentationModel model = new StudentClassChooserPresentationModel(
                    (StudentClass)getBufferedValue(Student.PROPERTYNAME_STUDENTCLASS),
                    new CWHeaderInfo(
                        "Klasse auswählen",
                        "Wählen Sie eine Klasse aus, die Sie dem Schüler zuweisen wollen.",
                        CWUtils.loadIcon("cw/studentmanagementmodul/images/studentClass_search.png"),
                        CWUtils.loadIcon("cw/studentmanagementmodul/images/studentClass_search.png")
                    )
            );
            StudentClassChooserView view = new StudentClassChooserView(model);

            model.addButtonListener(new ButtonListener() {
                public void buttonPressed(ButtonEvent e) {
                    if(e.getType() == ButtonEvent.OK_BUTTON) {
                        setBufferedValue(Student.PROPERTYNAME_STUDENTCLASS, model.getSelectedStudentClass());
                    }
                    else if(e.getType() == ButtonEvent.CUSTOM_BUTTON && e.getCustomButtonText().equals("noClassButton")) {
                        setBufferedValue(Student.PROPERTYNAME_STUDENTCLASS, null);
                    }
                    model.removeButtonListener(this);
                    GUIManager.changeToLastView();
                }
            });

            GUIManager.changeView(view, true);
        }
    }

    ////////////////////////////////////////////////////////////////////////////
    // Extention Methods
    ////////////////////////////////////////////////////////////////////////////
        
    public void save() {
        triggerCommit();
    }
    
    
    ////////////////////////////////////////////////////////////////////////////
    // Getter methods for the model
    ////////////////////////////////////////////////////////////////////////////

    public Action getStudentClassChooserAction() {
        return studentClassChooserAction;
    }

    public CWHeaderInfo getHeaderInfo() {
        return headerInfo;
    }

}

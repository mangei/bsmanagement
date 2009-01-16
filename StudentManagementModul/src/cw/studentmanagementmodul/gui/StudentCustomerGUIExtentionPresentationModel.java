package cw.studentmanagementmodul.gui;

import cw.boardingschoolmanagement.app.ButtonEvent;
import cw.boardingschoolmanagement.app.ButtonListener;
import cw.boardingschoolmanagement.app.CWUtils;
import com.jgoodies.binding.PresentationModel;
import com.jgoodies.binding.value.ValueHolder;
import com.jgoodies.binding.value.ValueModel;
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
public class StudentCustomerGUIExtentionPresentationModel
        extends PresentationModel<Student>
{
    
    private Action studentClassChooserAction;
    private ValueModel studentClassName;
    
    private ValueModel unsaved;
    
    public StudentCustomerGUIExtentionPresentationModel(Student student, final ValueModel unsaved) {
        super(student);
        this.unsaved = unsaved;

        initModels();
        initEventHandling();
    }

    public void initModels() {
        studentClassChooserAction = new StudentClassChooserAction("Klasse auswählen", CWUtils.loadIcon("cw/studentmanagementmodul/images/box_add.png"));
        studentClassName = new ValueHolder();
    }

    private void initEventHandling() {
        getBufferedModel(Student.PROPERTYNAME_STUDENTCLASS).addValueChangeListener(new PropertyChangeListener() {
            public void propertyChange(PropertyChangeEvent evt) {
                updateStudentClassName();
                unsaved.setValue(true);
            }
        });
        updateStudentClassName();

        getBufferedModel(Student.PROPERTYNAME_ACTIVE).addValueChangeListener(new PropertyChangeListener() {
            public void propertyChange(PropertyChangeEvent evt) {
                unsaved.setValue(true);
                updateComponentsEnabled();
            }
        });
        updateComponentsEnabled();
    }

    ////////////////////////////////////////////////////////////////////////////
    // Useful methods
    ////////////////////////////////////////////////////////////////////////////

    private void updateStudentClassName() {
        if(getBufferedModel(Student.PROPERTYNAME_STUDENTCLASS) != null) {
            studentClassName.setValue(getBufferedModel(Student.PROPERTYNAME_STUDENTCLASS).toString());
        } else {
            studentClassName.setValue("Keine Klasse");
        }
    }

    private void updateComponentsEnabled() {
        studentClassChooserAction.setEnabled((Boolean)getBufferedModel(Student.PROPERTYNAME_ACTIVE).getValue());
    }

    ////////////////////////////////////////////////////////////////////////////
    // Action classes
    ////////////////////////////////////////////////////////////////////////////

    private class StudentClassChooserAction extends AbstractAction {

        public StudentClassChooserAction(String name, Icon icon) {
            super(name, icon);
        }

        public void actionPerformed(ActionEvent e) {
            final StudentClassChooserPresentationModel model = new StudentClassChooserPresentationModel((StudentClass)getBufferedValue(Student.PROPERTYNAME_STUDENTCLASS), "Klasse auswählen");
            StudentClassChooserView view = new StudentClassChooserView(model);

            model.addButtonListener(new ButtonListener() {
                public void buttonPressed(ButtonEvent e) {
                    if(e.getType() == ButtonEvent.OK_BUTTON) {
                        setBufferedValue(Student.PROPERTYNAME_STUDENTCLASS, model.getSelectedStudentClass());
                    }
                    model.removeButtonListener(this);
                    GUIManager.changeToLastView();
                }
            });

            GUIManager.changeView(view.buildPanel(), true);
        }
    }

    ////////////////////////////////////////////////////////////////////////////
    // Extention Methods
    ////////////////////////////////////////////////////////////////////////////
        
    public void save() {
        triggerCommit();
    }
    
    public void reset() {
        triggerFlush();
    }

    
    ////////////////////////////////////////////////////////////////////////////
    // Getter methods for the model
    ////////////////////////////////////////////////////////////////////////////


    public Action getStudentClassChooserAction() {
        return studentClassChooserAction;
    }

    public ValueModel getStudentClassName() {
        return studentClassName;
    }

}

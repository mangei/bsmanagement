package cw.studentmanagementmodul.student.gui;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;

import com.jgoodies.binding.list.SelectionInList;
import com.jgoodies.binding.value.ValueHolder;
import com.jgoodies.binding.value.ValueModel;

import cw.boardingschoolmanagement.gui.CWPresentationModel;
import cw.studentmanagementmodul.student.extention.StudentCustomerSelectorFilterExtention;
import cw.studentmanagementmodul.student.persistence.PMStudentClass;

/**
 * @author CreativeWorkers.at
 */
public class StudentCustomerSelectorFilterExtentionPresentationModel
	extends CWPresentationModel
{

    private Action noMatterAction;
    private Action inactiveAction;
    private Action activeAction;
    private SelectionInList studentClassSelection;
    private ValueModel option;

    public StudentCustomerSelectorFilterExtentionPresentationModel() {
        initModels();
        initEventHandling();
    }

    public void initModels() {
        option = new ValueHolder(StudentCustomerSelectorFilterExtention.NO_MATTER);
        noMatterAction = new AbstractAction("egal") {
            public void actionPerformed(ActionEvent e) {
                option.setValue(StudentCustomerSelectorFilterExtention.NO_MATTER);
            }
        };
        inactiveAction = new AbstractAction("ist kein Schueler") {
            public void actionPerformed(ActionEvent e) {
                option.setValue(StudentCustomerSelectorFilterExtention.INACTIVE);
            }
        };
        activeAction = new AbstractAction("ist ein Schueler in ") {
            public void actionPerformed(ActionEvent e) {
                option.setValue(StudentCustomerSelectorFilterExtention.ACTIVE);
            }
        };
        studentClassSelection = new SelectionInList(PMStudentClass.getInstance().getAll());
        studentClassSelection.getList().add(0, "egal");
        studentClassSelection.getList().add(1, "keiner Klasse");
    }

    private void initEventHandling() {
        studentClassSelection.setSelectionIndex(0);
    }

    public void dispose() {
        // Nothing to do
    }

    
    ////////////////////////////////////////////////////////////////////////////
    // Getter methods for the model
    ////////////////////////////////////////////////////////////////////////////

    public Action getActiveAction() {
        return activeAction;
    }

    public Action getInactiveAction() {
        return inactiveAction;
    }

    public Action getNoMatterAction() {
        return noMatterAction;
    }

    public SelectionInList getStudentClassSelection() {
        return studentClassSelection;
    }

    public ValueModel getOption() {
        return option;
    }

}

package cw.studentmanagementmodul.gui;

import com.jgoodies.binding.list.SelectionInList;
import com.jgoodies.binding.value.ValueHolder;
import com.jgoodies.binding.value.ValueModel;
import cw.studentmanagementmodul.extention.StudentCustomerSelectorFilterExtention;
import cw.studentmanagementmodul.pojo.manager.StudentClassManager;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.Action;

/**
 * @author CreativeWorkers.at
 */
public class StudentCustomerSelectorFilterExtentionPresentationModel
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
        inactiveAction = new AbstractAction("ist kein Schüler") {
            public void actionPerformed(ActionEvent e) {
                option.setValue(StudentCustomerSelectorFilterExtention.INACTIVE);
            }
        };
        activeAction = new AbstractAction("ist ein Schüler in ") {
            public void actionPerformed(ActionEvent e) {
                option.setValue(StudentCustomerSelectorFilterExtention.ACTIVE);
            }
        };
        studentClassSelection = new SelectionInList(StudentClassManager.getInstance().getAll());
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

package cw.studentmanagementmodul.student.gui;

import com.jgoodies.binding.value.ValueHolder;
import com.jgoodies.binding.value.ValueModel;

import cw.boardingschoolmanagement.gui.CWPresentationModel;
import cw.studentmanagementmodul.student.persistence.PMStudent;

/**
 *
 * @author Manuel Geier
 */
public class StudentHomeExtentionPresentationModel
	extends CWPresentationModel
{

    private ValueModel sizeStudentsValueModel;

    public StudentHomeExtentionPresentationModel() {
        initModels();
        initEventHandling();
    }

    private void initModels() {
        sizeStudentsValueModel = new ValueHolder("Schueler: " + PMStudent.getInstance().sizeActive());
    }

    private void initEventHandling() {
        // Nothing to do
    }

    public void dispose() {
        // Nothing to do
    }

    public ValueModel getSizeStudentsValueModel() {
        return sizeStudentsValueModel;
    }

}

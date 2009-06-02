package cw.studentmanagementmodul.gui;

import com.jgoodies.binding.value.ValueHolder;
import com.jgoodies.binding.value.ValueModel;
import cw.studentmanagementmodul.pojo.manager.StudentManager;

/**
 *
 * @author ManuelG
 */
public class StudentHomeExtentionPresentationModel
{

    private ValueModel sizeStudentsValueModel;

    public StudentHomeExtentionPresentationModel() {
        initModels();
        initEventHandling();
    }

    private void initModels() {
        sizeStudentsValueModel = new ValueHolder("Schüler: " + StudentManager.getInstance().sizeActive());
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

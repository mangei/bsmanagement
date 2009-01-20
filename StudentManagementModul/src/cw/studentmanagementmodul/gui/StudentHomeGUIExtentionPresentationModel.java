package cw.studentmanagementmodul.gui;

import com.jgoodies.binding.value.ValueHolder;
import com.jgoodies.binding.value.ValueModel;
import cw.studentmanagementmodul.pojo.manager.StudentManager;

/**
 *
 * @author ManuelG
 */
public class StudentHomeGUIExtentionPresentationModel {

    private ValueModel sizeStudentsValueModel;

    public StudentHomeGUIExtentionPresentationModel() {
        initModels();
        initEventHandling();
    }

    private void initModels() {
        sizeStudentsValueModel = new ValueHolder("Schüler: " + StudentManager.getInstance().sizeActive());
    }

    private void initEventHandling() {

    }

    public ValueModel getSizeStudentsValueModel() {
        return sizeStudentsValueModel;
    }

}

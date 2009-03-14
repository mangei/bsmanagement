/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package cw.coursemanagementmodul.extentions;

import cw.coursemanagementmodul.pojo.CoursePosting;
import cw.coursemanagementmodul.pojo.manager.CoursePostingManager;
import cw.customermanagementmodul.extentions.interfaces.EditReversePostingPostingCategoryExtention;
import cw.customermanagementmodul.gui.EditReversePostingPresentationModel;
import cw.customermanagementmodul.pojo.Posting;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JComponent;
import javax.swing.JLabel;

/**
 *
 * @author André Salmhofer
 */
public class ReversePostingCategoryExtention implements EditReversePostingPostingCategoryExtention{
    private EditReversePostingPresentationModel reversePostingModel;
    public void initPresentationModel(EditReversePostingPresentationModel editReversePostingModel) {
        reversePostingModel = editReversePostingModel;
    }

    public JComponent getView() {
        CoursePosting p = CoursePostingManager.getInstance().get(((Posting)reversePostingModel.getPostingPresentationModel().getBean()));
        return new JLabel(p.getCourseAddition().getCourse().getName());
    }

    public void save() {
        System.out.println("SAVE");
    }

    public List<String> validate() {
        return new ArrayList();
    }

    public String getKey() {
        return "Kurs-Buchung";
    }

    public void dispose() {
        
    }
}

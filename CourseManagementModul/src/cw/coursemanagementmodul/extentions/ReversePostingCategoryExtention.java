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
    private CoursePosting coursePosting;
    public void initPresentationModel(EditReversePostingPresentationModel editReversePostingModel) {
        reversePostingModel = editReversePostingModel;
        Posting p = reversePostingModel.getPostingPresentationModel().getBean();
        if(p.isBalancePosting()) {
            p = p.getPreviousPosting();
        }
        coursePosting = CoursePostingManager.getInstance().get(p);
    }

    public JComponent getView() {
        return new JLabel(coursePosting.getCourseAddition().getCourse().getName());
    }

    public void save() {
        CoursePosting coursePostingNew = new CoursePosting();
        coursePostingNew.setCourseAddition(coursePosting.getCourseAddition());
        coursePostingNew.setPosting(reversePostingModel.getPostingPresentationModel().getBean());
        CoursePostingManager.getInstance().save(coursePostingNew);
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

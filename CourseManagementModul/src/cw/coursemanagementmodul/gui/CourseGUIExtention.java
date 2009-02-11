/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package cw.coursemanagementmodul.gui;

import com.jgoodies.binding.value.ValueModel;
import cw.boardingschoolmanagement.extentions.interfaces.GUIExtention;
import javax.swing.JComponent;
import cw.coursemanagementmodul.pojo.Course;

/**
 *
 * @author André Salmhofer
 */
public interface CourseGUIExtention extends GUIExtention{
    public void initPresenter(Course course, ValueModel unsaved);
    public JComponent getView();
    public void save();
    public void reset();
    public void delete(Course course);
}

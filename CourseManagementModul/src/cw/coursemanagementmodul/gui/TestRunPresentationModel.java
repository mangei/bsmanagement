/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package cw.coursemanagementmodul.gui;

import com.jgoodies.binding.list.SelectionInList;
import cw.boardingschoolmanagement.app.CWUtils;
import cw.boardingschoolmanagement.gui.component.JViewPanel.HeaderInfo;
import cw.boardingschoolmanagement.interfaces.Disposable;
import cw.boardingschoolmanagement.manager.GUIManager;
import cw.coursemanagementmodul.pojo.Course;
import cw.coursemanagementmodul.pojo.CourseAddition;
import cw.coursemanagementmodul.pojo.CourseParticipant;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.Action;

/**
 *
 * @author André Salmhofer
 */
public class TestRunPresentationModel implements Disposable{
    private SelectionInList<CourseParticipant> coursePartSelection;
    private Course selectedCourse;
    private Action backButtonAction;
    private HeaderInfo headerInfo;

    public TestRunPresentationModel(SelectionInList<CourseParticipant> courseParticipants,
            Course selectedCourse) {
        this.coursePartSelection = courseParticipants;
        this.selectedCourse = selectedCourse;
        initModels();

        headerInfo = new HeaderInfo(
                "Testlauf",
                "Sie befinden sich im Soll-Testlauf des Kurses " + selectedCourse.getName() + "!",
                CWUtils.loadIcon("cw/coursemanagementmodul/images/course.png"),
                CWUtils.loadIcon("cw/coursemanagementmodul/images/course.png"));
    }

    public SelectionInList<CourseParticipant> getCoursePartSelection() {
        return coursePartSelection;
    }

    public void initModels() {
        //Anlegen der Aktionen, für die Buttons
        backButtonAction = new BackButtonAction("Zurück");
    }

    public void dispose() {
        
    }

    private class BackButtonAction
            extends AbstractAction {

        {
            putValue(Action.SMALL_ICON, CWUtils.loadIcon("cw/coursemanagementmodul/images/back.png"));
        }

        private BackButtonAction(String name) {
            super(name);
        }

        public void actionPerformed(ActionEvent e) {
            GUIManager.getInstance().unlockMenu();
            GUIManager.changeToLastView();
        }
    }
    
    public Action getBackButtonAction() {
        return backButtonAction;
    }
    
    public CourseAddition getSelectedCourseAddition(CourseParticipant courseParticipant) {
        for(int i = 0; i < courseParticipant.getCourseList().size(); i++){
            if(courseParticipant.getCourseList().get(i).getCourse().getId()
                    == selectedCourse.getId()){
                return courseParticipant.getCourseList().get(i);
            }
        }
        return new CourseAddition();
    }

    public HeaderInfo getHeaderInfo() {
        return headerInfo;
    }
}

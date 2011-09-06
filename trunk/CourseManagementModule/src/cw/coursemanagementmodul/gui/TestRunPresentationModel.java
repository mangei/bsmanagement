package cw.coursemanagementmodul.gui;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;

import com.jgoodies.binding.list.SelectionInList;

import cw.boardingschoolmanagement.app.CWUtils;
import cw.boardingschoolmanagement.gui.component.CWView.CWHeaderInfo;
import cw.boardingschoolmanagement.manager.GUIManager;
import cw.coursemanagementmodul.pojo.Course;
import cw.coursemanagementmodul.pojo.CourseAddition;
import cw.coursemanagementmodul.pojo.CourseParticipant;

/**
 *
 * @author André Salmhofer
 */
public class TestRunPresentationModel
{
    private SelectionInList<CourseParticipant> coursePartSelection;
    private Course selectedCourse;
    private Action backButtonAction;
    private Action printAction;
    private CWHeaderInfo headerInfo;

    public TestRunPresentationModel(SelectionInList<CourseParticipant> courseParticipants,
            Course selectedCourse) {
        this.coursePartSelection = courseParticipants;
        this.selectedCourse = selectedCourse;

        initModels();
        initEventHandling();
    }

    private void initModels() {

        headerInfo = new CWHeaderInfo(
                "Testlauf",
                "Sie befinden sich im Soll-Testlauf des Kurses " + selectedCourse.getName() + "!",
                CWUtils.loadIcon("cw/coursemanagementmodul/images/course.png"),
                CWUtils.loadIcon("cw/coursemanagementmodul/images/course.png"));

        //Anlegen der Aktionen, fuer die Buttons
        backButtonAction = new BackButtonAction("Zurueck");
        printAction = new PrintAction("Drucken");
    }

    private void initEventHandling() {
    }

    public void dispose() {
    }


    public SelectionInList<CourseParticipant> getCoursePartSelection() {
        return coursePartSelection;
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

    private class PrintAction extends AbstractAction{
        {
            putValue( Action.SMALL_ICON, CWUtils.loadIcon("cw/coursemanagementmodul/images/print.png") );
        }

        public void actionPerformed(ActionEvent e){
            GUIManager.changeView(new PrintPostingView(
                    new PrintPostingRunPresentationModel(coursePartSelection.getList(),
                    new CWHeaderInfo("Kursteilnehmerliste drucken"), selectedCourse)), true);
        }

        private PrintAction(String string){
            super(string);
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

    public CWHeaderInfo getHeaderInfo() {
        return headerInfo;
    }

    public Action getPrintAction() {
        return printAction;
    }
}

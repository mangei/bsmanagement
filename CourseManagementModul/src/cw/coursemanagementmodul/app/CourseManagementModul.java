/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package cw.coursemanagementmodul.app;

import cw.boardingschoolmanagement.app.CWUtils;
import cw.boardingschoolmanagement.gui.component.JMenuPanel;
import cw.boardingschoolmanagement.interfaces.Modul;
import cw.boardingschoolmanagement.manager.GUIManager;
import cw.boardingschoolmanagement.manager.MenuManager;
import cw.boardingschoolmanagement.manager.ModulManager;
import cw.customermanagementmodul.pojo.Customer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.JButton;
import cw.coursemanagementmodul.gui.ActivityPresentationModel;
import cw.coursemanagementmodul.gui.ActivityView;
import cw.coursemanagementmodul.gui.HistoryPresentationModel;
import cw.coursemanagementmodul.gui.HistoryView;
import cw.coursemanagementmodul.gui.CoursePostingPresentationModel;
import cw.coursemanagementmodul.gui.CoursePostingView;
import cw.coursemanagementmodul.gui.CoursePresentationModel;
import cw.coursemanagementmodul.gui.CourseView;
import cw.coursemanagementmodul.gui.SubjectPresentationModel;
import cw.coursemanagementmodul.gui.SubjectView;
import cw.coursemanagementmodul.pojo.CoursePosting;
import cw.coursemanagementmodul.pojo.Activity;
import cw.coursemanagementmodul.pojo.Course;
import cw.coursemanagementmodul.pojo.CourseAddition;
import cw.coursemanagementmodul.pojo.CourseParticipant;
import cw.coursemanagementmodul.pojo.Subject;
import org.hibernate.cfg.AnnotationConfiguration;
/**
 *
 * @author André Salmhofer
 */
public class CourseManagementModul implements Modul{

    public void init() {
        JMenuPanel menuPanel = MenuManager.getSideMenu();
        menuPanel.addCategory("Kursverwaltung", "course");
        
        JButton courseButton = new JButton("Kurse");
        courseButton.setIcon(CWUtils.loadIcon("cw/coursemanagementmodul/images/course.png"));
        courseButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                GUIManager.changeView(new CourseView(new CoursePresentationModel()).buildPanel());
            }
        });
        
        JButton activityButton = new JButton("Aktivität");
        activityButton.setIcon(CWUtils.loadIcon("cw/coursemanagementmodul/images/activity.png"));
        activityButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                GUIManager.changeView(new ActivityView(new ActivityPresentationModel()).buildPanel());
            }
        });
        
        JButton subjectButton = new JButton("Kursgegenstand");
        subjectButton.setIcon(CWUtils.loadIcon("cw/coursemanagementmodul/images/subject.png"));
        subjectButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                GUIManager.changeView(new SubjectView(new SubjectPresentationModel()).buildPanel());
            }
        });

        JButton accountingButton = new JButton("Gebührenlauf");
        accountingButton.setIcon(CWUtils.loadIcon("cw/coursemanagementmodul/images/accounting.png"));
        accountingButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                GUIManager.changeView(new CoursePostingView(new CoursePostingPresentationModel(new CoursePosting())).buildPanel());
            }
        });

        JButton accountingHistoryButton = new JButton("Kurshistorie");
        accountingHistoryButton.setIcon(CWUtils.loadIcon("cw/coursemanagementmodul/images/courseHistory.png"));
        accountingHistoryButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                GUIManager.changeView(new HistoryView(new HistoryPresentationModel()).buildPanel());
            }
        });

        menuPanel.addItem(courseButton, "course");
        menuPanel.addItem(activityButton, "course");
        menuPanel.addItem(subjectButton, "course");
        menuPanel.addItem(accountingButton, "course");
        menuPanel.addItem(accountingHistoryButton, "course");
    }

    public Class getBaseClass() {
        return Customer.class;
    }

    public Modul createModul(Object obj) {
        
//        throw new UnsupportedOperationException("Not supported yet.");
        return null;
    }

    public void registerAnnotationClasses(AnnotationConfiguration configuration) {
        configuration.addAnnotatedClass(Course.class)
                     .addAnnotatedClass(CourseParticipant.class)
                     .addAnnotatedClass(Activity.class)
                     .addAnnotatedClass(Subject.class)
                     .addAnnotatedClass(CourseAddition.class)
                     .addAnnotatedClass(CoursePosting.class);
    }

    public List<Class> getDependencies() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}

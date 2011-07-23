package cw.coursemanagementmodul.app;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;

import cw.boardingschoolmanagement.app.CWUtils;
import cw.boardingschoolmanagement.app.CascadeEvent;
import cw.boardingschoolmanagement.app.CascadeListener;
import cw.boardingschoolmanagement.gui.component.CWMenuPanel;
import cw.boardingschoolmanagement.interfaces.Modul;
import cw.boardingschoolmanagement.manager.GUIManager;
import cw.boardingschoolmanagement.manager.MenuManager;
import cw.coursemanagementmodul.gui.ActivityPresentationModel;
import cw.coursemanagementmodul.gui.ActivityView;
import cw.coursemanagementmodul.gui.CoursePostingPresentationModel;
import cw.coursemanagementmodul.gui.CoursePostingView;
import cw.coursemanagementmodul.gui.CoursePresentationModel;
import cw.coursemanagementmodul.gui.CourseView;
import cw.coursemanagementmodul.gui.HistoryPresentationModel;
import cw.coursemanagementmodul.gui.HistoryView;
import cw.coursemanagementmodul.gui.SubjectPresentationModel;
import cw.coursemanagementmodul.gui.SubjectView;
import cw.coursemanagementmodul.pojo.CourseParticipant;
import cw.coursemanagementmodul.pojo.CoursePosting;
import cw.coursemanagementmodul.pojo.manager.CourseParticipantManager;
import cw.customermanagementmodul.pojo.Customer;
import cw.customermanagementmodul.pojo.manager.CustomerManager;
/**
 *
 * @author André Salmhofer (CreativeWorkers)
 */
public class CourseManagementModul implements Modul{

    public void init() {
        CWMenuPanel menuPanel = MenuManager.getSideMenu();
        menuPanel.addCategory("Kursverwaltung", "course");
        
        JButton courseButton = new JButton("Kurse");
        courseButton.setIcon(CWUtils.loadIcon("cw/coursemanagementmodul/images/course.png"));
        courseButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                GUIManager.changeView(new CourseView(new CoursePresentationModel()));
            }
        });
        
        JButton activityButton = new JButton("Aktivität");
        activityButton.setIcon(CWUtils.loadIcon("cw/coursemanagementmodul/images/activity.png"));
        activityButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                GUIManager.changeView(new ActivityView(new ActivityPresentationModel()));
            }
        });
        
        JButton subjectButton = new JButton("Kursgegenstand");
        subjectButton.setIcon(CWUtils.loadIcon("cw/coursemanagementmodul/images/subject.png"));
        subjectButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                GUIManager.changeView(new SubjectView(new SubjectPresentationModel()));
            }
        });

        JButton accountingButton = new JButton("Gebührenlauf");
        accountingButton.setIcon(CWUtils.loadIcon("cw/coursemanagementmodul/images/accounting.png"));
        accountingButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                GUIManager.changeView(new CoursePostingView(new CoursePostingPresentationModel(new CoursePosting())));
            }
        });

        JButton accountingHistoryButton = new JButton("Kurshistorie");
        accountingHistoryButton.setIcon(CWUtils.loadIcon("cw/coursemanagementmodul/images/courseHistory.png"));
        accountingHistoryButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                GUIManager.changeView(new HistoryView(new HistoryPresentationModel()));
            }
        });

        CustomerManager.getInstance().addCascadeListener(new CascadeListener() {
            public void deleteAction(CascadeEvent evt) {
                Customer customer = new Customer();// = (Customer) evt.getObject();
                List<CourseParticipant> courseParticipants = CourseParticipantManager.getInstance().getAll(customer);

                for(CourseParticipant cp: courseParticipants) {
                    CourseParticipantManager.getInstance().delete(cp);
                }
            }
        });

        menuPanel.addItem(courseButton, "course");
        menuPanel.addItem(activityButton, "course");
        menuPanel.addItem(subjectButton, "course");
        menuPanel.addItem(accountingButton, "course");
        menuPanel.addItem(accountingHistoryButton, "course");

        //Dere Geier --> Bitte nicht löschen --> wird für Kursbuchungen benötigt!!!
        if(PostingCategoryManager.getInstance().get("Kurs-Buchung") == null) {
            PostingCategory category = new PostingCategory();
            category.setName("Kurs-Buchung");
            category.setKey("Kurs-Buchung");
            PostingCategoryManager.getInstance().save(category);
        }

        if(PostingCategoryManager.getInstance().get("Kurs-Buchung-Storno") == null) {
            PostingCategory category = new PostingCategory();
            category.setName("Kurs-Buchung-Storno");
            category.setKey("Kurs-Buchung-Storno");
            PostingCategoryManager.getInstance().save(category);
        }
    }

}

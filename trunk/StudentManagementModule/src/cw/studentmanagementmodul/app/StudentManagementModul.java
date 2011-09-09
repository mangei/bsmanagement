package cw.studentmanagementmodul.app;

import cw.boardingschoolmanagement.app.CWUtils;
import cw.studentmanagementmodul.gui.StudentClassManagementPresentationModel;
import cw.studentmanagementmodul.gui.StudentClassManagementView;
import java.awt.event.ActionEvent;
import java.util.List;
import javax.swing.AbstractAction;
import javax.swing.JButton;
import cw.boardingschoolmanagement.manager.GUIManager;
import cw.boardingschoolmanagement.manager.MenuManager;
import cw.boardingschoolmanagement.module.Module;
import cw.boardingschoolmanagement.persistence.CascadeEvent;
import cw.boardingschoolmanagement.persistence.CascadeListener;
import cw.customermanagementmodul.persistence.PMCustomer;
import cw.customermanagementmodul.persistence.model.CustomerModel;
import cw.studentmanagementmodul.pojo.Student;
import cw.studentmanagementmodul.pojo.StudentClass;
import cw.studentmanagementmodul.pojo.manager.StudentClassManager;
import cw.studentmanagementmodul.pojo.manager.StudentManager;

/**
 * Das Schuelermodul
 * @author CreativeWorkers.at
 */
public class StudentManagementModul
implements Module
{

    public void init() {

        MenuManager.getSideMenu().addCategory("Schule", "school");
        MenuManager.getSideMenu().addItem(new JButton(new AbstractAction("Klassen", CWUtils.loadIcon("cw/studentmanagementmodul/images/studentClass.png")) {

            public void actionPerformed(ActionEvent e) {
                GUIManager.changeView(new StudentClassManagementView(new StudentClassManagementPresentationModel()));
            }
        }), "school");

        // Wenn ein Kunde geloescht wird, wird der Student mitgeloescht
        PMCustomer.getInstance().addCascadeListener(new CascadeListener() {
            public void deleteAction(CascadeEvent evt) {
                CustomerModel customer = (CustomerModel) evt.getSource();
                Student student = StudentManager.getInstance().get(customer);
                if(student != null) {
                    StudentManager.getInstance().delete(student);
                }
            }
        });

        // Wenn eine Klasse geloescht wird, wird die Klasse der Studenten auf null gesetzt
        // und der Student inaktiv gesetzt
        StudentClassManager.getInstance().addCascadeListener(new CascadeListener() {
            public void deleteAction(CascadeEvent evt) {
                StudentClass studentClass = (StudentClass) evt.getSource();
                List<Student> students = StudentManager.getInstance().getAll(studentClass);
                for(int i=0, l=students.size(); i<l; i++) {
                    students.get(i).setStudentClass(null);
                    students.get(i).setActive(false);
                }
            }
        });

    }
}

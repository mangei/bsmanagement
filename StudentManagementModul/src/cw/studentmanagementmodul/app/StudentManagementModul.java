package cw.studentmanagementmodul.app;

import cw.boardingschoolmanagement.app.CWUtils;
import cw.boardingschoolmanagement.app.CascadeEvent;
import cw.boardingschoolmanagement.app.CascadeListener;
import cw.studentmanagementmodul.gui.StudentClassManagementPresentationModel;
import cw.studentmanagementmodul.gui.StudentClassManagementView;
import cw.boardingschoolmanagement.interfaces.Modul;
import java.awt.event.ActionEvent;
import java.util.List;
import javax.swing.AbstractAction;
import javax.swing.JButton;
import cw.boardingschoolmanagement.manager.GUIManager;
import cw.boardingschoolmanagement.manager.MenuManager;
import cw.customermanagementmodul.app.CustomerManagementModul;
import cw.customermanagementmodul.pojo.Customer;
import cw.customermanagementmodul.pojo.manager.CustomerManager;
import cw.studentmanagementmodul.pojo.Student;
import cw.studentmanagementmodul.pojo.StudentClass;
import cw.studentmanagementmodul.pojo.manager.StudentClassManager;
import cw.studentmanagementmodul.pojo.manager.StudentManager;
import java.util.ArrayList;

/**
 * Das Schülermodul
 * @author CreativeWorkers.at
 */
public class StudentManagementModul
implements Modul
{

    public void init() {

        MenuManager.getSideMenu().addCategory("Schule", "school");
        MenuManager.getSideMenu().addItem(new JButton(new AbstractAction("Klassen", CWUtils.loadIcon("cw/studentmanagementmodul/images/image.png")) {

            public void actionPerformed(ActionEvent e) {
                GUIManager.changeView(new StudentClassManagementView(new StudentClassManagementPresentationModel("Klassenverwaltung")).buildPanel());
            }
        }), "school");

        CustomerManager.getInstance().addCascadeListener(new CascadeListener() {
            public void deleteAction(CascadeEvent evt) {
                Customer customer = (Customer) evt.getObject();
                Student student = StudentManager.getInstance().get(customer);
                if(student != null) {
                    StudentManager.getInstance().delete(student);
                }
            }
        });

        StudentClassManager.getInstance().addCascadeListener(new CascadeListener() {
            public void deleteAction(CascadeEvent evt) {
                StudentClass studentClass = (StudentClass) evt.getObject();
                List<Student> students = StudentManager.getInstance().getAll(studentClass);
                for(int i=0, l=students.size(); i<l; i++) {
                    students.get(i).setStudentClass(null);
                    students.get(i).setActive(false);
                }
            }
        });

    }

    public List<Class> getDependencies() {
        List<Class> dependencies = new ArrayList<Class>();
        dependencies.add(CustomerManagementModul.class);
        return dependencies;
    }

}

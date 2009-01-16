package cw.studentmanagementmodul.pojo.manager;

import cw.boardingschoolmanagement.app.HibernateUtil;
import java.util.List;
import org.hibernate.Session;
import org.apache.log4j.Logger;
import cw.customermanagementmodul.pojo.Customer;
import cw.studentmanagementmodul.pojo.Student;

/**
 * Manages Students
 * @author CreativeWorkers.at
 */
public class StudentManager
{
    private static StudentManager instance;
    private static Logger logger = Logger.getLogger(StudentManager.class);

    private StudentManager() {
    }

    public static StudentManager getInstance() {
        if(instance == null) {
            instance = new StudentManager();
        }
        return instance;
    }

    public static void saveStudent(Student student) {
        System.out.println("student: " + student.getCustomer());
        System.out.println("student: " + student.getCustomer().getForename());
        Session ses = HibernateUtil.getSession();
        ses.beginTransaction();
        ses.saveOrUpdate(student);
        ses.getTransaction().commit();
    }

    public static List<Student> getStudents() {
        Session ses = HibernateUtil.getSession();
        ses.beginTransaction();
        List<Student> list = (List<Student>) HibernateUtil.getSession().createQuery("FROM Student").list();
        ses.getTransaction().commit();
        return list;
    }
    
    public static Student getStudent(Customer customer) {
        Session ses = HibernateUtil.getSession();
        ses.beginTransaction();
        Student student = (Student) ses.createQuery("SELECT s FROM Student s WHERE id=" + customer.getId()).uniqueResult();
        ses.getTransaction().commit();
        return student;
    }

    public static int getSize() {
        int size = 0;

        Session ses = HibernateUtil.getSession();
        size = ( (Long) ses.createQuery("SELECT COUNT(*) FROM Student").iterate().next() ).intValue();

        return size;
    }
    
    public static int getSizeActive() {
        int size = 0;

        Session ses = HibernateUtil.getSession();
        size = ( (Long) ses.createQuery("SELECT COUNT(*) FROM Student WHERE active=true").iterate().next() ).intValue();

        return size;
    }
}

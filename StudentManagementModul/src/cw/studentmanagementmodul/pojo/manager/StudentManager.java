package cw.studentmanagementmodul.pojo.manager;

import cw.boardingschoolmanagement.app.HibernateUtil;
import cw.boardingschoolmanagement.app.POJOManagerEvent;
import cw.boardingschoolmanagement.app.POJOManagerListener;
import cw.boardingschoolmanagement.pojo.manager.AbstractPOJOManager;
import java.util.List;
import org.apache.log4j.Logger;
import cw.customermanagementmodul.pojo.Customer;
import cw.customermanagementmodul.pojo.manager.CustomerManager;
import cw.studentmanagementmodul.pojo.Student;
import javax.persistence.NoResultException;

/**
 * Manages Students
 * @author CreativeWorkers.at
 */
public class StudentManager extends AbstractPOJOManager<Student> {

    private static StudentManager instance;
    private static Logger logger = Logger.getLogger(StudentManager.class);

    private StudentManager() {

        CustomerManager.getInstance().addPOJOManagerListener(new POJOManagerListener() {
            public void deleteAction(POJOManagerEvent evt) {
                Customer customer = (Customer) evt.getObject();
                Student student = get(customer);
                delete(student);
            }
        });

    }

    public static StudentManager getInstance() {
        if (instance == null) {
            instance = new StudentManager();
        }
        return instance;
    }

    public int size() {
        return ((Long) HibernateUtil.getEntityManager().createQuery("SELECT COUNT(*) FROM Student").getResultList().iterator().next()).intValue();
    }

    @Override
    public List<Student> getAll() {
        return HibernateUtil.getEntityManager().createQuery("FROM Student").getResultList();
    }

    public Student get(Customer customer) {
        Student student = null;
        try {
            student = (Student) HibernateUtil.getEntityManager().createQuery("FROM Student WHERE id=" + customer.getId()).getSingleResult();
        } catch (NoResultException ex) {
        }
        return student;
    }

    public int sizeActive() {
        return ((Long) HibernateUtil.getEntityManager().createQuery("SELECT COUNT(*) FROM Student WHERE active=true").getResultList().iterator().next()).intValue();
    }
}

package cw.studentmanagementmodul.pojo.manager;

import cw.boardingschoolmanagement.app.HibernateUtil;
import cw.boardingschoolmanagement.pojo.manager.AbstractPOJOManager;
import java.util.List;
import cw.customermanagementmodul.pojo.Customer;
import cw.studentmanagementmodul.pojo.Student;
import cw.studentmanagementmodul.pojo.StudentClass;
import java.util.logging.Logger;
import javax.persistence.NoResultException;

/**
 * Manages Students
 * @author CreativeWorkers.at
 */
public class StudentManager extends AbstractPOJOManager<Student> {

    private static StudentManager instance;
    private static Logger logger = Logger.getLogger(StudentManager.class.getName());

    private StudentManager() {
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

    public List<Student> getAllActive() {
        return HibernateUtil.getEntityManager().createQuery("FROM Student WHERE customer.active=true").getResultList();
    }
    
    public List<Student> getAll(StudentClass studentClass) {
        return HibernateUtil.getEntityManager().createQuery("FROM Student WHERE studentClass.id=" + studentClass.getId()).getResultList();
    }

    public List<Student> getAllActive(StudentClass studentClass) {
        return HibernateUtil.getEntityManager().createQuery("FROM Student WHERE studentClass.id=" + studentClass.getId() + " AND customer.active=true").getResultList();
    }

    public Student get(Customer customer) {
        Student student = null;
        try {
            student = (Student) HibernateUtil.getEntityManager().createQuery("FROM Student WHERE customer.id=" + customer.getId()).getSingleResult();
        } catch (NoResultException ex) {
        }
        return student;
    }

    public int sizeActive() {
        return ((Long) HibernateUtil.getEntityManager().createQuery("SELECT COUNT(*) FROM Student WHERE active=true").getResultList().iterator().next()).intValue();
    }
}

package cw.coursemanagementmodul.pojo.manager;

import cw.boardingschoolmanagement.app.HibernateUtil;
import cw.boardingschoolmanagement.pojo.manager.AbstractPOJOManager;
import cw.coursemanagementmodul.pojo.Activity;
import cw.coursemanagementmodul.pojo.Course;
import cw.coursemanagementmodul.pojo.CourseAddition;
import cw.coursemanagementmodul.pojo.CourseParticipant;
import cw.coursemanagementmodul.pojo.Subject;
import cw.customermanagementmodul.pojo.Customer;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.NoResultException;

/**
 *
 * @author André Salmhofer
 */
public class CourseParticipantManager extends AbstractPOJOManager<CourseParticipant> {

    private static CourseParticipantManager instance;

    private CourseParticipantManager() {
    }

    public static CourseParticipantManager getInstance() {
        if(instance == null) {
            instance = new CourseParticipantManager();
        }
        return instance;
    }

    @Override
    public List<CourseParticipant> getAll() {
        return HibernateUtil.getEntityManager().createQuery("FROM CourseParticipant c order by c.customer.surname;").getResultList();
    }

    public List<CourseParticipant> getAll(Customer customer) {
        return HibernateUtil.getEntityManager().createQuery("FROM CourseParticipant c where c.customer.id = " + customer.getId()).getResultList();
    }
    
    public List<CourseParticipant> getAll(Course course) {
        List<CourseParticipant> myList =  HibernateUtil.getEntityManager().createQuery("FROM CourseParticipant where customer.active = true order by customer.surname").getResultList();
        
        List<CourseParticipant> list2 = new ArrayList<CourseParticipant>();
        for(CourseParticipant cp : myList) {
            for(CourseAddition ca: cp.getCourseList()) {
                if(ca.getCourse().getId() == course.getId()) {
                    list2.add(cp);
                }
            }
        }
        return list2;
    }

    public List<CourseParticipant> getAll(Activity activity) {
        List<CourseParticipant> myList =  HibernateUtil.getEntityManager().createQuery("FROM CourseParticipant where customer.active = true").getResultList();

        List<CourseParticipant> list2 = new ArrayList<CourseParticipant>();
        for(CourseParticipant cp : myList) {
            for(CourseAddition ca: cp.getCourseList()) {
                if(ca.getActivities().contains(activity)) {
                    list2.add(cp);
                }
            }
        }
        return list2;
    }

    public List<CourseParticipant> getAll(Subject subject) {
        List<CourseParticipant> myList =  HibernateUtil.getEntityManager().createQuery("FROM CourseParticipant where customer.active = true").getResultList();

        List<CourseParticipant> list2 = new ArrayList<CourseParticipant>();
        for(CourseParticipant cp : myList) {
            for(CourseAddition ca: cp.getCourseList()) {
                if(ca.getSubjects().contains(subject)) {
                    list2.add(cp);
                }
            }
        }
        return list2;
    }

    /**
     * Abfrage ob eine Kunde ein Kursteilnehmer ist
     * @param customer
     * @return Kursteilnehmer - Kunde ist ein Kursteilnehmer, null - Kunde ist kein Kursteilnehmer
     */
    public CourseParticipant get(Customer customer) {
        CourseParticipant coursePart = null;
        try {
            coursePart = (CourseParticipant) HibernateUtil.getEntityManager().createQuery("FROM CourseParticipant where customer.active = true AND customer.id=" + customer.getId()).getSingleResult();
        } catch (NoResultException ex) {
        }
        return coursePart;
    }

    @Override
    public int size() {
        return ( (Long) HibernateUtil.getEntityManager().createQuery("SELECT COUNT(*) FROM CourseParticipant where customer.active = true").getResultList().iterator().next() ).intValue();
    }
}

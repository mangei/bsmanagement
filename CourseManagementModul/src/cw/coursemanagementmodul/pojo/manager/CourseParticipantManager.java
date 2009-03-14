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
import org.apache.log4j.Logger;

/**
 *
 * @author André Salmhofer
 */
public class CourseParticipantManager extends AbstractPOJOManager<CourseParticipant> {

    private static CourseParticipantManager instance;
    private static Logger logger = Logger.getLogger(CourseParticipantManager.class);

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
        return HibernateUtil.getEntityManager().createQuery("FROM CourseParticipant").getResultList();
    }

    public List<CourseParticipant> getAll(Customer customer) {
        return HibernateUtil.getEntityManager().createQuery("FROM CourseParticipant c where c.customer.id = " + customer.getId()).getResultList();
    }
    
    public List<CourseParticipant> getAll(Course course) {
        List<CourseParticipant> myList =  HibernateUtil.getEntityManager().createQuery("FROM CourseParticipant").getResultList();
        
        List<CourseParticipant> list2 = new ArrayList<CourseParticipant>();
        for(CourseParticipant cp : myList) {
            for(CourseAddition ca: cp.getCourseList()) {
                if(ca.getCourse().getId() == course.getId()) {
                    System.out.println("SAME_COURSE___________________IN");
                    list2.add(cp);
                }
            }
        }
        return list2;
    }

    public List<CourseParticipant> getAll(Activity activity) {
        List<CourseParticipant> myList =  HibernateUtil.getEntityManager().createQuery("FROM CourseParticipant").getResultList();

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
        List<CourseParticipant> myList =  HibernateUtil.getEntityManager().createQuery("FROM CourseParticipant").getResultList();

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

    public CourseParticipant get(Customer customer) {
        CourseParticipant coursePart = null;
        try {
            coursePart = (CourseParticipant) HibernateUtil.getEntityManager().createQuery("FROM CourseParticipant WHERE customer.id=" + customer.getId()).getSingleResult();
        } catch (NoResultException ex) {
        }
        return coursePart;
    }

    @Override
    public int size() {
        return ( (Long) HibernateUtil.getEntityManager().createQuery("SELECT COUNT(*) FROM CourseParticipant").getResultList().iterator().next() ).intValue();
    }
}

package cw.coursemanagementmodul.pojo.manager;

import java.util.List;

import cw.boardingschoolmanagement.persistence.AbstractPersistenceManager;
import cw.boardingschoolmanagement.persistence.HibernateUtil;
import cw.coursemanagementmodul.pojo.Course;

/**
 *
 * @author André Salmhofer
 */
public class CourseManager extends AbstractPersistenceManager<Course> {
    
    private static CourseManager instance;

    private CourseManager() {
    }

    public static CourseManager getInstance() {
        if(instance == null) {
            instance = new CourseManager();
        }
        return instance;
    }

    @Override
    public List<Course> getAll() {
        return HibernateUtil.getEntityManager().createQuery("FROM Course").getResultList();
    }

    @Override
    public int size() {
        return ( (Long) HibernateUtil.getEntityManager().createQuery("SELECT COUNT(*) FROM Customer").getResultList().iterator().next() ).intValue();
    }
}

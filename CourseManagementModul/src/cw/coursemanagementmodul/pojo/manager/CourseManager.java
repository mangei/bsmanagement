package cw.coursemanagementmodul.pojo.manager;

import cw.boardingschoolmanagement.app.HibernateUtil;
import cw.boardingschoolmanagement.pojo.manager.AbstractPOJOManager;
import java.util.List;
import cw.coursemanagementmodul.pojo.Course;
import org.apache.log4j.Logger;

/**
 *
 * @author André Salmhofer
 */
public class CourseManager extends AbstractPOJOManager<Course> {
    
    private static CourseManager instance;
    private static Logger logger = Logger.getLogger(CourseManager.class);

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

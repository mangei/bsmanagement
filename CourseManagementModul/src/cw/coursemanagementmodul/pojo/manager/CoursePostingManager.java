package cw.coursemanagementmodul.pojo.manager;

import cw.boardingschoolmanagement.app.HibernateUtil;
import cw.boardingschoolmanagement.pojo.manager.AbstractPOJOManager;
import cw.coursemanagementmodul.pojo.Activity;
import cw.coursemanagementmodul.pojo.CoursePosting;
import java.util.List;
import org.apache.log4j.Logger;

/**
 *
 * @author André Salmhofer
 */
public class CoursePostingManager extends AbstractPOJOManager<CoursePosting> {

    private static CoursePostingManager instance;
    private static Logger logger = Logger.getLogger(CoursePostingManager.class);

    private CoursePostingManager() {
    }

    public static CoursePostingManager getInstance() {
        if(instance == null) {
            instance = new CoursePostingManager();
        }
        return instance;
    }

    @Override
    public List<CoursePosting> getAll() {
        return HibernateUtil.getEntityManager().createQuery("FROM CoursePosting").getResultList();
    }

    @Override
    public int size() {
        return ( (Long) HibernateUtil.getEntityManager().createQuery("SELECT COUNT(*) FROM CoursePosting").getResultList().iterator().next() ).intValue();
    }
}

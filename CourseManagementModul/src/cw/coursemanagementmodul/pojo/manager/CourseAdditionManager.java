package cw.coursemanagementmodul.pojo.manager;

import cw.boardingschoolmanagement.app.HibernateUtil;
import cw.boardingschoolmanagement.pojo.manager.AbstractPOJOManager;
import cw.coursemanagementmodul.pojo.CourseAddition;
import java.util.List;
import org.apache.log4j.Logger;

/**
 *
 * @author André Salmhofer
 */
public class CourseAdditionManager extends AbstractPOJOManager<CourseAddition> {

    private static CourseAdditionManager instance;
    private static Logger logger = Logger.getLogger(CourseAdditionManager.class);

    private CourseAdditionManager() {
    }

    public static CourseAdditionManager getInstance() {
        if(instance == null) {
            instance = new CourseAdditionManager();
        }
        return instance;
    }

    @Override
    public List<CourseAddition> getAll() {
        return HibernateUtil.getEntityManager().createQuery("FROM CourseAddition").getResultList();
    }

    @Override
    public int size() {
        return ( (Long) HibernateUtil.getEntityManager().createQuery("SELECT COUNT(*) FROM CourseAddition").getResultList().iterator().next() ).intValue();
    }
}

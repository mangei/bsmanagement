package cw.coursemanagementmodul.pojo.manager;

import java.util.List;

import cw.boardingschoolmanagement.app.HibernateUtil;
import cw.boardingschoolmanagement.pojo.manager.AbstractPersistenceManager;
import cw.coursemanagementmodul.pojo.CourseAddition;

/**
 *
 * @author André Salmhofer
 */
public class CourseAdditionManager extends AbstractPersistenceManager<CourseAddition> {

    private static CourseAdditionManager instance;

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

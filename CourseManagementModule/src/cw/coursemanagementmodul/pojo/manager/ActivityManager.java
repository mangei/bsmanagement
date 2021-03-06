package cw.coursemanagementmodul.pojo.manager;

import java.util.List;

import cw.boardingschoolmanagement.persistence.AbstractPersistenceManager;
import cw.boardingschoolmanagement.persistence.HibernateUtil;
import cw.coursemanagementmodul.pojo.Activity;

/**
 *
 * @author André Salmhofer
 */
public class ActivityManager extends AbstractPersistenceManager<Activity> {

    private static ActivityManager instance;

    private ActivityManager() {
    }

    public static ActivityManager getInstance() {
        if(instance == null) {
            instance = new ActivityManager();
        }
        return instance;
    }

    @Override
    public List<Activity> getAll() {
        return HibernateUtil.getEntityManager().createQuery("FROM Activity").getResultList();
    }

    @Override
    public int size() {
        return ( (Long) HibernateUtil.getEntityManager().createQuery("SELECT COUNT(*) FROM Activity").getResultList().iterator().next() ).intValue();
    }
}

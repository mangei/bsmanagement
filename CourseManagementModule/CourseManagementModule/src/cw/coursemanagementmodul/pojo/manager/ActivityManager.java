package cw.coursemanagementmodul.pojo.manager;

import cw.boardingschoolmanagement.app.HibernateUtil;
import cw.boardingschoolmanagement.pojo.manager.AbstractPOJOManager;
import cw.coursemanagementmodul.pojo.Activity;
import java.util.List;

/**
 *
 * @author André Salmhofer
 */
public class ActivityManager extends AbstractPOJOManager<Activity> {

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

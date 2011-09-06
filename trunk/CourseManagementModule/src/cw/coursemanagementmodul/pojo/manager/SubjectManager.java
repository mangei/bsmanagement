package cw.coursemanagementmodul.pojo.manager;

import java.util.List;

import cw.boardingschoolmanagement.app.HibernateUtil;
import cw.boardingschoolmanagement.pojo.manager.AbstractPersistenceManager;
import cw.coursemanagementmodul.pojo.Subject;

/**
 *
 * @author André Salmhofer
 */
public class SubjectManager extends AbstractPersistenceManager<Subject> {

    private static SubjectManager instance;

    private SubjectManager() {
    }

    public static SubjectManager getInstance() {
        if(instance == null) {
            instance = new SubjectManager();
        }
        return instance;
    }

    @Override
    public List<Subject> getAll() {
        return HibernateUtil.getEntityManager().createQuery("FROM Subject").getResultList();
    }

    @Override
    public int size() {
        return ( (Long) HibernateUtil.getEntityManager().createQuery("SELECT COUNT(*) FROM Subject").getResultList().iterator().next() ).intValue();
    }
}

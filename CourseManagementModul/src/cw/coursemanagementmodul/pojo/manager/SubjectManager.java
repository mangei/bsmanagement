package cw.coursemanagementmodul.pojo.manager;

import cw.boardingschoolmanagement.app.HibernateUtil;
import cw.boardingschoolmanagement.pojo.manager.AbstractPOJOManager;
import cw.coursemanagementmodul.pojo.Subject;
import java.util.List;
import org.apache.log4j.Logger;

/**
 *
 * @author André Salmhofer
 */
public class SubjectManager extends AbstractPOJOManager<Subject> {

    private static SubjectManager instance;
    private static Logger logger = Logger.getLogger(SubjectManager.class);

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

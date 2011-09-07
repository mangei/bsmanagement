package cw.coursemanagementmodul.pojo.manager;

import java.util.List;

import cw.boardingschoolmanagement.persistence.HibernateUtil;
import cw.boardingschoolmanagement.pojo.manager.AbstractPersistenceManager;
import cw.coursemanagementmodul.pojo.PostingRun;

/**
 *
 * @author André Salmhofer
 */
public class PostingRunManager extends AbstractPersistenceManager<PostingRun> {

    private static PostingRunManager instance;

    private PostingRunManager() {
    }

    public static PostingRunManager getInstance() {
        if(instance == null) {
            instance = new PostingRunManager();
        }
        return instance;
    }

    @Override
    public List<PostingRun> getAll() {
        return HibernateUtil.getEntityManager().createQuery("FROM PostingRun").getResultList();
    }

    @Override
    public int size() {
        return ( (Long) HibernateUtil.getEntityManager().createQuery("SELECT COUNT(*) FROM PostingRun").getResultList().iterator().next() ).intValue();
    }
}

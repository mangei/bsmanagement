package cw.customermanagementmodul.pojo.manager;

import cw.boardingschoolmanagement.app.HibernateUtil;
import cw.boardingschoolmanagement.pojo.manager.AbstractPOJOManager;
import java.util.List;
import org.apache.log4j.Logger;
import cw.customermanagementmodul.pojo.ReversePosting;

/**
 *
 * @author CreativeWorkers.at
 */
public class ReversePostingManager extends AbstractPOJOManager<ReversePosting>
{

    private static ReversePostingManager instance;
    private static Logger logger = Logger.getLogger(ReversePostingManager.class);

    private ReversePostingManager() {
    }

    public static ReversePostingManager getInstance() {
        if(instance == null) {
            instance = new ReversePostingManager();
        }
        return instance;
    }

    public int size() {
        return ( (Long) HibernateUtil.getEntityManager().createQuery("SELECT COUNT(*) FROM ReversePosting").getResultList().iterator().next() ).intValue();
    }

    @Override
    public List<ReversePosting> getAll() {
        return HibernateUtil.getEntityManager().createQuery("FROM ReversePosting").getResultList();
    }
    
}

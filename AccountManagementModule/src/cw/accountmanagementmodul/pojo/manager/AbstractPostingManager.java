package cw.accountmanagementmodul.pojo.manager;

import cw.accountmanagementmodul.pojo.AbstractPosting;
import cw.accountmanagementmodul.pojo.Account;
import cw.boardingschoolmanagement.app.HibernateUtil;
import cw.boardingschoolmanagement.pojo.manager.AbstractPOJOManager;
import java.util.List;
import java.util.logging.Logger;

/**
 *
 * @author CreativeWorkers.at
 */
public class AbstractPostingManager extends AbstractPOJOManager<AbstractPosting>
{

    private static AbstractPostingManager instance;
    private static Logger logger = Logger.getLogger(AbstractPostingManager.class.getName());

    private AbstractPostingManager() {
    }

    public static AbstractPostingManager getInstance() {
        if(instance == null) {
            instance = new AbstractPostingManager();
        }
        return instance;
    }

    public int size() {
        return ( (Long) HibernateUtil.getEntityManager().createQuery("SELECT COUNT(*) FROM AbstractPosting").getResultList().iterator().next() ).intValue();
    }

    @Override
    public List<AbstractPosting> getAll() {
        return HibernateUtil.getEntityManager().createQuery("FROM AbstractPosting").getResultList();
    }
    
    public List<AbstractPosting> getAll(Account a) {
        return HibernateUtil.getEntityManager().createQuery("FROM AbstractPosting p WHERE p.account.id=" + a.getId()).getResultList();
    }

}

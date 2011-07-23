package cw.accountmanagementmodul.pojo.manager;

import cw.accountmanagementmodul.pojo.Account;
import cw.accountmanagementmodul.pojo.Bailment;
import cw.boardingschoolmanagement.app.HibernateUtil;
import cw.boardingschoolmanagement.pojo.manager.AbstractPOJOManager;
import java.util.List;
import cw.accountmanagementmodul.pojo.AccountPosting;
import java.util.logging.Logger;

/**
 *
 * @author CreativeWorkers.at
 */
public class BailmentManager extends AbstractPOJOManager<Bailment>
{

    private static BailmentManager instance;
    private static Logger logger = Logger.getLogger(BailmentManager.class.getName());

    private BailmentManager() {
    }

    public static BailmentManager getInstance() {
        if(instance == null) {
            instance = new BailmentManager();
        }
        return instance;
    }

    public int size() {
        return ( (Long) HibernateUtil.getEntityManager().createQuery("SELECT COUNT(*) FROM Bailment").getResultList().iterator().next() ).intValue();
    }

    @Override
    public List<Bailment> getAll() {
        return HibernateUtil.getEntityManager().createQuery("FROM Bailment").getResultList();
    }
    
    public List<AccountPosting> getAll(Account a) {
        return HibernateUtil.getEntityManager().createQuery("FROM Bailment b WHERE b.account.id=" + a.getId()).getResultList();
    }

}

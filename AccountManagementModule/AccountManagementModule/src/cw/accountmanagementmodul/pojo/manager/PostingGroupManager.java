package cw.accountmanagementmodul.pojo.manager;

import cw.accountmanagementmodul.pojo.Account;
import cw.boardingschoolmanagement.app.HibernateUtil;
import cw.boardingschoolmanagement.pojo.manager.AbstractPOJOManager;
import java.util.List;
import cw.accountmanagementmodul.pojo.PostingGroup;
import java.util.logging.Logger;

/**
 *
 * @author CreativeWorkers.at
 */
public class PostingGroupManager extends AbstractPOJOManager<PostingGroup>
{

    private static PostingGroupManager instance;
    private static Logger logger = Logger.getLogger(PostingGroupManager.class.getName());

    private PostingGroupManager() {
    }

    public static PostingGroupManager getInstance() {
        if(instance == null) {
            instance = new PostingGroupManager();
        }
        return instance;
    }

    public int size() {
        return ( (Long) HibernateUtil.getEntityManager().createQuery("SELECT COUNT(*) FROM PostingGroup").getResultList().iterator().next() ).intValue();
    }

    @Override
    public List<PostingGroup> getAll() {
        return HibernateUtil.getEntityManager().createQuery("FROM PostingGroup").getResultList();
    }
    
    public List<PostingGroup> getAll(Account a) {
        return HibernateUtil.getEntityManager().createQuery("FROM PostingGroup a WHERE a.account.id=" + a.getId()).getResultList();
    }

}

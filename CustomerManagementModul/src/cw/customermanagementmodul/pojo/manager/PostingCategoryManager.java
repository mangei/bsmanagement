package cw.customermanagementmodul.pojo.manager;

import cw.boardingschoolmanagement.app.HibernateUtil;
import cw.boardingschoolmanagement.pojo.manager.AbstractPOJOManager;
import java.util.Date;
import java.util.List;
import org.apache.log4j.Logger;
import cw.customermanagementmodul.pojo.Posting;
import cw.customermanagementmodul.pojo.Customer;
import cw.customermanagementmodul.pojo.PostingCategory;

/**
 *
 * @author CreativeWorkers.at
 */
public class PostingCategoryManager extends AbstractPOJOManager<PostingCategory>
{

    private static PostingCategoryManager instance;
    private static Logger logger = Logger.getLogger(PostingCategoryManager.class);

    private PostingCategoryManager() {
    }

    public static PostingCategoryManager getInstance() {
        if(instance == null) {
            instance = new PostingCategoryManager();
        }
        return instance;
    }
    
    public int size() {
        return ( (Long) HibernateUtil.getEntityManager().createQuery("SELECT COUNT(*) FROM PostingCategory").getResultList().iterator().next() ).intValue();
    }

    @Override
    public List<PostingCategory> getAll() {
        return HibernateUtil.getEntityManager().createQuery("FROM PostingCategory").getResultList();
    }

}

package cw.customermanagementmodul.pojo.manager;

import cw.boardingschoolmanagement.app.HibernateUtil;
import cw.boardingschoolmanagement.pojo.manager.AbstractPOJOManager;
import java.util.Date;
import java.util.List;
import org.apache.log4j.Logger;
import cw.customermanagementmodul.pojo.Posting;
import cw.customermanagementmodul.pojo.PostingCategory;
import cw.customermanagementmodul.pojo.Customer;

/**
 *
 * @author CreativeWorkers.at
 */
public class PostingManager extends AbstractPOJOManager<Posting>
{

    private static PostingManager instance;
    private static Logger logger = Logger.getLogger(PostingManager.class);

    private PostingManager() {
    }

    public static PostingManager getInstance() {
        if(instance == null) {
            instance = new PostingManager();
        }
        return instance;
    }
    
    /**
     * Cancel an accounting.
     * @param a Accounting
     * @return the new Accounting
     */
    public Posting cancelAnAccounting(Posting a) {
        if(a != null && a.getId() != null) {
            Posting cancelA = new Posting();
            cancelA.setCustomer(a.getCustomer());
            cancelA.setLiabilities(!a.isLiabilities());
            cancelA.setAmount(a.getAmount());
            cancelA.setPostingDate(new Date());
            cancelA.setPostingCategory(a.getPostingCategory());
            cancelA.setDescription("STORNO: " + a.getDescription());
            save(cancelA);
            return cancelA;
        }
        return null;
    }

    public int size() {
        return ( (Long) HibernateUtil.getEntityManager().createQuery("SELECT COUNT(*) FROM Posting").getResultList().iterator().next() ).intValue();
    }

    @Override
    public List<Posting> getAll() {
        return HibernateUtil.getEntityManager().createQuery("FROM Posting").getResultList();
    }
    
    public List<Posting> getAll(Customer c) {
        return HibernateUtil.getEntityManager().createQuery("FROM Posting a WHERE a.customer.id=" + c.getId()).getResultList();
    }

    public List<Posting> getAll(PostingCategory postingCategory) {
        return HibernateUtil.getEntityManager().createQuery("FROM Posting a WHERE a.postingCategory.id=" + postingCategory.getId()).getResultList();
    }

    public List<String> getYears() {
        return HibernateUtil.getEntityManager().createQuery("SELECT DISTINCT str(YEAR(p.postingEntryDate)) FROM Posting p WHERE p.postingEntryDate IS NOT NULL").getResultList();
    }
    
    public List<String> getYears(Customer customer) {
        return HibernateUtil.getEntityManager().createQuery("SELECT DISTINCT str(YEAR(p.postingEntryDate)) FROM Posting p WHERE p.customer.id="+customer.getId()+" AND p.postingEntryDate IS NOT NULL").getResultList();
    }

}

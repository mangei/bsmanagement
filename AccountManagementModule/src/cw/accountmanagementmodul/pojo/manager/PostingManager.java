package cw.accountmanagementmodul.pojo.manager;

import cw.accountmanagementmodul.pojo.Account;
import cw.boardingschoolmanagement.persistence.AbstractPersistenceManager;
import cw.boardingschoolmanagement.persistence.HibernateUtil;
import java.util.Date;
import java.util.List;
import cw.accountmanagementmodul.pojo.AccountPosting;
import java.util.logging.Logger;

/**
 *
 * @author CreativeWorkers.at
 */
public class PostingManager extends AbstractPersistenceManager<AccountPosting>
{

    private static PostingManager instance;
    private static Logger logger = Logger.getLogger(PostingManager.class.getName());

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
    public AccountPosting cancelAnAccounting(AccountPosting a) {
        if(a != null && a.getId() != null) {
            AccountPosting cancelA = new AccountPosting();
            cancelA.setAccount(a.getAccount());
            cancelA.setAmount(a.getAmount() * -1);
            cancelA.setCreationDate(new Date());
            cancelA.setName("STORNO: " + a.getName());
            save(cancelA);
            return cancelA;
        }
        return null;
    }

    public int size() {
        return ( (Long) HibernateUtil.getEntityManager().createQuery("SELECT COUNT(*) FROM Posting").getResultList().iterator().next() ).intValue();
    }

    @Override
    public List<AccountPosting> getAll() {
        return HibernateUtil.getEntityManager().createQuery("FROM Posting").getResultList();
    }
    
    public List<AccountPosting> getAll(Account a) {
        if(a == null) {
            throw new NullPointerException("account is null");
        }

        return HibernateUtil.getEntityManager().createQuery("FROM Posting p WHERE p.account.id=" + a.getId()).getResultList();
    }

    public List<String> getYears() {
        return HibernateUtil.getEntityManager().createQuery("SELECT DISTINCT str(YEAR(p.postingEntryDate)) FROM Posting p WHERE p.postingEntryDate IS NOT NULL").getResultList();
    }
    
    public List<String> getYears(Account account) {
        if(account == null) {
            throw new NullPointerException("account is null");
        }
        
        return HibernateUtil.getEntityManager().createQuery("SELECT DISTINCT str(YEAR(p.postingEntryDate)) FROM Posting p WHERE p.account.id="+account.getId()+" AND p.postingEntryDate IS NOT NULL").getResultList();
    }

}

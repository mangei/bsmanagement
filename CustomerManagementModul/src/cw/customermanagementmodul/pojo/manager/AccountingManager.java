package cw.customermanagementmodul.pojo.manager;

import cw.boardingschoolmanagement.app.HibernateUtil;
import cw.boardingschoolmanagement.pojo.manager.AbstractPOJOManager;
import java.util.Date;
import java.util.List;
import org.apache.log4j.Logger;
import cw.customermanagementmodul.pojo.Accounting;
import cw.customermanagementmodul.pojo.Customer;

/**
 *
 * @author CreativeWorkers.at
 */
public class AccountingManager extends AbstractPOJOManager<Accounting>
{

    private static AccountingManager instance;
    private static Logger logger = Logger.getLogger(AccountingManager.class);

    private AccountingManager() {
    }

    public static AccountingManager getInstance() {
        if(instance == null) {
            instance = new AccountingManager();
        }
        return instance;
    }
    
    /**
     * Cancel an accounting.
     * @param a Accounting
     * @return the new Accounting
     */
    public Accounting cancelAnAccounting(Accounting a) {
        if(a != null && a.getId() != null) {
            Accounting cancelA = new Accounting();
            cancelA.setCustomer(a.getCustomer());
            cancelA.setLiabilities(!a.isLiabilities());
            cancelA.setAmount(a.getAmount());
            cancelA.setAccountingDate(new Date());
            cancelA.setCategory(a.getCategory());
            cancelA.setDescription("STORNO: " + a.getDescription());
            save(cancelA);
            return cancelA;
        }
        return null;
    }

    public int size() {
        return ( (Long) HibernateUtil.getEntityManager().createQuery("SELECT COUNT(*) FROM Accounting").getResultList().iterator().next() ).intValue();
    }

    @Override
    public List<Accounting> getAll() {
        return HibernateUtil.getEntityManager().createQuery("FROM Accounting").getResultList();
    }
    
    public List<Accounting> getAll(Customer c) {
        return HibernateUtil.getEntityManager().createQuery("FROM Accounting a WHERE a.costumer.id=" + c.getId()).getResultList();
    }

    
    
}

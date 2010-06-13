package cw.accountmanagementmodul.pojo.manager;

import cw.accountmanagementmodul.pojo.Account;
import cw.boardingschoolmanagement.app.HibernateUtil;
import cw.boardingschoolmanagement.pojo.manager.AbstractPOJOManager;
import java.util.List;
import cw.customermanagementmodul.pojo.Customer;
import java.util.logging.Logger;
import javax.persistence.NoResultException;

/**
 *
 * @author CreativeWorkers.at
 */
public class AccountManager extends AbstractPOJOManager<Account>
{

    private static AccountManager instance;
    private static Logger logger = Logger.getLogger(AccountManager.class.getName());

    private AccountManager() {
    }

    public static AccountManager getInstance() {
        if(instance == null) {
            instance = new AccountManager();
        }
        return instance;
    }

    public int size() {
        return ( (Long) HibernateUtil.getEntityManager().createQuery("SELECT COUNT(*) FROM Account").getResultList().iterator().next() ).intValue();
    }

    @Override
    public List<Account> getAll() {
        return HibernateUtil.getEntityManager().createQuery("FROM Account").getResultList();
    }
    
    public Account get(Customer c) {
        Account account = null;
        try {
            account = (Account) HibernateUtil.getEntityManager().createQuery("FROM Account a WHERE a.customer.id=" + c.getId()).getSingleResult();
        } catch (NoResultException e) {
        }
        return account;
    }

}
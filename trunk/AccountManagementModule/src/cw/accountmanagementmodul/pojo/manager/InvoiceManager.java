package cw.accountmanagementmodul.pojo.manager;

import cw.accountmanagementmodul.pojo.Account;
import cw.accountmanagementmodul.pojo.Invoice;
import cw.boardingschoolmanagement.app.HibernateUtil;
import cw.boardingschoolmanagement.pojo.manager.AbstractPersistenceManager;
import java.util.List;
import java.util.logging.Logger;

/**
 *
 * @author CreativeWorkers.at
 */
public class InvoiceManager extends AbstractPersistenceManager<Invoice>
{

    private static InvoiceManager instance;
    private static Logger logger = Logger.getLogger(InvoiceManager.class.getName());

    private InvoiceManager() {
    }

    public static InvoiceManager getInstance() {
        if(instance == null) {
            instance = new InvoiceManager();
        }
        return instance;
    }

    public int size() {
        return ( (Long) HibernateUtil.getEntityManager().createQuery("SELECT COUNT(*) FROM Invoice").getResultList().iterator().next() ).intValue();
    }

    public int size(Account a) {
        return ( (Long) HibernateUtil.getEntityManager().createQuery("SELECT COUNT(*) FROM Invoice i WHERE i.account.id=" + a.getId()).getResultList().iterator().next() ).intValue();
    }

    @Override
    public List<Invoice> getAll() {
        return HibernateUtil.getEntityManager().createQuery("FROM Invoice").getResultList();
    }
    
    public List<Invoice> getAll(Account a) {
        return HibernateUtil.getEntityManager().createQuery("FROM Invoice i WHERE i.account.id=" + a.getId()).getResultList();
    }

}

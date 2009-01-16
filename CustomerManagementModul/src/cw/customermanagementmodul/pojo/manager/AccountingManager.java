package cw.customermanagementmodul.pojo.manager;

import cw.boardingschoolmanagement.app.HibernateUtil;
import com.jgoodies.binding.beans.Model;
import java.util.Date;
import java.util.List;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;
import cw.customermanagementmodul.pojo.Accounting;
import cw.customermanagementmodul.pojo.Customer;

/**
 *
 * @author CreativeWorkers.at
 */
public class AccountingManager extends Model
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
    
    public static void saveAccounting(Accounting a) {
        Session ses = HibernateUtil.getSession();
        ses.beginTransaction();
        ses.saveOrUpdate(a);
        ses.getTransaction().commit();
    }
    
    public static void removeAccounting(Accounting a) {

    }

    /**
     * Cancel an accounting.
     * @param a Accounting
     */
    public static Accounting cancelAnAccounting(Accounting a) {
        if(a != null && a.getId() != null) {
            Accounting cancelA = new Accounting();
            cancelA.setCustomer(a.getCustomer());
            cancelA.setLiabilities(!a.isLiabilities());
            cancelA.setAmount(a.getAmount());
            cancelA.setAccountingDate(new Date());
            cancelA.setCategory(a.getCategory());
            cancelA.setDescription("STORNO: " + a.getDescription());
            saveAccounting(cancelA);
            return cancelA;
        }
        return null;
    }
    
    public static Accounting getAccounting(int id) {
        if(id < 0)
            return null;

        Accounting a = (Accounting) HibernateUtil.getSession().createQuery("SELECT a FROM Accounting a WHERE id=" + id).uniqueResult();
        return a;
    }
    
    public static List<Accounting> getAccountings() {
        Session ses = HibernateUtil.getSession();
        Transaction tran = ses.beginTransaction();
        List list = ses.createQuery("FROM Accounting").list();
        tran.commit();
        return list;
    }
    
    public static List<Accounting> getAccountings(Customer c) {
        Session ses = HibernateUtil.getSession();
        Transaction tran = ses.beginTransaction();
        List list = ses.createQuery("SELECT a FROM Accounting a WHERE a.customer.id = " + c.getId()).list();
        tran.commit();
        return list;
    }
    
}

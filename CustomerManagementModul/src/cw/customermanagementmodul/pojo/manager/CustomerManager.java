package cw.customermanagementmodul.pojo.manager;

import cw.boardingschoolmanagement.app.HibernateUtil;
import com.jgoodies.binding.beans.Model;
import java.util.List;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;
import cw.customermanagementmodul.pojo.Customer;
import cw.customermanagementmodul.pojo.Group;

/**
 * Manages Customers
 * @author CreativeWorkers.at
 */
public class CustomerManager extends Model {

    private static CustomerManager instance;
    private static Logger logger = Logger.getLogger(CustomerManager.class);

    private CustomerManager() {
    }

    public static CustomerManager getInstance() {
        if(instance == null) {
            instance = new CustomerManager();
        }
        return instance;
    }

    /**
     * Saves a customer
     * @param customer Customer
     */
    public static void saveCustomer(Customer customer) {
        Session ses = HibernateUtil.getSession();
        ses.beginTransaction();
        ses.saveOrUpdate(customer);
        ses.getTransaction().commit();
    }

    public static void removeCustomer(Customer customer) {
        Session sess = HibernateUtil.getSession();
        Transaction tran = sess.beginTransaction();
        sess.delete(customer);
        tran.commit();
    }

    public static List<Customer> getCustomers() {
        Session ses = HibernateUtil.getSession();
        Transaction tran = ses.beginTransaction();
        
        List<Customer> list = ses.createQuery("FROM Customer").list();
        tran.commit();

        return list;
    }

    public static List<Customer> getCustomers(Group group) {
        if(group != null) {
            throw new NullPointerException();
        }

        Session ses = HibernateUtil.getSession();
        Transaction tran = ses.beginTransaction();

        List<Customer> list = ses.createQuery("FROM Customer WHERE group.id=" + group.getId()).list();
        tran.commit();

        return list;
    }

    public static int getSize() {
        int size = 0;

        Session ses = HibernateUtil.getSession();
        size = ( (Long) ses.createQuery("SELECT COUNT(*) FROM Customer").iterate().next() ).intValue();

        return size;
    }
}

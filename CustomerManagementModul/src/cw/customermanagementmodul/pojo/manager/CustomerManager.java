package cw.customermanagementmodul.pojo.manager;

import cw.boardingschoolmanagement.app.HibernateUtil;
import cw.boardingschoolmanagement.pojo.manager.AbstractPOJOManager;
import java.util.List;
import cw.customermanagementmodul.pojo.Customer;
import cw.customermanagementmodul.pojo.Group;
import java.util.logging.Logger;
import javax.persistence.NoResultException;

/**
 * Manages Customers
 * @author CreativeWorkers.at
 */
public class CustomerManager extends AbstractPOJOManager<Customer> {

    private static CustomerManager instance;
    private static Logger logger = Logger.getLogger(CustomerManager.class.getName());

    private CustomerManager() {
    }

    public static CustomerManager getInstance() {
        if(instance == null) {
            instance = new CustomerManager();
        }
        return instance;
    }

    public List<Customer> getAll(Group group) {
        if(group == null) {
            throw new NullPointerException("group is null");
        }

        return group.getCustomers();

//        return HibernateUtil.getEntityManager().
//                createQuery("FROM Customer c WHERE group in elements(c.groups)")
//                .getResultList();
    }

    public int size() {
        return ( (Long) HibernateUtil.getEntityManager().createQuery("SELECT COUNT(*) FROM Customer").getResultList().iterator().next() ).intValue();
    }

    public int sizeActive() {
        return ( (Long) HibernateUtil.getEntityManager().createQuery("SELECT COUNT(*) FROM Customer WHERE active=true").getResultList().iterator().next() ).intValue();
    }

    public int sizeInactive() {
        return ( (Long) HibernateUtil.getEntityManager().createQuery("SELECT COUNT(*) FROM Customer WHERE active=false").getResultList().iterator().next() ).intValue();
    }

    @Override
    public List<Customer> getAll() {
        return HibernateUtil.getEntityManager().createQuery("FROM Customer").getResultList();
    }

    public List<Customer> getAllActive() {
        return HibernateUtil.getEntityManager().createQuery("FROM Customer WHERE active=true").getResultList();
    }

    public List<Customer> getAllInactive() {
        return HibernateUtil.getEntityManager().createQuery("FROM Customer WHERE active=false").getResultList();
    }

    public List<String> getList(String attribute) {
        return HibernateUtil.getEntityManager().createQuery("SELECT str("+attribute+") FROM Customer WHERE "+attribute+" IS NOT NULL").getResultList();
    }

    public String getResult(String attribute1, String value1, String attribute2) {
        try {
            return (String) HibernateUtil.getEntityManager().createQuery("SELECT str(" + attribute2 + ") FROM Customer WHERE " + attribute1 + " IS NOT NULL AND " + attribute1 + "='" + value1 + "'").setMaxResults(1).getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
}

package cw.customermanagementmodul.pojo.manager;

import cw.boardingschoolmanagement.app.HibernateUtil;
import cw.boardingschoolmanagement.pojo.manager.AbstractPOJOManager;
import java.util.List;
import org.apache.log4j.Logger;
import cw.customermanagementmodul.pojo.Customer;
import cw.customermanagementmodul.pojo.Group;
import javax.persistence.NoResultException;

/**
 * Manages Customers
 * @author CreativeWorkers.at
 */
public class CustomerManager extends AbstractPOJOManager<Customer> {

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

    public List<Customer> getAll(Group group) {
        if(group != null) {
            throw new NullPointerException();
        }

        return HibernateUtil.getEntityManager().createQuery("FROM Customer WHERE group.id=" + group.getId()).getResultList();
    }

    public int size() {
        return ( (Long) HibernateUtil.getEntityManager().createQuery("SELECT COUNT(*) FROM Customer").getResultList().iterator().next() ).intValue();
    }

    @Override
    public List<Customer> getAll() {
        return HibernateUtil.getEntityManager().createQuery("FROM Customer").getResultList();
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

package cw.accountmanagementmodul.pojo.manager;

import cw.accountmanagementmodul.pojo.Invoice;
import cw.accountmanagementmodul.pojo.InvoiceItem;
import cw.boardingschoolmanagement.app.HibernateUtil;
import cw.boardingschoolmanagement.pojo.manager.AbstractPersistenceManager;
import java.util.List;
import java.util.logging.Logger;

/**
 *
 * @author CreativeWorkers.at
 */
public class InvoiceItemManager extends AbstractPersistenceManager<InvoiceItem>
{

    private static InvoiceItemManager instance;
    private static Logger logger = Logger.getLogger(InvoiceItemManager.class.getName());

    private InvoiceItemManager() {
    }

    public static InvoiceItemManager getInstance() {
        if(instance == null) {
            instance = new InvoiceItemManager();
        }
        return instance;
    }

    public int size() {
        return ( (Long) HibernateUtil.getEntityManager().createQuery("SELECT COUNT(*) FROM InvoiceItem").getResultList().iterator().next() ).intValue();
    }

    public int size(Invoice i) {
        return ( (Long) HibernateUtil.getEntityManager().createQuery("SELECT COUNT(*) FROM InvoiceItem ii WHERE ii.invoice.id=" + i.getId()).getResultList().iterator().next() ).intValue();
    }

    @Override
    public List<InvoiceItem> getAll() {
        return HibernateUtil.getEntityManager().createQuery("FROM InvoiceItem").getResultList();
    }
    
    public List<InvoiceItem> getAll(Invoice i) {
        return HibernateUtil.getEntityManager().createQuery("FROM InvoiceItem ii WHERE ii.invoice.id=" + i.getId()).getResultList();
    }

}

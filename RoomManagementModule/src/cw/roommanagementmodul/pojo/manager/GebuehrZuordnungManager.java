package cw.roommanagementmodul.pojo.manager;

import cw.boardingschoolmanagement.app.HibernateUtil;
import cw.boardingschoolmanagement.pojo.manager.AbstractPOJOManager;
import java.util.List;
import cw.roommanagementmodul.pojo.Bewohner;
import cw.roommanagementmodul.pojo.Gebuehr;
import cw.roommanagementmodul.pojo.GebuehrZuordnung;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

/**
 *
 * @author Dominik
 */
public class GebuehrZuordnungManager extends AbstractPOJOManager<GebuehrZuordnung> {

    private static GebuehrZuordnungManager instance;
    private static Logger logger = Logger.getLogger(GebuehrZuordnungManager.class.getName());

    private GebuehrZuordnungManager() {
    }

    public static GebuehrZuordnungManager getInstance() {
        if (instance == null) {
            instance = new GebuehrZuordnungManager();
        }
        return instance;
    }

    public void removeGebuehrZuordnung(Bewohner b) {
        List<GebuehrZuordnung> l = this.getGebuehrZuordnung(b);
        for(GebuehrZuordnung gz: l) {
            super.delete(gz);
        }
    }

    public void removeGebuehrZuordnung(Gebuehr g) {
        EntityManager entityManager = HibernateUtil.getEntityManager();
        List<GebuehrZuordnung> l = this.getGebuehrZuordnung(g);
        EntityTransaction tran = entityManager.getTransaction();
        tran.begin();
        for (int i = 0; i < l.size(); i++) {
            l.get(i).setGebuehr(null);
            l.get(i).setBewohner(null);
            super.delete(l.get(i));

        }
        tran.commit();
    }

    public List<GebuehrZuordnung> getGebuehrZuordnung(Gebuehr g) {
        EntityManager entityManager = HibernateUtil.getEntityManager();
        List<GebuehrZuordnung> list = entityManager.createQuery("Select g From GebuehrZuordnung g where g.gebuehr.id = " + g.getId()).getResultList();
        return list;
    }

    public List getGebuehrZuordnung(Bewohner b) {
        EntityManager entityManager = HibernateUtil.getEntityManager();
        List<GebuehrZuordnung> list = entityManager.createQuery("Select g From GebuehrZuordnung g where g.bewohner.id = " + b.getId()).getResultList();
        return list;
    }

    @Override
    public List<GebuehrZuordnung> getAll() {
        return HibernateUtil.getEntityManager().createQuery("FROM GebuehrZuordnung").getResultList();
    }

    @Override
    public int size() {
        return ((Long) HibernateUtil.getEntityManager().createQuery("SELECT COUNT(*) FROM GebuehrZuordnung").getResultList().iterator().next()).intValue();
    }
}

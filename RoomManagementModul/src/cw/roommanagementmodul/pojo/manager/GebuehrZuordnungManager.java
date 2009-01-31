/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cw.roommanagementmodul.pojo.manager;

import cw.boardingschoolmanagement.app.HibernateUtil;
import cw.boardingschoolmanagement.pojo.manager.AbstractPOJOManager;
import java.util.List;
import cw.roommanagementmodul.pojo.Bewohner;
import cw.roommanagementmodul.pojo.GebuehrZuordnung;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import org.apache.log4j.Logger;

/**
 *
 * @author Dominik
 */
public class GebuehrZuordnungManager  extends AbstractPOJOManager<GebuehrZuordnung> {

    private static GebuehrZuordnungManager instance;
    private static Logger logger = Logger.getLogger(GebuehrZuordnungManager.class);

    private GebuehrZuordnungManager() {
    }

    public static GebuehrZuordnungManager getInstance() {
        if(instance == null) {
            instance = new GebuehrZuordnungManager();
        }
        return instance;
    }

    public void removeGebuehrZuordnung(Bewohner b) {
        EntityManager entityManager = HibernateUtil.getEntityManager();
        List<GebuehrZuordnung> l = this.getGebuehrZuordnung(b);
        EntityTransaction tran = entityManager.getTransaction();
           for(int i=0;i<l.size();i++){
            entityManager.remove(l.get(i));
        }
        tran.commit();
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

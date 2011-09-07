/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package cw.roommanagementmodul.pojo.manager;


import cw.boardingschoolmanagement.perstistence.HibernateUtil;
import cw.boardingschoolmanagement.pojo.manager.AbstractPersistenceManager;
import java.util.List;
import cw.roommanagementmodul.pojo.Gebuehr;
import cw.roommanagementmodul.pojo.Tarif;
import java.util.logging.Logger;
import javax.persistence.EntityManager;


/**
 *
 * @author Dominik
 */
public class TarifManager extends AbstractPersistenceManager<Tarif> {

    private static TarifManager instance;
    private static Logger logger = Logger.getLogger(TarifManager.class.getName());

    private TarifManager() {
    }

    
    public static TarifManager getInstance() {
        if (instance == null) {
            instance = new TarifManager();
        }
        return instance;
    }

    public List getTarif(Gebuehr g){
        EntityManager entityManager = HibernateUtil.getEntityManager();
        List<Tarif> list = entityManager.createQuery("Select t From Tarif t where t.gebuehr.id = " + g.getId()).getResultList();
        return list;
    }

    @Override
    public List<Tarif> getAll() {
        return HibernateUtil.getEntityManager().createQuery("FROM Tarif").getResultList();
    }

   
    public List<Tarif> getAllOrderd(Gebuehr g) {
        return HibernateUtil.getEntityManager().createQuery("Select t FROM Tarif t where t.gebuehr.id="+g.getId()+" order by t.ab asc, t.bis asc").getResultList();
    }

    @Override
    public int size() {
        return ((Long) HibernateUtil.getEntityManager().createQuery("SELECT COUNT(*) FROM Tarif").getResultList().iterator().next()).intValue();
    }


}

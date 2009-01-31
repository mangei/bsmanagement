/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package cw.roommanagementmodul.pojo.manager;

import cw.boardingschoolmanagement.app.HibernateUtil;
import cw.boardingschoolmanagement.pojo.manager.AbstractPOJOManager;
import cw.roommanagementmodul.pojo.GebLauf;
import java.util.List;
import javax.persistence.EntityManager;
import org.apache.log4j.Logger;


/**
 *
 * @author Dominik
 */
public class GebLaufManager extends AbstractPOJOManager<GebLauf> {

    private static GebLaufManager instance;
    private static Logger logger = Logger.getLogger(GebLaufManager.class);

    private GebLaufManager() {
    }

    public static GebLaufManager getInstance() {
        if(instance == null) {
            instance = new GebLaufManager();
        }
        return instance;
    }


    public boolean existGebLauf(long abrMonat){
        EntityManager entityManager = HibernateUtil.getEntityManager();
        GebLauf gebLauf=(GebLauf)entityManager.createQuery("SELECT gl FROM GebLauf gl where gl.abrMonat ="+abrMonat).getSingleResult();
        if(gebLauf==null){
            return false;
        }else{
            return true;
        }
        
    }

    @Override
    public List<GebLauf> getAll() {
        return HibernateUtil.getEntityManager().createQuery("FROM GebLauf").getResultList();
    }

    @Override
    public int size() {
        return ((Long) HibernateUtil.getEntityManager().createQuery("SELECT COUNT(*) FROM GebLauf").getResultList().iterator().next()).intValue();
    }
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cw.roommanagementmodul.pojo.manager;

import cw.boardingschoolmanagement.app.HibernateUtil;
import cw.boardingschoolmanagement.pojo.manager.AbstractPOJOManager;
import cw.roommanagementmodul.pojo.Zimmer;
import java.util.List;
import org.apache.log4j.Logger;

/**
 *
 * @author Dominik
 */
public class ZimmerManager extends AbstractPOJOManager<Zimmer> {

    private static ZimmerManager instance;
    private static Logger logger = Logger.getLogger(ZimmerManager.class);

    private ZimmerManager() {
    }


    public static ZimmerManager getInstance() {
        if (instance == null) {
            instance = new ZimmerManager();
        }
        return instance;
    }

    @Override
    public List<Zimmer> getAll() {
        return HibernateUtil.getEntityManager().createQuery("FROM Zimmer").getResultList();
    }

    @Override
    public int size() {
        return ((Long) HibernateUtil.getEntityManager().createQuery("SELECT COUNT(*) FROM Zimmer").getResultList().iterator().next()).intValue();
    }



    
}

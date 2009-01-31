/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cw.roommanagementmodul.pojo.manager;

import cw.boardingschoolmanagement.app.HibernateUtil;
import cw.boardingschoolmanagement.pojo.manager.AbstractPOJOManager;
import java.util.List;
import cw.roommanagementmodul.pojo.Kaution;
import org.apache.log4j.Logger;

/**
 *
 * @author Dominik
 */
public class KautionManager extends AbstractPOJOManager<Kaution> {

    private static KautionManager instance;
    private static Logger logger = Logger.getLogger(KautionManager.class);

    private KautionManager() {
    }

    public static KautionManager getInstance() {
        if (instance == null) {
            instance = new KautionManager();
        }
        return instance;
    }

    @Override
    public List<Kaution> getAll() {
        return HibernateUtil.getEntityManager().createQuery("FROM Kaution").getResultList();
    }

    @Override
    public int size() {
        return ((Long) HibernateUtil.getEntityManager().createQuery("SELECT COUNT(*) FROM Kaution").getResultList().iterator().next()).intValue();
    }
  

}

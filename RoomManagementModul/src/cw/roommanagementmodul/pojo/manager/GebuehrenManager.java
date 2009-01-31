/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cw.roommanagementmodul.pojo.manager;

import cw.boardingschoolmanagement.app.HibernateUtil;
import cw.boardingschoolmanagement.pojo.manager.AbstractPOJOManager;
import cw.roommanagementmodul.pojo.Gebuehr;
import cw.roommanagementmodul.pojo.GebuehrZuordnung;
import java.util.List;
import org.apache.log4j.Logger;

/**
 *
 * @author Dominik
 */
public class GebuehrenManager extends AbstractPOJOManager<Gebuehr> {

    private static GebuehrenManager instance;
    private static Logger logger = Logger.getLogger(GebuehrenManager.class);

    private GebuehrenManager() {
    }

    public static GebuehrenManager getInstance() {
        if (instance == null) {
            instance = new GebuehrenManager();
        }
        return instance;
    }

    @Override
    public List<Gebuehr> getAll() {
        return HibernateUtil.getEntityManager().createQuery("FROM Gebuehr").getResultList();
    }

    @Override
    public int size() {
        return ((Long) HibernateUtil.getEntityManager().createQuery("SELECT COUNT(*) FROM Gebuehr").getResultList().iterator().next()).intValue();
    }


}

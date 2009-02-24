/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package cw.roommanagementmodul.pojo.manager;

import cw.boardingschoolmanagement.app.HibernateUtil;
import cw.boardingschoolmanagement.pojo.manager.AbstractPOJOManager;
import cw.roommanagementmodul.pojo.GebuehrZuordnung;
import java.util.List;
import cw.roommanagementmodul.pojo.GebuehrenKategorie;
import org.apache.log4j.Logger;

/**
 *
 * @author Dominik
 */
public class GebuehrenKatManager extends AbstractPOJOManager<GebuehrenKategorie> {

    private static GebuehrenKatManager instance;
    private static Logger logger = Logger.getLogger(GebuehrenKatManager.class);

    private GebuehrenKatManager() {
    }

    public static GebuehrenKatManager getInstance() {
        if(instance == null) {
            instance = new GebuehrenKatManager();
        }
        return instance;
    }


    @Override
    public List<GebuehrenKategorie> getAll() {
        return HibernateUtil.getEntityManager().createQuery("FROM GebuehrenKategorie gk order by gk.name asc").getResultList();
    }

    @Override
    public int size() {
        return ((Long) HibernateUtil.getEntityManager().createQuery("SELECT COUNT(*) FROM GebuehrenKategorie").getResultList().iterator().next()).intValue();
    }
}

package cw.customermanagementmodul.pojo.manager;

import cw.boardingschoolmanagement.app.HibernateUtil;
import cw.boardingschoolmanagement.pojo.manager.AbstractPOJOManager;
import java.util.List;
import org.apache.log4j.Logger;
import cw.customermanagementmodul.pojo.Group;

/**
 * Manages Groups
 * @author CreativeWorkers.at
 */
public class GroupManager extends AbstractPOJOManager<Group> {

    private static GroupManager instance;
    private static Logger logger = Logger.getLogger(GroupManager.class);
    
    private GroupManager() {
    }
    
    public static GroupManager getInstance() {
        if(instance == null) {
            instance = new GroupManager();
        }
        return instance;
    }

    public int size() {
        return ( (Long) HibernateUtil.getEntityManager().createQuery("SELECT COUNT(*) FROM Group").getResultList().iterator().next() ).intValue();
    }

    @Override
    public List<Group> getAll() {
        return HibernateUtil.getEntityManager().createQuery("FROM Group").getResultList();
    }
}

package cw.customermanagementmodul.persistence;

import java.util.List;
import java.util.logging.Logger;

import javax.persistence.EntityManager;

import cw.boardingschoolmanagement.app.HibernateUtil;
import cw.boardingschoolmanagement.pojo.manager.AbstractPersistenceManager;

/**
 * Manages Groups
 * 
 * @author Manuel Geier
 */
public class GroupManager extends AbstractPersistenceManager<Group> {

    private static GroupManager instance;
    private static Logger logger = Logger.getLogger(GroupManager.class.getName());
    
    private GroupManager() {
    }
    
    public static GroupManager getInstance() {
        if(instance == null) {
            instance = new GroupManager();
        }
        return instance;
    }

	public Group create(EntityManager entityManager) {
		return new GroupImpl(entityManager);
	}
    
    public int countAll(EntityManager entityManager) {
        return ( (Long) entityManager.createQuery("SELECT COUNT(*) FROM " + Group.ENTITY_NAME).getResultList().iterator().next() ).intValue();
    }

	public List<Group> getAll(EntityManager entityManager) {
		return entityManager.createQuery("FROM Group").getResultList();
	}
}

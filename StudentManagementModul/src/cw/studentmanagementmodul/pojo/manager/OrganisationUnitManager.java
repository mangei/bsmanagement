package cw.studentmanagementmodul.pojo.manager;

import cw.boardingschoolmanagement.app.HibernateUtil;
import cw.boardingschoolmanagement.pojo.manager.AbstractPOJOManager;
import java.util.List;
import cw.studentmanagementmodul.pojo.OrganisationUnit;
import java.util.logging.Logger;

/**
 * Manages OrganisationUnits
 * @author CreativeWorkers.at
 */
public class OrganisationUnitManager extends AbstractPOJOManager<OrganisationUnit> {

    private static OrganisationUnitManager instance;
    private static Logger logger = Logger.getLogger(OrganisationUnitManager.class.getName());

    private OrganisationUnitManager() {
    }

    public static OrganisationUnitManager getInstance() {
        if(instance == null) {
            instance = new OrganisationUnitManager();
        }
        return instance;
    }

    public int size() {
        return ( (Long) HibernateUtil.getEntityManager().createQuery("SELECT COUNT(*) FROM OrganisationUnit").getResultList().iterator().next() ).intValue();
    }

    @Override
    public List<OrganisationUnit> getAll() {
        return HibernateUtil.getEntityManager().createQuery("FROM OrganisationUnit").getResultList();
    }

    public List<OrganisationUnit> getAll(int from, int limit) {
        return HibernateUtil.getEntityManager().createQuery("FROM OrganisationUnit").setFirstResult(from).setMaxResults(limit).getResultList();
    }

    public List<OrganisationUnit> getRoots() {
        return HibernateUtil.getEntityManager().createQuery("FROM OrganisationUnit WHERE parent IS NULL ORDER BY name").getResultList();
    }

    public List<OrganisationUnit> getChildren(OrganisationUnit organisationUnit) {
        return HibernateUtil.getEntityManager().createQuery("FROM OrganisationUnit WHERE parent=" + organisationUnit.getId()).getResultList();

    }
}

package cw.studentmanagementmodul.pojo.manager;

import cw.boardingschoolmanagement.persistence.AbstractPersistenceManager;
import cw.boardingschoolmanagement.persistence.HibernateUtil;
import java.util.List;
import cw.studentmanagementmodul.pojo.OrganisationUnit;
import cw.studentmanagementmodul.pojo.StudentClass;
import java.util.logging.Logger;

/**
 * Manages OrganisationUnits
 * @author CreativeWorkers.at
 */
public class OrganisationUnitManager extends AbstractPersistenceManager<OrganisationUnit> {

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

    @Override
    public boolean delete(OrganisationUnit o) {

        // Loeschen der darunterliegenden Bereiche
        List<OrganisationUnit> children = o.getChildren();
        for(int i=0, l=children.size(); i<l; i++) {
            delete(children.get(i));
        }

        // Loeschen der darin befindlichen Klassen
        List<StudentClass> studentClasses = o.getStudentClasses();
        for(int i=0, l=studentClasses.size(); i<l; i++) {
            StudentClassManager.getInstance().delete(studentClasses.get(i));
        }

        // Den Bereich selbst loeschen
        return super.delete(o);
    }

}

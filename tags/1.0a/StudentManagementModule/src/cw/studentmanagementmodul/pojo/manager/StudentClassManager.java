package cw.studentmanagementmodul.pojo.manager;

import cw.boardingschoolmanagement.app.HibernateUtil;
import cw.boardingschoolmanagement.pojo.manager.AbstractPOJOManager;
import java.util.List;
import cw.studentmanagementmodul.pojo.OrganisationUnit;
import cw.studentmanagementmodul.pojo.StudentClass;
import java.util.logging.Logger;

/**
 *
 * @author CreativeWorkers.at
 */
public class StudentClassManager extends AbstractPOJOManager<StudentClass>
{
    private static StudentClassManager instance;
    private static Logger logger = Logger.getLogger(StudentClassManager.class.getName());

    private StudentClassManager() {
    }

    public static StudentClassManager getInstance() {
        if(instance == null) {
            instance = new StudentClassManager();
        }
        return instance;
    }

    public int size() {
        return ( (Long) HibernateUtil.getEntityManager().createQuery("SELECT COUNT(*) FROM StudentClass").getResultList().iterator().next() ).intValue();
    }

    @Override
    public List<StudentClass> getAll() {
        return HibernateUtil.getEntityManager().createQuery("FROM StudentClass").getResultList();
    }

    public List<StudentClass> getAll(OrganisationUnit organisationUnit) {
        return HibernateUtil.getEntityManager().createQuery("FROM StudentClass WHERE organisationUnit=" + organisationUnit.getId() + "ORDER BY name").getResultList();
    }

}

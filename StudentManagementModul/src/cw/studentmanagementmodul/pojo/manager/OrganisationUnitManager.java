package cw.studentmanagementmodul.pojo.manager;

import cw.boardingschoolmanagement.app.HibernateUtil;
import com.jgoodies.binding.beans.Model;
import cw.studentmanagementmodul.pojo.StudentClass;
import java.util.Iterator;
import java.util.List;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;
import cw.studentmanagementmodul.pojo.OrganisationUnit;

/**
 * Manages OrganisationUnits
 * @author CreativeWorkers.at
 */
public class OrganisationUnitManager extends Model {

    private static OrganisationUnitManager instance;
    private static Logger logger = Logger.getLogger(OrganisationUnitManager.class);

    private OrganisationUnitManager() {
    }

    public static OrganisationUnitManager getInstance() {
        if(instance == null) {
            instance = new OrganisationUnitManager();
        }
        return instance;
    }

    public static void saveOrganisationUnit(OrganisationUnit organisationUnit) {
        Session ses = HibernateUtil.getSession();
        ses.beginTransaction();
        ses.saveOrUpdate(organisationUnit);
        ses.getTransaction().commit();
    }

    public static void removeOrganisationUnit(OrganisationUnit organisationUnit) {
        if(organisationUnit == null) return;

        // Delete student class children
        Iterator<StudentClass> iteratorStudentClasses = organisationUnit.getStudentClasses().iterator();
        while(iteratorStudentClasses.hasNext()) {
//            StudentClassManager.removeStudentClass(iteratorStudentClasses.next());
            iteratorStudentClasses.next().setOrganisationUnit(null);
        }
        
        // Delete children
        // TODO Löschen einer Organisationseinheit
        Iterator<OrganisationUnit> iteratorChildren = organisationUnit.getChildren().iterator();
        while(iteratorChildren.hasNext()) {
//            removeOrganisationUnit(iteratorChildren.next());
            iteratorChildren.next().setParent(null);
        }

        // Delete from parent
        if(organisationUnit.getParent() != null) {
            organisationUnit.getParent().getChildren().remove(organisationUnit);
        }

        Session sess = HibernateUtil.getSession();
        Transaction tran = sess.beginTransaction();
        sess.delete(organisationUnit);
        tran.commit();
    }

    public static List<OrganisationUnit> getOrganisationUnits() {
        Session ses = HibernateUtil.getSession();
        Transaction tran = ses.beginTransaction();
        List<OrganisationUnit> list = ses.createQuery("FROM OrganisationUnit").list();
        tran.commit();

        return list;
    }

    public static List<OrganisationUnit> getOrganisationUnitRoots() {
        Session ses = HibernateUtil.getSession();
        Transaction tran = ses.beginTransaction();
        List<OrganisationUnit> list = ses.createQuery("FROM OrganisationUnit WHERE parent IS NULL ORDER BY name").list();
        tran.commit();

        return list;
    }

    public static List<OrganisationUnit> getOrganisationUnitChildren(OrganisationUnit organisationUnit) {
        Session ses = HibernateUtil.getSession();
        Transaction tran = ses.beginTransaction();
        List<OrganisationUnit> list = ses.createQuery("FROM OrganisationUnit WHERE parent=" + organisationUnit.getId()).list();
        tran.commit();

        return list;
    }
}

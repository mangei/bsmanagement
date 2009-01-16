package cw.studentmanagementmodul.pojo.manager;

import cw.boardingschoolmanagement.app.HibernateUtil;
import java.util.List;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;
import cw.studentmanagementmodul.pojo.OrganisationUnit;
import cw.studentmanagementmodul.pojo.StudentClass;

/**
 *
 * @author CreativeWorkers.at
 */
public class StudentClassManager
{
    private static StudentClassManager instance;
    private static Logger logger = Logger.getLogger(StudentClassManager.class);

    private StudentClassManager() {
    }

    public static StudentClassManager getInstance() {
        if(instance == null) {
            instance = new StudentClassManager();
        }
        return instance;
    }

    public static void saveStudentClass(StudentClass studentClass) {
        Session ses = HibernateUtil.getSession();
        ses.beginTransaction();
        ses.saveOrUpdate(studentClass);
        ses.getTransaction().commit();
    }

    public static void removeStudentClass(StudentClass studentClass) {
        if(studentClass.getOrganisationUnit() != null) {
            studentClass.getOrganisationUnit().getStudentClasses().remove(studentClass);
        }

        Session sess = HibernateUtil.getSession();
        Transaction tran = sess.beginTransaction();
        sess.delete(studentClass);
        tran.commit();
    }

    public static List<StudentClass> getStudentClasses() {
        Session ses = HibernateUtil.getSession();
        Transaction tran = ses.beginTransaction();
        List<StudentClass> list = ses.createQuery("FROM StudentClass").list();
        tran.commit();
        
        return list;
    }

    public static List<StudentClass> getStudentClasses(OrganisationUnit organisationUnit) {
        Session ses = HibernateUtil.getSession();
        Transaction tran = ses.beginTransaction();
        List<StudentClass> list = ses.createQuery("FROM StudentClass WHERE organisationUnit=" + organisationUnit.getId() + "ORDER BY name").list();
        tran.commit();

        return list;
    }
}

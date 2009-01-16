package cw.customermanagementmodul.pojo.manager;

import cw.boardingschoolmanagement.app.HibernateUtil;
import com.jgoodies.binding.beans.Model;
import java.util.List;
import org.apache.log4j.Logger;
import org.hibernate.NonUniqueResultException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import cw.customermanagementmodul.pojo.Group;

/**
 * Manages Groups
 * @author CreativeWorkers.at
 */
public class GroupManager extends Model {

    private static GroupManager instance;
    private static Logger logger = Logger.getLogger(CustomerManager.class);
    
    private GroupManager() {
    }
    
    public static GroupManager getInstance() {
        if(instance == null) {
            instance = new GroupManager();
        }
        return instance;
    }

    public static void saveGroup(Group g) {
        Session ses = HibernateUtil.getSession();
        Transaction tran = ses.beginTransaction();
        ses.saveOrUpdate(g);
        tran.commit();
    }

    public static void removeGroup(Group g) {
        Session sess = HibernateUtil.getSession();
        Transaction tran = sess.beginTransaction();
        sess.delete(g);
        tran.commit();
    }

    public static Group getRootGroup() {
        Group g;
        try {
            g = (Group) HibernateUtil.getSession().createQuery("SELECT g FROM Group g WHERE parent=null").uniqueResult();

        } catch (NonUniqueResultException e) {
            g = (Group) HibernateUtil.getSession().createQuery("SELECT g FROM Group g WHERE parent=null").list().get(0);
        }
        if (g == null) {
            g = new Group();
            g.setName("Alle");
            saveGroup(g);
        }

//        Group g2 = new Group("Schüler");
//        g.getChildren().add(g2);
//        saveGroup(g2);

        return g;
    }

    public static Group getGroup(Long id) {
        if (id < 0) {
            return null;
        }

        Group g = (Group) HibernateUtil.getSession().createQuery("SELECT g FROM Group g WHERE id=" + id).uniqueResult();

        return g;
    }

    public static List getGroups() {
        List list = HibernateUtil.getSession().createQuery("SELECT g FROM Group g").list();

        return list;
    }
}

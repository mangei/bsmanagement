package cw.coursemanagementmodul.pojo.manager;

import java.util.ArrayList;
import java.util.List;

import cw.accountmanagementmodul.pojo.AccountPosting;
import cw.boardingschoolmanagement.app.HibernateUtil;
import cw.boardingschoolmanagement.pojo.manager.AbstractPOJOManager;
import cw.coursemanagementmodul.pojo.CoursePosting;

/**
 *
 * @author André Salmhofer
 */
public class CoursePostingManager extends AbstractPOJOManager<CoursePosting> {

    private static CoursePostingManager instance;

    private CoursePostingManager() {
    }

    public static CoursePostingManager getInstance() {
        if(instance == null) {
            instance = new CoursePostingManager();
        }
        return instance;
    }

    @Override
    public List<CoursePosting> getAll() {
        return HibernateUtil.getEntityManager().createQuery("FROM CoursePosting").getResultList();
    }
    
    public CoursePosting get(AccountPosting accountPosting) {
        return (CoursePosting) HibernateUtil.getEntityManager().createQuery("FROM CoursePosting p where p.posting.id = " + accountPosting.getId()).getResultList().get(0);
    }

    public List<CoursePosting> getAllExceptStorno() {
        List<CoursePosting> list = getAll();
        List<CoursePosting> nonStornoList = new ArrayList<CoursePosting>();

        for(int i = 0; i < list.size(); i++){
            if(!list.get(i).getPosting().getPostingCategory().getKey().equals("Kurs-Buchung-Storno")){
                nonStornoList.add(list.get(i));
            }
        }

        return nonStornoList;
    }

    @Override
    public int size() {
        return ( (Long) HibernateUtil.getEntityManager().createQuery("SELECT COUNT(*) FROM CoursePosting").getResultList().iterator().next() ).intValue();
    }
}

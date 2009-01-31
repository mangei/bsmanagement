/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cw.roommanagementmodul.pojo.manager;

import cw.boardingschoolmanagement.app.HibernateUtil;
import cw.boardingschoolmanagement.pojo.manager.AbstractPOJOManager;
import java.util.List;
import cw.roommanagementmodul.pojo.Bereich;
import java.util.ArrayList;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import org.apache.log4j.Logger;

/**
 *
 * @author Dominik
 */
public class BereichManager extends AbstractPOJOManager<Bereich> {

    private static BereichManager instance;
    private static Logger logger = Logger.getLogger(BereichManager.class);

    private BereichManager() {
    }

    public static BereichManager getInstance() {
        if(instance == null) {
            instance = new BereichManager();
        }
        return instance;
    }

    public boolean existRoot() {
        EntityManager entityManager = HibernateUtil.getEntityManager();
        EntityTransaction tran = entityManager.getTransaction();
        Object obj = entityManager.createQuery("select b from Bereich b where b.parentBereich is null").getSingleResult();
        tran.commit();
        if (obj == null) {
            return false;
        } else {
            return true;
        }

    }

    public Bereich getRoot() {
        EntityManager entityManager = HibernateUtil.getEntityManager();
        EntityTransaction tran = entityManager.getTransaction();
        Bereich bereich = (Bereich) entityManager.createQuery("select b from Bereich b where b.parentBereich is null").getSingleResult();
        tran.commit();
        return bereich;

    }

    public void refreshBereich(Bereich bereich) {
        EntityManager entityManager = HibernateUtil.getEntityManager();
        EntityTransaction tran = entityManager.getTransaction();
        entityManager.refresh(bereich);
        tran.commit();


    }

    public List<Bereich> getBereich() {
        EntityManager entityManager = HibernateUtil.getEntityManager();
        EntityTransaction tran = entityManager.getTransaction();
        List list = entityManager.createQuery("SELECT b FROM Bereich b").getResultList();
        tran.commit();
        return list;
    }

    public List<Bereich> getBereichLeaf() {

        EntityManager entityManager = HibernateUtil.getEntityManager();
        EntityTransaction tran = entityManager.getTransaction();
        List<Bereich> list = entityManager.createQuery("SELECT b FROM Bereich b").getResultList();
        tran.commit();
        List<Bereich> leafList= new ArrayList<Bereich>();
        for(int i=0;i<list.size();i++){
            if(list.get(i).getChildBereichList()==null || list.get(i).getChildBereichList().size()==0){
                leafList.add(list.get(i));
            }

        }
        return leafList;

    }

    public List<Bereich> getBereichNull() {
        EntityManager entityManager = HibernateUtil.getEntityManager();
        EntityTransaction tran = entityManager.getTransaction();
        List list = entityManager.createQuery("SELECT b FROM Bereich b where b.parentBereich is not null").getResultList();
        tran.commit();
        return list;
    }

    @Override
    public List<Bereich> getAll() {
        return HibernateUtil.getEntityManager().createQuery("FROM Bereich").getResultList();
    }

    @Override
    public int size() {
        return ( (Long) HibernateUtil.getEntityManager().createQuery("SELECT COUNT(*) FROM Bereich").getResultList().iterator().next() ).intValue();
    }
}

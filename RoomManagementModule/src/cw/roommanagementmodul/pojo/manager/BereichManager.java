/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cw.roommanagementmodul.pojo.manager;

import cw.boardingschoolmanagement.perstistence.HibernateUtil;
import cw.boardingschoolmanagement.pojo.manager.AbstractPersistenceManager;
import java.util.List;
import cw.roommanagementmodul.pojo.Bereich;
import java.util.ArrayList;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.NoResultException;
import javax.persistence.Query;

/**
 *
 * @author Dominik
 */
public class BereichManager extends AbstractPersistenceManager<Bereich> {

    private static BereichManager instance;
    private static Logger logger = Logger.getLogger(BereichManager.class.getName());

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
        Query query = entityManager.createQuery("select b from Bereich b where b.parentBereich is null");
        
        try{
            Object obj= query.getSingleResult();
        }catch(NoResultException ex){
            return false;
        }
        
        return true;

    }

    public Bereich getRoot() {
        EntityManager entityManager = HibernateUtil.getEntityManager();
        Bereich bereich = (Bereich) entityManager.createQuery("select b from Bereich b where b.parentBereich is null").getSingleResult();
        return bereich;

    }

    public void refreshBereich(Bereich bereich) {
        EntityManager entityManager = HibernateUtil.getEntityManager();
        entityManager.refresh(bereich);


    }

    public List<Bereich> getBereich() {
        EntityManager entityManager = HibernateUtil.getEntityManager();
        List list = entityManager.createQuery("FROM Bereich b order by b.name asc").getResultList();
        return list;
    }

    public List<Bereich> getBereichLeaf() {

        EntityManager entityManager = HibernateUtil.getEntityManager();
        List<Bereich> list = this.getAll();
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
        return HibernateUtil.getEntityManager().createQuery("FROM Bereich b order by b.name asc").getResultList();
    }

    @Override
    public int size() {
        return ( (Long) HibernateUtil.getEntityManager().createQuery("SELECT COUNT(*) FROM Bereich").getResultList().iterator().next() ).intValue();
    }
}

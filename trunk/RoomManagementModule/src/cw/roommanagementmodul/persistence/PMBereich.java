/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cw.roommanagementmodul.persistence;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import cw.boardingschoolmanagement.persistence.CWPersistenceManager;
import cw.boardingschoolmanagement.persistence.HibernateUtil;
import cw.customermanagementmodul.customer.persistence.Customer;

/**
 *
 * @author Dominik
 */
public class PMBereich 
	extends CWPersistenceManager<Bereich> {

    private static PMBereich instance;
    private static Logger logger = Logger.getLogger(PMBereich.class.getName());

    private PMBereich() {
    	super(Bereich.class);
    }

    public static PMBereich getInstance() {
        if(instance == null) {
            instance = new PMBereich();
        }
        return instance;
    }
    
    public Bereich create(EntityManager entityManager) {
    	Bereich bereich = new Bereich(entityManager);
    	entityManager.persist(bereich);
    	return bereich;
    }

    public boolean existRoot(EntityManager entityManager) {
        Query query = entityManager.createQuery("select b from Bereich b where b.parentBereich is null");
        
        try{
            Object obj= query.getSingleResult();
        }catch(NoResultException ex){
            return false;
        }
        
        return true;

    }

    public Bereich getRoot(EntityManager entityManager) {
        Bereich bereich = (Bereich) entityManager.createQuery("select b from Bereich b where b.parentBereich is null").getSingleResult();
        return bereich;

    }

    public List<Bereich> getBereich(EntityManager entityManager) {
        List list = entityManager.createQuery("FROM Bereich b order by b.name asc").getResultList();
        return list;
    }

    public List<Bereich> getBereichLeaf(EntityManager entityManager) {
        List<Bereich> list = this.getAll(entityManager);
        List<Bereich> leafList= new ArrayList<Bereich>();
        for(int i=0;i<list.size();i++){
            if(list.get(i).getChildBereichList()==null || list.get(i).getChildBereichList().size()==0){
                leafList.add(list.get(i));
            }

        }
        return leafList;

    }

    public List<Bereich> getBereichNull(EntityManager entityManager) {
        List list = entityManager.createQuery("SELECT b FROM Bereich b where b.parentBereich is not null").getResultList();
        return list;
    }

    public List<Bereich> getAll(EntityManager entityManager) {
        return setEntityManager(entityManager.createQuery(
        		"FROM " + 
        			Bereich.ENTITY_NAME)
        .getResultList(), entityManager);
    }

    public int countAll(EntityManager entityManager) {
        return ( (Long) entityManager.createQuery(
        		"SELECT " +
        			"COUNT(*)" +
        		" FROM " + 
        			Bereich.ENTITY_NAME)
        .getResultList().iterator().next() ).intValue();
    }
}

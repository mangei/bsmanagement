package cw.boardingschoolmanagement.pojo.manager;

import javax.persistence.EntityManager;

import cw.boardingschoolmanagement.persistence.CWPersistence;
import cw.boardingschoolmanagement.persistence.CascadeListener;
import cw.boardingschoolmanagement.persistence.CascadeListenerSupport;
import cw.boardingschoolmanagement.persistence.PersistenceManager;

/**
 * Basic class for all persistence managers.
 *
 * @author Manuel Geier
 */
public abstract class AbstractPersistenceManager<T extends CWPersistence>
	implements PersistenceManager<T> {

    protected CascadeListenerSupport cascadeListenerSupport;

    public AbstractPersistenceManager() {
        cascadeListenerSupport = new CascadeListenerSupport();
    }

    public void removeCascadeListener(CascadeListener listener) {
        cascadeListenerSupport.removeCascadeListener(listener);
    }

    public void addCascadeListener(CascadeListener listener) {
        cascadeListenerSupport.addCascadeListener(listener);
    }
    
    public void remove(T persistence, EntityManager entityManager) {
    	cascadeListenerSupport.fireCascadeDelete(persistence, entityManager);
    	entityManager.remove(persistence);
    	entityManager.getTransaction().commit();
    	entityManager.getTransaction().begin();
    }

//    /**
//     * Speichert die mit dem Parameter uebergebenen Objekte in die Datenbank.
//     *
//     * @param obj Zu speicherntes Objekt
//     * @return false - Speicherung fehlgeschlagen, true - Speicherung erfolgreich
//     */
//    public boolean save(CWModel obj) {
//    	
//    	
//    	
//        System.out.println(obj);
//        if (obj == null) {
//            return false;
//        }
//
//        try {
//            EntityManager em = HibernateUtil.getEntityManager();
//            System.out.println(!em.contains(obj));
//            if (!em.contains(obj)) {
//                em.getTransaction().begin();
//                em.persist(obj);
//                em.getTransaction().commit();
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//
//            // Something went wrong
//            return false;
//        }
//
//        // Everything worked
//        return true;
//    }
//
//    /**
//     * Delete Funktion zum loeschen von Objekten aus der Datenbank. Wurde als
//     * Template realiserit.
//     *
//     * @param obj - Enthaelt das zu loeschende Objekt
//     * @return Liefert true wenn loeschen erfolgreich war, false wenn ein Fehler aufgetreten ist
//     */
//    public boolean delete(T obj) {
//        if (obj == null) {
//            return false;
//        }
//
//        //cascadeListenerSupport.fireCascadeDelete(obj);
//
//        try {
//            EntityManager em = HibernateUtil.getEntityManager();
//
//            if (em.contains(obj)) {
//                em.getTransaction().begin();
//                em.remove(obj);
//                em.getTransaction().commit();
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//
//            // Something went wrong
//            return false;
//        }
//
//        // Everything worked
//        return true;
//    }
//
//    /**
//     * Speichert die Aenderung eines Datensatzens in die Datenbank.
//     *
//     * @param obj - Objekt das geaendert Wurde
//     * @return true - Aenderung erfolgreich, false - Aenderung nicht erfolgreich
//     */
//    public boolean update(T obj) {
//        System.out.println(obj);
//        if (obj == null) {
//            return false;
//        }
//
//        try {
//            EntityManager em = HibernateUtil.getEntityManager();
//            System.out.println(!em.contains(obj));
//
//            em.getTransaction().begin();
//            em.persist(obj);
//            em.getTransaction().commit();
//
//        } catch (Exception e) {
//            e.printStackTrace();
//
//            // Something went wrong
//            return false;
//        }
//
//        // Everything worked
//        return true;
//    }
}

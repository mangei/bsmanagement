package cw.boardingschoolmanagement.pojo.manager;

import javax.persistence.EntityManager;

import cw.boardingschoolmanagement.app.CWPersistence;
import cw.boardingschoolmanagement.app.CascadeListener;
import cw.boardingschoolmanagement.app.CascadeListenerSupport;
import cw.boardingschoolmanagement.app.PersistenceManager;

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
//     * Delete Funktion zum löschen von Objekten aus der Datenbank. Wurde als
//     * Template realiserit.
//     *
//     * @param obj - Enthält das zu löschende Objekt
//     * @return Liefert true wenn löschen erfolgreich war, false wenn ein Fehler aufgetreten ist
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
//     * Speichert die Änderung eines Datensatzens in die Datenbank.
//     *
//     * @param obj - Objekt das geändert Wurde
//     * @return true - Änderung erfolgreich, false - Änderung nicht erfolgreich
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

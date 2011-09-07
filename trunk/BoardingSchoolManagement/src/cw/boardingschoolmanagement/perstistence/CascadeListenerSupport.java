package cw.boardingschoolmanagement.perstistence;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

/**
 * Supports the management of CascadeListeners.
 *
 * @see CascadeEvent
 * @see CascadeListener
 * @see CascadeAdapter
 *
 * @author Manuel Geier (CreativeWorkers)
 */
public class CascadeListenerSupport {

    // Manages the listener list.
    private List<CascadeListener> listeners;

    public CascadeListenerSupport() {
        listeners = new ArrayList<CascadeListener>();
    }

    public void addCascadeListener(CascadeListener listener) {
	listeners.add(listener);
    }

    public void removeCascadeListener(CascadeListener listener) {
	listeners.remove(listener);
    }

    public void fireCascadeDelete(Object obj, EntityManager entityManager) {
        fireCascadeDelete(new CascadeEvent(obj, CascadeEvent.TYPE_DELETE, entityManager));
    }

    public void fireCascadeDelete(CascadeEvent evt) {
        List<CascadeListener> copy = new ArrayList<CascadeListener>(listeners);
        for(int i=0, l=copy.size(); i<l; i++) {
            copy.get(i).deleteAction(evt);
        }
    }

//    public void fireCascadeUpdate(CascadeEvent evt) {
//        for(int i=0, l=listeners.size(); i<l; i++) {
//            (listeners.get(i)).updateAction(evt);
//        }
//    }

}

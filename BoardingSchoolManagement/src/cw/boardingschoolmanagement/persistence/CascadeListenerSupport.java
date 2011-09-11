package cw.boardingschoolmanagement.persistence;

import java.util.ArrayList;
import java.util.List;

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

    public void fireCascadeDelete(CWPersistence persistence) {
        fireCascadeDelete(new CascadeEvent(persistence, CascadeEvent.TYPE_DELETE));
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

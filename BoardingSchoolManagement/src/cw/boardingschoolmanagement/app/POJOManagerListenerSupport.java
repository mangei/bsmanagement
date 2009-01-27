package cw.boardingschoolmanagement.app;

import java.util.ArrayList;
import java.util.List;

/**
 * Supports the management of POJOManagerListeners.
 *
 * @see POJOManagerEvent
 * @see POJOManagerListener
 * @see POJOManagerAdapter
 *
 * @author Manuel Geier (CreativeWorkers)
 */
public class POJOManagerListenerSupport {

    // Manages the listener list.
    private List<POJOManagerListener> listeners;

    public POJOManagerListenerSupport() {
        listeners = new ArrayList<POJOManagerListener>();
    }

    public void addPOJOManagerListener(POJOManagerListener listener) {
	listeners.add(listener);
    }

    public void removePOJOManagerListener(POJOManagerListener listener) {
	listeners.remove(listener);
    }

    public void firePOJOManagerDelete(Object obj) {
        firePOJOManagerDelete(new POJOManagerEvent(obj, POJOManagerEvent.TYPE_DELETE));
    }

    public void firePOJOManagerDelete(POJOManagerEvent evt) {
        for(int i=0, l=listeners.size(); i<l; i++) {
            (listeners.get(i)).deleteAction(evt);
        }
    }

//    public void firePOJOManagerUpdate(POJOManagerEvent evt) {
//        for(int i=0, l=listeners.size(); i<l; i++) {
//            (listeners.get(i)).updateAction(evt);
//        }
//    }

}

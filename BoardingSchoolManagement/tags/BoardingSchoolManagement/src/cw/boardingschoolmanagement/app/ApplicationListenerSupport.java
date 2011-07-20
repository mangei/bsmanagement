package cw.boardingschoolmanagement.app;

import java.util.ArrayList;
import java.util.List;

/**
 * Supports the management of ApplicationListeners
 *
 * @see ApplicationListener
 * @see ApplicationAdapter
 *
 * @author Manuel Geier (CreativeWorkers)
 */
public class ApplicationListenerSupport {

    private List<ApplicationListener> listener;

    public ApplicationListenerSupport() {
        listener = new ArrayList<ApplicationListener>();
    }

    public void addApplicationListener(ApplicationListener applicationListener) {
        listener.add(applicationListener);
    }

    public void removeApplicationListener(ApplicationListener applicationListener) {
        listener.remove(applicationListener);
    }

    public void fireApplicationClosing() {
        for(ApplicationListener l: listener) {
            l.applicationClosing();
        }
    }
}

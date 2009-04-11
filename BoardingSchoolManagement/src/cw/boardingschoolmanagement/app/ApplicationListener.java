package cw.boardingschoolmanagement.app;

import java.util.EventListener;

/**
 * Listener if you want to get informed when something happens in the application
 *
 * @see ApplicationAdapter
 *
 * @author Manuel Geier (CreativeWorkers)
 */
public interface ApplicationListener extends EventListener {
    public void applicationClosing();
}

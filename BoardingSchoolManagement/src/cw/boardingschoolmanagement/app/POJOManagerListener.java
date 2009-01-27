package cw.boardingschoolmanagement.app;

/**
 * This is used, when an object gets deleted or updated and you want to get
 * informed about this action.
 *
 * Update is not supported yet (not necessary).
 *
 * @see POJOManagerEvent
 * @see POJOManagerListenerSupport
 * @see POJOManagerAdapter
 *
 * @author Manuel Geier (CreativeWorkers)
 */
public interface POJOManagerListener extends java.util.EventListener {
    void deleteAction(POJOManagerEvent evt);
//    void updateAction(POJOManagerEvent evt);
}

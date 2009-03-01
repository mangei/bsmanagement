package cw.boardingschoolmanagement.app;

/**
 * This is used, when an object gets deleted or updated and you want to get
 * informed about this action.
 *
 * Update is not supported yet (not necessary).
 *
 * @see CascadeEvent
 * @see CascadeListenerSupport
 * @see CascadeAdapter
 *
 * @author Manuel Geier (CreativeWorkers)
 */
public interface CascadeListener extends java.util.EventListener {
    void deleteAction(CascadeEvent evt);
//    void updateAction(CascadeEvent evt);
}

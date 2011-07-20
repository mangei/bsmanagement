package cw.boardingschoolmanagement.app;

import java.util.EventListener;

/**
 * This is used, when a button on an panel or something
 * else is pressed and you want to inform others about this.
 *
 * @see ButtonEvent
 * @see ButtonListenerSupport
 *
 * @author Manuel Geier (CreativeWorkers)
 */
public interface ButtonListener extends EventListener {
    void buttonPressed(ButtonEvent evt);
}

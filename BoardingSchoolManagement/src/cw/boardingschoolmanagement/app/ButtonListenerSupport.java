package cw.boardingschoolmanagement.app;

import java.util.ArrayList;
import java.util.List;

/**
 * Supports the management of ButtonListeners.
 *
 * @see ButtonEvent
 * @see ButtonListener
 *
 * @author Manuel Geier (CreativeWorkers)
 */
public class ButtonListenerSupport {

    // Manages the listener list.
    private List<ButtonListener> listeners;

    public ButtonListenerSupport() {
        listeners = new ArrayList<ButtonListener>();
    }

    public void addButtonListener(ButtonListener listener) {
	listeners.add(listener);
    }

    public void removeButtonListener(ButtonListener listener) {
	listeners.remove(listener);
    }

    public void fireButtonPressed(ButtonEvent evt) {
        for(int i=0, l=listeners.size(); i<l; i++) {
            listeners.get(i).buttonPressed(evt);
        }
    }

}

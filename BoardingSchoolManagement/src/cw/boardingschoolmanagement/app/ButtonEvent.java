package cw.boardingschoolmanagement.app;

/**
 * Descripes an ButtonEvent.
 *
 * @see ButtonListener
 * @see ButtonListenerSupport
 *
 * @author Manuel Geier (CreativeWorkers)
 */
public class ButtonEvent{

    public static final int OK_BUTTON = 1;
    public static final int CANCEL_BUTTON = 2;
    public static final int RESET_BUTTON = 3;
    public static final int SAVE_BUTTON = 4;
    public static final int EXIT_BUTTON = 5;
    public static final int SAVE_EXIT_BUTTON = 6;
    public static final int YES_BUTTON = 7;
    public static final int NO_BUTTON = 8;
    public static final int DELETE_BUTTON = 9;
    
    public int type;
    
    public ButtonEvent(int type) {
	this.type = type;
    }

    public int getType() {
	return type;
    }
}

package cw.boardingschoolmanagement.app;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.Icon;

/**
 * Base class for actions.<br>
 * Every action is running in its own thread.
 * 
 * @author Manuel Geier
 *
 */
public abstract class CWAction extends AbstractAction {

	public CWAction() {
		super();
	}
	
	public CWAction(String name, Icon icon) {
		super(name, icon);
	}

	public CWAction(String name) {
		super(name);
	}

	@Override
	public void actionPerformed(final ActionEvent e) {
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				action(e);
			}
			
		}, (String) super.getValue(Action.NAME)).start();
	}

	/**
	 * Run your action.
	 * @param e ActionEvent
	 */
	public abstract void action(ActionEvent e);
	
}

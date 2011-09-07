package cw.boardingschoolmanagement.app;

import java.util.EventListener;

/**
 * Listener der anzeigt wenn etwas Programm geschieht(z.B. Buttons betaetigen)
 *
 * @see ApplicationAdapter
 *
 * @author Manuel Geier (CreativeWorkers)
 */
public interface ApplicationListener extends EventListener {
    /**
     * Verwendet im GUIManager; Wird in einer Anonymen-Klasse ueberladen und
     * schließt die Anwendung.
     */
    public void applicationClosing();
}

package cw.boardingschoolmanagement.gui.component;

import cw.boardingschoolmanagement.interfaces.Disposable;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JPanel;

/**
 *
 * @author Manuel Geier
 */
public class CWJPanel
        extends JPanel
        implements Disposable
{

    private List<Disposable> disposableListenerList = new ArrayList<Disposable>();

    public CWJPanel() {
    }

    public void addDisposableListener(Disposable listener) {
        disposableListenerList.add(listener);
    }

    public void removeDisposableListener(Disposable listener) {
        disposableListenerList.remove(listener);
    }

    public void dispose() {
        Object[] toArray = disposableListenerList.toArray();
        for(int i=0, l=toArray.length; i<l; i++) {
            ((Disposable)toArray[i]).dispose();
        }
        disposableListenerList.clear();
    }

}

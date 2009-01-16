package cw.boardingschoolmanagement.gui.component;

import java.awt.BorderLayout;
import java.util.LinkedList;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author ManuelG
 */
public class JPathPanel extends JPanel {

    private String separator = " >> ";
//    private LinkedList entries;

    private JLabel path;

    public JPathPanel() {
//        entries = new LinkedList();

        path = new JLabel();
        
        setLayout(new BorderLayout());
        add(path, BorderLayout.CENTER);
    }

//    public void addPathEntry(String name) {
//        entries.push(name);
//        reloadPath();
//    }
//
//    public void removeLastPathEntry() {
//        entries.pop();
//        reloadPath();
//    }
//
//    public void clearPath() {
//        entries.clear();
//        reloadPath();
//    }

    public void reloadPath(LinkedList<JComponent> lastComponents, JComponent shownComponent) {
        StringBuilder pathStr = new StringBuilder("<html><b>Pfad:</b></html> ");
        System.out.println("size1: " + lastComponents.size());
        for(int i=0, l=lastComponents.size(); i<l; i++) {
            pathStr.append(lastComponents.get(i).getName());
            pathStr.append(separator);
        }
        pathStr.append(shownComponent.getName());
        path.setText(pathStr.toString());
        System.out.println("size2: " + lastComponents.size());
        System.out.println("path: " + pathStr.toString());
        validate();
    }
}

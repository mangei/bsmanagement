package cw.boardingschoolmanagement.gui.component;

import cw.boardingschoolmanagement.gui.component.CWView.CWHeaderInfo;
import cw.boardingschoolmanagement.manager.PropertiesManager;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.LinkedList;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;

/**
 *
 * @author ManuelG
 */
public class CWPathPanel extends JPanel {

    private String separator = ">";
    private JPanel innerPathPanel;
    private PathPanelPosition position;

    private Action northPositionAction;
    private Action southPositionAction;

    public enum PathPanelPosition {

        NORTH, SOUTH
    }

    public CWPathPanel() {
        setLayout(new BorderLayout());

        setBackground(new Color(201,208,218));
        setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1,new Color(178,187,200)));

        innerPathPanel = new JPanel();
        innerPathPanel.setOpaque(false);
        innerPathPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        add(innerPathPanel, BorderLayout.CENTER);

        final JPopupMenu pop = new JPopupMenu();
        JMenu positionMenu = new JMenu("Position");
        pop.add(positionMenu);
        positionMenu.add(northPositionAction = new AbstractAction("Oben") {
            public void actionPerformed(ActionEvent e) {
                setPosition(PathPanelPosition.NORTH);
            }
        });
        positionMenu.add(southPositionAction = new AbstractAction("Unten") {
            public void actionPerformed(ActionEvent e) {
                setPosition(PathPanelPosition.SOUTH);
            }
        });
        pop.add(new AbstractAction("Pfadanzeige schließen") {
            public void actionPerformed(ActionEvent e) {
                CWPathPanel.this.setVisible(false);
            }
        });

        // Listener to open the popup menu
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(e.getButton() == MouseEvent.BUTTON3) {
                    pop.show(CWPathPanel.this, e.getX(), e.getY());
                }
            }
        });

        // Listener to update the actions
        addPropertyChangeListener("position", new PropertyChangeListener() {
            public void propertyChange(PropertyChangeEvent evt) {
                updateActions();
            }
        });
        addPropertyChangeListener("visible", new PropertyChangeListener() {
            public void propertyChange(PropertyChangeEvent evt) {
                PropertiesManager.setProperty("configuration.general.pathPanelActive", Boolean.toString(CWPathPanel.this.isVisible()));
            }
        });

        // Load the position of the PathPanel
        position = PathPanelPosition.valueOf(PropertiesManager.getProperty("configuration.general.pathPanelPosition", PathPanelPosition.SOUTH.name()));

        // Load the visibility of the PathPanel
        setVisible(Boolean.parseBoolean(PropertiesManager.getProperty("configuration.general.pathPanelActive", Boolean.TRUE.toString())));

        updateActions();
    }

    private void updateActions() {
        if(position == PathPanelPosition.NORTH) {
            northPositionAction.setEnabled(false);
            southPositionAction.setEnabled(true);
        } else {
            northPositionAction.setEnabled(true);
            southPositionAction.setEnabled(false);
        }
    }

    public void reloadPath(LinkedList<CWView> lastViews, CWView shownView) {

        innerPathPanel.removeAll();

        innerPathPanel.add(new JLabel("Sie sind hier: "));
        
        for (int i = lastViews.size()-1; i >= 0; i--) {
            innerPathPanel.add(createLabel(lastViews.get(i)));
            innerPathPanel.add(new JLabel(separator));
        }

        JLabel l = createLabel(shownView);
        l.setFont(l.getFont().deriveFont(Font.BOLD));
        innerPathPanel.add(l);

        innerPathPanel.validate();
        innerPathPanel.repaint();
    }

    private JLabel createLabel(CWView view) {
        JLabel label = new JLabel();

        CWHeaderInfo headerInfo = view.getHeaderInfo();
        
        label.setText(headerInfo.getHeaderText());
        label.setToolTipText(headerInfo.getDescription());
        label.setIcon(headerInfo.getSmallIcon());

        return label;
    }

    public PathPanelPosition getPosition() {
        return position;
    }

    public void setPosition(PathPanelPosition position) {
        PathPanelPosition old = this.position;
        this.position = position;
        firePropertyChange("position", old, position);
        PropertiesManager.setProperty("configuration.general.pathPanelPosition", position.name());
    }
}

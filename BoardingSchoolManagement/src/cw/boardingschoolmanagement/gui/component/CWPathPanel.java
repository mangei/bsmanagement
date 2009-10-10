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
import java.util.LinkedList;
import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;

/**
 *
 * @author ManuelG
 */
public class CWPathPanel extends JPanel {

    private String separator = ">";
    private JPanel pathPanel;

    public CWPathPanel() {
        setLayout(new BorderLayout());

        setBackground(new Color(201,208,218));
        setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1,new Color(178,187,200)));

        pathPanel = new JPanel();
        pathPanel.setOpaque(false);
        pathPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        add(pathPanel, BorderLayout.CENTER);

        final JPopupMenu pop = new JPopupMenu();
        pop.add(new AbstractAction("Pfadanzeige schließen") {
            public void actionPerformed(ActionEvent e) {
                CWPathPanel.this.setVisible(false);
                PropertiesManager.setProperty("configuration.general.pathPanelActive", "false");
            }
        });

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(e.getButton() == MouseEvent.BUTTON3) {
                    pop.show(CWPathPanel.this, e.getX(), e.getY());
                }
            }
        });
    }

    public void reloadPath(LinkedList<CWView> lastViews, CWView shownView) {

        pathPanel.removeAll();

        pathPanel.add(new JLabel("Sie sind hier: "));
        
        for (int i = lastViews.size()-1; i >= 0; i--) {
            pathPanel.add(createLabel(lastViews.get(i)));
            pathPanel.add(new JLabel(separator));
        }

        JLabel l = createLabel(shownView);
        l.setFont(l.getFont().deriveFont(Font.BOLD));
        pathPanel.add(l);

        pathPanel.validate();
        pathPanel.repaint();
    }

    private JLabel createLabel(CWView view) {
        JLabel label = new JLabel();

        CWHeaderInfo headerInfo = view.getHeaderInfo();
        
        label.setText(headerInfo.getHeaderText());
        label.setToolTipText(headerInfo.getDescription());
        label.setIcon(headerInfo.getSmallIcon());

        return label;
    }
}

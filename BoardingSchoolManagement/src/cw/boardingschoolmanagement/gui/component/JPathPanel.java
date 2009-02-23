package cw.boardingschoolmanagement.gui.component;

import cw.boardingschoolmanagement.interfaces.HeaderInfoCallable;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.util.LinkedList;
import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author ManuelG
 */
public class JPathPanel extends JPanel {

    private String separator = ">";
    private JPanel pathPanel;

    public JPathPanel() {
        setLayout(new BorderLayout());

        setBackground(new Color(201,208,218));
        setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1,new Color(178,187,200)));

        pathPanel = new JPanel();
        pathPanel.setOpaque(false);
        pathPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        add(pathPanel, BorderLayout.CENTER);
        
        add(new JButton(new AbstractAction("   X   ") {
            public void actionPerformed(ActionEvent e) {
                JPathPanel.this.setVisible(false);
            }
        }), BorderLayout.EAST);
    }

    public void reloadPath(LinkedList<JComponent> lastComponents, JComponent shownComponent) {

        pathPanel.removeAll();

        pathPanel.add(new JLabel("Sie sind hier: "));
        
        for (int i = lastComponents.size()-1; i >= 0; i--) {
            pathPanel.add(createLabel(lastComponents.get(i)));
            pathPanel.add(new JLabel(separator));
        }

        JLabel l = createLabel(shownComponent);
        l.setFont(l.getFont().deriveFont(Font.BOLD));
        pathPanel.add(l);

        pathPanel.validate();
        pathPanel.repaint();
    }

    private JLabel createLabel(JComponent comp) {
        HeaderInfoCallable headerInfoCallable;
        JLabel label = new JLabel();
        if (comp instanceof HeaderInfoCallable) {
            headerInfoCallable = (HeaderInfoCallable) comp;
            label.setText(headerInfoCallable.getHeaderInfo().getHeaderText());
            label.setToolTipText(headerInfoCallable.getHeaderInfo().getDescription());
            label.setIcon(headerInfoCallable.getHeaderInfo().getSmallIcon());
        } else {
            label.setText(comp.getName());
        }
        return label;
    }
}

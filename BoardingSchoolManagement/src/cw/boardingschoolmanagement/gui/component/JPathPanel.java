package cw.boardingschoolmanagement.gui.component;

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

    private JLabel separator;
    private JPanel pathPanel;

    public JPathPanel() {
        setLayout(new BorderLayout());

        setBackground(new Color(201,208,218));
        setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1,new Color(178,187,200)));

        pathPanel = new JPanel();
        pathPanel.setOpaque(false);
        pathPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        add(pathPanel, BorderLayout.CENTER);
        
        separator = new JLabel(">");

        add(new JButton(new AbstractAction("   X   ") {
            public void actionPerformed(ActionEvent e) {
                JPathPanel.this.setVisible(false);
            }
        }), BorderLayout.EAST);
    }

    public void reloadPath(LinkedList<JComponent> lastComponents, JComponent shownComponent) {

        pathPanel.removeAll();

        pathPanel.add(new JLabel("Sie sind hier: "));
        
        for (int i = 0, l = lastComponents.size(); i < l; i++) {
            pathPanel.add(createLabel(lastComponents.get(i)));
            pathPanel.add(separator);
        }

        JLabel l = createLabel(shownComponent);
        l.setFont(l.getFont().deriveFont(Font.BOLD));
        pathPanel.add(l);

        pathPanel.validate();
        pathPanel.repaint();
    }

    private JLabel createLabel(JComponent comp) {
        JViewPanel viewPanel;
        JLabel label = new JLabel();
        if (comp instanceof JViewPanel) {
            viewPanel = (JViewPanel) comp;
            label.setText(viewPanel.getHeaderInfo().getHeaderText());
            label.setToolTipText(viewPanel.getHeaderInfo().getDescription());
            label.setIcon(viewPanel.getHeaderInfo().getSmallIcon());
        } else {
            System.out.println("KKK");
            label.setText(comp.getName());
        }

        System.out.println("comp: " + comp.getName());
        System.out.println("label: " + label.getText());
        return label;
    }
}

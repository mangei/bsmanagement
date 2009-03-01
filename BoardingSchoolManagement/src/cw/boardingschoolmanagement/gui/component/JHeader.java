package cw.boardingschoolmanagement.gui.component;

import cw.boardingschoolmanagement.app.CWUtils;
import com.jidesoft.swing.JideSwingUtilities;
import cw.boardingschoolmanagement.gui.ui.HeaderButtonUI2;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JPanel;

/**
 *
 * @author Manuel Geier
 */
public class JHeader extends JComponent {

    private JPanel menubuttons;

    public JHeader() {
        setPreferredSize(new Dimension(100, 50));

        setLayout(null);

        int x = 300;
        menubuttons = new JPanel(new FlowLayout(FlowLayout.LEFT, 4, 0));
        menubuttons.setOpaque(false);
        menubuttons.setBounds(x, 0, 1000, 50);
        add(menubuttons);
    }

    private static final Color COLOR1 = new Color(60,82,112);
    private static final Color COLOR2 = new Color(19,30,48);
    
    @Override
    protected void paintComponent(Graphics g) {
        Rectangle rect = new Rectangle(0, 0, getWidth(), getHeight());
        JideSwingUtilities.fillGradient((Graphics2D) g, rect, COLOR1, COLOR2, true);
        ImageIcon logo = (ImageIcon) CWUtils.loadIcon("cw/boardingschoolmanagement/images/cw-logo.png");
        g.drawImage(logo.getImage(), getWidth() - logo.getIconWidth() - (getHeight() - logo.getIconHeight()) / 2, getHeight() - logo.getIconHeight() - (getHeight() - logo.getIconHeight()) / 2, null);
        ImageIcon title = (ImageIcon) CWUtils.loadIcon("cw/boardingschoolmanagement/images/header.png");
        g.drawImage(title.getImage(), 0, 0, null);
        setOpaque(false);
        super.paintComponent(g);
    }

    public void addHeaderMenuItem(AbstractAction action) {
        JButton button = new JButton(action);
        button.setUI(new HeaderButtonUI2());
        menubuttons.add(button);
    }
    
}

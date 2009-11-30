package cw.boardingschoolmanagement.gui.component;

import cw.boardingschoolmanagement.app.CWUtils;
import com.jidesoft.swing.JideSwingUtilities;
import cw.boardingschoolmanagement.gui.ui.HeaderButtonUI2;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.font.FontRenderContext;
import java.awt.font.LineMetrics;
import java.awt.geom.AffineTransform;
import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

/**
 *
 * @author Manuel Geier
 */
public class CWHeader extends JComponent {

    private JPanel menubuttons;

    public CWHeader() {
        setPreferredSize(new Dimension(100, 50));

        setLayout(null);

        int x = 300;
        menubuttons = new JPanel(new FlowLayout(FlowLayout.LEFT, 4, 0));
        menubuttons.setOpaque(false);
        menubuttons.setBounds(x, 0, 1000, 50);
        add(menubuttons);
    }

    private static final Color BG_TOP = new Color(60,82,112);
    private static final Color BG_BOTTOM = new Color(19,30,48);
    private static final String SOFTWARE_NAME = "Internatsverwaltung";
    private static final Color COLOR_SOFTWARE_NAME = new Color(255,255,255);
    private static final Font FONT_SOFTWARE_NAME = new Font("Arial", Font.ITALIC|Font.PLAIN, 28);
    private static final String SOFTWARE_VERSION = "preAlpha";
    private static final Color COLOR_SOFTWARE_VERSION = new Color(255,255,255,180);
    private static final Font FONT_SOFTWARE_VERSION = new Font("Arial", Font.PLAIN, 10);

    
    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        Rectangle rect = new Rectangle(0, 0, getWidth(), getHeight());
        JideSwingUtilities.fillGradient(g2d, rect, BG_TOP, BG_BOTTOM, true);
        ImageIcon logo = CWUtils.loadIcon("cw/boardingschoolmanagement/images/cw-logo.png");
        g2d.drawImage(logo.getImage(), getWidth() - logo.getIconWidth() - (getHeight() - logo.getIconHeight()) / 2, getHeight() - logo.getIconHeight() - (getHeight() - logo.getIconHeight()) / 2, null);
//        ImageIcon title = CWUtils.loadIcon("cw/boardingschoolmanagement/images/header.png");
//        g.drawImage(title.getImage(), 0, 0, null);

        // Zeichne den Softwarenamen
        g2d.setFont(FONT_SOFTWARE_NAME);
        g2d.setColor(COLOR_SOFTWARE_NAME);
        int nameWidth = g.getFontMetrics().stringWidth(SOFTWARE_NAME);
        g2d.drawString(SOFTWARE_NAME, 20, 35);

//        // Zeichne die Version
        g2d.setFont(FONT_SOFTWARE_VERSION);
        g2d.setColor(COLOR_SOFTWARE_VERSION);
        int versionWidth = g.getFontMetrics().stringWidth(SOFTWARE_VERSION);
//        g2d.drawString(SOFTWARE_VERSION, getWidth() - versionWidth - logo.getIconWidth() - 15, getHeight() - 5);
//        g2d.drawString(SOFTWARE_VERSION, 25, getHeight() - 5);
        g2d.drawString(SOFTWARE_VERSION, nameWidth + 22, getHeight() - 10);

        setOpaque(false);
        super.paintComponent(g2d);
    }

    public void addHeaderMenuItem(AbstractAction action) {
        JButton button = new JButton(action);
        button.setUI(new HeaderButtonUI2());
        menubuttons.add(button);
    }
    
}

package cw.boardingschoolmanagement.gui.component;

import cw.boardingschoolmanagement.gui.ui.JButtonPanelUI;
import com.jidesoft.swing.JideSwingUtilities;
import com.l2fprod.common.swing.JButtonBar;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import javax.swing.JButton;
import javax.swing.border.EmptyBorder;

/**
 *
 * @author Manuel Geier
 */
public class JButtonPanel extends JButtonBar {

    public JButtonPanel() {
        setUI(new JButtonPanelUI());
        FlowLayout layout = new FlowLayout();
        layout.setAlignment(FlowLayout.LEFT);
        setLayout(layout);
        
    }
    
    public void add(JButton button) {
//        button.setContentAreaFilled(false);
//        button.setBorder(new EmptyBorder(10,10,10,10));
//        button.setFocusable(false);
//        button.setFont(button.getFont().deriveFont(Font.PLAIN));
        super.add(button);
    }

    private static final Color COLOR1 = new Color(234,237,241);
    
    @Override
    protected void paintComponent(Graphics g) {
        Rectangle rect = new Rectangle(0, 0, getWidth(), getHeight());
        JideSwingUtilities.fillGradient((Graphics2D) g, rect, Color.WHITE, COLOR1, true);
//        g.setColor(new Color(201,208,218));
//        g.fillRect(0, 0, getWidth(), getHeight());
        setOpaque(false);
        super.paintComponent(g);
    }
}

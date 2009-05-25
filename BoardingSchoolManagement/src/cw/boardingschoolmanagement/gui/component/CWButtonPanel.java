package cw.boardingschoolmanagement.gui.component;

import cw.boardingschoolmanagement.gui.ui.JButtonPanelUI;
import com.jidesoft.swing.JideSwingUtilities;
import com.l2fprod.common.swing.JButtonBar;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import javax.swing.JButton;

/**
 *
 * @author Manuel Geier
 */
public class CWButtonPanel extends JButtonBar {

    public CWButtonPanel() {
        setUI(new JButtonPanelUI());
        FlowLayout layout = new FlowLayout();
        layout.setAlignment(FlowLayout.LEFT);
        setLayout(layout);
        setOpaque(true);
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
        super.paintComponent(g);
        if(isOpaque()) {
            Rectangle rect = new Rectangle(0, 0, getWidth(), getHeight());
            JideSwingUtilities.fillGradient((Graphics2D) g, rect, Color.WHITE, COLOR1, true);
        }
    }
}

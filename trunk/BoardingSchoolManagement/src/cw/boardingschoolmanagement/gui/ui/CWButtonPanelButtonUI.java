package cw.boardingschoolmanagement.gui.ui;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.FontMetrics;
import java.awt.Graphics;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.geom.RoundRectangle2D;
import javax.swing.ButtonModel;
import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.plaf.basic.BasicButtonUI;

/**
 * BlueishButtonUI. <br>
 *  
 */
public class CWButtonPanelButtonUI
        extends BasicButtonUI {

    private boolean roundCorners;

    public CWButtonPanelButtonUI() {
        this(false);
    }

    public CWButtonPanelButtonUI(boolean roundCorners) {
        super();
        this.roundCorners = roundCorners;
    }

    public void installUI(JComponent c) {
        super.installUI(c);

        AbstractButton button = (AbstractButton) c;
        button.setRolloverEnabled(true);
        button.setBorder(BorderFactory.createEmptyBorder(3, 3, 3, 3));
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    }

    private static final Color BORDER_OVER = new Color(200,200,200);
    private static final Color FILL_OVER = new Color(235,235,235);
    private static final Color BORDER_ACTIVE = new Color(200,200,200);
    private static final Color FILL_ACTIVE = new Color(235,235,235);

    public void paint(Graphics g, JComponent c) {

        AbstractButton button = (AbstractButton) c;
        if ((button.getModel().isRollover() || (button.getClientProperty("active") != null && (Boolean)button.getClientProperty("active"))) && button.getModel().isEnabled()) {
            Color oldColor = g.getColor();

            int x = 0;
            int y = 0;
            int w = c.getWidth()-1;
            int h = c.getHeight()-1;
            int arc = 6;

//            if(button.getClientProperty("roundCorners") != null && !(Boolean)button.getClientProperty("roundCorners")) {
            if(!roundCorners) {
                arc = 0;
            }

            Color fillColor = (!button.getModel().isRollover()
                    && button.getClientProperty("active") != null
                    && (Boolean)button.getClientProperty("active"))
                    ? FILL_ACTIVE : FILL_OVER;
            Color borderColor = (!button.getModel().isRollover()
                    && button.getClientProperty("active") != null
                    && (Boolean)button.getClientProperty("active"))
                    ? BORDER_ACTIVE : BORDER_OVER;

            Graphics2D g2d = (Graphics2D) g;
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            // Button Background
            Shape backgroundText = new RoundRectangle2D.Double(
                    x,
                    y,
                    w,
                    h,
                    arc,arc
            );
            g2d.setColor(fillColor);
            g2d.fill(backgroundText);
            g2d.setColor(borderColor);
            g2d.draw(backgroundText);

            g.setColor(oldColor);
        }

        super.paint(g, c);
    }

    protected void paintText(Graphics g, JComponent c, Rectangle textRect, String text) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);
        
        AbstractButton b = (AbstractButton) c;
        ButtonModel model = b.getModel();
//        FontMetrics fm = SwingUtilities2.getFontMetrics(c, g);
//        int mnemonicIndex = b.getDisplayedMnemonicIndex();

        FontMetrics fm = g.getFontMetrics();

        /* Draw the Text */
        if (model.isEnabled()) {
            /*** paint the text normally */
            g.setColor(b.getForeground());
            g.drawString(text, textRect.x + getTextShiftOffset(), textRect.y + fm.getAscent() + getTextShiftOffset());
//	    SwingUtilities2.drawStringUnderlineCharAt(c, g,text, mnemonicIndex,
//					  textRect.x + getTextShiftOffset(),
//					  textRect.y + fm.getAscent() + getTextShiftOffset());
        } else {
            /*** paint the text disabled ***/
//	    g.setColor(b.getBackground());
            g.setColor(Color.LIGHT_GRAY);
            g.drawString(text, textRect.x + getTextShiftOffset(), textRect.y + fm.getAscent() + getTextShiftOffset());
//	    SwingUtilities2.drawStringUnderlineCharAt(c, g,text, mnemonicIndex,
//					  textRect.x, textRect.y + fm.getAscent());
//	    g.setColor(b.getBackground().darker());
//	    SwingUtilities2.drawStringUnderlineCharAt(c, g,text, mnemonicIndex,
//					  textRect.x, textRect.y + fm.getAscent());
        }
    }
}

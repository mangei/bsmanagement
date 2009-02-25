package cw.boardingschoolmanagement.gui.ui;

import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.geom.Area;
import java.awt.geom.RoundRectangle2D;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.plaf.basic.BasicPanelUI;

/**
 *
 * @author ManuelG
 */
public class RoundPanelUITest extends BasicPanelUI {

    private static final Color backgroundColor1 = new Color(240, 240, 240);
    private static final Color backgroundColor2 = new Color(214, 219, 191);
    private static final Color borderColor = new Color(86, 88, 72);
    private static final Color borderColorAlpha1 = new Color(86, 88, 72, 100);
    private static final Color borderColorAlpha2 = new Color(86, 88, 72, 50);
    private static final Color borderHighlight = new Color(225, 224, 224);
    private static final Color COLOR1 = new Color(234, 237, 241);
    private static final Color COLOR2 = new Color(215, 220, 228);

    @Override
    protected void installDefaults(JPanel p) {
        p.setOpaque(false);
    }

    @Override
    public void paint(Graphics g, JComponent c) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        Insets vInsets = c.getInsets();

        int w = c.getWidth() - (vInsets.left + vInsets.right)-1;
        int h = c.getHeight() - (vInsets.top + vInsets.bottom)-1;

        int x = vInsets.left;
        int y = vInsets.top;
        int arc = 16;

        Shape vButtonShape = new RoundRectangle2D.Double((double) x, (double) y, (double) w, (double) h, (double) arc, (double) arc);

        Area area = new Area(vButtonShape);
//        area.add(new Area(new Rectangle2D.Double(x, y+arc/2, w, h-arc/2)));

        Shape vOldClip = g.getClip();

        // Background
        g2d.setClip(area);
        GradientPaint vPaint = new GradientPaint(x, y, Color.WHITE, x, y + h, COLOR1);
        g2d.setColor(backgroundColor1);
        g2d.setPaint(vPaint);
        g2d.fillRect(x, y, w, h);
        g2d.setClip(vOldClip);

        // Border
        g2d.setColor(COLOR2);
        g2d.draw(area);

//        GradientPaint vPaint = new GradientPaint(x, y, borderColor, x, y + h, borderHighlight);
//        g2d.setPaint(vPaint);
//        g2d.drawRoundRect(x, y, w, h, arc, arc);

//        g2d.clipRect(x, y, w + 1, h - arc / 4);
//        g2d.setColor(borderColorAlpha1);
//        g2d.drawRoundRect(x, y + 1, w, h - 1, arc, arc - 1);
//
//        g2d.setClip(vOldClip);
//        g2d.setColor(borderColorAlpha2);
//        g2d.drawRoundRect(x + 1, y + 2, w - 2, h - 3, arc, arc - 2);
    }
}

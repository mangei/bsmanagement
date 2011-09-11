/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cw.boardingschoolmanagement.gui.ui;

import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.geom.RoundRectangle2D;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.plaf.basic.BasicLabelUI;

/**
 *
 * @author ManuelG
 */
public class ActiveRoundMenuButtonUI extends BasicLabelUI {

    private static final Color COLOR1 = new Color(126, 154, 192);
    private static final Color COLOR2 = new Color(73, 106, 156);
    private static final Color COLOR3 = new Color(69, 101, 152);
    private static final Color LIGHT_VIOLET_COLOR = new Color(201,208,218);

    @Override
    protected void installDefaults(JLabel c) {
    }

    @Override
    public void paint(Graphics g, JComponent c) {

        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int w = c.getWidth()-5;
        int h = c.getHeight()-1;

        int x = 2;
        int y = 0;

        int arc1 = h/3*2-1;
        int arc2 = h-1;

        if(arc1%2 == 1) {
            arc1--;
        }
        if(arc2%2 == 1) {
            arc2--;
        }

        g2d.setColor(LIGHT_VIOLET_COLOR);
        g2d.fillRect(0, 0, c.getWidth(), c.getHeight());

        Shape vButtonShapeFill = new RoundRectangle2D.Double((double) x, (double) y, (double) w+1, (double) h, (double) arc1, (double) arc2);
        Shape vButtonShapeBorder = new RoundRectangle2D.Double((double) x, (double) y, (double) w, (double) h, (double) arc1, (double) arc2);

        // Background
        Shape vOldClip = g.getClip();
        g2d.setClip(vButtonShapeFill);
        GradientPaint vPaint = new GradientPaint(x, y, COLOR1, x, y + h, COLOR2);
        g2d.setPaint(vPaint);
        g2d.fillRect(x, y, w, h);
        g2d.setClip(vOldClip);

        // Border
        g2d.setColor(COLOR3);
        g2d.draw(vButtonShapeBorder);


//        // Blue Background Gradient
//        Rectangle rect = new Rectangle(x, y, w, h);
//        JideSwingUtilities.fillGradient((Graphics2D) g, rect, COLOR1, COLOR2, true);
//
//        // Top Line
//        g.setColor(COLOR3);
//        g.drawLine(x, y, w, x);
//
//        // Bottom Line
//        g.setColor(COLOR3);
//        g.drawLine(x, h, w, h);

        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);

        super.paint(g,c);
    }
}

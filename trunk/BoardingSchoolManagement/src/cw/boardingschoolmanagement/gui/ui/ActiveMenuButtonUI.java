/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cw.boardingschoolmanagement.gui.ui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.plaf.basic.BasicLabelUI;

import com.jidesoft.swing.JideSwingUtilities;

/**
 *
 * @author ManuelG
 */
public class ActiveMenuButtonUI extends BasicLabelUI {

    private static final Color COLOR1 = new Color(126, 154, 192);
    private static final Color COLOR2 = new Color(73, 106, 156);
    private static final Color COLOR3 = new Color(69, 101, 152);
    private static final Color COLOR4 = new Color(201, 208, 218);

    @Override
    protected void installDefaults(JLabel c) {
    }

    @Override
    public void paint(Graphics g, JComponent c) {

        int w = c.getWidth();
        int h = c.getHeight();

        int x = 0;
        int y = 0;

        // Blue Background Gradient
        Rectangle rect = new Rectangle(x, y, w, h);
        JideSwingUtilities.fillGradient((Graphics2D) g, rect, COLOR1, COLOR2, true);

        // Top Line
        g.setColor(COLOR3);
        g.drawLine(x, y, w, x);

        // Bottom Line
        g.setColor(COLOR3);
        g.drawLine(x, h, w, h);

        super.paint(g,c);
    }
}

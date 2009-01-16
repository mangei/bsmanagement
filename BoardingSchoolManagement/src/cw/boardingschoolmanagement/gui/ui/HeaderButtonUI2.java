/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cw.boardingschoolmanagement.gui.ui;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.geom.Area;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;
import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.ButtonModel;
import javax.swing.JComponent;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.basic.BasicButtonListener;
import javax.swing.plaf.basic.BasicButtonUI;

/**
 *
 * @author ManuelG
 */
public class HeaderButtonUI2 extends BasicButtonUI {

    private static final HeaderButtonUI2 headerButtonUI = new HeaderButtonUI2();

    // NOTE: These are not really needed, but at this point we can't pull
    // them. Their values are updated purely for historical reasons.
    protected Color focusColor;
    protected Color selectColor;
    protected Color disabledTextColor;

    // ********************************
    //          Create PLAF
    // ********************************
    public static ComponentUI createUI(JComponent c) {
        return headerButtonUI;
    }

    // ********************************
    //          Install
    // ********************************
    public void installDefaults(AbstractButton b) {
        b.setOpaque(false);
        b.setForeground(Color.DARK_GRAY);
        b.setBorder(BorderFactory.createEmptyBorder(4, 10, 4, 10));
        super.installDefaults(b);
    }

    public void uninstallDefaults(AbstractButton b) {
        super.uninstallDefaults(b);
    }

    // ********************************
    //         Create Listeners
    // ********************************
    protected BasicButtonListener createButtonListener(AbstractButton b) {
        return super.createButtonListener(b);
    }

    // ********************************
    //         Default Accessors
    // ********************************
    protected Color getSelectColor() {
        selectColor = Color.WHITE;
        return selectColor;
    }

    protected Color getDisabledTextColor() {
        disabledTextColor = Color.GRAY;
        return disabledTextColor;
    }

    protected Color getFocusColor() {
        focusColor = Color.YELLOW;
        return focusColor;
    }

    private static final Color ENABLED_BORDER = new Color(215, 220, 228);
    private static final Color ENABLED_DARK = new Color(234, 237, 241);
    private static final Color ENABLED_LIGHT = Color.WHITE;
    private static final Color DISABLED_BORDER = new Color(215, 220, 228);
    private static final Color DISABLED_DARK = new Color(158, 158, 158);
    private static final Color DISABLED_LIGHT = new Color(204, 204, 204);
    private static final Color ACTIVE_BORDER = new Color(215, 220, 228);
    private static final Color ACTIVE_DARK = Color.WHITE;
    private static final Color ACTIVE_LIGHT = new Color(234, 237, 241);

    // ********************************
    //          Paint
    // ********************************
    /**
     * If necessary paints the background of the component, then
     * invokes <code>paint</code>.
     *
     * @param g Graphics to paint to
     * @param c JComponent painting on
     * @throws NullPointerException if <code>g</code> or <code>c</code> is
     *         null
     * @see javax.swing.plaf.ComponentUI#update
     * @see javax.swing.plaf.ComponentUI#paint
     * @since 1.5
     */
    public void update(Graphics g, JComponent c) {
        AbstractButton button = (AbstractButton) c;
        ButtonModel model = button.getModel();

        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int w = c.getWidth() - 5;
        int h = c.getHeight() - 1;

        int x = 2;
        int y = 0;

        int arc = h / 3 * 2 - 1;

        if (arc % 2 == 1) {
            arc--;
        }

        // Colors
        Color cBorder, cDark, cLight;

        if (c.isEnabled()) {
            if (model.isPressed()) {
                cBorder = ACTIVE_BORDER;
                cDark = ACTIVE_DARK;
                cLight = ACTIVE_LIGHT;

            } else {
                cBorder = ENABLED_BORDER;
                cDark = ENABLED_DARK;
                cLight = ENABLED_LIGHT;
            }
        } else {
            cBorder = DISABLED_BORDER;
            cDark = DISABLED_DARK;
            cLight = DISABLED_LIGHT;
        }

        Shape vButtonShapeFill = new RoundRectangle2D.Double((double) x, (double) y, (double) w+1, (double) h, (double) arc, (double) arc);
        Shape vButtonShapeBorder = new RoundRectangle2D.Double((double) x, (double) y, (double) w, (double) h, (double) arc, (double) arc);

        Area areaFill = new Area(vButtonShapeFill);
        areaFill.add(new Area(new Rectangle2D.Double(x, y, w, h-arc/2)));
        Area areaBorder = new Area(vButtonShapeBorder);
        areaBorder.add(new Area(new Rectangle2D.Double(x, y, w, h-arc/2)));

        // Background
        Shape vOldClip = g.getClip();
        g2d.setClip(areaFill);
        GradientPaint vPaint = new GradientPaint(x, y, cLight, x, y + h, cDark);
        g2d.setPaint(vPaint);
        g2d.fillRect(x, y, w, h);
        g2d.setClip(vOldClip);

        // Border
        g2d.setColor(cBorder);
        g2d.draw(areaBorder);

        paint(g, c);
    }

    @Override
    public void paint(Graphics g, JComponent c) {
        super.paint(g, c);
    }

    protected void paintButtonPressed(Graphics g, AbstractButton b) {

        ButtonModel model = b.getModel();

        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int w = b.getWidth() - 5;
        int h = b.getHeight() - 1;

        int x = 2;
        int y = 0;

        int arc = h / 3 * 2 - 1;

        if (arc % 2 == 1) {
            arc--;
        }

        // Colors
        Color cBorder, cDark, cLight;

        if (b.isEnabled()) {
            if (model.isPressed()) {
                cBorder = ACTIVE_BORDER;
                cDark = ACTIVE_DARK;
                cLight = ACTIVE_LIGHT;

            } else {
                cBorder = ENABLED_BORDER;
                cDark = ENABLED_DARK;
                cLight = ENABLED_LIGHT;
            }
        } else {
            cBorder = DISABLED_BORDER;
            cDark = DISABLED_DARK;
            cLight = DISABLED_LIGHT;
        }

        Shape vButtonShapeFill = new RoundRectangle2D.Double((double) x, (double) y, (double) w+1, (double) h, (double) arc, (double) arc);
        Shape vButtonShapeBorder = new RoundRectangle2D.Double((double) x, (double) y, (double) w, (double) h, (double) arc, (double) arc);

        Area areaFill = new Area(vButtonShapeFill);
        areaFill.add(new Area(new Rectangle2D.Double(x, y, w, h-arc/2)));
        Area areaBorder = new Area(vButtonShapeBorder);
        areaBorder.add(new Area(new Rectangle2D.Double(x, y, w, h-arc/2)));

        // Background
        Shape vOldClip = g.getClip();
        g2d.setClip(areaFill);
        GradientPaint vPaint = new GradientPaint(x, y, cLight, x, y + h, cDark);
        g2d.setPaint(vPaint);
        g2d.fillRect(x, y, w, h);
        g2d.setClip(vOldClip);

        // Border
        g2d.setColor(cBorder);
        g2d.draw(areaBorder);

    }

    protected void paintFocus2(Graphics g, AbstractButton b,
            Rectangle viewRect, Rectangle textRect, Rectangle iconRect) {

        Rectangle focusRect = new Rectangle();
        String text = b.getText();
        boolean isIcon = b.getIcon() != null;

        // If there is text
        if (text != null && !text.equals("")) {
            if (!isIcon) {
                focusRect.setBounds(textRect);
            } else {
                focusRect.setBounds(iconRect.union(textRect));
            }
        } // If there is an icon and no text
        else if (isIcon) {
            focusRect.setBounds(iconRect);
        }

        g.setColor(getFocusColor());
        g.drawRect((focusRect.x - 1), (focusRect.y - 1),
                focusRect.width + 1, focusRect.height + 1);

    }

    protected void paintText(Graphics g, JComponent c, Rectangle textRect, String text) {
        AbstractButton b = (AbstractButton) c;
        ButtonModel model = b.getModel();
//        FontMetrics fm = SwingUtilities2.getFontMetrics(c, g);
        FontMetrics fm = g.getFontMetrics();
        int mnemIndex = b.getDisplayedMnemonicIndex();

        /* Draw the Text */
        if (model.isEnabled()) {
            /*** paint the text normally */
            g.setColor(b.getForeground());
        } else {
            /*** paint the text disabled ***/
            g.setColor(getDisabledTextColor());
        }
        g.drawString(text, textRect.x, textRect.y + fm.getAscent());
//        SwingUtilities2.drawStringUnderlineCharAt(c, g, text, mnemIndex,
//                textRect.x, textRect.y + fm.getAscent());
    }
}

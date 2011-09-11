/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cw.boardingschoolmanagement.gui.ui;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.geom.RoundRectangle2D;

import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.ButtonModel;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.basic.BasicButtonListener;
import javax.swing.plaf.basic.BasicButtonUI;

/**
 *
 * @author ManuelG
 */
public class MyButtonUITest extends JFrame {

    public static void main(String[] args) {
        new MyButtonUITest();
    }

    public MyButtonUITest() {
        super("ButtonTest");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new FlowLayout(FlowLayout.CENTER));

        JButton b = new JButton("Testbutton");
        b.setUI(new CWButtonUI());
        b.setBorder(BorderFactory.createEmptyBorder(4,10,4,10));
        add(b);
        b = new JButton("Testbutton");
        b.setUI(new CWButtonUI());
        b.setBorder(BorderFactory.createEmptyBorder(4,10,4,10));
        b.setEnabled(false);
        add(b);

        setSize(400, 400);
        setVisible(true);
    }

    public static class CWButtonUI extends BasicButtonUI {

        private static final CWButtonUI cwButtonUI = new CWButtonUI();

        // NOTE: These are not really needed, but at this point we can't pull
        // them. Their values are updated purely for historical reasons.
        protected Color focusColor;
        protected Color selectColor;
        protected Color disabledTextColor;

        // ********************************
        //          Create PLAF
        // ********************************
        public static ComponentUI createUI(JComponent c) {
            return cwButtonUI;
        }

        // ********************************
        //          Install
        // ********************************
        public void installDefaults(AbstractButton b) {
            b.setOpaque(false);
            b.setForeground(Color.DARK_GRAY);
//            b.setForeground(new Color(173,208,242));
//            b.setForeground(new Color(102,153,203));
            b.setBorder(null);
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
        
        private static final Color ENABLED_BORDER = new Color(69, 101, 152);
        private static final Color ENABLED_DARK = new Color(73, 106, 156);
        private static final Color ENABLED_LIGHT = new Color(126, 154, 192);
        private static final Color DISABLED_BORDER = new Color(138, 138, 138);
        private static final Color DISABLED_DARK = new Color(158, 158, 158);
        private static final Color DISABLED_LIGHT = new Color(204, 204, 204);
        private static final Color ACTIVE_BORDER = new Color(255, 204, 51);
        private static final Color ACTIVE_DARK = new Color(238, 198, 67);
        private static final Color ACTIVE_LIGHT = new Color(255, 222, 112);

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

            int arc1 = h / 3 * 2 - 1;
            int arc2 = h - 1;

            if (arc1 % 2 == 1) {
                arc1--;
            }
            if (arc2 % 2 == 1) {
                arc2--;
            }

            Shape vButtonShapeFill = new RoundRectangle2D.Double((double) x, (double) y, (double) w + 1, (double) h, (double) arc1, (double) arc2);
            Shape vButtonShapeBorder = new RoundRectangle2D.Double((double) x, (double) y, (double) w, (double) h, (double) arc1, (double) arc2);

            // Colors
            Color cBorder, cDark, cLight;

            if (c.isEnabled()) {
                if(model.isPressed()) {
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

            // Background
            Shape vOldClip = g.getClip();
            g2d.setClip(vButtonShapeFill);
            GradientPaint vPaint = new GradientPaint(x, y, cLight, x, y + h, cDark);
            g2d.setPaint(vPaint);
            g2d.fillRect(x, y, w, h);
            g2d.setClip(vOldClip);

            // Border
            g2d.setColor(cBorder);
            g2d.draw(vButtonShapeBorder);

            paint(g, c);
        }

        @Override
        public void paint(Graphics g, JComponent c) {
            super.paint(g, c);
        }



        protected void paintButtonPressed(Graphics g, AbstractButton b) {
//            if (b.isContentAreaFilled()) {
//                Dimension size = b.getSize();
//                g.setColor(getSelectColor());
//                g.fillRect(0, 0, size.width, size.height);
//            }

            ButtonModel model = b.getModel();

            Graphics2D g2d = (Graphics2D) g;
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            int w = b.getWidth() - 5;
            int h = b.getHeight() - 1;

            int x = 2;
            int y = 0;

            int arc1 = h / 3 * 2 - 1;
            int arc2 = h - 1;

            if (arc1 % 2 == 1) {
                arc1--;
            }
            if (arc2 % 2 == 1) {
                arc2--;
            }

            Shape vButtonShapeFill = new RoundRectangle2D.Double((double) x, (double) y, (double) w + 1, (double) h, (double) arc1, (double) arc2);
            Shape vButtonShapeBorder = new RoundRectangle2D.Double((double) x, (double) y, (double) w, (double) h, (double) arc1, (double) arc2);

            // Colors
            Color cBorder, cDark, cLight;

            if (b.isEnabled()) {
                if(model.isPressed()) {
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

            // Background
            Shape vOldClip = g.getClip();
            g2d.setClip(vButtonShapeFill);
            GradientPaint vPaint = new GradientPaint(x, y, cLight, x, y + h, cDark);
            g2d.setPaint(vPaint);
            g2d.fillRect(x, y, w, h);
            g2d.setClip(vOldClip);

            // Border
            g2d.setColor(cBorder);
            g2d.draw(vButtonShapeBorder);

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
//            FontMetrics fm = SwingUtilities2.getFontMetrics(c, g);
            int mnemIndex = b.getDisplayedMnemonicIndex();

            /* Draw the Text */
            if (model.isEnabled()) {
                /*** paint the text normally */
                g.setColor(b.getForeground());
            } else {
                /*** paint the text disabled ***/
                g.setColor(getDisabledTextColor());
            }
//            SwingUtilities2.drawStringUnderlineCharAt(c, g, text, mnemIndex,
//                    textRect.x, textRect.y + fm.getAscent());
        }
    }
}

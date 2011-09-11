package cw.boardingschoolmanagement.gui.ui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.plaf.basic.BasicPanelUI;

/**
 *
 * @author ManuelG
 */
public class ColoredPanelUI extends BasicPanelUI {

    public static final ColorStyle ONLY_STD_BORDER_COLOR_STYLE = new ColorStyle(null, new Color(178, 187, 200));
    public static final ColorStyle LIGHTGRAY_COLOR_STYLE = new ColorStyle(Color.LIGHT_GRAY, Color.GRAY);

    private ColorStyle colorStyle;
    private boolean hoverEffect;

    private MouseListener repaintListener;

    public ColoredPanelUI(ColorStyle colorStyle) {
        this(colorStyle, false);
    }

    public ColoredPanelUI(ColorStyle colorStyle, boolean hoverEffect) {
        this.colorStyle = colorStyle;
        this.hoverEffect = hoverEffect;
    }

    @Override
    protected void installDefaults(final JPanel p) {
        p.setOpaque(true);

        p.addMouseListener(repaintListener = new MouseAdapter() {

            @Override
            public void mouseEntered(MouseEvent e) {
                p.repaint();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                p.repaint();
            }
        });

    }

    @Override
    protected void uninstallDefaults(JPanel p) {
        p.removeMouseListener(repaintListener);
        super.uninstallDefaults(p);
    }

    @Override
    public void paint(Graphics g, JComponent c) {

        int w = c.getWidth();
        int h = c.getHeight();

        // Mousehover
        if(hoverEffect && c.getMousePosition() != null) {
            if(colorStyle.getBackgroundColor() != null) {
                g.setColor(colorStyle.getBackgroundColor().brighter());
                g.fillRect(0, 0, w, h);
            }
            if(colorStyle.getBorderColor() != null) {
                g.setColor(colorStyle.getBorderColor().brighter());
                g.drawRect(0, 0, w-1, h-1);
            }
        }

        // No Mousehover
        else {
            if(colorStyle.getBackgroundColor() != null) {
                g.setColor(colorStyle.getBackgroundColor());
                g.fillRect(0, 0, w, h);
            }
            if(colorStyle.getBorderColor() != null) {
                g.setColor(colorStyle.getBorderColor());
                g.drawRect(0, 0, w-1, h-1);
            }
        }
    }



    public static class ColorStyle {

        private Color backgroundColor;
        private Color borderColor;

        public ColorStyle(Color backgroundColor, Color borderColor) {
            this.backgroundColor = backgroundColor;
            this.borderColor = borderColor;
        }

        public Color getBackgroundColor() {
            return backgroundColor;
        }

        public Color getBorderColor() {
            return borderColor;
        }

    }
}

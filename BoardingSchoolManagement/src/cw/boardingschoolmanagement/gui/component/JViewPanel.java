package cw.boardingschoolmanagement.gui.component;

import com.jidesoft.swing.JideSwingUtilities;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.basic.BasicPanelUI;

/**
 *
 * @author Manuel Geier
 */
public class JViewPanel extends JPanel {

    private JButtonPanel buttonPanel;
    private JPanel topPanel;
    private JPanel contentPanel;
    private HeaderInfo headerInfo;

    // Alignment of the header
    public static final int LEFT = JLabel.LEFT;
    public static final int CENTER = JLabel.CENTER;
    public static final int RIGHT = JLabel.RIGHT;

    public JViewPanel() {
        this("");
    }

    public JViewPanel(String headerText) {
        this(headerText, null);
    }

    public JViewPanel(String headerText, JComponent comp) {
        setName(headerText);
        setLayout(new BorderLayout());
        setBorder(new EmptyBorder(0, 0, 0, 0));

        int gab = 10;
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(new EmptyBorder(gab, gab, gab, gab));
        mainPanel.setUI(new ViewPanelUI());

        buttonPanel = new JButtonPanel();
        buttonPanel.setBorder(new EmptyBorder(0, 10, 0, 10));

        JPanel topInfoActionPanel = new JPanel(new BorderLayout());
        headerInfo = new HeaderInfo(headerText);
        topInfoActionPanel.add(headerInfo, BorderLayout.NORTH);
        topInfoActionPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(topInfoActionPanel, BorderLayout.NORTH);

        topPanel = new JPanel(new BorderLayout());
        topPanel.setOpaque(false);
        mainPanel.add(topPanel, BorderLayout.NORTH);


        contentPanel = new JPanel();
        contentPanel.setLayout(new BorderLayout());
        contentPanel.setBorder(new EmptyBorder(0, 0, 0, 0));
        contentPanel.setOpaque(false);
        mainPanel.add(contentPanel, BorderLayout.CENTER);

        // Initial Component
        if (comp != null) {
            contentPanel.add(comp);
        }

        add(mainPanel, BorderLayout.CENTER);

        // Borderline
        setBorder(BorderFactory.createLineBorder(new Color(215, 220, 228)));
    }

    public String getHeaderText() {
        return headerInfo.getHeaderText();
    }

    public void setHeaderText(String headerText) {
        headerInfo.setHeaderText(headerText);
    }

    public JButtonPanel getButtonPanel() {
        return buttonPanel;
    }

    public JPanel getTopPanel() {
        return topPanel;
    }

    public JPanel getContentPanel() {
        return contentPanel;
    }

    public void setHeaderAlignment(int alignment) {
        headerInfo.setHeaderAlignment(alignment);
    }

    public int getHeaderAlignment() {
        return headerInfo.getHeaderAlignment();
    }

    private class HeaderInfo extends JPanel {

        private JLabel label;

        public HeaderInfo(String text) {
            setUI(new HeaderPanelUI());

            int gab = 3;

            label = new JLabel();
            label.setOpaque(false);
            label.setForeground(new Color(56, 61, 65));
            label.setFont(getFont().deriveFont(Font.BOLD, (float) 12));
            label.setText(text);
            label.setBorder(new EmptyBorder(gab, gab + 5, gab, gab));
            label.setHorizontalAlignment(JLabel.CENTER);

            setLayout(new BorderLayout());
            add(label, BorderLayout.CENTER);
        }

//        @Override
//        public Dimension getMaximumSize() {
//            if (headerText.isEmpty()) {
//                return new Dimension(0, 0);
//            }
//            return super.getMaximumSize();
//        }

        public String getHeaderText() {
            return label.getText();
        }

        public void setHeaderText(String headerText) {
            label.setText(headerText);
            if(headerText.isEmpty()) {
                setVisible(false);
            } else {
                setVisible(true);
            }
        }

        public void setHeaderAlignment(int alignment) {
            label.setHorizontalAlignment(alignment);
        }

        public int getHeaderAlignment() {
            return label.getHorizontalAlignment();
        }
    }

    private class ViewPanelUI extends BasicPanelUI {

        private final Color bottomBorderColor = new Color(215, 220, 228);
        private final Color lightGrayColor = new Color(234, 237, 241);

        @Override
        public void paint(Graphics g, JComponent c) {

            Rectangle rect = new Rectangle(0, 0, c.getWidth(), c.getHeight());

            // If the topPanel isn't visible, than twist the gradient
            // > 1: because the topPanel, contains the buttonPanel
            if (JViewPanel.this.buttonPanel.getComponentCount() > 0) {
                JideSwingUtilities.fillGradient((Graphics2D) g, rect, lightGrayColor, Color.WHITE, true);
            } else {
                JViewPanel.this.buttonPanel.setVisible(false);
                JideSwingUtilities.fillGradient((Graphics2D) g, rect, Color.WHITE, lightGrayColor, true);
            }

        }
    }

    private class HeaderPanelUI extends BasicPanelUI {

        private final Color bottomBorderColor = new Color(215, 220, 228);
        private final Color lightGrayColor = new Color(234, 237, 241);

        @Override
        public void paint(Graphics g, JComponent c) {
            Rectangle rect = new Rectangle(0, 0, c.getWidth(), c.getHeight());

            // Weißer Heller Verlauf
            JideSwingUtilities.fillGradient((Graphics2D) g, rect, Color.WHITE, lightGrayColor, true);
//            JideSwingUtilities.fillGradient((Graphics2D) g, rect, new Color(234, 237, 241), new Color(255, 255, 255), true);


//            Rectangle rect1 = new Rectangle(0, 0, getWidth(), getHeight()/2);
//            Rectangle rect2 = new Rectangle(0, getHeight()/2, getWidth(), getHeight()-1);
//            JideSwingUtilities.fillGradient((Graphics2D) g, rect1, new Color(255, 255, 255), new Color(234, 237, 241), true);
//            JideSwingUtilities.fillGradient((Graphics2D) g, rect2, new Color(234, 237, 241), new Color(255, 255, 255), true);

            // Blauer dunkler Verlauf
//            JideSwingUtilities.fillGradient((Graphics2D) g, rect, new Color(60, 82, 112), new Color(19, 30, 48), true);

            // Dunkelblau
//            g.setColor(new Color(42,60,85));
//            g.fillRect(0, 0, getWidth(), getHeight());

            // Helles Blau-Violett
//            g.setColor(new Color(201,208,218));
//            g.fillRect(0, 0, getWidth(), getHeight());

            // Weißer Heller Verlauf
//            JideSwingUtilities.fillGradient((Graphics2D) g, rect, new Color(201, 208, 218), new Color(168, 177, 189), false);

//            g.setColor(new Color(178, 187, 200));
            g.setColor(bottomBorderColor);
            g.drawLine(0, c.getHeight() - 1, c.getWidth(), c.getHeight() - 1);

        }


    }
}

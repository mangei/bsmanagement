package cw.boardingschoolmanagement.gui.component;

import com.jgoodies.binding.beans.Model;
import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;
import com.jidesoft.swing.JideSwingUtilities;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;
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
    private JPanel mainPanel;
    private HeaderInfoPanel headerInfoPanel;
    private HeaderInfo headerInfo;

    // Alignment of the header
    public static final int LEFT = JLabel.LEFT;
    public static final int CENTER = JLabel.CENTER;
    public static final int RIGHT = JLabel.RIGHT;

    public JViewPanel() {
        this(new HeaderInfo());
    }

    public JViewPanel(String headerText) {
        this(new HeaderInfo(headerText));
    }

    public JViewPanel(HeaderInfo headerInfo) {
        if(headerInfo == null) throw new NullPointerException("headerInfo is null");

        this.headerInfo = headerInfo;

        setName(headerInfo.getHeaderText());
        setLayout(new BorderLayout());
        setBorder(new EmptyBorder(0, 0, 0, 0));

        int gab = 10;
        mainPanel = new JPanel(new BorderLayout());
        mainPanel.setUI(new ViewPanelUI());
        mainPanel.setBorder(new EmptyBorder(gab, gab, gab, gab));

        buttonPanel = new JButtonPanel();
        buttonPanel.setBorder(new EmptyBorder(0, 10, 0, 10));

        JPanel topInfoActionPanel = new JPanel(new BorderLayout());
        topInfoActionPanel.add(headerInfoPanel = new HeaderInfoPanel(headerInfo), BorderLayout.NORTH);
        topInfoActionPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(topInfoActionPanel, BorderLayout.NORTH);

        topPanel = new JPanel(new BorderLayout());
        topPanel.setOpaque(false);
        mainPanel.add(topPanel, BorderLayout.NORTH);

        // ContentPanel
        contentPanel = new JPanel();
        contentPanel.setLayout(new BorderLayout());
        contentPanel.setBorder(new EmptyBorder(0,0,0,0));
        contentPanel.setOpaque(false);
        mainPanel.add(contentPanel, BorderLayout.CENTER);

        add(mainPanel, BorderLayout.CENTER);

        // Borderline
        setBorder(BorderFactory.createLineBorder(BORDERCOLOR));
    }

    private static Color BORDERCOLOR = new Color(215, 220, 228);

//    @Override
//    protected void paintComponent(Graphics g) {
//        super.paintComponent(g);
//        g.setColor(BORDERCOLOR);
//        int h = getHeight();
//        int w = getWidth();
//        g.drawLine(0, 0, w, 0);
//        g.drawLine(0, h, w, h);
//
//    }

    public JButtonPanel getButtonPanel() {
        return buttonPanel;
    }

    public JPanel getTopPanel() {
        return topPanel;
    }

    /**
     * Return the panel for the contents.<br>
     * Default LayoutManager is  the BorderLayout.
     * @return JPanel ContentPanel
     */
    public JPanel getContentPanel() {
        return contentPanel;
    }

    public HeaderInfo getHeaderInfo() {
        return headerInfo;
    }

    public void setHeaderInfo(HeaderInfo headerInfo) {
        headerInfoPanel.setHeaderInfo(headerInfo);
    }

    public void setInnerPanelBorder(Border border) {
        mainPanel.setBorder(border);
    }

    public static class HeaderInfo extends Model {

        private String headerText;
        private String description;
        private Icon icon;
        private Icon smallIcon;

        public HeaderInfo() {
            this("");
        }

        public HeaderInfo(String headerText) {
            this(headerText, "");
        }

        public HeaderInfo(String headerText, String description) {
            this(headerText, description, null, null);
        }

        public HeaderInfo(String headerText, String description, Icon icon, Icon smallIcon) {
            this.headerText = headerText;
            this.description = description;
            this.icon = icon;
            this.smallIcon = smallIcon;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            String old = this.description;
            this.description = description;
            firePropertyChange("description", old, description);
        }

        public String getHeaderText() {
            return headerText;
        }

        public void setHeaderText(String headerText) {
            String old = this.headerText;
            this.headerText = headerText;
            firePropertyChange("headerText", old, headerText);
        }

        public Icon getIcon() {
            return icon;
        }

        public Icon getSmallIcon() {
            return smallIcon;
        }

        public void setIcons(Icon icon, Icon smallIcon) {
            Icon old = this.icon;
            this.icon = icon;
            firePropertyChange("icon", old, icon);
            old = this.smallIcon;
            this.smallIcon = smallIcon;
            firePropertyChange("smallIcon", old, smallIcon);
        }

    }

    private static class HeaderInfoPanel extends JPanel implements PropertyChangeListener {

        private JLabel lHeaderText;
        private JLabel lDescription;
        private JLabel lImage;

        private HeaderInfo headerInfo;

        public HeaderInfoPanel(HeaderInfo headerInfo) {

            this.headerInfo = headerInfo;
            headerInfo.addPropertyChangeListener(this);

            setUI(new HeaderPanelUI());
            int gab = 3;
            setBorder(new EmptyBorder(gab, gab + 5, gab, gab));
            setOpaque(false);

            lHeaderText = new JLabel();
            if(headerInfo.getDescription().isEmpty()) {
                lHeaderText.setFont(getFont().deriveFont(Font.BOLD, (float) 12));
            } else {
                lHeaderText.setFont(getFont().deriveFont(Font.BOLD, (float) 13));
            }
            lHeaderText.setForeground(new Color(56, 61, 65));

            if(headerInfo.getDescription().isEmpty()) {
                lHeaderText.setHorizontalAlignment(JLabel.CENTER);
            }

            lDescription = new JLabel();
            lDescription.setForeground(Color.DARK_GRAY);

            lImage = new JLabel();

            FormLayout layout = new FormLayout(
                    "2dlu, pref, 10dlu, 2dlu, pref:grow",
                    "2dlu, pref, pref, 2dlu, "
            );

            PanelBuilder builder = new PanelBuilder(layout, this);
            CellConstraints cc = new CellConstraints();
            builder.add(lHeaderText, cc.xyw(4, 2, 2));
            builder.add(lDescription, cc.xy(5, 3));
            builder.add(lImage, cc.xywh(2, 2, 1, 2));

            updateHeaderInfo();
        }

        public HeaderInfo getHeaderInfo() {
            return headerInfo;
        }

        public void setHeaderInfo(HeaderInfo headerInfo) {
            this.headerInfo.removePropertyChangeListener(this);
            this.headerInfo = headerInfo;
            this.headerInfo.addPropertyChangeListener(this);
            updateHeaderInfo();
        }

        private void updateHeaderInfo() {
            lHeaderText.setText(headerInfo.getHeaderText());
            lDescription.setText(headerInfo.getDescription());
            lImage.setIcon(headerInfo.getIcon());
        }

        public void propertyChange(PropertyChangeEvent evt) {
            updateHeaderInfo();
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

    private static class HeaderPanelUI extends BasicPanelUI {

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

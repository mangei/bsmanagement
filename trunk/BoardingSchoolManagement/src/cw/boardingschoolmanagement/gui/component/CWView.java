package cw.boardingschoolmanagement.gui.component;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.ContainerAdapter;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.basic.BasicPanelUI;

import com.jgoodies.binding.beans.Model;
import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;
import com.jidesoft.swing.JideSwingUtilities;

import cw.boardingschoolmanagement.app.CWUtils;
import cw.boardingschoolmanagement.extention.point.CWViewExtentionPoint;
import cw.boardingschoolmanagement.manager.GUIManager;
import cw.boardingschoolmanagement.manager.ModulManager;

/**
 *
 * @author ManuelG
 */
public class CWView extends CWPanel {

    private JPanel buttonPanel;
    private CWButtonPanel leftButtonPanel;
    private CWButtonPanel rightButtonPanel;
    private JPanel topPanel;
    private JScrollPane contentScrollPane;
    private JPanel contentPanel;
    private JPanel mainPanel;
    private CWHeaderInfoPanel headerInfoPanel;
    private CWHeaderInfo headerInfo;

    // Alignment of the header
    public static final int LEFT = JLabel.LEFT;
    public static final int CENTER = JLabel.CENTER;
    public static final int RIGHT = JLabel.RIGHT;

    public static enum ButtonPanelPosition {
        LEFT, RIGHT
    };

    public CWView() {
        this(new CWHeaderInfo());
    }

    public CWView(boolean contentScrolls) {
        this(new CWHeaderInfo(), contentScrolls);
    }

    public CWView(String headerText) {
        this(new CWHeaderInfo(headerText));
    }

    public CWView(CWHeaderInfo headerInfo) {
        this(headerInfo, true);
    }

    public CWView(CWHeaderInfo headerInfo, boolean contentScrolls) {
        if(headerInfo == null) {
            throw new NullPointerException("headerInfo is null");
        }

        this.headerInfo = headerInfo;

        setName(headerInfo.getHeaderText());
        setLayout(new BorderLayout());
        setBorder(new EmptyBorder(0, 0, 0, 0));

        int gab = 10;
        mainPanel = new JPanel(new BorderLayout());
        mainPanel.setUI(new CWViewUI());
        mainPanel.setBorder(new EmptyBorder(gab, gab, gab, gab));

        leftButtonPanel = new CWButtonPanel();
        leftButtonPanel.setBorder(new EmptyBorder(0, 10, 0, 10));
        rightButtonPanel = new CWButtonPanel(FlowLayout.RIGHT);
        rightButtonPanel.setBorder(new EmptyBorder(0, 10, 0, 10));

        buttonPanel = new JPanel(new BorderLayout());
        buttonPanel.add(leftButtonPanel, BorderLayout.WEST);
        buttonPanel.add(rightButtonPanel, BorderLayout.CENTER);

        initButtonPanelEventHandling();

        JPanel topInfoActionPanel = CWComponentFactory.createPanel(new BorderLayout());
        topInfoActionPanel.add(headerInfoPanel = new CWHeaderInfoPanel(headerInfo), BorderLayout.NORTH);
        topInfoActionPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(topInfoActionPanel, BorderLayout.NORTH);

        topPanel = new JPanel(new BorderLayout());
        topPanel.setOpaque(false);
        mainPanel.add(topPanel, BorderLayout.NORTH);

        // ContentPanel
        contentPanel = CWComponentFactory.createPanel();
        contentPanel.setLayout(new BorderLayout());
        contentPanel.setBorder(null);

        contentScrollPane = CWComponentFactory.createScrollPane(contentPanel);
        contentScrollPane.setBorder(null);
        
        if(contentScrolls) {
            mainPanel.add(contentScrollPane, BorderLayout.CENTER);
        } else {
            mainPanel.add(contentPanel, BorderLayout.CENTER);
        }
        
        add(mainPanel, BorderLayout.CENTER);

        // Borderline
        setBorder(BorderFactory.createLineBorder(GUIManager.BORDER_COLOR));
    }

    public void dispose() {
        disposeButtonPanelEventHandling();
        headerInfoPanel.dispose();

        leftButtonPanel.removeAll();
        leftButtonPanel.setLayout(null);
        leftButtonPanel.setUI(null);
        rightButtonPanel.removeAll();
        rightButtonPanel.setLayout(null);
        rightButtonPanel.setUI(null);

        this.removeAll();
        this.setLayout(null);
        this.setUI(null);
    }


    private CWButton bMore;
    private CWPopupMenu popMore;

    private void initButtonPanelEventHandling() {
        popMore = CWComponentFactory.createPopupMenu();
        bMore = CWComponentFactory.createButton(new AbstractAction("...", CWUtils.loadIcon("cw/boardingschoolmanagement/images/more.png")) {
            public void actionPerformed(ActionEvent e) {
                popMore.add(new JButton("Drucken"));
                popMore.show(bMore, 0, bMore.getSize().height);
            }
        });
//        rightButtonPanel.add(bMore);

        buttonPanel.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                System.out.println("size: " + e.getComponent().getSize());
                System.out.println("pref: " + e.getComponent().getPreferredSize());
                
                Component comp = e.getComponent();
                int size = comp.getSize().width;
                int pref = comp.getPreferredSize().width;

                if(size < pref) {
                    bMore.setVisible(true);
                    System.out.println("dooo");
                } else {
                    bMore.setVisible(false);
                }
            }
        });

        buttonPanel.addContainerListener(new ContainerAdapter() {

        });
    }

    private void disposeButtonPanelEventHandling() {

    }
    
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

    /**
     * returns the left ButtonPanel
     * @return left ButtonPanel
     */
    public CWButtonPanel getButtonPanel() {
        return getButtonPanel(ButtonPanelPosition.LEFT);
    }

    /**
     * returns the left ButtonPanel
     * @return left ButtonPanel
     */
    public CWButtonPanel getButtonPanel(ButtonPanelPosition position) {
        if(position == ButtonPanelPosition.LEFT) {
            return leftButtonPanel;
        } else if(position == ButtonPanelPosition.RIGHT) {
            return rightButtonPanel;
        }
        return leftButtonPanel;
    }

    /**
     * Add a button to the left ButtonPanel
     * @param button button
     */
    public void addToLeftButtonPanel(JButton button) {
        leftButtonPanel.add(button);
    }

    /**
     * Add a button to the right ButtonPanel
     * @param button button
     */
    public void addToRightButtonPanel(JButton button) {
        rightButtonPanel.add(button);
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

    public JScrollPane getContentScrollPane() {
        return contentScrollPane;
    }

    public CWHeaderInfo getHeaderInfo() {
        return headerInfo;
    }

    public void setHeaderInfo(CWHeaderInfo headerInfo) {
        this.headerInfo = headerInfo;
        setName(headerInfo.getHeaderText());
        headerInfoPanel.setHeaderInfo(headerInfo);
    }

    public void setInnerPanelBorder(Border border) {
        mainPanel.setBorder(border);
    }
    
    protected void loadExtentions() {
    	//super.loadExtentions();
    	
    	loadExtentions(this);
    }
    
    protected final void loadExtentions(CWView view) {
    	
    	// Load extentions with view.getClass()
    	// and call with view object as parameter
    	
    	List<CWViewExtentionPoint> viewExtentions = (List<CWViewExtentionPoint>) 
    		ModulManager.getExtentions(CWViewExtentionPoint.class);
    	
    	for(CWViewExtentionPoint ex: viewExtentions) {
    		
    		// Check base class
    		if(ex.getExtentionViewClass().equals(view.getClass())) {
    			
    			// Execute extention
    			ex.execute(view);
    		}
    	}
    	
    }

    public static class CWHeaderInfo
            extends Model
    {

        private String headerText;
        private String description;
        private Icon icon;
        private Icon smallIcon;

        public CWHeaderInfo() {
            this("");
        }

        public CWHeaderInfo(String headerText) {
            this(headerText, "");
        }

        public CWHeaderInfo(String headerText, String description) {
            this(headerText, description, null, null);
        }

        public CWHeaderInfo(String headerText, String description, Icon icon, Icon smallIcon) {
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

    private static class CWHeaderInfoPanel
            extends CWPanel {

        private JLabel lHeaderText;
        private JLabel lDescription;
        private JLabel lImage;

        private CWHeaderInfo headerInfo;

        private PropertyChangeListener headerInfoChangeListener;

        public CWHeaderInfoPanel(CWHeaderInfo headerInfo) {

            // Build the interface
            setUI(new CWHeaderPanelUI());
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

            lDescription = new JLabel();
            lDescription.setForeground(Color.DARK_GRAY);

            lImage = new JLabel();

            FormLayout layout = new FormLayout(
                    "2dlu, default, 10dlu, 2dlu, default:grow",
                    "2dlu, pref:grow, pref, 2dlu"
            );

            PanelBuilder builder = new PanelBuilder(layout, this);
            CellConstraints cc = new CellConstraints();
            builder.add(lHeaderText, cc.xyw(4, 2, 2));
            builder.add(lDescription, cc.xy(5, 3));
            builder.add(lImage, cc.xywh(2, 2, 1, 2));

            setHeaderInfo(headerInfo);
            
            initEventHandling();
        }

        public void dispose() {
            disposeEventHandling();
        }

        private void initEventHandling() {
            // If a info element is changed, update the header panel
            headerInfo.addPropertyChangeListener(headerInfoChangeListener = new PropertyChangeListener() {
                public void propertyChange(PropertyChangeEvent evt) {
                    CWHeaderInfoPanel.this.updateHeaderInfo();
                }
            });
        }

        private void disposeEventHandling() {
            headerInfo.removePropertyChangeListener(headerInfoChangeListener);
        }

        public CWHeaderInfo getHeaderInfo() {
            return headerInfo;
        }

        public void setHeaderInfo(CWHeaderInfo headerInfo) {
            this.headerInfo = headerInfo;

            // Entferne das HTML-Markup, denn es wird automatisch hinzugefuegt.
            headerInfo.getDescription().toLowerCase().replace("<html>", "");
            headerInfo.getDescription().toLowerCase().replace("</html>", "");
            headerInfo.getDescription().toLowerCase().replace("<body>", "");
            headerInfo.getDescription().toLowerCase().replace("</body>", "");
            
            updateHeaderInfo();
        }

        private void updateHeaderInfo() {
            lHeaderText.setText(headerInfo.getHeaderText());
            lDescription.setText("<html><body>" + headerInfo.getDescription() + "</body></html>");
            lImage.setIcon(headerInfo.getIcon());

            // If the image is heighter than the headline + the description, stretch the headline
            // otherwise the image would be cut
            if( ( lHeaderText.getPreferredSize().getHeight() + lDescription.getPreferredSize().getHeight() ) < lImage.getPreferredSize().getHeight() ) {
                if(headerInfo.getDescription().isEmpty()) {
                    lHeaderText.setPreferredSize(new Dimension((int) lHeaderText.getPreferredSize().getWidth(), (int) lImage.getPreferredSize().getHeight()));
                } else {
                    lDescription.setPreferredSize(new Dimension((int) lDescription.getPreferredSize().getWidth(), (int) lImage.getPreferredSize().getHeight() - (int) lHeaderText.getPreferredSize().getHeight()));
                }
            }

            if(headerInfo.getDescription().isEmpty() && headerInfo.getIcon() == null) {
                lHeaderText.setHorizontalAlignment(JLabel.CENTER);
            } else {
                lHeaderText.setHorizontalAlignment(JLabel.LEFT);
            }

            repaint();
        }
    }

    private class CWViewUI extends BasicPanelUI {

        private final Color bottomBorderColor = new Color(215, 220, 228);
        private final Color lightGrayColor = new Color(234, 237, 241);

        @Override
        public void paint(Graphics g, JComponent c) {

            Rectangle rect = new Rectangle(0, 0, c.getWidth(), c.getHeight());
            CWView view = (CWView) c.getParent();

            // If there are no buttons, than twist the gradient
            if (view.leftButtonPanel.getComponentCount() > 0  || view.rightButtonPanel.getComponentCount() > 0) {
                JideSwingUtilities.fillGradient((Graphics2D) g, rect, lightGrayColor, Color.WHITE, true);
            } else {
                view.buttonPanel.setVisible(false);
                JideSwingUtilities.fillGradient((Graphics2D) g, rect, Color.WHITE, lightGrayColor, true);
            }

        }
    }

    private static class CWHeaderPanelUI extends BasicPanelUI {

        private static final Color bottomBorderColor = new Color(215, 220, 228);
        private static final Color lightGrayColor = new Color(234, 237, 241);

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

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
import java.util.ArrayList;
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
import com.jidesoft.swing.JideTabbedPane;

import cw.boardingschoolmanagement.app.CWUtils;
import cw.boardingschoolmanagement.extention.point.CWIViewExtentionPoint;
import cw.boardingschoolmanagement.gui.CWIPresentationModel;
import cw.boardingschoolmanagement.gui.component.CWComponentFactory.CWComponentContainer;
import cw.boardingschoolmanagement.manager.GUIManager;
import cw.boardingschoolmanagement.manager.ModuleManager;

/**
 *
 * @author Manuel Geier
 */
public class CWView<TPresentationModel extends CWIPresentationModel>
	extends CWPanel {

	private static final String KEY_PANEL_GROW = "KEY_PANEL_GROW";
	private TPresentationModel model;
	private boolean displayPanelsInTabs = false;
    private JPanel buttonPanel;
    private CWButtonPanel leftButtonPanel;
    private CWButtonPanel rightButtonPanel;
    private JPanel topPanel;
    private JScrollPane contentScrollPane;
    private JPanel contentPanel;
    private JPanel mainPanel;
    private CWHeaderInfoPanel headerInfoPanel;
    private CWHeaderInfo headerInfo;
    private JideTabbedPane contentTabs;
    private CWComponentContainer componentContainer;
    private List<CWIViewExtentionPoint> viewExtentions = new ArrayList<CWIViewExtentionPoint>();

    // Alignment of the header
    public static final int LEFT = JLabel.LEFT;
    public static final int CENTER = JLabel.CENTER;
    public static final int RIGHT = JLabel.RIGHT;

    public static enum ButtonPanelPosition {
        LEFT, RIGHT
    };

    public CWView(TPresentationModel model) {
        this(model, true);
    }

    public CWView(TPresentationModel model, boolean contentScrolls) {

        this.model = model;

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
        contentPanel.setLayout(new FormLayout());
        contentPanel.setBorder(null);

        if(contentScrolls) {
        	contentScrollPane = CWComponentFactory.createScrollPane(contentPanel);
            contentScrollPane.setBorder(null);
            mainPanel.add(contentScrollPane, BorderLayout.CENTER);
        } else {
            mainPanel.add(contentPanel, BorderLayout.CENTER);
        }
        
        add(mainPanel, BorderLayout.CENTER);

        // Borderline
        setBorder(BorderFactory.createLineBorder(GUIManager.BORDER_COLOR));
        
        componentContainer = CWComponentFactory.createComponentContainer();
        
        loadViewExtentions();
    }
    
    /**
     * Initializes all components from the view.<br>
     * All subclasses need to call this method at the beginning.
     */
    public void initComponents() {
    	for(CWIViewExtentionPoint ex : viewExtentions) {
    		ex.getView().initComponents();
    	}
    }
    
    /**
     * Builds the view.<br>
     * All subclasses need to call this method at the beginning.
     */
    public void buildView() {
    	for(CWIViewExtentionPoint ex : viewExtentions) {
    		ex.getView().buildView();
    	}
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
        
        componentContainer.dispose();
        
        if(model != null) {
        	model.release();
        }
    }

    public TPresentationModel getModel() {
		return model;
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
//                System.out.println("size: " + e.getComponent().getSize());
//                System.out.println("pref: " + e.getComponent().getPreferredSize());
                
                Component comp = e.getComponent();
                int size = comp.getSize().width;
                int pref = comp.getPreferredSize().width;

                if(size < pref) {
                    bMore.setVisible(true);
//                    System.out.println("dooo");
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
    private JPanel getContentPanel() {
        return contentPanel;
    }
    
    public void addToContentPanel(JComponent comp) {
    	addToContentPanel(comp, false);
    }
    
    public void addToContentPanel(JComponent comp, boolean shouldGrow) {
    	if(comp == null) {
    		throw new NullPointerException("comp is null");
    	}
    	
    	if(displayPanelsInTabs) {
    		addToContentPanelAsTab(comp, shouldGrow);
    	} else {
    		addToContentPanelAsPanel(comp, shouldGrow);
    	}
    	
    }
    
    private void addToContentPanelAsPanel(JComponent comp, boolean shouldGrow) {
    	
    	// If it is an CWView, we add it to the componentContainer, because it also 
    	// needs to be disposed.
    	if(comp instanceof CWView) {
        	componentContainer.addComponent(comp);
    	}
    	
    	comp.setOpaque(false);
    	
    	// set grow attribute
    	if(shouldGrow) {
    		comp.putClientProperty(KEY_PANEL_GROW, Boolean.TRUE);
    	}
    	
    	// Build layout
    	int countComp = contentPanel.getComponentCount();
    	Component[] oldComponents = contentPanel.getComponents();
    	
    	StringBuilder formRowLayoutString = new StringBuilder();;
    	for(int i=0; i<countComp; i++) {
    		
    		JComponent c = (JComponent) oldComponents[i];
    		Object obj = c.getClientProperty(KEY_PANEL_GROW);
    		
    		if(obj instanceof Boolean && ((Boolean) obj) == Boolean.TRUE) {
	    		if(i == 0) {
	        		formRowLayoutString.append("fill:default:grow");
	    		} else {
	        		formRowLayoutString.append(", 4dlu, fill:default:grow");
	    		}
    		} else {
    			if(i == 0) {
	        		formRowLayoutString.append("default");
	    		} else {
	        		formRowLayoutString.append(", 4dlu, default");
	    		}
    		}
    	}
    	
    	if(shouldGrow) {
    		if(countComp == 0) {
        		formRowLayoutString.append("fill:default:grow");
    		} else {
        		formRowLayoutString.append(", 4dlu, fill:default:grow");
    		}
		} else {
			if(countComp == 0) {
        		formRowLayoutString.append("default");
    		} else {
        		formRowLayoutString.append(", 4dlu, default");
    		}
		}
    	
    	FormLayout newLayout = new FormLayout(
                "fill:default:grow",
                formRowLayoutString.toString());

        PanelBuilder builder = new PanelBuilder(newLayout, contentPanel);
        CellConstraints cc = new CellConstraints();

        // Add old components
        for(int i=0; i<countComp; i++) {
        	builder.add(oldComponents[i], cc.xy(1, (i*2+1)));
        }
        
        // Add new component
        builder.add(comp, cc.xy(1, (countComp*2+1)));
    }
    
    private void addToContentPanelAsTab(JComponent comp, boolean shouldGrow) {
    	// Load dynamic components in tabs
    	
    	if(contentTabs == null) {
    		contentTabs = new JideTabbedPane();
    		addToContentPanelAsPanel(contentTabs, true);
    	}
      
    	if(comp instanceof CWView) {
    		contentTabs.addTab(comp.getName(),((CWView) comp).getHeaderInfo().getIcon(), comp);
    	} else {
    		contentTabs.addTab(comp.getName(), comp);
    	}
    	
    }

    public JScrollPane getContentScrollPane() {
        return contentScrollPane;
    }

    public CWHeaderInfo getHeaderInfo() {
        return headerInfo;
    }

    public void setHeaderInfo(CWHeaderInfo headerInfo) {
        this.headerInfo = headerInfo;
        headerInfoPanel.setHeaderInfo(headerInfo);
        
    	if(headerInfo != null) {
	        setName(headerInfo.getHeaderText());
    	}
    }

    public void setInnerPanelBorder(Border border) {
        mainPanel.setBorder(border);
    }
    
    protected CWComponentContainer getComponentContainer() {
    	if(componentContainer == null) {
    		componentContainer = CWComponentFactory.createComponentContainer();
    	}
    	return componentContainer;
    }
    
    private final void loadViewExtentions() {
    	
    	// Load extentions with view.getClass()
    	// and call with view object as parameter
    	
    	List<CWIViewExtentionPoint> allViewExtentions = (List<CWIViewExtentionPoint>) 
    		ModuleManager.getExtentions(CWIViewExtentionPoint.class, this.getClass());
    	
    	for(CWIViewExtentionPoint ex: allViewExtentions) {
    		viewExtentions.add(ex);
    		ex.init(this);
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

            headerInfoChangeListener = new PropertyChangeListener() {
                public void propertyChange(PropertyChangeEvent evt) {
                    CWHeaderInfoPanel.this.updateHeaderInfo();
                }
            };
            
            setHeaderInfo(headerInfo);
            
        }

        public void dispose() {
            disposeEventHandling();
        }

        private void disposeEventHandling() {
        	if(headerInfo != null ) {
        		headerInfo.removePropertyChangeListener(headerInfoChangeListener);
        	}
        }

        public void setHeaderInfo(CWHeaderInfo headerInfo) {
        	
            if(this.headerInfo != headerInfo) {
            	
            	CWHeaderInfo old = this.headerInfo;
            	this.headerInfo = headerInfo;
            	
            	if(headerInfo != null) {
                	
                	if(headerInfo.getDescription().isEmpty()) {
                        lHeaderText.setFont(getFont().deriveFont(Font.BOLD, (float) 12));
                    } else {
                        lHeaderText.setFont(getFont().deriveFont(Font.BOLD, (float) 13));
                    }
                	
    	            // Entferne das HTML-Markup, denn es wird automatisch hinzugefuegt.
    	            headerInfo.getDescription().toLowerCase().replace("<html>", "");
    	            headerInfo.getDescription().toLowerCase().replace("</html>", "");
    	            headerInfo.getDescription().toLowerCase().replace("<body>", "");
    	            headerInfo.getDescription().toLowerCase().replace("</body>", "");
    	            
    	            updateHeaderInfo();
                	setVisible(true);
    	            
                } else {
                	clearHeaderInfo();
                	setVisible(false);
                }
                
            	// change Event
            	if(old != null) {
            		old.removePropertyChangeListener(headerInfoChangeListener);
            	}
            	if(headerInfo != null) {
            		headerInfo.addPropertyChangeListener(headerInfoChangeListener);
            	}
            }
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
        
        private void clearHeaderInfo() {
        	lHeaderText.setText("");
        	lDescription.setText("");
        	lImage.setIcon(null);
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

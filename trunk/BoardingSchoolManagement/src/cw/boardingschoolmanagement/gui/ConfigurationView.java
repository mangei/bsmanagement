package cw.boardingschoolmanagement.gui;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import cw.boardingschoolmanagement.app.CWUtils;
import cw.boardingschoolmanagement.extention.point.IConfigurationExtentionPoint;
import cw.boardingschoolmanagement.gui.component.CWButtonPanel;
import cw.boardingschoolmanagement.gui.component.CWComponentFactory;
import cw.boardingschoolmanagement.gui.component.CWView;
import cw.boardingschoolmanagement.manager.GUIManager;

/**
 *
 * @author ManuelG
 */
public class ConfigurationView
	extends CWView<ConfigurationPresentationModel>
{
    private JButton bSave;
    private JButton bCancel;

    private static final String CARD_KEY = "cardKey";

    public ConfigurationView(ConfigurationPresentationModel model) {
        super(model, false);
    }

    public void initComponents() {
    	super.initComponents();
    	
        bSave               = CWComponentFactory.createButton(getModel().getSaveAction());
        bCancel             = CWComponentFactory.createButton(getModel().getCancelAction());

        getComponentContainer()
                .addComponent(bSave)
                .addComponent(bCancel);
    }

    public void buildView() {
    	super.buildView();
    	
        this.setHeaderInfo(getModel().getHeaderInfo());

        JPanel masterPanel = new JPanel(new BorderLayout());
        
        CWButtonPanel buttonPanel = this.getButtonPanel();
        buttonPanel.add(bSave);
        buttonPanel.add(bCancel);
        this.addToRightButtonPanel(new JButton(new AbstractAction("Drucken", CWUtils.loadIcon("cw/boardingschoolmanagement/images/print.png")) {
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "Drucken");
            }
        }));

        JPanel buttonBarPanel = CWComponentFactory.createPanel();
        buttonBarPanel.setOpaque(false);
        buttonBarPanel.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 0, GUIManager.BORDER_COLOR));
        final CWButtonPanel buttonBar = new CWButtonPanel();
        buttonBar.setOrientation(CWButtonPanel.VERTICAL);
        buttonBar.setBorder(BorderFactory.createEmptyBorder(3, 3, 3, 3));
        buttonBar.setOpaque(false);
        buttonBarPanel.add(buttonBar, BorderLayout.CENTER);
        buttonBar.setMinimumSize(new Dimension(100,100));

        final CardLayout cardLayout = new CardLayout();
        final JPanel cardPanel = CWComponentFactory.createPanel(cardLayout);

        List<IConfigurationExtentionPoint> extentions = getModel().getExtentions();
        for(int i=0, l=extentions.size(); i<l; i++) {
            // Get the component
            IConfigurationExtentionPoint ex = extentions.get(i);

            // Create the button
            JButton button = new JButton(ex.getButtonName());
            getComponentContainer().addComponent(button);
            button.putClientProperty("roundCorners", Boolean.FALSE);

            // Set the right icon
            button.setIcon(ex.getButtonIcon());

            // Add an action to the button
            button.addActionListener(new AbstractAction() {
                public void actionPerformed(ActionEvent e) {
                    // Show the right panel
                    cardLayout.show(cardPanel, ((JButton)e.getSource()).getClientProperty(CARD_KEY).toString());
                    
                    // Deactivate all buttons
                    for(int i=0,l=buttonBar.getComponentCount(); i<l; i++) {
                        ((JButton)buttonBar.getComponent(i)).putClientProperty("active", Boolean.FALSE);
                        ((JButton)buttonBar.getComponent(i)).repaint();
                    }

                    // Activate the button
                    ((JButton)e.getSource()).putClientProperty("active", Boolean.TRUE);
                    ((JButton)e.getSource()).repaint();
                }
            });

            // Activate it if it is the first button
            if(i == 0) {
                button.putClientProperty("active", Boolean.TRUE);
            }

            // Set the 'index' of the button and add it to the buttonbar
            button.putClientProperty(CARD_KEY, i);
            buttonBar.add(button);

            // Add the component to the layout and to the panel with the right 'index'
            cardLayout.addLayoutComponent(ex.getView(), Integer.toString(i));
            cardPanel.add(ex.getView(), Integer.toString(i));
        }

        masterPanel.add(buttonBarPanel, BorderLayout.WEST);
        masterPanel.add(cardPanel, BorderLayout.CENTER);

        this.addToContentPanel(masterPanel);
        
        addToContentPanel(this, true);
    }

    @Override
    public void dispose() {
    	super.dispose();
    }
}

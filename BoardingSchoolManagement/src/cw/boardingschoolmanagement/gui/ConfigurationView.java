package cw.boardingschoolmanagement.gui;

import cw.boardingschoolmanagement.gui.component.JViewPanel;
import cw.boardingschoolmanagement.app.CWComponentFactory;
import cw.boardingschoolmanagement.extentions.interfaces.ConfigurationExtention;
import cw.boardingschoolmanagement.gui.component.JButtonPanel;
import cw.boardingschoolmanagement.interfaces.Disposable;
import cw.boardingschoolmanagement.interfaces.HeaderInfoCallable;
import cw.boardingschoolmanagement.manager.GUIManager;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.util.List;
import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;

/**
 *
 * @author ManuelG
 */
public class ConfigurationView
    implements Disposable
{

    private ConfigurationPresentationModel model;

    private CWComponentFactory.CWComponentContainer componentContainer;
    private JViewPanel panel;
    private JButton bSave;
    private JButton bCancel;

    private static final String CARD_KEY = "cardKey";

    public ConfigurationView(ConfigurationPresentationModel model) {
        this.model = model;
    }

    public void initComponents() {
        bSave               = CWComponentFactory.createButton(model.getSaveAction());
        bCancel             = CWComponentFactory.createButton(model.getCancelAction());

        componentContainer = CWComponentFactory.createCWComponentContainer()
                .addComponent(bSave)
                .addComponent(bCancel);
    }

    public void initEventHandling() {

    }

    public JViewPanel buildPanel() {
        initComponents();

        panel = CWComponentFactory.createViewPanel(model.getHeaderInfo());

        JButtonPanel buttonPanel = panel.getButtonPanel();
        buttonPanel.add(bSave);
        buttonPanel.add(bCancel);

        JPanel buttonBarPanel = CWComponentFactory.createPanel();
        buttonBarPanel.setOpaque(false);
        buttonBarPanel.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 0, GUIManager.BORDER_COLOR));
        final JButtonPanel buttonBar = new JButtonPanel();
        buttonBar.setOrientation(JButtonPanel.VERTICAL);
        buttonBar.setBorder(BorderFactory.createEmptyBorder(3, 3, 3, 3));
        buttonBar.setOpaque(false);
        buttonBarPanel.add(buttonBar, BorderLayout.CENTER);
        buttonBar.setPreferredSize(new Dimension(100,100));
        final CardLayout cardLayout = new CardLayout();
        final JPanel cardPanel = CWComponentFactory.createPanel(cardLayout);

        List<ConfigurationExtention> extentions = model.getExtentions();
        for(int i=0, l=extentions.size(); i<l; i++) {
            // Get the component
            ConfigurationExtention ex = extentions.get(i);

            // Create the button
            JButton button = new JButton(ex.getButtonName());
            componentContainer.addComponent(button);
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

        panel.getContentPanel().add(buttonBarPanel, BorderLayout.WEST);
        panel.getContentPanel().add(cardPanel, BorderLayout.CENTER);

        panel.addDisposableListener(this);

        initEventHandling();

        return panel;
    }

    public void dispose() {
        panel.removeDisposableListener(this);

        componentContainer.dispose();

        model.dispose();
    }
}

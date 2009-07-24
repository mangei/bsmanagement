package cw.boardingschoolmanagement.gui;

import cw.boardingschoolmanagement.gui.component.CWComponentFactory;
import cw.boardingschoolmanagement.extention.point.ConfigurationExtention;
import cw.boardingschoolmanagement.gui.component.CWButtonPanel;
import cw.boardingschoolmanagement.gui.component.CWView;
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
public class ConfigurationView extends CWView
{

    private ConfigurationPresentationModel model;

    private CWComponentFactory.CWComponentContainer componentContainer;
    private JButton bSave;
    private JButton bCancel;

    private static final String CARD_KEY = "cardKey";

    public ConfigurationView(ConfigurationPresentationModel model) {
        super(false);
        this.model = model;

        initComponents();
        buildView();
        initEventHandling();
    }

    private void initComponents() {
        bSave               = CWComponentFactory.createButton(model.getSaveAction());
        bCancel             = CWComponentFactory.createButton(model.getCancelAction());

        componentContainer = CWComponentFactory.createComponentContainer()
                .addComponent(bSave)
                .addComponent(bCancel);
    }

    private void initEventHandling() {

    }

    private void buildView() {
        this.setHeaderInfo(model.getHeaderInfo());

        CWButtonPanel buttonPanel = this.getButtonPanel();
        buttonPanel.add(bSave);
        buttonPanel.add(bCancel);

        JPanel buttonBarPanel = CWComponentFactory.createPanel();
        buttonBarPanel.setOpaque(false);
        buttonBarPanel.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 0, GUIManager.BORDER_COLOR));
        final CWButtonPanel buttonBar = new CWButtonPanel();
        buttonBar.setOrientation(CWButtonPanel.VERTICAL);
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

        this.getContentPanel().add(buttonBarPanel, BorderLayout.WEST);
        this.getContentPanel().add(cardPanel, BorderLayout.CENTER);
    }

    @Override
    public void dispose() {
        componentContainer.dispose();

        model.dispose();
    }
}

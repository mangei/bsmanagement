package cw.boardingschoolmanagement.gui.component;

import com.jidesoft.swing.JideSwingUtilities;
import cw.boardingschoolmanagement.app.CWUtils;
import cw.boardingschoolmanagement.gui.component.JViewPanel.HeaderInfo;
import cw.boardingschoolmanagement.gui.ui.JButtonPanelButtonUI;
import cw.boardingschoolmanagement.interfaces.HeaderInfoCallable;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JPanel;
import cw.boardingschoolmanagement.manager.GUIManager;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import javax.swing.JComponent;
import javax.swing.plaf.basic.BasicPanelUI;

/**
 *
 * @author ManuelG
 */
public class JBackPanel
        extends JPanel
        implements HeaderInfoCallable {

    private String backText;
    private JPanel panel;
    private HeaderInfo headerInfo;

    public JBackPanel(JPanel panel) {
        this(panel, "");
    }

    public JBackPanel(JPanel panel, String backText) {
        if (panel == null) {
            throw new NullPointerException("panel is null");
        }

        if (panel instanceof HeaderInfoCallable) {
            headerInfo = ((HeaderInfoCallable) panel).getHeaderInfo();
        }

        this.setLayout(new BorderLayout());
        this.backText = backText;
        this.panel = panel;

        this.add(panel, BorderLayout.CENTER);

        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        topPanel.setOpaque(false);

        JButton backButton = new JButton(new AbstractAction(backText, CWUtils.loadIcon("cw/boardingschoolmanagement/images/arrow_left.png")) {

            public void actionPerformed(ActionEvent e) {
                GUIManager.changeToLastView();
            }
        });
        backButton.setUI(new JButtonPanelButtonUI());
        backButton.setBorderPainted(false);
        backButton.setContentAreaFilled(false);
        backButton.setOpaque(false);


        topPanel.add(backButton);

        this.add(topPanel, BorderLayout.NORTH);

        this.setUI(new BackPanelUI());
    }

    public JPanel getPanel() {
        return this;
    }

    public HeaderInfo getHeaderInfo() {
        return headerInfo;
    }

    private class BackPanelUI extends BasicPanelUI {

        private final Color bottomBorderColor = new Color(215, 220, 228);
        private final Color lightGrayColor = new Color(234, 237, 241);

        @Override
        public void paint(Graphics g, JComponent c) {

            Rectangle rect = new Rectangle(0, 0, c.getWidth(), c.getHeight());
            JideSwingUtilities.fillGradient((Graphics2D) g, rect, lightGrayColor, Color.WHITE, true);
        }
    }
}

package cw.boardingschoolmanagement.gui.component;

import com.jidesoft.swing.JideSwingUtilities;
import cw.boardingschoolmanagement.app.CWUtils;
import cw.boardingschoolmanagement.gui.ui.JButtonPanelButtonUI;
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
public class CWBackView
        extends CWView
{

    private String backText;
    private JComponent comp;
    private CWHeaderInfo headerInfo;

    public CWBackView(JComponent comp) {
        this(comp, "");
    }

    public CWBackView(JComponent comp, String backText) {
        if (comp == null) {
            throw new NullPointerException("panel is null");
        }

        if (comp instanceof CWView) {
            headerInfo = ((CWView) comp).getHeaderInfo();
        }

        this.setLayout(new BorderLayout());
        this.backText = backText;
        this.comp = comp;

        this.add(comp, BorderLayout.CENTER);

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

    public CWView getPanel() {
        return this;
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

    @Override
    public void dispose() {
        if(comp instanceof CWView) {
            ((CWView)comp).dispose();
        }
    }
}

package cw.boardingschoolmanagement.gui.component;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.plaf.basic.BasicPanelUI;

import com.jidesoft.swing.JideSwingUtilities;

import cw.boardingschoolmanagement.app.CWUtils;
import cw.boardingschoolmanagement.gui.ui.CWButtonPanelButtonUI;
import cw.boardingschoolmanagement.manager.GUIManager;

/**
 *
 * @author ManuelG
 */
public class CWBackView
        extends CWView
{

    private String backText;
    private CWView innerView;
    private CWHeaderInfo headerInfo;

    CWBackView(CWView innerView) {
        this(innerView, "");
    }

    CWBackView(CWView innerView, String backText) {
    	super(null);
        if (innerView == null) {
            throw new NullPointerException("panel is null");
        }

        if (innerView instanceof CWView) {
            headerInfo = ((CWView) innerView).getHeaderInfo();
        }

        this.setLayout(new BorderLayout());
        this.backText = backText;
        this.innerView = innerView;

        this.add(innerView, BorderLayout.CENTER);

        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        topPanel.setOpaque(false);

        JButton backButton = new JButton(new AbstractAction(backText, CWUtils.loadIcon("cw/boardingschoolmanagement/images/arrow_left.png")) {

            public void actionPerformed(ActionEvent e) {
                GUIManager.changeToLastView();
            }
        });
        backButton.setUI(new CWButtonPanelButtonUI());
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
        if(innerView instanceof CWView) {
            ((CWView)innerView).dispose();
        }
    }
}

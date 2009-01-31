package cw.boardingschoolmanagement.gui.component;

import cw.boardingschoolmanagement.app.CWUtils;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JPanel;
import cw.boardingschoolmanagement.manager.GUIManager;

/**
 *
 * @author ManuelG
 */
public class JBackPanel {

    private String backText;
    private JPanel mainPanel;
    private JPanel panel;

    public JBackPanel(JPanel panel) {
        this(panel, "");
    }

    public JBackPanel(JPanel panel, String backText) {
        if(panel == null) throw new NullPointerException("panel is null");

        mainPanel = new JPanel(new BorderLayout());
        this.backText = backText;
        this.panel = panel;

        mainPanel.add(panel, BorderLayout.CENTER);

        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT,0,0));
        topPanel.add(new JButton(new AbstractAction(backText, CWUtils.loadIcon("cw/boardingschoolmanagement/images/arrow_left.png")) {
            public void actionPerformed(ActionEvent e) {
                GUIManager.changeToLastView();
            }
        }));

        mainPanel.add(topPanel, BorderLayout.NORTH);
    }

    public JPanel getPanel() {
        return mainPanel;
    }
}

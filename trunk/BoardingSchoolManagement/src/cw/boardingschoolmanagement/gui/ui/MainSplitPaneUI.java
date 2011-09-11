package cw.boardingschoolmanagement.gui.ui;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JComponent;
import javax.swing.plaf.basic.BasicSplitPaneUI;

/**
 *
 * @author ManuelG
 */
public class MainSplitPaneUI extends BasicSplitPaneUI {

    @Override
    public void paint(Graphics g, JComponent jc) {
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, jc.getWidth(), jc.getHeight());
    }



}

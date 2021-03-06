package cw.boardingschoolmanagement.gui.component;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.ListModel;

import org.jdesktop.swingx.JXList;

/**
 *
 * @author ManuelG
 */
public class CWList extends JXList {
    private String emptyText = "";

    CWList(ListModel dataModel) {
        super(dataModel);
    }

    CWList() {
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if(getModel().getSize() == 0) {
            int x = ( getWidth() - g.getFontMetrics().stringWidth(emptyText) ) / 2;
            int y = ( getHeight() - g.getFontMetrics().getHeight() ) / 2;
            g.setColor(Color.GRAY);
            g.drawString(emptyText, x, y);
        }
    }

    public void setEmptyText(String emptyText) {
        this.emptyText = emptyText;
        repaint();
    }

    public String getEmptyText() {
        return emptyText;
    }

}

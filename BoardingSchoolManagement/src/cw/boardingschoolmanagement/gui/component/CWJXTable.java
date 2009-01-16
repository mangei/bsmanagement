package cw.boardingschoolmanagement.gui.component;

import java.awt.Color;
import java.awt.Graphics;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;
import org.jdesktop.swingx.JXTable;

/**
 *
 * @author ManuelG
 */
public class CWJXTable extends JXTable {
    private String emptyText = "";

    public CWJXTable(TableModel dm, TableColumnModel cm) {
        super(dm, cm);
    }

    public CWJXTable(TableModel dm) {
        super(dm);
    }

    public CWJXTable() {
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if(getModel().getRowCount() == 0) {
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

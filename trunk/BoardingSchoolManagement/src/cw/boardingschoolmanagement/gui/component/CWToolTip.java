package cw.boardingschoolmanagement.gui.component;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.net.URL;

import javax.swing.JComponent;
import javax.swing.JToolTip;
import javax.swing.plaf.basic.BasicToolTipUI;

import com.jidesoft.swing.JideSwingUtilities;

/**
 *
 * @author ManuelG
 */
public class CWToolTip extends JToolTip {

    private String header;
    private String description;
    private URL imgURL;

    CWToolTip() {
        setUI(new CWToolTipUI());
    }

    CWToolTip(String header, String description) {
        this(header,description,null);
    }

    CWToolTip(String header, String description, URL imgURL) {
        this.header = header;
        this.description = description;
        this.imgURL = imgURL;

        setUI(new CWToolTipUI());
    }

    public String getToolTip() {
        StringBuilder builder = new StringBuilder("<html><table><tr><td colspan=\"2\" style=\"font-size:12pt;\"><b>");

        builder.append(header);
        builder.append("</b></td></tr><tr><td valign=\"top\">");
        if(imgURL != null) {
            builder.append("<img src=\"");
            builder.append(imgURL);
            builder.append("\" />");
        }
        builder.append("</td><td style=\"font-size:11pt;\" valign=\"top\">");
        builder.append(description);
        builder.append("</td></tr></table></html>");

        return builder.toString();
    }

    @Override
    public String toString() {
        return header;
    }

    private static final Color lightGrayColor = new Color(234, 237, 241);

    public class CWToolTipUI extends BasicToolTipUI
    {

        public CWToolTipUI() {
        }

        @Override
        public void paint(Graphics g, JComponent c)
        {
            Rectangle rect = new Rectangle(0, 0, c.getWidth(), c.getHeight());
            JideSwingUtilities.fillGradient((Graphics2D) g, rect, Color.WHITE, lightGrayColor, true);
            super.paint(g, c);
        }

    } 

}

package cw.boardingschoolmanagement.gui.component;

import java.awt.Graphics;

import javax.swing.Icon;
import javax.swing.JLabel;

/**
 * A special Label, which shows nothing (empty String) instead of null if the text is null
 * @author ManuelG
 */
public class JNotNullLabel extends JLabel{

    public JNotNullLabel() {
    }

    public JNotNullLabel(Icon image) {
        super(image);
    }

    public JNotNullLabel(Icon image, int horizontalAlignment) {
        super(image, horizontalAlignment);
    }

    public JNotNullLabel(String text) {
        super(text);
    }

    public JNotNullLabel(String text, int horizontalAlignment) {
        super(text, horizontalAlignment);
    }

    public JNotNullLabel(String text, Icon icon, int horizontalAlignment) {
        super(text, icon, horizontalAlignment);
    }

    @Override
    public String toString() {
        String str = super.toString();

        System.out.println("String: " + str);

        if(str == null || str.equals("null")) {
            return "";
        }
        return str;
    }

    @Override
    public String getText() {
        String text =  super.getText();


        System.out.println("Text: " + text);

        if(text == null || text.equals("null")) {
            return "";
        }
        return text;
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
    }



}

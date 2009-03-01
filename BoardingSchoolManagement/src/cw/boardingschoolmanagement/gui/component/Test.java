package cw.boardingschoolmanagement.gui.component;

import java.awt.Dimension;
import java.text.NumberFormat;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;

/**
 *
 * @author Manuel Geier
 */
public class Test
extends JFrame
{

    public static void main(String[] args) {
        new Test().setVisible(true);
    }
    
    public Test() {
        
        JFormattedTextField tf = new JFormattedTextField(NumberFormat.getInstance());
        getContentPane().add(tf);
        
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(new Dimension(200,200));
    }
    
}

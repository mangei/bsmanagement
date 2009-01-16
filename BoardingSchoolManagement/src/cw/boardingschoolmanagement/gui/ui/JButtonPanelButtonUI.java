package cw.boardingschoolmanagement.gui.ui;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;

import java.awt.Rectangle;
import javax.swing.ButtonModel;
import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.plaf.basic.BasicButtonUI;

/**
 * BlueishButtonUI. <br>
 *  
 */
public class JButtonPanelButtonUI
  extends BasicButtonUI {

  private static Color blueishBackgroundOver = new Color(224, 232, 246);
  private static Color blueishBorderOver = new Color(152, 180, 226);

  private static Color blueishBackgroundSelected = new Color(193, 210, 238);
  private static Color blueishBorderSelected = new Color(49, 106, 197);

  public JButtonPanelButtonUI() {
    super();
  }

  public void installUI(JComponent c) {
    super.installUI(c);

    AbstractButton button = (AbstractButton)c;
    button.setRolloverEnabled(false);
    button.setBorder(BorderFactory.createEmptyBorder(3, 3, 3, 3));
  }

  public void paint(Graphics g, JComponent c) {
//    AbstractButton button = (AbstractButton)c;
//    if (button.getModel().isRollover()
//      || button.getModel().isArmed()
//      || button.getModel().isSelected()) {
//      Color oldColor = g.getColor();
//      if (button.getModel().isSelected()) {
//        g.setColor(blueishBackgroundSelected);
//      } else {
//        g.setColor(blueishBackgroundOver);
//      }
//      g.fillRect(0, 0, c.getWidth() - 1, c.getHeight() - 1);
//
//      if (button.getModel().isSelected()) {
//        g.setColor(blueishBorderSelected);
//      } else {
//        g.setColor(blueishBorderOver);
//      }
//      g.drawRect(0, 0, c.getWidth() - 1, c.getHeight() - 1);
//
//      g.setColor(oldColor);
//    }

    super.paint(g, c);

  }

  protected void paintText(Graphics g, JComponent c, Rectangle textRect, String text) {
        AbstractButton b = (AbstractButton) c;                       
        ButtonModel model = b.getModel();
//        FontMetrics fm = SwingUtilities2.getFontMetrics(c, g);
//        int mnemonicIndex = b.getDisplayedMnemonicIndex();
        
        FontMetrics fm = g.getFontMetrics();

	/* Draw the Text */
	if(model.isEnabled()) {
	    /*** paint the text normally */
	    g.setColor(b.getForeground());
            g.drawString(text, textRect.x + getTextShiftOffset(), textRect.y + fm.getAscent() + getTextShiftOffset());
//	    SwingUtilities2.drawStringUnderlineCharAt(c, g,text, mnemonicIndex,
//					  textRect.x + getTextShiftOffset(),
//					  textRect.y + fm.getAscent() + getTextShiftOffset());
	}
	else {
	    /*** paint the text disabled ***/
//	    g.setColor(b.getBackground());
	    g.setColor(Color.LIGHT_GRAY);
            g.drawString(text, textRect.x + getTextShiftOffset(), textRect.y + fm.getAscent() + getTextShiftOffset());
//	    SwingUtilities2.drawStringUnderlineCharAt(c, g,text, mnemonicIndex,
//					  textRect.x, textRect.y + fm.getAscent());
//	    g.setColor(b.getBackground().darker());
//	    SwingUtilities2.drawStringUnderlineCharAt(c, g,text, mnemonicIndex,
//					  textRect.x, textRect.y + fm.getAscent());
	}
    }
  
}

package cw.boardingschoolmanagement.gui.ui;

import com.l2fprod.common.swing.plaf.ButtonBarButtonUI;
import com.l2fprod.common.swing.plaf.basic.BasicButtonBarUI;

import com.l2fprod.common.swing.plaf.blue.BlueishButtonUI;
import java.awt.Color;

import javax.swing.AbstractButton;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.plaf.ColorUIResource;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.UIResource;

/**
 * BlueishButtonBarUI. <br>
 *  
 */
public class CWButtonPanelUI extends BasicButtonBarUI {

  public static ComponentUI createUI(JComponent c) {
    return new CWButtonPanelUI();
  }

    @Override
  protected void installDefaults() {
    Border b = bar.getBorder();
    if (b == null || b instanceof UIResource) {
        bar.setBorder(UIManager.getBorder("TextPane.border"));
//      bar.setBorder(
//        new BorderUIResource(
//          new CompoundBorder(
//            BorderFactory.createLineBorder(
//              UIManager.getColor("controlDkShadow")),
//            BorderFactory.createEmptyBorder(3, 3, 3, 3))));
    }
    
    Color color = bar.getBackground();
    if (color == null || color instanceof ColorUIResource) {
      bar.setBackground(null);
      bar.setOpaque(false);
//      bar.setBackground(UIManager.getColor("Panel.background"));
    }
    bar.setBorder(null);
  }

    @Override
  public void installButtonBarUI(AbstractButton button) {
    button.setUI(new CWButtonPanelButtonUI(true));
    button.setHorizontalTextPosition(JButton.CENTER);
    button.setVerticalTextPosition(JButton.BOTTOM);
    button.setBorderPainted(false);
    button.setContentAreaFilled(false);
    button.setOpaque(false);
  }

  static class BlueishButtonBarButtonUI
    extends BlueishButtonUI implements ButtonBarButtonUI {
  }

}

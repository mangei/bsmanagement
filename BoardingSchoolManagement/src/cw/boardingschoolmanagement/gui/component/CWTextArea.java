package cw.boardingschoolmanagement.gui.component;

import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JTextArea;
import javax.swing.JToolTip;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.PlainDocument;

import com.jgoodies.validation.view.ValidationResultViewFactory;

/**
 *
 * @author ManuelG
 */
public class CWTextArea extends JTextArea {

    private int maxTextLength;
    private ImageIcon icon;
    private Image iconImage;

    CWTextArea(String text) {
        super(text);
        init();
    }

    CWTextArea() {
    	init();
    }

    CWTextArea(int maxTextLength) {
        this.maxTextLength = maxTextLength;
        init();
    }
    
    private void init() {
    	setIcon(ValidationResultViewFactory.getErrorIcon());
    	setToolTipText(new CWToolTip("Header", "Description").getToolTip());
    }

    @Override
    public void paintComponent(Graphics g) {
    	super.paintComponent(g);
    	if(iconImage != null) {
    		g.drawImage(iconImage, (int) (getWidth()-icon.getIconWidth()-2), 2, null);
    	}
    }
    
    public void setIcon(ImageIcon icon) {
    	this.icon = icon;
		this.iconImage = icon.getImage();
	}
    
    public ImageIcon getIcon() {
		return icon;
	}

    @Override
    protected Document createDefaultModel() {
        return new PlainDocument() {

            @Override
            public void insertString(int offs, String str, AttributeSet a) throws BadLocationException {

                // Check the maximum length
                if (maxTextLength > -1 && getLength() >= maxTextLength) {
                    return;
                }

                super.insertString(offs, str, a);
            }
        };
    }

    public int getMaxTextLength() {
        return maxTextLength;
    }

    public void getMaxTextLength(int maxTextLength) {
        this.maxTextLength = maxTextLength;
    }
    
    @Override
    public JToolTip createToolTip() {
        return new CWToolTip();
    }
}
